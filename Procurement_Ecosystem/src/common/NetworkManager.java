package common;

import model.ecosystem.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * NetworkManager is a utility class responsible for maintaining a centralized list of all {@link Network} instances
 * in the system. It provides static methods to register, retrieve, find, and manage network objects globally.
 * <p>
 * This class follows a static singleton-like pattern, meaning it cannot be instantiated and all its behavior
 * is accessed through static methods.
 * <p>
 * It is typically used during system setup and throughout the application lifecycle whenever global access
 * to all networks is required.
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * Network google = new Network("Google");
 * NetworkManager.registerNetwork(google);
 *
 * Optional<Network> found = NetworkManager.findByName("Google");
 * }</pre>
 *
 * @author tisaac
 */
public class NetworkManager {
    private static final List<Network> networks = new ArrayList<>();

    private NetworkManager() {
        // Private constructor to prevent instantiation
    }

    public static void registerNetwork(Network network) {
        if (network != null && !networks.contains(network)) {
            networks.add(network);
        }
    }

    public static List<Network> getNetworks() {
        return networks;
    }

    public static Optional<Network> findByName(String name) {
        return networks.stream()
                .filter(network -> network.getName().equals(name))
                .findFirst();
    }

    public static void removeNetwork(String name) {
        networks.removeIf(n -> n.getName().equalsIgnoreCase(name));
    }

    public static void clearAll() {
        networks.clear();
    }
}
