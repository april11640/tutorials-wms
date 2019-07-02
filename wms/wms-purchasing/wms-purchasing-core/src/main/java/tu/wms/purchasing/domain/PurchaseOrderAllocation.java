package tu.wms.purchasing.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderAllocation {

    private Long id;
    private Long OrderId;
    private Long coordX;
    private Long coordY;
    private Long coordZ;
    private Long goodsId;
    private Integer count;

}
