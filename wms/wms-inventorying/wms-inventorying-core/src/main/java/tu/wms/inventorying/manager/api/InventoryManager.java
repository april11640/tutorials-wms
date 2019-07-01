package tu.wms.inventorying.manager.api;

import tu.wms.framework.model.tuples.Pair;
import tu.wms.inventorying.domain.Inventory;
import tu.wms.inventorying.domain.InventoryGetRequest;
import tu.wms.inventorying.domain.InventoryListRequest;
import tu.wms.inventorying.domain.InventoryLog;

import java.util.Collection;
import java.util.List;

public interface InventoryManager {

    int saveInventory(Inventory inventory, InventoryLog inventoryLog);

    int saveAllInventory(Collection<Inventory> inventoryCollection,
                         Collection<InventoryLog> inventoryLogCollection);

    int updateInventory(Inventory before, Inventory after, InventoryLog inventoryLog);

    int updateAllInventory(Collection<Pair<Inventory, Inventory>> inventoryCollection,
                           Collection<InventoryLog> inventoryLogCollection);

    Inventory getInventory(InventoryGetRequest request);

    Inventory getInventoryWithoutTransaction(InventoryGetRequest request);

    List<Inventory> listInventory(InventoryListRequest request);

}
