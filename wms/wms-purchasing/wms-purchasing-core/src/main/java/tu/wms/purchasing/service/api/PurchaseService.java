package tu.wms.purchasing.service.api;

import tu.wms.purchasing.domain.PurchaseOrder;
import tu.wms.purchasing.domain.SavePurchaseOrderRequest;
import tu.wms.purchasing.domain.ShelvePurchaseOrderRequest;

public interface PurchaseService {

    /**
     * 保存采购单
     *
     * @param savePurchaseOrderRequest
     */
    void savePurchaseOrder(SavePurchaseOrderRequest savePurchaseOrderRequest);

    /**
     * 上架采购单
     *
     * @param shelvePurchaseOrderRequest
     */
    void shelvePurchaseOrder(ShelvePurchaseOrderRequest shelvePurchaseOrderRequest);

}
