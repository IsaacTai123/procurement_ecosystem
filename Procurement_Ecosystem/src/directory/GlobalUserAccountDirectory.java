package directory;

import enums.OrganizationType;
import enums.Role;
import model.user.UserAccount;
import registry.UserRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
 * @author tisaac
 */
public class GlobalUserAccountDirectory {
    private final List<UserAccount> allUsers;
    private final UserRegistry userRegistry;

    public GlobalUserAccountDirectory(UserRegistry userRegistry) {
        this.allUsers = new ArrayList<>();
        this.userRegistry = userRegistry;
    }

    public List<UserAccount> getAllUsers() {
        return allUsers;
    }

    public void addUser(UserAccount user) {
        allUsers.add(user);
        userRegistry.register(user);
    }

    public void deleteUser(String userId) {
        allUsers.removeIf(user -> user.getUserId().equals(userId));
        userRegistry.unregister(userId);
    }

    public UserRegistry getUserRegistry() {
        return userRegistry;
    }

    /**
     * Finds a user by their organization type and role.
     *
     * @param orgType The type of organization to search for.
     * @param role    The role of the user to search for.
     * @return An Optional containing the UserAccount if found, or empty if not found.
     *
     * <h3>Example usage:</h3>
     * <pre>{@code
     * userDirectory.findUserByOrganizationAndRole(OrganizationType.FINANCE, Role.MANAGER)
     *     .ifPresent(step::setAssignedUser);
     * }</pre>
     */
    public Optional<UserAccount> findUserByOrgAndRole(OrganizationType orgType, Role role) {
        return allUsers.stream()
                .filter(user -> user.getOrg().getTypeName() == orgType && user.getUserType() == role)
                .findFirst();
    }
}
