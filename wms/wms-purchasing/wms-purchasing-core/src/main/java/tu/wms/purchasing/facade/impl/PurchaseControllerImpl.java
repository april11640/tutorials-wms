package tu.wms.purchasing.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tu.wms.framework.model.Result;
import tu.wms.purchasing.domain.SavePurchaseOrderRequest;
import tu.wms.purchasing.domain.ShelvePurchaseOrderRequest;
import tu.wms.purchasing.service.api.PurchaseService;

@RestController
public class PurchaseControllerImpl {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("purchase-order/save")
    public Result<?> savePurchaseOrder(@RequestBody SavePurchaseOrderRequest request) {
        purchaseService.savePurchaseOrder(request);
        return Result.ok();
    }

    @PostMapping("purchase-order/shelve")
    public Result<?> shelvePurchaseOrder(@RequestBody ShelvePurchaseOrderRequest request) {
        purchaseService.shelvePurchaseOrder(request);
        return Result.ok();
    }

}
