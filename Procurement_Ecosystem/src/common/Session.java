package common;

import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.user.UserAccount;


/**
 * Session is a global holder for the current logged-in user and network context.
 * <p>
 * It is implemented as a simple singleton-style utility with static access,
 * making it easy to store and retrieve user-related session information throughout the app.
 *
 * Example usage:
 * <pre>
 *     Session.setCurrentUser(user);
 *     Session.setCurrentNetwork(network);
 *     UserAccount current = Session.getCurrentUser();
 * </pre>
 *
 * @Author tisaac
 */
public class Session {
    private static UserAccount currentUser;
    private static Network currentNetwork;
    private static Enterprise userEnterprise;

    private Session() {
        // Prevent instantiation
    }

    /**
     * Sets the currently logged-in user.
     * @param user the user account that has logged in
     */
    public static void setCurrentUser(UserAccount user) {
        currentUser = user;
    }

    /**
     * Returns the currently logged-in user.
     * @return the current UserAccount, or null if not set
     */
    public static UserAccount getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currently active network context.
     * @param network the selected network at login
     */
    public static void setCurrentNetwork(Network network) {
        currentNetwork = network;
    }

    /**
     * Returns the currently active network context.
     * @return the selected Network, or null if not set
     */
    public static Network getCurrentNetwork() {
        return currentNetwork;
    }

    /**
     * Clears the session (e.g., on logout).
     */
    public static void clear() {
        currentUser = null;
        currentNetwork = null;
    }
}
