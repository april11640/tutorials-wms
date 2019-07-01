package tu.wms.inventorying.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tu.wms.framework.model.Result;
import tu.wms.inventorying.domain.InventoryChangeRequest;
import tu.wms.inventorying.domain.InventoryId;
import tu.wms.inventorying.domain.InventoryOperationTypeEnum;
import tu.wms.inventorying.service.api.InventoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class InventoryControllerImpl {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("inventory/update")
    public Result<?> updateInvertory(@RequestBody InventoryChangeRequest request) {
        inventoryService.updateInventory(request);
        return Result.ok();
    }


    /// ********** tests **********

    @PostMapping("test/inventory/update-lock")
    public Result<?> testUpdateInvertoryLock() {
        List<InventoryChangeRequest> requestList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 3; i++) {
            InventoryChangeRequest request = new InventoryChangeRequest();
            request.setInventoryId(new InventoryId((long)1,(long)1,(long)1,(long)1));
            request.setOperationType(InventoryOperationTypeEnum.ADDITION);
            request.setOperationId(1L);
            request.setBizId(1L);
            request.setCount(1);
            requestList.add(request);
        }
        requestList.parallelStream().forEach((request -> inventoryService.updateInventory(request)));
        return Result.ok();
    }

    @PostMapping("test/inventory/update-parallel")
    public Result<?> testUpdateInvertoryParallel() {
        List<InventoryChangeRequest> requestList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 10000; i++) {
            InventoryChangeRequest request = new InventoryChangeRequest();
            request.setInventoryId(new InventoryId((long)random.nextInt(3),
                    (long)random.nextInt(3),
                    (long)random.nextInt(3),
                    (long)1));
            request.setOperationType(InventoryOperationTypeEnum.ADDITION);
            request.setOperationId(1L);
            request.setBizId(1L);
            request.setCount(1);
            requestList.add(request);
        }
        requestList.parallelStream().forEach((request -> inventoryService.updateInventory(request)));
        return Result.ok();
    }

    @PostMapping("test/inventory/update-collection")
    public Result<?> testUpdateInvertoryCollection() {
        List<InventoryChangeRequest> requestList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            InventoryChangeRequest request = new InventoryChangeRequest();
            request.setInventoryId(new InventoryId((long)random.nextInt(3),
                    (long)random.nextInt(3),
                    (long)random.nextInt(3),
                    (long)1));
            request.setOperationType(InventoryOperationTypeEnum.ADDITION);
            request.setOperationId(1L);
            request.setBizId(1L);
            request.setCount(1);
            requestList.add(request);
        }
        inventoryService.updateInventory(requestList);
        return Result.ok();
    }

}
