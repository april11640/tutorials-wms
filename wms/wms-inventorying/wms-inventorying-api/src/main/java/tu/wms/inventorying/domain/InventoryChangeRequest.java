package tu.wms.inventorying.domain;

public class InventoryChangeRequest {

    private Long operationId;
    private Long bizId;
    private InventoryOperationTypeEnum operationType;
    private InventoryId inventoryId;
    private Integer count;

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

    public InventoryOperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(InventoryOperationTypeEnum operationType) {
        this.operationType = operationType;
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
}
