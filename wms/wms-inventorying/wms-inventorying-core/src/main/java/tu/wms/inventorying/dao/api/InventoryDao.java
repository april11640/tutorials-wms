package tu.wms.inventorying.dao.api;

import tu.wms.inventorying.domain.InventoryGetRequest;
import tu.wms.inventorying.domain.po.InventoryPO;

/**
*  @author author
*/
public interface InventoryDao {

    int saveInventory(InventoryPO object);

    int updateInventory(InventoryPO before, InventoryPO after);

    InventoryPO getInventory(InventoryGetRequest request);

    InventoryPO getInventoryWithLock(InventoryGetRequest request);

}