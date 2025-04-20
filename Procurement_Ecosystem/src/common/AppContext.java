package common;

import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.user.UserAccount;

/**
 * @author tisaac
 */
public class AppContext {
    private static UserAccount currentUser;
    private static Network currentNetwork;
    private static Enterprise userEnterprise;

    public static void setUser(UserAccount user) {
        currentUser = user;
    }

    public static void setNetwork(Network network) {
        currentNetwork = network;
    }

    public static void setContext(UserAccount user, Network network) {
        currentUser = user;
        currentNetwork = network;
        userEnterprise = user.getOrg().getEnterprise();
    }

    public static UserAccount getUser() {
        return currentUser;
    }

    public static Network getNetwork() {
        return currentNetwork;
    }

    public static Enterprise getUserEnterprise() {
        return userEnterprise;
    }

    public static void clear() {
        currentUser = null;
        currentNetwork = null;
    }
}
