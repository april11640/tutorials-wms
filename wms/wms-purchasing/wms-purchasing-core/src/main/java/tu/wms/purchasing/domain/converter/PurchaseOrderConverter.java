package tu.wms.purchasing.domain.converter;

import tu.wms.framework.util.BeanUtilsEx;
import tu.wms.purchasing.domain.PurchaseOrder;
import tu.wms.purchasing.domain.po.PurchaseOrderPO;

import java.util.Collection;
import java.util.List;

public class PurchaseOrderConverter {

    public final static PurchaseOrderPO toPO(PurchaseOrder source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderPO::new);
    }

    public final static List<PurchaseOrderPO> toPO(Collection<PurchaseOrder> source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderPO::new);
    }

    public final static PurchaseOrder fromPO(PurchaseOrderPO source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrder::new, false);
    }

    public final static List<PurchaseOrder> fromPO(Collection<PurchaseOrderPO> source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrder::new, false);
    }

}
