package tu.wms.inventorying.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tu.wms.framework.model.Result;
import tu.wms.framework.model.tuples.Triple;
import tu.wms.inventorying.domain.InventoryChangeRequest;
import tu.wms.inventorying.domain.InventoryId;
import tu.wms.inventorying.domain.InventoryOperationTypeEnum;
import tu.wms.inventorying.facade.api.InventoryClient;
import tu.wms.inventorying.service.api.InventoryService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@RestController
public class InventoryControllerImpl implements InventoryClient {

    @Autowired
    private InventoryService inventoryService;

    @Override
    public Result<?> updateInvertory(@RequestBody InventoryChangeRequest request) {
        inventoryService.updateInventory(request);
        return Result.ok();
    }

    @Override
    public Result<?> updateAllInvertory(@RequestBody Collection<InventoryChangeRequest> collection) {
        inventoryService.updateInventory(collection);
        return Result.ok();
    }

    @Override
    public Result<List<Triple<Long, Long, Long>>> listAvailableLocation() {
        List<Triple<Long, Long, Long>> tripleList = new ArrayList<>();
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                for(int z = 0; z < 3; z++) {
                    tripleList.add(new Triple<>((long)x, (long)y, (long)z));
                }
            }
        }
        return Result.ok(tripleList);
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
