package common.dto;

import enums.RequestStatus;
import model.procurement.PurchaseItem;
import model.user.UserAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tisaac
 */
public class PurchaseRequestDTO {
    private String reason;
    private List<PurchaseItemDTO> purchaseItems;

    public PurchaseRequestDTO() {
        this.purchaseItems = new ArrayList<>();
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public List<PurchaseItemDTO> getPurchaseItems() {
        return purchaseItems;
    }

    public void addPurchaseItem(PurchaseItemDTO item) {
        this.purchaseItems.add(item);
    }

    public void removePurchaseItem(PurchaseItemDTO item) {
        this.purchaseItems.remove(item);
    }
}
