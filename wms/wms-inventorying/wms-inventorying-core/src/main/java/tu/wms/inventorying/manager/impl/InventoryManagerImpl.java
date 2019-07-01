package tu.wms.inventorying.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tu.wms.framework.aop.DuplicateKeyCheck;
import tu.wms.inventorying.dao.api.InventoryDao;
import tu.wms.inventorying.dao.api.InventoryLogDao;
import tu.wms.inventorying.domain.Inventory;
import tu.wms.inventorying.domain.InventoryGetRequest;
import tu.wms.inventorying.domain.converter.InventoryConverter;
import tu.wms.inventorying.domain.converter.InventoryLogConverter;
import tu.wms.inventorying.manager.api.InventoryManager;

@Service
public class InventoryManagerImpl implements InventoryManager {

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private InventoryLogDao inventoryLogDao;

    @DuplicateKeyCheck
    @Override
    public int saveInventory(Inventory inventory) {
        int affectedRows = inventoryDao.saveInventory(InventoryConverter.toPO(inventory));
        if(affectedRows > 0) {
            inventoryLogDao.saveInventoryLog(InventoryLogConverter.toPO(inventory.getInventoryLog()));
        }
        return affectedRows;
    }

    @Override
    public int updateInventory(Inventory before, Inventory after) {
        int affectedRows =  inventoryDao.updateInventory(InventoryConverter.toPO(before), InventoryConverter.toPO(after));
        if(affectedRows > 0) {
            inventoryLogDao.saveInventoryLog(InventoryLogConverter.toPO(after.getInventoryLog()));
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
    public Inventory getInventoryWithLock(InventoryGetRequest request) {
        return InventoryConverter.fromPO(inventoryDao.getInventoryWithLock(request));
    }

}
