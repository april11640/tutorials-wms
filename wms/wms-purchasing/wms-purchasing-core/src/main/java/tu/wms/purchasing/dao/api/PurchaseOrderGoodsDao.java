package tu.wms.purchasing.dao.api;

import tu.wms.purchasing.domain.po.PurchaseOrderGoodsPO;

import java.util.Collection;
import java.util.List;

public interface PurchaseOrderGoodsDao {

    int saveAllPurchaseOrderGoods(Collection<PurchaseOrderGoodsPO> collection);

    List<PurchaseOrderGoodsPO> listPurchaseOrderGoodsByOrderId(Long orderId);

}
