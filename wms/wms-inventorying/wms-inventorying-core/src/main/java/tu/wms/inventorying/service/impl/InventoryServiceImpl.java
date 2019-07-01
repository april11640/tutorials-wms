package tu.wms.inventorying.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tu.wms.framework.model.tuples.Pair;
import tu.wms.inventorying.domain.*;
import tu.wms.inventorying.manager.api.InventoryManager;
import tu.wms.inventorying.service.api.InventoryService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static tu.wms.framework.model.CommonConstants.DUPLICATE_KEY_AFFECTED_ROWS;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final static Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private InventoryManager inventoryManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateInventory(InventoryChangeRequest inventoryChangeRequest) {
        InventoryId inventoryId = inventoryChangeRequest.getInventoryId();
        InventoryGetRequest inventoryGetRequest = new InventoryGetRequest(inventoryId);
        Inventory inventoryBeforeChange = inventoryManager.getInventory(inventoryGetRequest);
        InventoryOperationTypeEnum operationType = inventoryChangeRequest.getOperationType();
        int affectedRows;
        if (inventoryBeforeChange == null) {
            //库存不存在，尝试保存库存。
            if (!Objects.equals(InventoryOperationTypeEnum.ADDITION, operationType)) {
                throw new InventoryException("库存" + inventoryId + "不存在，不能受理除了增加库存之外的其它库存操作。");
            }
            Inventory inventory = createInventory(inventoryChangeRequest);
            inventory.change(inventoryChangeRequest.getOperationType(), inventoryChangeRequest.getCount());
            affectedRows = inventoryManager.saveInventory(inventory,
                    createInventoryLog(inventoryChangeRequest, inventory));
            if (affectedRows > 0) {
                return;
            } else if(DUPLICATE_KEY_AFFECTED_ROWS == affectedRows) {
                //数据主键冲突，操作改为更新库存。
                inventoryBeforeChange = inventoryManager.getInventoryWithoutTransaction(inventoryGetRequest);
            }
        }
        //基于乐观锁的保护来更新库存
        if(logger.isDebugEnabled()) {
            logger.debug("乐观锁尝试: " + inventoryChangeRequest + " " + inventoryBeforeChange);
        }
        affectedRows = doUpdateInventory(inventoryChangeRequest, inventoryBeforeChange);
        if (affectedRows > 0) {
            return;
        }
        //乐观锁升级为悲观锁
        inventoryBeforeChange = inventoryManager.getInventory(inventoryGetRequest.clone().withLock());
        if(logger.isDebugEnabled()) {
            logger.debug("悲观锁尝试: " + inventoryChangeRequest + " " + inventoryBeforeChange);
        }
        affectedRows = doUpdateInventory(inventoryChangeRequest, inventoryBeforeChange);
        if (affectedRows < 1) {
            throw new InventoryException("尝试更新库存" + inventoryBeforeChange + "失败，请稍后重试");
        }
    }

    private int doUpdateInventory(InventoryChangeRequest inventoryChangeRequest, Inventory inventoryBeforeChange) {
        InventoryOperationTypeEnum operationType = inventoryChangeRequest.getOperationType();
        Integer count = inventoryChangeRequest.getCount();
        Inventory inventoryAfterChange = inventoryBeforeChange.clone();
        inventoryAfterChange.change(operationType, count);
        if(inventoryAfterChange.wasOutOfStock()) {
            throw new OutOfStockException("库存不足：invetory: " + inventoryBeforeChange
                    + ", type: " + operationType + ", change: " + count);
        }
        inventoryAfterChange.setLastModificationTime(LocalDateTime.now());
        return inventoryManager.updateInventory(inventoryBeforeChange, inventoryAfterChange,
                createInventoryLog(inventoryChangeRequest, inventoryAfterChange));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateInventory(Collection<InventoryChangeRequest> collection) {
        if (StringUtils.isEmpty(collection)) {
            return;
        }
        Collection<InventoryChangeRequest> mergeRequestCollection = mergeRequest(collection);
        if(mergeRequestCollection.size() < 6) {
            for(InventoryChangeRequest request : mergeRequestCollection) {
                updateInventory(request);
            }
            return;
        }

        Set<InventoryId> inventoryIdSet = mergeRequestCollection.stream().map(InventoryChangeRequest::getInventoryId)
                .collect(Collectors.toSet());
        List<Inventory> inventoryBeforeChangeList = inventoryManager.listInventory(
                new InventoryListRequest(inventoryIdSet).withLock());
        Map<InventoryId, Inventory> inventoryBeforeChangeMap = null;
        Map<InventoryId, List<InventoryChangeRequest>> saveRequestMap = null, updateRequestMap = null;
        if(CollectionUtils.isEmpty(inventoryBeforeChangeList)) {
            saveRequestMap = mergeRequestCollection.stream().collect(Collectors.groupingBy(
                    InventoryChangeRequest::getInventoryId));
        } else {
            saveRequestMap = new HashMap<>(mergeRequestCollection.size());
            updateRequestMap = new HashMap<>(mergeRequestCollection.size());
            inventoryBeforeChangeMap = inventoryBeforeChangeList.stream()
                    .collect(Collectors.toMap(Inventory::getInventoryId, e -> e));
            InventoryId inventoryId;
            for (InventoryChangeRequest request : mergeRequestCollection) {
                Inventory inventoryBeforeChange = inventoryBeforeChangeMap.get(inventoryId = request.getInventoryId());
                if(inventoryBeforeChange != null) {
                    updateRequestMap.computeIfAbsent(inventoryId, (key) -> new ArrayList<>()).add(request);
                } else {
                    saveRequestMap.computeIfAbsent(inventoryId, (key) -> new ArrayList<>()).add(request);
                }
            }
        }
        if(!CollectionUtils.isEmpty(saveRequestMap)) {
            boolean flag;
            Inventory inventory = null;
            Map<InventoryId, Inventory> saveInventoryMap = new HashMap<>(saveRequestMap.size());
            List<InventoryLog> inventoryLogList = new ArrayList<>();
            for(Map.Entry<InventoryId, List<InventoryChangeRequest>> entry : saveRequestMap.entrySet()) {
                flag = false;
                for(InventoryChangeRequest request : entry.getValue()) {
                    if(InventoryOperationTypeEnum.ADDITION.equals(request.getOperationType())) {
                        flag = true;
                    }
                    inventory = saveInventoryMap.computeIfAbsent(request.getInventoryId(),
                            (key) -> createInventory(request));
                    inventoryLogList.add(createInventoryLog(request, inventory));
                    inventory.change(request.getOperationType(), request.getCount());
                }
                if(!flag) {
                    throw new InventoryException("库存" + entry.getKey() + "不存在，要求至少有一个库存操作是增加库存的。");
                }
                if(inventory.wasOutOfStock()) {
                    throw new OutOfStockException("保存时库存不足: " + entry.getValue());
                }
            }
            int affectedRows = inventoryManager.saveAllInventory(saveInventoryMap.values(), inventoryLogList);
            if(DUPLICATE_KEY_AFFECTED_ROWS == affectedRows) {
                throw new InventoryException("保存时数据库键值冲突，请重新尝试");
            }
        }
        if(!CollectionUtils.isEmpty(updateRequestMap)) {
            List<Pair<Inventory, Inventory>> updateInventoryList = new ArrayList<>();
            Map<InventoryId, Inventory> inventoryAfterChangeMap = new HashMap<>(updateRequestMap.size());
            List<InventoryLog> inventoryLogList = new ArrayList<>();
            Inventory inventory = null;
            Map<InventoryId, Inventory> inventoryBeforeChangeMap0 = inventoryBeforeChangeMap;
            for(Map.Entry<InventoryId, List<InventoryChangeRequest>> entry : updateRequestMap.entrySet()) {
                for(InventoryChangeRequest request : entry.getValue()) {
                    inventory = inventoryAfterChangeMap.computeIfAbsent(request.getInventoryId(),
                            (key) -> {
                        Inventory inventoryBeforeChange = inventoryBeforeChangeMap0.get(request.getInventoryId());
                        Inventory inventoryAfterChange = inventoryBeforeChange.clone();
                        inventoryAfterChange.setLastModificationTime(LocalDateTime.now());
                        updateInventoryList.add(new Pair<>(inventoryBeforeChange, inventoryAfterChange));
                        return inventoryAfterChange;
                    });
                    inventoryLogList.add(createInventoryLog(request, inventory));
                    inventory.change(request.getOperationType(), request.getCount());
                }
                if(inventory.wasOutOfStock()) {
                    throw new OutOfStockException("更新时库存不足: " + entry.getValue());
                }
            }
            inventoryManager.updateAllInventory(updateInventoryList, inventoryLogList);
        }
    }

    private Collection<InventoryChangeRequest> mergeRequest(Collection<InventoryChangeRequest> collection) {
        Map<InventoryChangeRequest, InventoryChangeRequest> requestMap = new HashMap<>(collection.size());
        for (InventoryChangeRequest request : collection) {
            requestMap.compute(request,
                    (key, oldValue) -> {
                        if(oldValue == null) {
                            return request;
                        } else {
                            oldValue.addCount(request.getCount());
                            return oldValue;
                        }
                    });
        }
        return requestMap.values();
    }

    private Inventory createInventory(InventoryChangeRequest inventoryChangeRequest) {
        Inventory inventory = new Inventory(inventoryChangeRequest.getInventoryId());

        inventory.setLastModificationTime(LocalDateTime.now());
        return inventory;
    }

    private InventoryLog createInventoryLog(InventoryChangeRequest inventoryChangeRequest, Inventory inventory) {
        InventoryLog inventoryLog = new InventoryLog()
                .build(inventoryChangeRequest)
                .build(inventory)
                .build(inventoryChangeRequest.getCount());
        inventoryLog.setCreationTime(LocalDateTime.now());
        return inventoryLog;
    }

}
