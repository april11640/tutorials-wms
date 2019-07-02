package tu.wms.inventorying.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tu.wms.framework.aop.DuplicateKeyCheck;
import tu.wms.framework.model.tuples.Pair;
import tu.wms.inventorying.dao.api.InventoryDao;
import tu.wms.inventorying.dao.api.InventoryLogDao;
import tu.wms.inventorying.domain.Inventory;
import tu.wms.inventorying.domain.InventoryGetRequest;
import tu.wms.inventorying.domain.InventoryListRequest;
import tu.wms.inventorying.domain.InventoryLog;
import tu.wms.inventorying.domain.converter.InventoryConverter;
import tu.wms.inventorying.domain.converter.InventoryLogConverter;
import tu.wms.inventorying.domain.po.InventoryPO;
import tu.wms.inventorying.manager.api.InventoryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class InventoryManagerImpl implements InventoryManager {

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private InventoryLogDao inventoryLogDao;

    @DuplicateKeyCheck
    @Override
    public int saveInventory(Inventory inventory, InventoryLog inventoryLog) {
        int affectedRows = inventoryDao.saveInventory(InventoryConverter.toPO(inventory));
        System.out.println("save affectedRows=" + affectedRows);
        if(affectedRows > 0) {
            inventoryLogDao.saveInventoryLog(InventoryLogConverter.toPO(inventoryLog));
        }
        return affectedRows;
    }

    @DuplicateKeyCheck
    @Override
    public int saveAllInventory(Collection<Inventory> inventoryCollection,
                                Collection<InventoryLog> inventoryLogCollection) {
        int affectedRows = inventoryDao.saveAllInventory(InventoryConverter.toPO(inventoryCollection));
        if(affectedRows > 0) {
            inventoryLogDao.saveAllInventoryLog(InventoryLogConverter.toPO(inventoryLogCollection));
        }
        return affectedRows;
    }

    @Override
    public int updateInventory(Inventory before, Inventory after, InventoryLog inventoryLog) {
        int affectedRows =  inventoryDao.updateInventory(InventoryConverter.toPO(before),
                InventoryConverter.toPO(after));
        System.out.println("update affectedRows=" + affectedRows);
        if(affectedRows > 0) {
            System.out.println("2 update affectedRows=" + inventoryLogDao.saveInventoryLog(InventoryLogConverter.toPO(inventoryLog)));
        }
        return affectedRows;
    }

    @Override
    public int updateAllInventory(Collection<Pair<Inventory, Inventory>> inventoryCollection,
                                  Collection<InventoryLog> inventoryLogCollection) {
        List<Pair<InventoryPO, InventoryPO>> inventoryPOList = new ArrayList<>();
        for(Pair<Inventory, Inventory> pair : inventoryCollection) {
            inventoryPOList.add(new Pair<>(InventoryConverter.toPO(pair.getItem1()),
                    InventoryConverter.toPO(pair.getItem2())));
        }
        int affectedRows =  inventoryDao.updateAllInventory(inventoryPOList);
        if(affectedRows > 0) {
            inventoryLogDao.saveAllInventoryLog(InventoryLogConverter.toPO(inventoryLogCollection));
        }
        return affectedRows;
    }

    @Override
    public Inventory getInventory(InventoryGetRequest request) {
        return InventoryConverter.fromPO(inventoryDao.getInventory(request));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Inventory getInventoryWithoutTransaction(InventoryGetRequest request) {
        return InventoryConverter.fromPO(inventoryDao.getInventory(request));
    }

    @Override
    public List<Inventory> listInventory(InventoryListRequest request) {
        return InventoryConverter.fromPO(inventoryDao.listInventory(request));
    }

}
