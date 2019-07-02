package tu.wms.purchasing.domain.po;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderAllocationPO {

    private Long id;
    private Long OrderId;
    private Long coordX;
    private Long coordY;
    private Long coordZ;
    private Long goodsId;
    private Integer count;

}
