package tu.wms.inventorying.domain;

public class InventoryException extends RuntimeException {

    public InventoryException() {
        super();
    }

    public InventoryException(String message) {
        super(message);
    }

}
