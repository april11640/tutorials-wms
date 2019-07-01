package tu.wms.inventorying.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author author
 */
public class Inventory implements Serializable, Cloneable {

    private static final long serialVersionUID = 1561692686335L;

    private InventoryId inventoryId;
    private Integer count;
    private Integer allocationCount;
    private LocalDateTime lastModificationTime;

    public Inventory() {
        initCount();
    }

    public Inventory(InventoryId inventoryId) {
        this();
        this.inventoryId = inventoryId;
    }

    private void initCount() {
        count = 0;
        allocationCount = 0;
    }

    @Override
    public Inventory clone() {
        setCountZeroIfNull();
        Inventory target = new Inventory();
        target.setInventoryId(inventoryId);
        target.setCount(count);
        target.setAllocationCount(allocationCount);
        target.setLastModificationTime(lastModificationTime);
        return target;
    }

    public int getAvailableCount() {
        if(count != null && allocationCount != null) {
            return count - allocationCount;
        }
        return 0;
    }

    public void setCountZeroIfNull() {
        if(count == null) {
            count = 0;
        }
        if(allocationCount == null) {
            allocationCount = 0;
        }
    }

    public boolean wasOutOfStock() {
        return count < 0 || allocationCount < 0;
    }

    public void change(InventoryOperationTypeEnum operationType, Integer changeCount) {
        Objects.requireNonNull(operationType);
        if (changeCount == null) {
            return;
        }
        switch (operationType) {
            case ADDITION:
                add(changeCount);
                break;
            case CANCEL_ADDITION:
                cancelAdd(changeCount);
                break;
            case SUBTRACTION:
                subtract(changeCount);
                break;
            case CANCEL_SUBTRACTION:
                cancelSubtract(changeCount);
                break;
            case ALLOCATION:
                allot(changeCount);
                break;
            case FREE:
                free(changeCount);
                break;
            default:
                throw new InventoryException("error operation type: " + operationType);
        }
    }

    public void add(Integer changeCount) {
        if (changeCount == null) {
            return;
        }
        count += changeCount;
    }

    public void cancelAdd(Integer changeCount) {
        if (changeCount == null) {
            return;
        }
        count -= changeCount;
    }

    public void subtract(Integer changeCount) {
        if (changeCount == null) {
            return;
        }
        count -= changeCount;
        allocationCount -= changeCount;
    }

    public void cancelSubtract(Integer changeCount) {
        if (changeCount == null) {
            return;
        }
        count += changeCount;
        allocationCount += changeCount;
    }

    public void allot(Integer changeCount) {
        if (changeCount == null) {
            return;
        }
        allocationCount += changeCount;
    }

    public void free(Integer changeCount) {
        if (changeCount == null) {
            return;
        }
        allocationCount -= changeCount;
    }

    public InventoryId getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(InventoryId inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setAllocationCount(Integer allocationCount) {
        this.allocationCount = allocationCount;
    }

    public Integer getAllocationCount() {
        return this.allocationCount;
    }

    public void setLastModificationTime(LocalDateTime lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public LocalDateTime getLastModificationTime() {
        return this.lastModificationTime;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", count=" + count +
                ", allocationCount=" + allocationCount +
                ", lastModificationTime=" + lastModificationTime +
                '}';
    }
}
