package tu.wms.purchasing.dao.api;

import tu.wms.purchasing.domain.po.PurchaseOrderAllocationPO;

import java.util.Collection;

public interface PurchaseOrderAllocationDao {

    int saveAllPurchaseOrderAllocation(Collection<PurchaseOrderAllocationPO> collection);

}
