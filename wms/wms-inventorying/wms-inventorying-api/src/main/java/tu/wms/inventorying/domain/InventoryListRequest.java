package tu.wms.inventorying.domain;

import tu.wms.framework.model.LockableQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InventoryListRequest extends LockableQuery {

    private List<InventoryId> inventoryIdList;

    public InventoryListRequest() {

    }

    public InventoryListRequest(Collection<InventoryId> inventoryIdCollection) {
        inventoryIdList = new ArrayList<>();
        inventoryIdList.addAll(new ArrayList<>(inventoryIdCollection));
    }

    public List<InventoryId> getInventoryIdList() {
        return inventoryIdList;
    }

    public void setInventoryIdList(List<InventoryId> inventoryIdList) {
        this.inventoryIdList = inventoryIdList;
    }

}
