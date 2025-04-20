package model.quotation;
import enums.RFQStatus;
import model.ecosystem.Enterprise;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import model.procurement.ContractReviewRequest;
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
    private List<Quotation> quotations;
    private List<PurchaseItem>  purchaseItems;
    private LocalDate deadline;
    private RFQStatus status;
    private String remarks;

    public RFQ(String linkedPRId, List<PurchaseItem> purchaseItems) {
        this.linkedPRId = linkedPRId;
        this.id = IdGenerateUtil.generateWorkRequestId();
        this.quotations = new ArrayList<>();
        this.purchaseItems = purchaseItems;
        this.status = RFQStatus.DRAFT;
    }

    public String getId() { 
        return id; 
    }

    public String getRemarks() {
        if (remarks != null) return remarks;
        StringBuilder sb = new StringBuilder();
        for (Quotation q : quotations) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(q.getVendor().getName());
        }
        return sb.toString();
    }

    public void addQuotation(Quotation quotation) {
        this.quotations.add(quotation);
    }

    public List<Quotation> getQuotations() {
        return this.quotations;
    }

    public Quotation getBestQuotation() {
        return quotations.stream()
                .min(Comparator.comparingDouble(Quotation::getPrice))
                .orElse(null);
    }

    public Quotation getSelectedQuotation() {
        for (Quotation q : quotations) {
            if (q.isSelected()) {
                return q;
            }
        }
        return null; // 如果没有被选中的 quotation
    }

    public ContractReviewRequest toContractReviewRequest(Quotation selected) {
        selected.setSelected(true);
        return new ContractReviewRequest(selected);
    }

    public String getLinkedPRId() {
        return linkedPRId;
    }

    public void setSelectedQuotation(Quotation selectedQuotation) {
        for (Quotation q : quotations) {
            q.setSelected(false); //
        }
        selectedQuotation.setSelected(true); // 选中传入的 quotation
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

    @Override
    public String toString() {
        return id != null ? id : linkedPRId;
    }
}