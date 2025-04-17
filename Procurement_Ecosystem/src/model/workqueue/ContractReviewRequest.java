/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import model.quotation.Quotation;
import model.procurement.Contract;

/**
 *
 * @author linweihong
 */
public class ContractReviewRequest {
    private final Quotation quotation;
    private boolean approved;

    public ContractReviewRequest(Quotation quotation) {
        this.quotation = quotation;
        this.approved = false;
    }

    public void approve() {
        this.approved = true;
    }

    public boolean isApproved() {
        return approved;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public Contract createContract() {
        if (!approved) {
            throw new IllegalStateException("Quotation not approved yet.");
        }
        return new Contract(quotation);
    }
}
