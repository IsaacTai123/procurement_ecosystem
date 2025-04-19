package util;

import model.ecosystem.Enterprise;
import model.procurement.PurchaseRequest;
import model.quotation.Quotation;
import model.workqueue.RFQRequest;

import java.util.ArrayList;
import java.util.List;


public class TestRFQGenerator {

    public static List<RFQRequest> generateTestRFQs() {
        List<RFQRequest> rfqList = new ArrayList<>();

        rfqList.add(createSampleRFQ1());
        rfqList.add(createSampleRFQ2());
        rfqList.add(createSampleRFQ3());

        return rfqList;
    }

    public static RFQRequest createSampleRFQ1() {
        PurchaseRequest pr = new PurchaseRequest("PR-001");
        RFQRequest rfq = new RFQRequest(pr);

        rfq.addQuotation(new Quotation("Q001", new Enterprise("Vendor A"), "Fast delivery", 1000.0));
        rfq.addQuotation(new Quotation("Q002", new Enterprise("Vendor B"), "Includes accessories", 950.0));
        return rfq;
    }

    public static RFQRequest createSampleRFQ2() {
        PurchaseRequest pr = new PurchaseRequest("PR-002");
        RFQRequest rfq = new RFQRequest(pr);

        rfq.addQuotation(new Quotation("Q101", new Enterprise("Vendor X"), "Economical option", 800.0));
        rfq.addQuotation(new Quotation("Q102", new Enterprise("Vendor Y"), "Premium service", 1200.0));
        return rfq;
    }

    public static RFQRequest createSampleRFQ3() {
        PurchaseRequest pr = new PurchaseRequest("PR-003");
        RFQRequest rfq = new RFQRequest(pr);

        rfq.addQuotation(new Quotation("Q201", new Enterprise("Vendor Alpha"), "Same day shipping", 1100.0));
        rfq.addQuotation(new Quotation("Q202", new Enterprise("Vendor Beta"), "Extended warranty", 1150.0));
        rfq.addQuotation(new Quotation("Q203", new Enterprise("Vendor Gamma"), "Free installation", 1180.0));
        return rfq;
    }
}