package service;

import common.NetworkManager;
import common.Result;
import model.ecosystem.Ecosystem;
import model.ecosystem.Network;
import model.user.UserAccount;
import registry.UserRegistry;
import util.ResultUtil;

import java.util.Optional;

/**
 * @author tisaac
 */
public class UserService {
    private static UserService instance = new UserService();
    private static UserAccount currentUsr;

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
            if (admin.getUserId().equals(userId)) {
                return checkPassword(admin, pwd);
            } else {
                return ResultUtil.failure("Please select a network");
            }
        }

        // Check the user level
        return Optional.ofNullable(network.getUserRegistry()) // Optional<Network> → Optional<UserRegistry>
                .flatMap(reg -> reg.findByUserId(userId))// Optional<UserAccount>
                .map(user -> checkPassword(user, pwd)) // Optional<Result<UserAccount>>
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
