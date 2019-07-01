package tu.wms.inventorying.dao.api;

import tu.wms.framework.model.tuples.Pair;
import tu.wms.inventorying.domain.InventoryGetRequest;
import tu.wms.inventorying.domain.InventoryListRequest;
import tu.wms.inventorying.domain.po.InventoryPO;

import java.util.Collection;
import java.util.List;

/**
*  @author author
*/
public interface InventoryDao {

    int saveInventory(InventoryPO object);

    int saveAllInventory(Collection<InventoryPO> collection);

    int updateInventory(InventoryPO before, InventoryPO after);

    int updateAllInventory(Collection<Pair<InventoryPO, InventoryPO>> collection);

    InventoryPO getInventory(InventoryGetRequest request);

    List<InventoryPO> listInventory(InventoryListRequest request);

}