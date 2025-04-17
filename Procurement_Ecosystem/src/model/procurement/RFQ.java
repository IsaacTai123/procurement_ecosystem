/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.procurement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.ecosystem.Enterprise;
import model.quotation.Quotation;

/**
 *
 * @author linweihong
 */
public class RFQ {
    private PurchaseRequest linkedPR;
    private ArrayList<Enterprise> vendors;
    private List<Quotation> quotations;
    // getters, setters
    
    public RFQ(PurchaseRequest linkedPR) {
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
}
