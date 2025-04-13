/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ecosystem;

import common.NetworkManager;
import model.user.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * Ecosystem serves as the centralized data container and core context of the procurement system.
 * <p>
 * It maintains a list of all {@link Network} instances and acts as the root of the system hierarchy.
 * Each {@code Network} may contain multiple enterprises and organizations, forming the structure of the entire ecosystem.
 * <p>
 * This class is implemented as a Singleton using the Bill Pugh Singleton pattern to ensure that
 * only one instance of the Ecosystem exists throughout the application lifecycle.
 *
 * <h3>Design Rationale:</h3>
 * <ul>
 *   <li>The Singleton pattern guarantees centralized control and consistent access to system-wide data.</li>
 *   <li>The use of a static inner holder class ensures lazy initialization and thread safety without explicit synchronization.</li>
 *   <li>All methods such as {@code addNetwork()} are declared as instance methods
 *       to preserve the integrity of the Singleton design—ensuring that all operations go through the single shared instance.</li>
 * </ul>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * Ecosystem ecosystem = Ecosystem.getInstance();
 * Network network = new Network("Taiwan");
 * ecosystem.addNetwork(network);
 * }</pre>
 *
 * @author tisaac
 */
public class Ecosystem {
    private final List<Network> networkList;
    private UserAccount sysAdmin;

    private Ecosystem() {
        networkList = new ArrayList<>();
    }

    public void init(UserAccount sysAdmin) {
        if (this.sysAdmin != null) {
            throw new IllegalStateException("System Admin is already initialized.");
        }
        if (sysAdmin == null) {
            throw new IllegalArgumentException("sysAdmin cannot be null.");
        }
        this.sysAdmin = sysAdmin;
    }

    private static class EcosystemHolder {
        private static final Ecosystem INSTANCE = new Ecosystem();
    }

    public static Ecosystem getInstance() {
        return EcosystemHolder.INSTANCE;
    }

    public void AddNetwork(Network n) {
        if (n != null && !networkList.contains(n)) {
            networkList.add(n);
            NetworkManager.registerNetwork(n);
        }
    }

    public String getName() {
        return "Procurement Ecosystem";
    }

    public UserAccount getSysAdmin() {
        return sysAdmin;
    }
}
