package tu.wms.purchasing.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderGoods {

    private Long id;
    private Long OrderId;
    private Long goodsId;
    private Integer count;

}
