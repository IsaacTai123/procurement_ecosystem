package service.procurement;

import common.Result;
import directory.PurchaseRequestDirectory;
import model.ecosystem.Network;
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

    public Result<Void> submitRFQ() {
        return ResultUtil.success("RFQ submitted successfully");
    }
}
