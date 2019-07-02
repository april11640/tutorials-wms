package tu.wms.purchasing.domain.converter;

import tu.wms.framework.util.BeanUtilsEx;
import tu.wms.purchasing.domain.PurchaseOrderAllocation;
import tu.wms.purchasing.domain.po.PurchaseOrderAllocationPO;

import java.util.Collection;
import java.util.List;

public class PurchaseOrderAllocationConverter {

    public final static PurchaseOrderAllocationPO toPO(PurchaseOrderAllocation source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderAllocationPO::new);
    }

    public final static List<PurchaseOrderAllocationPO> toPO(Collection<PurchaseOrderAllocation> source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderAllocationPO::new);
    }

    public final static PurchaseOrderAllocation fromPO(PurchaseOrderAllocationPO source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderAllocation::new, false);
    }

    public final static List<PurchaseOrderAllocation> fromPO(Collection<PurchaseOrderAllocationPO> source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderAllocation::new, false);
    }

}
