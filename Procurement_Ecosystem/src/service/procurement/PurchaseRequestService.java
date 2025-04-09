package service.procurement;

import enums.RequestStatus;
import model.user.UserAccount;
import model.workqueue.PurchaseRequest;

/**
 * @author tisaac
 */
public class PurchaseRequestService {

    public void submitPurchaseRequest(PurchaseRequest purchaseRequest) {
        // Logic to submit the purchase request
        try {
            if (purchaseRequest == null) {
                throw new IllegalArgumentException("Purchase request cannot be null");
            }

            if (purchaseRequest.getStatus() != RequestStatus.PENDING) {
                throw new IllegalStateException("Purchase request is not in a valid state for submission");
            }
        } catch (Exception e) {
            // Handle exception
            System.out.println("Error submitting purchase request: " + e.getMessage());
        }

        // This could involve saving the request to a database or sending it to a queue for processing
    }

    public void reviewRequestByProcurement(PurchaseRequest request, UserAccount reviewer, String vendorSuggestion) {}

    public void approveBudgetByFinance(PurchaseRequest request, UserAccount financeUser) {}

    public void requestTechConfirmation(PurchaseRequest request, UserAccount procurementUser) {}

    public void confirmSpecsByIT(PurchaseRequest request, UserAccount itEngineer) {}
}
