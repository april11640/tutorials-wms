package tu.wms.inventorying.manager.api;

import tu.wms.inventorying.domain.Inventory;
import tu.wms.inventorying.domain.InventoryGetRequest;

public interface InventoryManager {

    int saveInventory(Inventory inventory);

    int updateInventory(Inventory before, Inventory after);

    Inventory getInventory(InventoryGetRequest request);

    Inventory getInventoryWithoutTransaction(InventoryGetRequest request);

    Inventory getInventoryWithLock(InventoryGetRequest request);

}
