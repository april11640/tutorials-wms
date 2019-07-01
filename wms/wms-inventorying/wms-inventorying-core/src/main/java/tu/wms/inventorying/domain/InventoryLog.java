package tu.wms.inventorying.domain;

import java.time.LocalDateTime;

public class InventoryLog {

    private Long id;
    private InventoryOperationTypeEnum operationType;
    private Long operationId;
    private Long bizId;
    private InventoryId inventoryId;
    private Integer count;
    private Integer allocationCount;
    private Integer changeCount;
    private LocalDateTime creationTime;

    public InventoryLog build(InventoryChangeRequest inventoryChangeRequest) {
        this.operationType = inventoryChangeRequest.getOperationType();
        this.operationId = inventoryChangeRequest.getOperationId();
        this.bizId = inventoryChangeRequest.getBizId();
        this.inventoryId = inventoryChangeRequest.getInventoryId();
        return this;
    }

    public InventoryLog build(Inventory inventory) {
        Integer count0;
        this.count = (count0 = inventory.getCount()) == null ? 0 : count0;
        this.allocationCount = (count0 = inventory.getAllocationCount()) == null ? 0 : count0;
        return this;
    }

    public InventoryLog build(Integer changeCount) {
        this.changeCount = changeCount == null ? 0 : changeCount;
        return this;
    }

    @Override
    public String toString() {
        return "InventoryLog{" +
                "id=" + id +
                ", operationType=" + operationType +
                ", operationId=" + operationId +
                ", bizId=" + bizId +
                ", inventoryId=" + inventoryId +
                ", count=" + count +
                ", allocationCount=" + allocationCount +
                ", changeCount=" + changeCount +
                ", creationTime=" + creationTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryOperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(InventoryOperationTypeEnum operationType) {
        this.operationType = operationType;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

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

    public Integer getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(Integer changeCount) {
        this.changeCount = changeCount;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
