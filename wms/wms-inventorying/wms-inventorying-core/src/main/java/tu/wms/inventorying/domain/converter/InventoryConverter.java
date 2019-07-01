package tu.wms.inventorying.domain.converter;

import tu.wms.framework.util.BeanUtilsEx;
import tu.wms.inventorying.domain.Inventory;
import tu.wms.inventorying.domain.po.InventoryPO;

public class InventoryConverter {

    public final static InventoryPO toPO(Inventory source) {
        return BeanUtilsEx.copyProperties(source, InventoryPO::new);
    }

    public final static Inventory fromPO(InventoryPO source) {
        return BeanUtilsEx.copyProperties(source, Inventory::new, false);
    }

}
