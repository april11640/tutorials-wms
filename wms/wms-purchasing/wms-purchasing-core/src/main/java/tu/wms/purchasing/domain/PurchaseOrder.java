package tu.wms.purchasing.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PurchaseOrder {

    private Long id;
    private LocalDateTime creationTime;
    private List<PurchaseOrderGoods> goodsList;

}
