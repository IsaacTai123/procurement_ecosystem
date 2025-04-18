package service.procurement;

import common.Result;
import common.Session;
import common.dto.PurchaseItemDTO;
import common.dto.PurchaseRequestDTO;
import directory.GlobalUserAccountDirectory;
import enums.ApprovalStatus;
import enums.RequestStatus;
import model.ecosystem.Network;
import model.procurement.PurchaseItem;
import model.product.Spec;
import model.user.UserAccount;
import model.procurement.PurchaseRequest;
import model.workqueue.WorkflowStep;
import util.ResultUtil;

/**
 * @author tisaac
 */
public class PurchaseRequestService {

    private UserAccount currentUser;
    private Network network;

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

            if (pr.getReason() == null || pr.getReason().isEmpty()) {
                throw new IllegalArgumentException("Must provide a description for the purchase request");
            }

        } catch (Exception e) {
            // Handle exception
            return ResultUtil.failure("Error submitting purchase request: " + e.getMessage());
        }

        // Get current user & network
        currentUser = Session.getCurrentUser();
        network = Session.getCurrentNetwork();

        GlobalUserAccountDirectory allUserDir = network.getGlobalUserAccountDir();

        // Change the workflowStep to submitted
        Result<Void> result = pr.advanceToNextStep(allUserDir, pr.getReason(), ApprovalStatus.SUBMITTED);

        // This could involve saving the request to a database or sending it to a queue for processing
        return result;
    }

    public void reviewRRByProcurement(PurchaseRequest pr, PurchaseItem item, Spec spec) {
        addSpecDetails(pr, item, spec);

        // send it back to the IT organization for confirmation by changing the receiver to the IT manager
        // TODO: Set the receiver to the appropriate IT manager
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
