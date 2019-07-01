package tu.wms.inventorying.dao.api;

import tu.wms.inventorying.domain.po.InventoryLogPO;

import java.util.Collection;

/**
*  @author author
*/
public interface InventoryLogDao {

    int saveInventoryLog(InventoryLogPO object);

    int saveAllInventoryLog(Collection<InventoryLogPO> collection);

}