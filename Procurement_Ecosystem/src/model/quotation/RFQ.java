/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.quotation;
import enums.RFQStatus;
import model.ecosystem.Enterprise;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import model.procurement.ContractReviewRequest;
import model.procurement.PurchaseRequest;


/**
 *
 * @author qiyaochen
 */
public class RFQ {
    private String id;
    private String linkedPRId;
    private List<Enterprise> vendors;
    private List<Quotation> quotations;
    private LocalDate deadline;
    private RFQStatus status;
    private String remarks;

    public RFQ(String linkedPRId) {
        this.linkedPRId = linkedPRId;
        this.id = linkedPRId; // Use PR ID as RFQ ID for visibility
        this.vendors = new ArrayList<>();
        this.quotations = new ArrayList<>();
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

    public void inviteVendors(List<Enterprise> vendorList) {
        this.vendors = new ArrayList<>(vendorList);
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

    public ContractReviewRequest toContractReviewRequest(Quotation selected) {
        selected.setSelected(true);
        return new ContractReviewRequest(selected);
    }

    public String getLinkedPRId() {
        return linkedPRId;
    }

    public void setSelectedQuotation(Quotation selectedQuotation) {
        quotations.add(selectedQuotation);
    }

    public void setStatus(RFQStatus status){
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id != null ? id : linkedPRId;
    }
}