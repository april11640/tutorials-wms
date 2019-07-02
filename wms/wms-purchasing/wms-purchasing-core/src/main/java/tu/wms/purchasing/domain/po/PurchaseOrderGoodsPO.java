package tu.wms.purchasing.domain.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderGoodsPO {

    private Long id;
    private Long OrderId;
    private Long goodsId;
    private Integer count;

}
