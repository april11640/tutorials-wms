package tu.wms.inventorying.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tu.wms.inventorying.domain.*;
import tu.wms.inventorying.manager.api.InventoryManager;
import tu.wms.inventorying.service.api.InventoryService;

import java.time.LocalDateTime;
import java.util.*;

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
        Integer changeCount = inventoryChangeRequest.getCount();
        int affectedRows;
        if (inventoryBeforeChange == null) {
            //库存不存在，尝试保存库存。
            if (!Objects.equals(InventoryOperationTypeEnum.ADDITION, operationType)) {
                throw new InventoryException("库存" + inventoryId + "不存在，不能受理除了增加库存之外的其它库存操作。");
            }
            Inventory inventory = new Inventory(inventoryId);
            inventory.change(operationType, changeCount);
            inventory.setLastModificationTime(LocalDateTime.now());
            inventory.setInventoryLog(createInventoryLog(inventoryChangeRequest, inventory));
            affectedRows = inventoryManager.saveInventory(inventory);
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
        inventoryBeforeChange = inventoryManager.getInventoryWithLock(inventoryGetRequest);
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
        inventoryAfterChange.setInventoryLog(createInventoryLog(inventoryChangeRequest, inventoryAfterChange));
        return inventoryManager.updateInventory(inventoryBeforeChange, inventoryAfterChange);
    }

    private InventoryLog createInventoryLog(InventoryChangeRequest inventoryChangeRequest, Inventory inventory) {
        InventoryLog inventoryLog = new InventoryLog()
                .build(inventoryChangeRequest).build(inventory).build(inventoryChangeRequest.getCount());
        inventoryLog.setCreationTime(LocalDateTime.now());
        inventory.setInventoryLog(inventoryLog);
        return inventoryLog;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateInventory(Collection<InventoryChangeRequest> collection) {
        if (StringUtils.isEmpty(collection)) {
            return;
        }
        List<InventoryChangeRequest> mergeList = new ArrayList<>();
        for (InventoryChangeRequest request : collection) {
            mergeList.add((request));
        }
        for (InventoryChangeRequest request : mergeList) {
            updateInventory(request);
        }
    }

}
