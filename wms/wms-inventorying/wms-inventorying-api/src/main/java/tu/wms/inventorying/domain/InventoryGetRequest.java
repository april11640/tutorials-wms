package tu.wms.inventorying.domain;

public class InventoryGetRequest {

    private InventoryId inventoryId;

    public InventoryGetRequest() {

    }

    public InventoryGetRequest(Long coordX, Long coordY, Long coordZ, Long goodsId) {
        this(new InventoryId(coordX, coordY, coordZ, goodsId));
    }

    public InventoryGetRequest(InventoryId inventoryId) {
        this.inventoryId = inventoryId;
    }

    public InventoryId getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(InventoryId inventoryId) {
        this.inventoryId = inventoryId;
    }

}
