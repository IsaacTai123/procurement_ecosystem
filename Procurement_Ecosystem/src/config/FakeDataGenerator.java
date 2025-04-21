package config;

import common.AppContext;
import common.Result;
import controller.VendorController;
import controller.procurement.ProcurementController;
import enums.ApprovalStatus;
import enums.OrganizationType;
import enums.RequestStatus;
import model.procurement.PurchaseRequest;
import model.product.Product;
import model.product.Spec;
import model.quotation.Quotation;
import model.quotation.RFQ;
import model.user.UserAccount;

/**
 * @author tisaac
 */
public class FakeDataGenerator {
    private static boolean initialized = false;

    private FakeDataGenerator() {
        // private constructor 避免被 new
    }

    /**
     * Generate test data only once per runtime.
     */
    public static void generateFakeDataForTest() {
        if (initialized) return;

        FakeDataGenerator instance = new FakeDataGenerator();

        PurchaseRequest pr = instance.fakePR();
        RFQ rfq = instance.fakeRFQ(pr);
        instance.fakeQuotation(rfq);

        initialized = true;
    }

    private PurchaseRequest fakePR() {
        UserAccount currentUsr = AppContext.getUser();

        PurchaseRequest p = new PurchaseRequest("PR-001");
        p.setSender(currentUsr);
        AppContext.getUserEnterprise().getPurchaseRequestList().addPurchaseRequest(p);

        p.addPurchaseItem(
                new Product("Asus Laptop"),
                1, 3.33,
                new Spec("Intel i7", "16GB RAM", "512GB SSD", "Black", "Laptop", "Asus Laptop")
        );

        p.createRequesterStep(currentUsr);

        p.getWorkflowSteps().forEach(step -> {
            OrganizationType type = step.getOrgType();
            if (type == OrganizationType.IT) {
                step.setStatus(ApprovalStatus.APPROVED);
                step.setAssignedUser(AppContext.getNetwork().getUserRegistry().findByUserId("A001").orElse(null));
            } else if (type == OrganizationType.PROCUREMENT) {
                step.setStatus(ApprovalStatus.APPROVED);
                step.setAssignedUser(AppContext.getNetwork().getUserRegistry().findByUserId("A002").orElse(null));
            } else {
                step.setStatus(ApprovalStatus.SUBMITTED);
            }
        });

        p.setStatus(RequestStatus.APPROVED);
        return p;
    }

    private RFQ fakeRFQ(PurchaseRequest pr) {
        RFQ rfq = AppContext.getNetwork().getRfqDirectory().addRFQ(
                pr.getId(),
                pr.getPurchaseItems().getPurchaseItemList()
        );

        ProcurementController.getInstance().handleRFQSubmit(rfq, "ASUS", "2026-10-10", "fake RFQ");
        return rfq;
    }

    private Quotation fakeQuotation(RFQ rfq) {
        Result<Void> r = VendorController.getInstance().handleSubmitQuotation(
                rfq,
                rfq.getVendor(),
                "1000"
        );
        if (!r.isSuccess()) {
            System.out.println("Error: " + r.getMessage());
            return null;
        }

        Quotation qt = rfq.getQuotations().getQuotationList()
                .stream()
                .peek(q -> {
                    q.getWorkflowSteps().forEach(step -> {
                        step.setStatus(ApprovalStatus.APPROVED);
                        step.setAssignedUser(AppContext.getNetwork().getUserRegistry()
                            .findByUserId("A001").orElse(null));
                    });
                    q.setStatus(RequestStatus.COMPLETED);
                })
                .findFirst().orElse(null);

        // print each quotation status inside rfq to see if it is added
        rfq.getQuotations().getQuotationList().forEach(q -> {
            System.out.println("Quotation ID: " + q.getId() + ", Status: " + q.getStatus());
        });


        return qt;
    }
}