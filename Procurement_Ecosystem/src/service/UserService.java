package service;

import common.AppContext;
import common.NetworkManager;
import common.Result;
import common.Session;
import directory.PurchaseRequestDirectory;
import enums.ApprovalStatus;
import enums.OrganizationType;
import enums.RequestStatus;
import model.ecosystem.Ecosystem;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.procurement.PurchaseItem;
import model.procurement.PurchaseRequest;
import model.product.Product;
import model.product.Spec;
import model.user.UserAccount;
import model.workqueue.WorkflowStep;
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
                        generateFakeDataForTest(); // for testing
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

    private void generateFakeDataForTest() {
        // A fake PR
        PurchaseRequest p = new PurchaseRequest("PR-001");
        p.setSender(currentUsr);
        AppContext.getUserEnterprise().getPurchaseRequestList().addPurchaseRequest(p);
        p.addPurchaseItem(
                new Product("Asus Laptop"),
                1, 3.33,
                new Spec("Intel i7", "16GB RAM", "512GB SSD", "Black", "Laptop", "Asus Laptop")
        );

        p.createRequesterStep(currentUsr);

        p.getWorkflowSteps().forEach(step -> {
            OrganizationType type = step.getOrgType();

            if (type == OrganizationType.IT) {
                step.setStatus(ApprovalStatus.APPROVED);
                step.setAssignedUser(AppContext.getNetwork().getUserRegistry().findByUserId("A001").orElse(null));
            } else if (type == OrganizationType.PROCUREMENT) {
                step.setStatus(ApprovalStatus.APPROVED); // 範例
                step.setAssignedUser(AppContext.getNetwork().getUserRegistry().findByUserId("A002").orElse(null));
            } else {
                step.setStatus(ApprovalStatus.SUBMITTED);
            }
        });

        p.setStatus(RequestStatus.APPROVED);

        // Print out PR details
        System.out.println("Purchase Request ID: " + p.getId());
        System.out.println("Sender: " + p.getSender().getUsername());
        System.out.println("Status: " + p.getStatus());
        System.out.println("Items:");
        for (PurchaseItem item : p.getPurchaseItems().getPurchaseItemList()) {
            System.out.println(" - " + item.getProduct().getName() + ": " + item.getQuantity() + " pcs");
        }

        // print workflow steps
        System.out.println("Workflow Steps:");
        for (WorkflowStep w : p.getWorkflowSteps()) {
            System.out.println( "status: " + w.getStatus());
        }
    }
}
