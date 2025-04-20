package util;

import enums.EnterpriseType;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseRequest;
import model.quotation.Quotation;
import model.quotation.RFQ;

import java.util.ArrayList;
import java.util.List;

public class TestRFQGenerator {
    private static List<RFQ> cachedRFQs = null;

    public static List<RFQ> generateTestRFQs() {
        if (cachedRFQs == null) {
//            cachedRFQs = new ArrayList<>();
//            cachedRFQs.add(createSampleRFQ1());
//            cachedRFQs.add(createSampleRFQ2());
//            cachedRFQs.add(createSampleRFQ3());
        }
        return cachedRFQs;
    }

    public static List<RFQ> getCachedRFQs() {
        return generateTestRFQs();
    }

    public static RFQ createSampleRFQ1() {
        PurchaseRequest pr = new PurchaseRequest("PR-001");
//        RFQ rfq = new RFQ(pr.getId());
//
//
//        rfq.addQuotation(new Quotation(new Enterprise("Vendor A", EnterpriseType.VENDOR), "Fast delivery", 1000.0, "Description A1"));
//        rfq.addQuotation(new Quotation(new Enterprise("Vendor B", EnterpriseType.VENDOR), "Includes accessories", 950.0, "Description A2"));
//
//        return rfq;
        return null;
    }
//
//    public static RFQ createSampleRFQ2() {
//        PurchaseRequest pr = new PurchaseRequest("PR-002");
//        RFQ rfq = new RFQ(pr.getId());
//
//        rfq.addQuotation(new Quotation(new Enterprise("Vendor X", EnterpriseType.VENDOR), "Economical option", 800.0, "Description B1"));
//        rfq.addQuotation(new Quotation(new Enterprise("Vendor Y", EnterpriseType.VENDOR), "Premium service", 1200.0, "Description B2"));
//
//        return rfq;
//    }
//
//    public static RFQ createSampleRFQ3() {
//        PurchaseRequest pr = new PurchaseRequest("PR-003");
//        RFQ rfq = new RFQ(pr.getId());
//        rfq.addQuotation(new Quotation(new Enterprise("Vendor Alpha", EnterpriseType.VENDOR), "Same day shipping", 1100.0, "Description C1"));
//        rfq.addQuotation(new Quotation(new Enterprise("Vendor Beta", EnterpriseType.VENDOR), "Extended warranty", 1150.0, "Description C2"));
//        rfq.addQuotation(new Quotation(new Enterprise("Vendor Gamma", EnterpriseType.VENDOR), "Free installation", 1180.0, "Description C3"));
//
//        return rfq;
//    }
}