package tu.wms.inventorying.domain;

import tu.wms.framework.model.CodeEnum;

/**
 * 库存操作分类
 */
public enum InventoryOperationTypeEnum implements CodeEnum {

    /**
     * 增加: count(+), allot(0)
     */
    ADDITION(1),
    /**
     * 取消增加: count(-), allot(0)
     */
    CANCEL_ADDITION(2),
    /**
     * 扣减: count(-), allot(-)
     */
    SUBTRACTION(3),
    /**
     * 取消扣减：count(+), allot(+)
     */
    CANCEL_SUBTRACTION(4),
    /**
     * 分配: count(0), allot(+)
     */
    ALLOCATION(5),
    /**
     * 释放: count(0), allot(-)
     */
    FREE(6);

    private int code;

    InventoryOperationTypeEnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }


    public static void main(String[] args) {
        InventoryOperationTypeEnum[] values = InventoryOperationTypeEnum.values();
        for(InventoryOperationTypeEnum value : values) {
            System.out.println(value.name() + ", " + value.getCode() + ", " + value.ordinal());
        }
    }

}
