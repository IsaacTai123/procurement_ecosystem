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
    private ArrayList<PurchaseOrder> purchaseOrderList;

    public PurchaseOrderDirectory() {
        purchaseOrderList = new ArrayList<>();
    }

    public void addPurchaseOrder(PurchaseOrder po) {
        purchaseOrderList.add(po);
    }

    public ArrayList<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }
    
    public long countUnassignedLogistics() {
        return purchaseOrderList.stream()
                .filter(po -> po.getLogistics() == null)
                .count();
    }

    

//    public List<PurchaseOrder> getRequestsBySenderId(String userId) {
//        List<PurchaseOrder> result = new ArrayList<>();
//        return purchaseOrderList.stream()
//                .filter(pr -> pr.getSender() != null && pr.getSender().getUserId().equals(userId))
//                .collect(Collectors.toList());
//    }

    @Override
    public String toString() {
        return purchaseOrderList.get(0).getPurchaseItems().get(0).getProduct().getName();
    }
    
    
    
}
