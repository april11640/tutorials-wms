package tu.wms.purchasing.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tu.wms.purchasing.dao.api.PurchaseOrderAllocationDao;
import tu.wms.purchasing.dao.api.PurchaseOrderDao;
import tu.wms.purchasing.dao.api.PurchaseOrderGoodsDao;
import tu.wms.purchasing.domain.PurchaseOrder;
import tu.wms.purchasing.domain.PurchaseOrderAllocation;
import tu.wms.purchasing.domain.converter.PurchaseOrderAllocationConverter;
import tu.wms.purchasing.domain.converter.PurchaseOrderConverter;
import tu.wms.purchasing.domain.converter.PurchaseOrderGoodsConverter;
import tu.wms.purchasing.domain.po.PurchaseOrderGoodsPO;
import tu.wms.purchasing.domain.po.PurchaseOrderPO;
import tu.wms.purchasing.manager.api.PurchaseManager;

import java.util.Collection;
import java.util.List;

@Service
public class PurchaseManagerImpl implements PurchaseManager {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;
    @Autowired
    private PurchaseOrderGoodsDao purchaseOrderGoodsDao;
    @Autowired
    private PurchaseOrderAllocationDao purchaseOrderAllocationDao;

    @Override
    public int savePurchaseOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrderPO purchaseOrderPO = PurchaseOrderConverter.toPO(purchaseOrder);
        int affectedRows = purchaseOrderDao.savePurchaseOrder(purchaseOrderPO);
        if(affectedRows > 0) {
            List<PurchaseOrderGoodsPO> purchaseOrderGoodsPOList = PurchaseOrderGoodsConverter.toPO(purchaseOrder.getGoodsList());
            for(PurchaseOrderGoodsPO purchaseOrderGoodsPO : purchaseOrderGoodsPOList) {
                purchaseOrderGoodsPO.setOrderId(purchaseOrderPO.getId());
            }
            purchaseOrderGoodsDao.saveAllPurchaseOrderGoods(purchaseOrderGoodsPOList);
        }
        return affectedRows;
    }

    @Override
    public PurchaseOrder getPurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = PurchaseOrderConverter.fromPO(purchaseOrderDao.getPurchaseOrder(id));
        if(purchaseOrder != null) {
            purchaseOrder.setGoodsList(PurchaseOrderGoodsConverter.fromPO(
                    purchaseOrderGoodsDao.listPurchaseOrderGoodsByOrderId(id)));
        }
        return purchaseOrder;
    }

    @Override
    public int saveAllPurchaseOrderAllocation(Collection<PurchaseOrderAllocation> collection) {
        return purchaseOrderAllocationDao.saveAllPurchaseOrderAllocation(
                PurchaseOrderAllocationConverter.toPO(collection));
    }

}
