/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.quotation;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author qiyaochen
 */
public class RFQ {
    private String rfqId;
    private String requester;
    private List<Quotation> quotations;

    public RFQ(String rfqId, String requester) {
        this.rfqId = rfqId;
        this.requester = requester;
        this.quotations = new ArrayList<>();
    }

    public String getRfqId() { return rfqId; }
    public String getRequester() { return requester; }
    public List<Quotation> getQuotations() { return quotations; }

    public void addQuotation(Quotation q) {
        quotations.add(q);
    }
}