package registry;

import model.user.UserAccount;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * UserRegistry is a centralized registry that maintains a mapping of user IDs to UserAccount instances.
 * <p>
 * It is designed to provide efficient, system-wide access to user accounts without requiring deep traversal
 * of the ecosystem hierarchy (Network → Enterprise → Organization → UserAccountDirectory).
 * <p>
 *
 *  This registry is particularly useful for quick lookups, user existence checks, or globally unique user identification.
 *  It does not own user data, but indexes it for fast retrieval.
 *
 *  <p><strong>Key Features:</strong></p>
 *  <ul>
 *     <li>Register and unregister users with unique IDs</li>
 *     <li>Support fast lookups by userId</li>
 *     <li>Can be integrated with system initialization to preload all existing users</li>
 *  </ul>
 * @author tisaac
 */
public class UserRegistry {

    private final Map<String, UserAccount> userIndex;

    public UserRegistry() {
        userIndex = new HashMap<>();
    }

    public void register(UserAccount userAccount) {
        userIndex.put(userAccount.getUserId(), userAccount);
    }

    public void unregister(String userId) {
        userIndex.remove(userId);
    }

    public Optional<UserAccount> findByUserId(String userId) {
        return Optional.ofNullable(userIndex.get(userId));
    }

    public void clear() {
        userIndex.clear();
    }
}
