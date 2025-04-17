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


/**
 *
 * @author qiyaochen
 */
public class RFQ {
    private String id;
    private String linkedPRId;
    private ArrayList<Enterprise> vendors;
    private List<Quotation> quotations;
    private LocalDate deadline;
    private RFQStatus status;
    private String remarks;

    public RFQ(String linkedPRId) {
        this.linkedPRId = linkedPRId;
        this.vendors = new ArrayList<>();
        this.quotations = new ArrayList<>();
    }

    public String getId() {
        return id;
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