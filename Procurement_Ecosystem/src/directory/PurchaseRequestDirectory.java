package directory;

import model.procurement.PurchaseRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tisaac
 */
public class PurchaseRequestDirectory {
    private List<PurchaseRequest> purchaseRequestList;

    public PurchaseRequestDirectory() {
        purchaseRequestList = new ArrayList<>();
    }

    public void addPurchaseRequest(PurchaseRequest pr) {
        purchaseRequestList.add(pr);
    }

    public List<PurchaseRequest> getPurchaseRequestList() {
        return purchaseRequestList;
    }

    public List<PurchaseRequest> getRequestsBySenderId(String userId) {
        List<PurchaseRequest> result = new ArrayList<>();
        return purchaseRequestList.stream()
                .filter(pr -> pr.getSender() != null && pr.getSender().getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public PurchaseRequest findRequestById(String id) {
        return purchaseRequestList.stream()
                .filter(pr -> pr.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
