package registry;

import enums.OrganizationType;
import enums.Role;
import view.HRServicePanel;
import view.ITServicePanel;
import view.shipping.ManageDeliveryReqPanel;

import javax.swing.*;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author tisaac
 */
public class ServiceRegistry {

    private static final Map<ServiceKey, List<ServiceItem>> serviceMap = new HashMap<>();
    private static final Map<String, Supplier<JPanel>> serviceItemMap = new HashMap<>();

    static {
        Supplier<JPanel> IT = ITServicePanel::new;
        Supplier<JPanel> HR = HRServicePanel::new;
        Supplier<JPanel> LOGISTICS = ManageDeliveryReqPanel::new;

        JPanel hrService = new HRServicePanel();

        // IT Manager
        serviceMap.put(new ServiceKey(Role.MANAGER, OrganizationType.IT), List.of(
                new ServiceItem("IT Management", IT),
                new ServiceItem("Manage Employees", HR)
        ));

        // IT Engineer
        serviceMap.put(new ServiceKey(Role.ENGINEER, OrganizationType.IT), List.of(
                new ServiceItem("IT Management", IT)
        ));

        // Finance Manager
        serviceMap.put(new ServiceKey(Role.ANALYST, OrganizationType.FINANCE), List.of(
        ));

        // Procurement Manager
        serviceMap.put(new ServiceKey(Role.SPECIALIST, OrganizationType.PROCUREMENT), List.of(
        ));

        // Legal Manager
        serviceMap.put(new ServiceKey(Role.LEGAL_REVIEWER, OrganizationType.LEGAL), List.of(
        ));

        // HR Manager
        serviceMap.put(new ServiceKey(Role.MANAGER, OrganizationType.HR), List.of(
                new ServiceItem("Manage Employees", HR)
        ));

        // Warehouse Manager
        serviceMap.put(new ServiceKey(Role.SPECIALIST, OrganizationType.WAREHOUSE), List.of(
        ));

        // Sales Manager
        serviceMap.put(new ServiceKey(Role.MANAGER, OrganizationType.SALES), List.of(
        ));

        // Logistics
        serviceMap.put(new ServiceKey(Role.SHIPPING_COORDINATOR, OrganizationType.LOGISTICS), List.of(
                new ServiceItem("Manage Delivery Requests", LOGISTICS)
        ));
    }

    /**
     * A composite key used for mapping role and organization Name to a list of services.
     * <p>
     * This key combines {@link Role} and {@link OrganizationName} to uniquely identify
     * a specific permission scope or user context.
     * <p>
     * Used as a key in maps such as:
     * <pre>
     * Map&lt;ServiceKey, List&lt;ServiceItem&gt;&gt;
     * </pre>
     */
    // inner class for key
    public static class ServiceKey {
        private final Role role;
        private final OrganizationType organizationType;

        public ServiceKey(Role Role, OrganizationType organizationType) {
            this.role = Role;
            this.organizationType = organizationType;
        }

        /**
         * Two ServiceKey objects are equal if they have the same Role and organizationType.
         *
         * @param o The other object to compare.
         * @return true if both role and organization types match; false otherwise.
         */
        // override equals & hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ServiceKey)) return false;
            ServiceKey that = (ServiceKey) o;
            return role == that.role && organizationType == that.organizationType;
        }

        /**
         * Generates a hash code based on the Role and organizationType.
         *
         * @return The hash code for this ServiceKey.
         */
        @Override
        public int hashCode() {
            return Objects.hash(role, organizationType);
        }
    }

    public static class ServiceItem {
        public final String name;
        public final Supplier<JPanel> panel;

        public ServiceItem(String name, Supplier<JPanel> panel) {
            this.name = name;
            this.panel = panel;
            serviceItemMap.put(name, panel);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Returns the list of services available to the given role within a specific organization.
     *
     * @param role     The user's role object (contains the role type like MANAGER, EMPLOYEE, etc.)
     * @param orgType  The organization type the user belongs to (like IT, FINANCE, SALES, etc.)
     * @return A list of available ServiceItem instances. Returns an empty list if no match is found.
     */
    public static List<ServiceItem> getServicesFor(Role role, OrganizationType orgType) {
        return serviceMap.getOrDefault(new ServiceKey(role, orgType), Collections.emptyList());
    }

    public static Supplier<JPanel> getServicePanel(String name) {
        return serviceItemMap.get(name);
    }
}
