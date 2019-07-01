package tu.wms.inventorying.domain;

import tu.wms.framework.model.LockableQuery;

public class InventoryGetRequest extends LockableQuery implements Cloneable {

    private InventoryId inventoryId;


    public InventoryGetRequest() {

    }

    public InventoryGetRequest(Long coordX, Long coordY, Long coordZ, Long goodsId) {
        this(new InventoryId(coordX, coordY, coordZ, goodsId));
    }

    public InventoryGetRequest(InventoryId inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public InventoryGetRequest clone() {
        InventoryGetRequest target = new InventoryGetRequest();
        target.setInventoryId(inventoryId);
        target.setEnableLock(enableLock);
        return target;
    }

    public InventoryId getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(InventoryId inventoryId) {
        this.inventoryId = inventoryId;
    }

}
