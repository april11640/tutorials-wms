package tu.wms.purchasing.domain.converter;

import tu.wms.framework.util.BeanUtilsEx;
import tu.wms.purchasing.domain.PurchaseOrderGoods;
import tu.wms.purchasing.domain.po.PurchaseOrderGoodsPO;

import java.util.Collection;
import java.util.List;

public class PurchaseOrderGoodsConverter {

    public final static PurchaseOrderGoodsPO toPO(PurchaseOrderGoods source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderGoodsPO::new);
    }

    public final static List<PurchaseOrderGoodsPO> toPO(Collection<PurchaseOrderGoods> source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderGoodsPO::new);
    }

    public final static PurchaseOrderGoods fromPO(PurchaseOrderGoodsPO source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderGoods::new, false);
    }

    public final static List<PurchaseOrderGoods> fromPO(Collection<PurchaseOrderGoodsPO> source) {
        return BeanUtilsEx.copyProperties(source, PurchaseOrderGoods::new, false);
    }

}
