package directory;

//import model.procurement.PurchaseRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.procurement.PurchaseOrder;

/**
 * @author alvin
 */
public class PurchaseOrderDirectory {
    private List<PurchaseOrder> purchaseOrderList;

    public PurchaseOrderDirectory() {
        purchaseOrderList = new ArrayList<>();
    }

    public void addPurchaseOrder(PurchaseOrder po) {
        purchaseOrderList.add(po);
    }

    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }
    
    public long countUnassignedLogistics() {
        return purchaseOrderList.stream()
                .filter(po -> po.getLogistics() == null)
                .count();
    }

    public PurchaseOrder findRequestsById(String id) {
        return purchaseOrderList.stream()
                .filter(pr -> pr.getId() != null && pr.getQuotationId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return purchaseOrderList.get(0).getPurchaseItems().get(0).getProduct().getName();
    }
    
}
