/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseRequest;
import model.quotation.Quotation;
import model.procurement.ContractReviewRequest;

/**
 *
 * @author linweihong
 */
public class RFQRequest extends WorkRequest {
    private PurchaseRequest linkedPR;
    private ArrayList<Enterprise> vendors;
    private List<Quotation> quotations;
    // getters, setters
    
    public RFQRequest(PurchaseRequest linkedPR) {
        this.linkedPR = linkedPR;
        this.vendors = new ArrayList<>();
        this.quotations = new ArrayList<>();
    }
    
    public void inviteVendors(List<Enterprise> vendorList) {
        this.vendors = new ArrayList<>(vendorList);
    }
    
    public void addQuotation(Quotation quotation) {
        this.quotations.add(quotation);
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
}
