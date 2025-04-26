package service.procurement;

import common.AppContext;
import common.Result;
import directory.PurchaseRequestDirectory;
import enums.*;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.procurement.PurchaseItem;
import model.procurement.PurchaseOrder;
import model.quotation.Quotation;
import model.quotation.RFQ;
import model.user.UserAccount;
import util.ResultUtil;

/**
 * @author tisaac
 */
public class RFQService {
    private UserAccount currentUser;
    private Network currentNetwork;

    public RFQService(UserAccount user, Network network) {
        this.currentUser = user;
        this.currentNetwork = network;
    }

    public Result<Void> submitRFQ(RFQ rfq, String vendor) {
        // Get Enterprise from vendor
        Enterprise v = AppContext.getNetwork().getEnterpriseDir().findEnterpriseByName(vendor);
        rfq.setVendor(v);
        rfq.markAsSent();

        // Link RFQ to Purchase Request
        currentUser.getEnterprise().getPurchaseRequestList()
                .findRequestById(rfq.getLinkedPRId())
                .addLinkedRFQId(rfq.getId());

        return ResultUtil.success("RFQ submitted successfully");
    }

    public Result<Void> rejectQuotation(Quotation q, String remark) {
        Result<Void> r = q.rejectCurrentStepAndTerminate(currentUser, remark);
        if (!r.isSuccess()) {
            return r;
        }

        q.markAsRejected();
        // get linked RFQ and mark as closed
        AppContext.getNetwork().getRfqDirectory().getRFQById(q.getLinkedRFQId()).markAsClosed();
        return ResultUtil.success("Quotation rejected successfully");
    }

    public Result<Void> fowardQuotation(Quotation q, RFQ rfq) {
        // Check authorization first
        Result<Void> result = isUserAuthorized(OrganizationType.PROCUREMENT, Role.SPECIALIST);
        if (!result.isSuccess()) {
            return result;
        }

        // Check if the purchase request is in a valid state for approval
        if (q.getStatus() != RequestStatus.PENDING) {
            return ResultUtil.failure("Quotation is not in a valid state to be forwarded");
        }

        // Also check if other quotations under RFQ are all in pending if not than decline the action
        boolean hasInvalidStatus = rfq.getQuotations().getQuotationList().stream()
                .anyMatch(x -> x.getStatus() == RequestStatus.RECEIVED ||
                        x.getStatus() == RequestStatus.APPROVED);
        if (hasInvalidStatus) {
            return ResultUtil.failure("This RFQ is currently being processed by Finance. Please wait until they’re finished.");
        }


        // Change quotation to Accepted
        result = q.advanceToNextStep(
                currentUser,
                currentNetwork.getGlobalUserAccountDir(),
                q.getRemarks(),
                ApprovalStatus.APPROVED,
                OrganizationType.FINANCE,
                EnterpriseType.BUYER
        );

        if (!result.isSuccess()) {
            return result;
        }

        q.markAsReceived();
        return ResultUtil.success("Quotation forwarded to Finance successfully");
    }

    public Result<Void> acceptQuotation(Quotation q) {
        // Check authorization first
        Result<Void> result = isUserAuthorized(OrganizationType.FINANCE, Role.ANALYST);
        if (!result.isSuccess()) {
            return result;
        }

        // Check if the purchase request is in a valid state for approval
        if (q.getStatus() != RequestStatus.RECEIVED) {
            return ResultUtil.failure("Quotation is not in a valid state for Finance to accept");
        }

        // Change quotation to Accepted
        result = q.advanceToNextStep(
                currentUser,
                currentNetwork.getGlobalUserAccountDir(),
                q.getRemarks(),
                ApprovalStatus.APPROVED,
                OrganizationType.FINANCE,
                EnterpriseType.BUYER
        );
        if (!result.isSuccess()) {
            return result;
        }

        // mark quotation as approved, waiting for PO, when PO created it mark as completed
        q.markAsApproved();

        // mark rfq as close
        RFQ rfq = AppContext.getNetwork().getRfqDirectory().getRFQById(q.getLinkedRFQId());
        rfq.markAsClosed();

        return ResultUtil.success("Quotation accepted by Finance, you can now create PO");
    }

    public  Result<Void> submitePO(RFQ rfq, Quotation quotation, double price, String remarks, String address) {
        // If RFQ status is CLOSED

        // Linked PR to PO
        PurchaseOrder po = new PurchaseOrder(
                quotation.getId(),
                rfq.getLinkedPRId(),
                quotation.getSender(),
                quotation.getVendor().findUserByOrgRoleAndEnt(OrganizationType.SALES, Role.SPECIALIST),
                rfq.getPurchaseItems(),
                address,
                price,
                remarks
        );

        AppContext.getUserEnterprise().getPurchaseOrderList().addPurchaseOrder(po);
        quotation.markAsCompleted();
        return ResultUtil.success("Purchase Order submitted successfully");
    }

    private Result<Void> isUserAuthorized(OrganizationType org, Role role) {
        // Check if current user match the role
        if (currentUser.getUserType().name().equals(role.name()) && currentUser.getOrg().getTypeName().name().equals(org.name())) {
            return ResultUtil.success("User is authorized");
        } else {
            return ResultUtil.failure("User is not authorized for this action");
        }
    }
}
