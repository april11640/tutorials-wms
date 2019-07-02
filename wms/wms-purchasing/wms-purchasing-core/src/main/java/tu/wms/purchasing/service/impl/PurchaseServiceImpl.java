package tu.wms.purchasing.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tu.wms.framework.model.tuples.Triple;
import tu.wms.inventorying.domain.InventoryChangeRequest;
import tu.wms.inventorying.domain.InventoryId;
import tu.wms.inventorying.domain.InventoryOperationTypeEnum;
import tu.wms.inventorying.facade.api.InventoryClient;
import tu.wms.purchasing.domain.*;
import tu.wms.purchasing.manager.api.PurchaseManager;
import tu.wms.purchasing.service.api.PurchaseService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseManager purchaseManager;

    @Autowired
    private InventoryClient inventoryClient;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePurchaseOrder(SavePurchaseOrderRequest savePurchaseOrderRequest) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCreationTime(LocalDateTime.now());
        List<PurchaseOrderGoods> goodsList = new ArrayList<>();
        purchaseOrder.setGoodsList(goodsList);
        for(SavePurchaseOrderRequest.Goods goods : savePurchaseOrderRequest.getGoodsList()) {
            PurchaseOrderGoods purchaseOrderGoods = new PurchaseOrderGoods();
            goodsList.add(purchaseOrderGoods);
            purchaseOrderGoods.setGoodsId(goods.getGoodsId());
            purchaseOrderGoods.setCount(goods.getCount());
        }
        purchaseManager.savePurchaseOrder(purchaseOrder);
    }

    @GlobalTransactional
//    @Transactional(rollbackFor = Exception.class)
    @Override
    public void shelvePurchaseOrder(ShelvePurchaseOrderRequest shelvePurchaseOrderRequest) {
        PurchaseOrder purchaseOrder = purchaseManager.getPurchaseOrder(shelvePurchaseOrderRequest.getPurchaseOrderId());
        if(purchaseOrder == null) {
            return;
        }
        List<PurchaseOrderGoods> purchaseOrderGoodsList = purchaseOrder.getGoodsList();
        List<Triple<Long, Long, Long>> availableLocationList = inventoryClient.listAvailableLocation().getData();
        Random random = new Random();
        List<PurchaseOrderAllocation> purchaseOrderAllocationList = new ArrayList<>();
        List<InventoryChangeRequest> inventoryChangeRequestList = new ArrayList<>();
        for(PurchaseOrderGoods purchaseOrderGoods : purchaseOrderGoodsList) {
            PurchaseOrderAllocation purchaseOrderAllocation = new PurchaseOrderAllocation();
            purchaseOrderAllocation.setOrderId(purchaseOrder.getId());
            Triple<Long, Long, Long> location = availableLocationList.get(random.nextInt(availableLocationList.size()));
            purchaseOrderAllocation.setCoordX(location.getItem1());
            purchaseOrderAllocation.setCoordY(location.getItem2());
            purchaseOrderAllocation.setCoordZ(location.getItem3());
            purchaseOrderAllocation.setGoodsId(purchaseOrderGoods.getGoodsId());
            purchaseOrderAllocation.setCount(purchaseOrderGoods.getCount());
            purchaseOrderAllocationList.add(purchaseOrderAllocation);

            InventoryChangeRequest inventoryChangeRequest = new InventoryChangeRequest();
            inventoryChangeRequest.setOperationId(random.nextLong());
            inventoryChangeRequest.setBizId(purchaseOrder.getId());
            inventoryChangeRequest.setOperationType(InventoryOperationTypeEnum.ADDITION);
            inventoryChangeRequest.setInventoryId(new InventoryId(location.getItem1(),
                    location.getItem2(), location.getItem3(), purchaseOrderGoods.getGoodsId()));
            inventoryChangeRequest.setCount(purchaseOrderGoods.getCount());
            inventoryChangeRequestList.add(inventoryChangeRequest);
        }
        purchaseManager.saveAllPurchaseOrderAllocation(purchaseOrderAllocationList);
        inventoryClient.updateAllInvertory(inventoryChangeRequestList);
    }

}
