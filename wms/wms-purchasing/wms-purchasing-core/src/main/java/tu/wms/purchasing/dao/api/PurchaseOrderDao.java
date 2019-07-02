package tu.wms.purchasing.dao.api;

import tu.wms.purchasing.domain.po.PurchaseOrderPO;

public interface PurchaseOrderDao {

    int savePurchaseOrder(PurchaseOrderPO purchaseOrderPO);

    PurchaseOrderPO getPurchaseOrder(Long id);

}
