package service.procurement;

import directory.GlobalUserAccountDirectory;
import enums.Role;
import model.ecosystem.Organization;
import model.user.UserAccount;

/**
 * UserAccountService is responsible for managing the creation of user accounts within the system.
 * <p>
 * It supports both organization-based users and system-level users (such as administrators).
 * When a user is created, this service ensures the following:
 * <ul>
 *   <li>The user is added to the corresponding organization's local UserAccountDirectory</li>
 *   <li>The user is registered in the global UserAccountDirectory</li>
 *   <li>The user is indexed in the global UserRegistry for quick lookup</li>
 * </ul>
 *
 * <p>This service enforces data consistency by centralizing the creation logic of user accounts,
 * ensuring that all necessary structures are kept in sync.</p>
 *
 * <p><b>Typical usage:</b></p>
 * <pre>
 * UserAccountService userService = new UserAccountService(globalDir);
 * UserAccount ua = userService.createUserForOrganization("isaac", "pw123", Role.EMPLOYEE, org);
 * </pre>
 *
 * @author tisaac
 */
public class UserAccountService {
    private final GlobalUserAccountDirectory globalDir;

    public UserAccountService(GlobalUserAccountDirectory globalDir) {
        this.globalDir = globalDir;
    }

    public UserAccount createUserFromOrganization(String name, String pw, Role type, Organization org) {
        UserAccount account = org.getUserAccountDir().createUserAccount(name, pw, type);
        globalDir.addUser(account);
        return account;
    }

    public void deleteUserFromOrganization(UserAccount account, Organization org) {
        org.getUserAccountDir().deleteUserAccount(account);
        globalDir.deleteUser(account.getUserId());
    }

}
