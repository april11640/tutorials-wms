package tu.wms.inventorying.service.api;

import tu.wms.inventorying.domain.InventoryChangeRequest;

import java.util.Collection;

public interface InventoryService {

    void updateInventory(InventoryChangeRequest request);

    void updateInventory(Collection<InventoryChangeRequest> collection);

}
