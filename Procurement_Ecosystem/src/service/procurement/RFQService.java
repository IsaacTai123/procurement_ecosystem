package service.procurement;

import common.Result;
import directory.PurchaseRequestDirectory;
import enums.RFQStatus;
import model.ecosystem.Network;
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
        rfq.setVendor(currentUser.getEnterprise());
        rfq.setStatus(RFQStatus.SENT);
        return ResultUtil.success("RFQ submitted successfully");
    }
}
