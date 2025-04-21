package registry;

import enums.EnterpriseType;
import enums.OrganizationType;
import enums.Role;
import view.quotation.ManageQuotationPanel;
import view.quotation.QuotationReviewPanel;
import view.services.HRServicePanel;
import view.services.ITServicePanel;
import view.rfq.RFQManagmentPanel;
import view.purchaseRequest.ManagePurchaseRequestsPanel;
import view.purchaseRequest.MyPurchaseRequestsPanel;
import view.purchaseRequest.PurchaseItemFormPanel;
import view.shipping.ManageDeliveryReqPanel;

import javax.swing.*;
import java.util.*;
import java.util.function.Supplier;
import view.purchaseOrder.MyPurchaseOrdersPanel;
import view.shipping.WarehousePOPanel;

/**
 * The ServiceRegistry manages all available services in the system based on a user's
 * Role, OrganizationType, and EnterpriseType.
 * <p>
 * It maps composite keys (ServiceKey) to a list of available services (ServiceItem).
 * Services are loaded statically during application startup.
 *
 * @author tisaac
 */
public class ServiceRegistry {

    private static final Map<ServiceKey, List<ServiceItem>> serviceMap = new HashMap<>();
    private static final Map<String, Supplier<JPanel>> serviceItemMap = new HashMap<>();

    static {
        Supplier<JPanel> IT = ITServicePanel::new;
        Supplier<JPanel> HR = HRServicePanel::new;
        Supplier<JPanel> LOGISTICS = ManageDeliveryReqPanel::new;
        Supplier<JPanel> MYPR = MyPurchaseRequestsPanel::new;
        Supplier<JPanel> myPurchaseOrdersPanel = MyPurchaseOrdersPanel::new;
        Supplier<JPanel> PRMANAGE_SERVICE = ManagePurchaseRequestsPanel::new;
        Supplier<JPanel> warehousePOPanel = WarehousePOPanel::new;
        Supplier<JPanel> RFQMANAGE_SERVICE = RFQManagmentPanel::new;
        Supplier<JPanel> QUOTATION_SERVICE = ManageQuotationPanel::new;
        Supplier<JPanel> QUOTATION_REVIEW = QuotationReviewPanel::new;


        

        JPanel hrService = new HRServicePanel();

        // Google IT Manager
        addService(Role.MANAGER, OrganizationType.IT, EnterpriseType.BUYER,
                new ServiceItem("IT Management", IT),
                new ServiceItem("Manage Employees", HR),
                new ServiceItem("Personal Purchase Requests", MYPR),
                new ServiceItem("Manage Purchase Requests", PRMANAGE_SERVICE)
        );

        // Google IT Engineer
        addService(Role.ENGINEER, OrganizationType.IT, EnterpriseType.BUYER,
                new ServiceItem("IT Management", IT),
                new ServiceItem("Personal Purchase Requests", MYPR)
        );

        // Google Finance Manager
        addService(Role.ANALYST, OrganizationType.FINANCE, EnterpriseType.BUYER,
                new ServiceItem("Quotation Review", QUOTATION_REVIEW)
        );

        // Google Procurement Specialist
        addService(Role.SPECIALIST, OrganizationType.PROCUREMENT, EnterpriseType.BUYER,
                new ServiceItem("Personal Purchase Requests", MYPR),
                new ServiceItem("Manage Purchase Requests", PRMANAGE_SERVICE),
                new ServiceItem("Manage RFQ", RFQMANAGE_SERVICE)
        );

        // Google Legal Manager
        addService(Role.LEGAL_REVIEWER, OrganizationType.LEGAL, EnterpriseType.BUYER);

        // Google HR Manager
        addService(Role.MANAGER, OrganizationType.HR, EnterpriseType.BUYER,
                new ServiceItem("Manage Employees", HR),
                new ServiceItem("Personal Purchase Requests", MYPR)
        );

  
        // Google Warehouse Manager
        addService(Role.SPECIALIST, OrganizationType.WAREHOUSE, EnterpriseType.BUYER,
               new ServiceItem("View Purchase Orders", warehousePOPanel)  
        );
        

        // Vendor Sales Manager
        addService(Role.MANAGER, OrganizationType.SALES, EnterpriseType.VENDOR,
                new ServiceItem("Manage RFQ", RFQMANAGE_SERVICE),
                new ServiceItem("Manage Quotation", QUOTATION_SERVICE)
        );

        // Logistics
        addService(Role.SHIPPING_COORDINATOR, OrganizationType.LOGISTICS, EnterpriseType.LOGISTICS,
                new ServiceItem("Manage Delivery Requests", LOGISTICS)
        );
    }

    /**
     * Adds a service list mapping to the internal serviceMap with given role/org/enterprise type.
     */
    private static void addService(Role role, OrganizationType orgType, EnterpriseType enterpriseType, ServiceItem... services) {
        ServiceKey key = new ServiceKey(role, orgType, enterpriseType);
        serviceMap.put(key, Arrays.asList(services));
        for (ServiceItem service : services) {
            serviceItemMap.put(service.name, service.panel);
        }
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
        private final EnterpriseType entType;

        public ServiceKey(Role Role, OrganizationType organizationType, EnterpriseType entType) {
            this.role = Role;
            this.organizationType = organizationType;
            this.entType = entType;
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
            return role == that.role &&
                    organizationType == that.organizationType &&
                    entType == that.entType;
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
     * Retrieves the list of available services for the given user context.
     *
     * @param role           The user's role (e.g., MANAGER, ENGINEER)
     * @param orgType        The organization type (e.g., IT, PROCUREMENT)
     * @param enterpriseType The enterprise type (e.g., BUYER, VENDOR)
     * @return List of services available to the user. Empty if none found.
     */
    public static List<ServiceItem> getServicesFor(Role role, OrganizationType orgType, EnterpriseType enterpriseType) {
        return serviceMap.getOrDefault(new ServiceKey(role, orgType, enterpriseType), Collections.emptyList());
    }

    public static Supplier<JPanel> getServicePanel(String name) {
        return serviceItemMap.get(name);
    }
}
