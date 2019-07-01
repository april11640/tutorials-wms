package tu.wms.inventorying.domain;

import java.util.Objects;

public class InventoryChangeRequest {

    private Long operationId;
    private Long bizId;
    private InventoryOperationTypeEnum operationType;
    private InventoryId inventoryId;
    private Integer count;

    public void addCount(Integer value) {
        if(value == null) {
            return ;
        }
        if(count == null) {
            count = value;
        } else {
            count += value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        InventoryChangeRequest request = (InventoryChangeRequest) o;
        return Objects.equals(operationId, request.operationId) &&
                Objects.equals(bizId, request.bizId) &&
                operationType == request.operationType &&
                Objects.equals(inventoryId, request.inventoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, bizId, operationType, inventoryId);
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
