package tu.wms.inventorying.domain.po;

import tu.wms.inventorying.domain.InventoryId;

import java.time.LocalDateTime;

public class InventoryPO {

    private InventoryId inventoryId;
    private Integer count;
    private Integer allocationCount;
    private LocalDateTime lastModificationTime;

    public InventoryId getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(InventoryId inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAllocationCount() {
        return allocationCount;
    }

    public void setAllocationCount(Integer allocationCount) {
        this.allocationCount = allocationCount;
    }

    public LocalDateTime getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(LocalDateTime lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }
}
