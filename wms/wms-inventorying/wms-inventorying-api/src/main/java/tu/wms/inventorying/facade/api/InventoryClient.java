package tu.wms.inventorying.facade.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tu.wms.framework.model.Result;
import tu.wms.framework.model.tuples.Triple;
import tu.wms.inventorying.domain.InventoryChangeRequest;

import java.util.Collection;
import java.util.List;

@FeignClient("${feign.wms-inventorying-service.name}")
public interface InventoryClient {

    @PostMapping("inventory/update")
    Result<?> updateInvertory(InventoryChangeRequest request);

    @PostMapping("inventory/update-all")
    Result<?> updateAllInvertory(Collection<InventoryChangeRequest> collection);

    @GetMapping("inventory/available-location")
    Result<List<Triple<Long, Long, Long>>> listAvailableLocation();

}
