package service.procurement;

import common.Result;
import enums.RequestStatus;
import model.procurement.PurchaseItem;
import model.user.UserAccount;
import model.procurement.PurchaseRequest;
import util.ResultUtil;

/**
 * @author tisaac
 */
public class PurchaseRequestService {

    public Result<Void> addPurchaseItem(PurchaseItem item, PurchaseRequest request) {
        // Logic to add a purchase item to the request
        try {
            if (item.getProduct() == null || request == null) {
                throw new IllegalArgumentException("Item's product and request cannot be null");
            }

            if (request.getStatus() != RequestStatus.PENDING) {
                throw new IllegalStateException("Purchase request is not in a valid state for adding items");
            }

            // Add item to the request
            request.getPurchaseItems().add(item);

        } catch (Exception e) {
            // Handle exception
            return ResultUtil.failure("Error adding purchase item: " + e.getMessage());
        }

        return ResultUtil.success("Purchase item added successfully");
    }

    public Result<Void> submitPR(PurchaseRequest purchaseRequest) {
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
            return ResultUtil.failure("Error submitting purchase request: " + e.getMessage());
        }

        // This could involve saving the request to a database or sending it to a queue for processing
        return ResultUtil.success("Purchase request submitted successfully");
    }

    public void reviewRRByProcurement(PurchaseRequest request, UserAccount reviewer, String vendorSuggestion) {}

    public void approveBudgetByFinance(PurchaseRequest request, UserAccount financeUser) {}

    public void requestTechConfirmation(PurchaseRequest request, UserAccount procurementUser) {}

    public void confirmSpecsByIT(PurchaseRequest request, UserAccount itEngineer) {}
}
