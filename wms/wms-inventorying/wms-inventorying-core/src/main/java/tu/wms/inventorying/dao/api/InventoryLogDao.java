package tu.wms.inventorying.dao.api;

import tu.wms.inventorying.domain.po.InventoryLogPO;

/**
*  @author author
*/
public interface InventoryLogDao {

    int saveInventoryLog(InventoryLogPO object);

}