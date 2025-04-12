package common.dto;

import enums.RequestStatus;
import model.procurement.PurchaseItem;
import model.user.UserAccount;

import java.util.Date;
import java.util.List;

/**
 * @author tisaac
 */
public class PurchaseRequestDTO {
    private String id;
    private String sender;
    private Date requestDate;

    private String description;
    private List<PurchaseItemDTO> purchaseItems;

    public PurchaseRequestDTO(String id, String sender, Date requestDate, String description, List<PurchaseItemDTO> purchaseItems) {
        this.id = id;
        this.sender = sender;
        this.requestDate = requestDate;
        this.description = description;
        this.purchaseItems = purchaseItems;
    }

    public String getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public String getDescription() {
        return description;
    }

    public List<PurchaseItemDTO> getPurchaseItems() {
        return purchaseItems;
    }
}
