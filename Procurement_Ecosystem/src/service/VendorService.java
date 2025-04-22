package service;

import common.Result;
import enums.ApprovalStatus;
import enums.OrganizationType;
import enums.RFQStatus;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.quotation.Quotation;
import model.quotation.RFQ;
import model.user.UserAccount;
import util.ResultUtil;

/**
 * @author tisaac
 */
public class VendorService {
    private UserAccount currentUser;
    private Network currentNetwork;

    public VendorService(UserAccount user, Network network) {
        this.currentUser = user;
        this.currentNetwork = network;
    }

    public Result<Void> submitQuotation(RFQ rfq, double price, Enterprise vendor) {
        rfq.setStatus(RFQStatus.RECEIVED);
        // Get Enterprise from vendor
        Quotation q = new Quotation(vendor, rfq.getRemarks(), price);

        // init requestor state
        q.createRequesterStep(currentUser);

        // handle current step and advance to next step
        Result<Void> result = q.advanceToNextStep(
                currentUser,
                currentNetwork.getGlobalUserAccountDir(),
                rfq.getRemarks(),
                ApprovalStatus.SUBMITTED,
                OrganizationType.PROCUREMENT
        );

        // Store quotation in the directory
        rfq.getQuotations().addQuotation(q);
        rfq.setStatus(RFQStatus.CLOSED);
        return result;
    }
}
