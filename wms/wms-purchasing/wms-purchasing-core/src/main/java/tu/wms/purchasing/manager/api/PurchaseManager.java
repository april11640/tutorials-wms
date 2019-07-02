package tu.wms.purchasing.manager.api;

import tu.wms.purchasing.domain.PurchaseOrder;
import tu.wms.purchasing.domain.PurchaseOrderAllocation;

import java.util.Collection;

public interface PurchaseManager {

    int savePurchaseOrder(PurchaseOrder purchaseOrder);

    PurchaseOrder getPurchaseOrder(Long id);

    int saveAllPurchaseOrderAllocation(Collection<PurchaseOrderAllocation> collection);

}
