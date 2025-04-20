package service;

import common.AppContext;
import common.NetworkManager;
import common.Result;
import common.Session;
import directory.PurchaseRequestDirectory;
import model.ecosystem.Ecosystem;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.procurement.PurchaseRequest;
import model.user.UserAccount;
import registry.UserRegistry;
import util.ResultUtil;

import java.util.List;
import java.util.Optional;

/**
 * @author tisaac
 */
public class UserService {
    private static UserService instance = new UserService();
    private static UserAccount currentUsr;
    private static Network currentNetwork;

    private UserService() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public Result<UserAccount> login(Network network, String userId, String pwd) {
        if (userId == null || userId.isEmpty() || pwd == null || pwd.isEmpty()) {
            return ResultUtil.failure("Please enter Username and Password");
        }

        // If network is empty, check the system admin
        if (network == null || "-- Select Network --".equals(network)) {

            UserAccount admin = Ecosystem.getInstance().getSysAdmin();
            if (admin.getUsername().equals(userId)) {
                return checkPassword(admin, pwd);
            } else {
                return ResultUtil.failure("Please select a network");
            }
        }

        // Check the user level
        return Optional.ofNullable(network.getUserRegistry()) // Optional<Network> → Optional<UserRegistry>
                .flatMap(reg -> reg.findByUserId(userId))// Optional<UserAccount>
                .map(user -> {
                    Result<UserAccount> result = checkPassword(user, pwd);
                    if (result.isSuccess()) {
                        Session.setSession(user, network); // for UI
                        AppContext.setContext(user, network); // for services
                    }
                    return result;
                }) // Optional<Result<UserAccount>>
                .orElse(ResultUtil.failure("User not found")); // If user not found, return failure
    }

    private Result<UserAccount> checkPassword(UserAccount user, String pwd) {
        if (user.getPassword().equals(pwd)) {
            currentUsr = user;
            return ResultUtil.success("Login successful", user);
        } else {
            return ResultUtil.failure("Invalid username or password");
        }
    }
}
