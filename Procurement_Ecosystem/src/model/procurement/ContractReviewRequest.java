package model.procurement;

import model.procurement.Contract;
import model.quotation.Quotation;

public class ContractReviewRequest {
    private Quotation quotation;
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
            throw new IllegalStateException("Contract cannot be created without approval.");
        }
        return new Contract(quotation);
    }
}