package model.quotation;
import directory.QuotationDirectory;
import enums.RFQStatus;
import model.ecosystem.Enterprise;

import java.time.LocalDate;
import java.util.List;

import model.procurement.PurchaseItem;
import util.IdGenerateUtil;


/**
 *
 * @author qiyaochen
 */
public class RFQ {
    private String id;
    private String linkedPRId;
    private Enterprise vendor;
    private QuotationDirectory quotations;
    private List<PurchaseItem>  purchaseItems;
    private LocalDate deadline;
    private RFQStatus status;
    private String remarks;

    public RFQ(String linkedPRId, List<PurchaseItem> purchaseItems) {
        this.linkedPRId = linkedPRId;
        this.id = IdGenerateUtil.generateIdByActionAndTimestamp("RFQ");
        this.quotations = new QuotationDirectory();
        this.purchaseItems = purchaseItems;
        this.status = RFQStatus.DRAFT;
    }

    public void markAsSent() {
        this.status = RFQStatus.SENT;
    }

    public void markAsReceived() {
        this.status = RFQStatus.RECEIVED;
    }

    public void markAsClosed() {
        this.status = RFQStatus.CLOSED;
    }

    public String getId() { 
        return id; 
    }

    public String getRemarks() {
        if (remarks != null) return remarks;
        StringBuilder sb = new StringBuilder();
        for (Quotation q : quotations.getQuotationList()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(q.getVendor().getName());
        }
        return sb.toString();
    }

    public String getLinkedPRId() {
        return linkedPRId;
    }

    public void setStatus(RFQStatus status){
        this.status = status;
    }

    public RFQStatus getStatus() {
        return this.status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public void setVendor(Enterprise vendor) {
        this.vendor = vendor;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Enterprise getVendor() {
        return vendor;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public QuotationDirectory getQuotations() {
        return quotations;
    }

    public int getQuotationCount() {
        return quotations.getQuotationList().size();
    }

    @Override
    public String toString() {
        return id;
    }
}