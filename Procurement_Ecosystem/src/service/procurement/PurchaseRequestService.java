package service.procurement;

import common.Result;
import common.Session;
import directory.GlobalUserAccountDirectory;
import directory.PurchaseRequestDirectory;
import enums.ApprovalStatus;
import enums.OrganizationType;
import enums.RequestStatus;
import enums.Role;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.procurement.PurchaseItem;
import model.product.Spec;
import model.user.UserAccount;
import model.procurement.PurchaseRequest;
import model.workqueue.WorkRequest;
import model.workqueue.WorkflowStep;
import util.ResultUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tisaac
 */
public class PurchaseRequestService {

    private UserAccount currentUser;
    private Network currentNetwork;
    private PurchaseRequestDirectory purchaseRequestList;

    public PurchaseRequestService(UserAccount user, Network network) {
        this.currentUser = user;
        this.currentNetwork = network;
    }

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
        pr.createRequesterStep(currentUser);
        GlobalUserAccountDirectory allUserDir = currentNetwork.getGlobalUserAccountDir();

        // Change the workflowStep to submitted (Point the next step to procurement)
        Result<Void> result = pr.advanceToNextStep(currentUser, allUserDir, pr.getReason(), ApprovalStatus.SUBMITTED, OrganizationType.PROCUREMENT);

        // Store purchase request in the purchase request list
        purchaseRequestList = currentUser.getEnterprise().getPurchaseRequestList();
        purchaseRequestList.addPurchaseRequest(pr);

        // This could involve saving the request to a database or sending it to a queue for processing
        return result;
    }

    public Result<Void> approveRRByProcurement(PurchaseRequest pr) {
        // Check authorization first
        Result<Void> result = isUserAuthorized(OrganizationType.PROCUREMENT, Role.SPECIALIST);
        if (!result.isSuccess()) {
            return result;
        }

        // Check if the purchase request is in a valid state for approval
        if (pr.getStatus() != RequestStatus.PENDING) {
            return ResultUtil.failure("Purchase request is not in a valid state for approval");
        }

        // Set current status to Approve
        result = pr.advanceToNextStep(
                currentUser,
                currentNetwork.getGlobalUserAccountDir(),
                "no comment",
                ApprovalStatus.APPROVED,
                OrganizationType.IT
        );
        if (!result.isSuccess()) {
            return result;
        }

        pr.setStatus(RequestStatus.APPROVED);

        // if there is still a pending step in IT, we need to skip it
        WorkflowStep itStep = pr.getPendingStep();
        if (itStep == null) {
            return ResultUtil.success("Purchase request approved by Procurement, You can now create RFQ");
        }

        result = pr.forceCompleteCurrentStep(
                "No need for IT approval",
                ApprovalStatus.SKIPPED
        );
        if (!result.isSuccess()) {
            return result;
        }

        // send it back to the IT organization for confirmation by changing the receiver to the IT manager
        return ResultUtil.success("Purchase request skipped IT and approved by Procurement, You can now create RFQ");
    }

    public Result<Void> approveRRByIT(PurchaseRequest pr) {
        // Check authorization first
        Result<Void> result = isUserAuthorized(OrganizationType.IT, Role.MANAGER);
        if (!result.isSuccess()) {
            return result;
        }

        // Check if the purchase request is in a valid state for approval
        if (pr.getStatus() != RequestStatus.PENDING) {
            return ResultUtil.failure("Purchase request is not in a valid state for approval");
        }

        // Set current status to Approve
        result = pr.advanceToNextStep(
                currentUser,
                currentNetwork.getGlobalUserAccountDir(),
                "no comment",
                ApprovalStatus.APPROVED,
                OrganizationType.PROCUREMENT
        );
        if (!result.isSuccess()) {
            return result;
        }

        // send it back to the IT organization for confirmation by changing the receiver to the IT manager
        return ResultUtil.success("Purchase request approved by IT, next step is Procurement");
    }

    public Result<Void> forwardPR2IT(PurchaseRequest pr) {
        Result<Void> result = isUserAuthorized(OrganizationType.PROCUREMENT, Role.SPECIALIST);
        if (!result.isSuccess()) {
            return result;
        }

        result = pr.forwardToNextStep(OrganizationType.IT, currentNetwork.getGlobalUserAccountDir());
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtil.success("Purchase request forwarded to IT for confirmation");
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
        return enterprise.getPurchaseRequestList().getRequestsBySenderId(userId);
    }

    private Result<Void> isUserAuthorized(OrganizationType org, Role role) {
        // Check if current user match the role
//        System.out.println("Current user: " + currentUser.getUserType().name() + " " + currentUser.getOrg().getTypeName().name());
        if (currentUser.getUserType().name().equals(role.name()) && currentUser.getOrg().getTypeName().name().equals(org.name())) {
            return ResultUtil.success("User is authorized");
        } else {
            return ResultUtil.failure("User is not authorized for this action");
        }
    }
}
