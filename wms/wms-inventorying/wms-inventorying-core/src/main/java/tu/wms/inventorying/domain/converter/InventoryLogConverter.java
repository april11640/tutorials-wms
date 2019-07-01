package tu.wms.inventorying.domain.converter;

import tu.wms.framework.util.BeanUtilsEx;
import tu.wms.inventorying.domain.InventoryLog;
import tu.wms.inventorying.domain.po.InventoryLogPO;

public class InventoryLogConverter {

    public final static InventoryLogPO toPO(InventoryLog source) {
        return BeanUtilsEx.copyProperties(source, InventoryLogPO::new);
    }

}
