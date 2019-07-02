package tu.wms.purchasing.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SavePurchaseOrderRequest {

    private List<Goods> goodsList;

    @Getter
    @Setter
    public static class Goods {
        private Long goodsId;
        private Integer count;
    }

}
