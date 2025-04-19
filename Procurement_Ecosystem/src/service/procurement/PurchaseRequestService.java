package service.procurement;

import common.Result;
import common.Session;
import directory.GlobalUserAccountDirectory;
import directory.PurchaseRequestDirectory;
import enums.ApprovalStatus;
import enums.RequestStatus;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.procurement.PurchaseItem;
import model.product.Spec;
import model.user.UserAccount;
import model.procurement.PurchaseRequest;
import util.ResultUtil;

import java.util.List;

/**
 * @author tisaac
 */
public class PurchaseRequestService {

    private UserAccount currentUser;
    private Network currentNetwork;
    private PurchaseRequestDirectory purchaseRequestList;

    public Result<Void> submitPR(PurchaseRequest pr) {
        // validate the status of purchase request
        try {
            if (pr.getStatus() != RequestStatus.PENDING) {
                throw new IllegalStateException("Purchase request is not in a valid state for submission");
            }

        } catch (Exception e) {
            return ResultUtil.failure("Error submitting purchase request: " + e.getMessage());
        }

        // Get current user to initialize a requester step
        currentUser = Session.getCurrentUser();
        pr.createRequesterStep(currentUser);

        currentNetwork = Session.getCurrentNetwork();
        GlobalUserAccountDirectory allUserDir = currentNetwork.getGlobalUserAccountDir();

        // Change the workflowStep to submitted
        Result<Void> result = pr.advanceToNextStep(allUserDir, pr.getReason(), ApprovalStatus.SUBMITTED);

        // Store purchase request in the purchase request list
        purchaseRequestList = currentUser.getEnterprise().getPurchaseRequestList();
        purchaseRequestList.addPurchaseRequest(pr);

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

    public List<PurchaseRequest> getPRbyUserId(String userId) {
        // Find user Enterprise then get the purchase request list
        Enterprise enterprise = currentNetwork.getEnterpriseDir().findEnterpriseByName(currentUser.getEnterprise().getName());
        return enterprise.getPurchaseRequestList().getRequestsBySenderId(currentUser.getUserId());
    }
}
