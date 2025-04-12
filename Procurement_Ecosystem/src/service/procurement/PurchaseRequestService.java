package service.procurement;

import common.NetworkManager;
import common.Result;
import enums.RequestStatus;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.ecosystem.Organization;
import model.procurement.PurchaseItem;
import model.product.Product;
import model.product.Spec;
import model.user.UserAccount;
import model.procurement.PurchaseRequest;
import model.workqueue.ApprovalStep;
import util.ProductUtil;
import util.ResultUtil;

import java.util.List;

/**
 * @author tisaac
 */
public class PurchaseRequestService {

    public Result<Void> submitPR(PurchaseRequest pr) {
        // validate the purchase request
        try {
            if (pr == null) {
                throw new IllegalArgumentException("Purchase request cannot be null");
            }

            if (pr.getStatus() != RequestStatus.PENDING) {
                throw new IllegalStateException("Purchase request is not in a valid state for submission");
            }

            if (pr.getPurchaseItems().getPurchaseItemList().isEmpty()) {
                throw new IllegalArgumentException("Purchase request must have at least one item");
            }

            if (pr.getDescription() == null || pr.getDescription().isEmpty()) {
                throw new IllegalArgumentException("Must provide a description for the purchase request");
            }

        } catch (Exception e) {
            // Handle exception
            return ResultUtil.failure("Error submitting purchase request: " + e.getMessage());
        }

        // Change the current status

        // Set the next reviewer
        pr.setReceiver(null); // TODO: Set the receiver to the appropriate user account

        // TODO: Set the next reviewer "Procurement Manager" of Procurement Organization in ApprovalStep

        // TODO: Update database

        // This could involve saving the request to a database or sending it to a queue for processing
        return ResultUtil.success("Purchase request submitted successfully");
    }

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
            request.getPurchaseItems().getPurchaseItemList().add(item);

            // TODO: Update database

        } catch (Exception e) {
            // Handle exception
            return ResultUtil.failure("Error adding purchase item: " + e.getMessage());
        }

        return ResultUtil.success("Purchase item added successfully");
    }

    public void reviewRRByProcurement(PurchaseRequest pr, PurchaseItem item, Spec spec) {
        addSpecDetails(pr, item, spec);

        // send it back to the IT organization for confirmation by changing the receiver to the IT manager
        pr.setReceiver(null); // TODO: Set the receiver to the appropriate IT manager
    }

    public void confirmSpecsByIT(PurchaseRequest pr) {

    }

    public void requestTechConfirmation(PurchaseRequest pr, UserAccount procurementUser) {
        // Add spec details to the purchase request
    }

    public void approveBudgetByFinance(PurchaseRequest request, UserAccount financeUser) {}

    // procurement organization could add spec details to the purchase request
    public void addSpecDetails(PurchaseRequest pr, PurchaseItem item, Spec spec) {
        try {
            if (pr.getStatus() != RequestStatus.PENDING) {
                throw new IllegalStateException("Purchase request is not in a valid state for adding spec details");
            }

            if (spec == null) {
                throw new IllegalArgumentException("Spec cannot be null");
            }

            // Add spec details to the purchase request
            // TODO: append the spec details to the purchaseItem and update the pr
            item.setSpec(spec);

        } catch (Exception e) {
            // Handle exception
            throw new IllegalArgumentException("Error adding spec details: " + e.getMessage());
        }

    }

    public void loadPurchaseItemsTable() {}
}
