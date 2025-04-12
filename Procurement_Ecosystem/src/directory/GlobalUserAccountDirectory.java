package directory;

import model.user.UserAccount;
import registry.UserRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * GlobalUserAccountDirectory is a centralized container for all user accounts created in the system,
 * regardless of their organizational affiliation.
 * <p>
 * This class does not directly create or manage user accounts. Instead, it acts as a helper class
 * that receives user accounts from the {@link service.UserAccountService} and ensures they are
 * stored in a system-wide list and registered in the {@link registry.UserRegistry} for fast lookup.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *   <li>Store all {@link model.UserAccount} instances created in the system</li>
 *   <li>Synchronize with {@link UserRegistry} to enable global search</li>
 *   <li>Serve as a global access point for listing or auditing all users</li>
 * </ul>
 *
 * <h3>Usage:</h3>
 * This class is used internally by {@code UserAccountService} and should not be accessed directly by external modules.
 *
 * <h3>Example (via Service):</h3>
 * <pre>{@code
 * UserAccountService userService = new UserAccountService(globalDir);
 * userService.createUserForOrganization("isaac", "123", Role.EMPLOYEE, org);
 * }</pre>
 *
 * @author Isaac
 */
public class GlobalUserAccountDirectory {
    private final List<UserAccount> globalUserAccountList;
    private final UserRegistry userRegistry;

    public GlobalUserAccountDirectory(UserRegistry userRegistry) {
        this.globalUserAccountList = new ArrayList<>();
        this.userRegistry = userRegistry;
    }

    public List<UserAccount> getUserAccountList() {
        return globalUserAccountList;
    }

    public void addUser(UserAccount user) {
        globalUserAccountList.add(user);
        userRegistry.register(user);
    }

    public void deleteUser(String userId) {
        globalUserAccountList.removeIf(user -> user.getUserId().equals(userId));
        userRegistry.unregister(userId);
    }

    public UserRegistry getUserRegistry() {
        return userRegistry;
    }
}
