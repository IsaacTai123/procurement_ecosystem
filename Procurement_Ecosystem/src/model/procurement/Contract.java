package model.procurement;

import java.util.Date;
import java.util.UUID;
import model.quotation.Quotation;

public class Contract {
    private String contractId;
    private Quotation quotation;
    private Date effectiveDate;

    public Contract(Quotation quotation) {
        this.contractId = UUID.randomUUID().toString();
        this.quotation = quotation;
        this.effectiveDate = new Date();
    }

    public String getContractId() {
        return contractId;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }
}