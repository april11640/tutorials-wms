package tu.wms.inventorying.domain.converter;

import tu.wms.framework.util.BeanUtilsEx;
import tu.wms.inventorying.domain.InventoryLog;
import tu.wms.inventorying.domain.po.InventoryLogPO;

import java.util.Collection;
import java.util.List;

public class InventoryLogConverter {

    public final static InventoryLogPO toPO(InventoryLog source) {
        return BeanUtilsEx.copyProperties(source, InventoryLogPO::new);
    }

    public final static List<InventoryLogPO> toPO(Collection<InventoryLog> source) {
        return BeanUtilsEx.copyProperties(source, InventoryLogPO::new);
    }

}
