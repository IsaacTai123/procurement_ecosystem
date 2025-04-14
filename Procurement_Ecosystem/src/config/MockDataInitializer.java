/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/
package config;

//import Business.Person.Person;
//import Business.Person.PersonDirectory;
//import Business.Profiles.EmployeeDirectory;
//import Business.Profiles.EmployeeProfile;
//import Business.Profiles.StudentDirectory;
//import Business.Profiles.StudentProfile;


import controller.DeliveryController;
import directory.OrganizationDirectory;
import enums.EnterpriseType;
import enums.Role;
import model.ecosystem.Ecosystem;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.ecosystem.Organization;
import model.user.UserAccount;
import directory.UserAccountDirectory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import model.delivery.ShipmentDirectory;
import model.delivery.ShipmentItem;
import model.product.Product;
import service.procurement.OrganizationService;
import service.procurement.UserAccountService;


/**
 *
 * @author tisaac
 */
public class MockDataInitializer {

    public static Network initialize() {
        Ecosystem eco = Ecosystem.getInstance();
        Network network = new Network("Google_TSMC");
        eco.AddNetwork(network);

        OrganizationService orgService = network.getOrgService();
        UserAccountService userAccountService = network.getUserAccountService();

        // Enterprise

        // Network Admin
        Enterprise systemCore = network.getEnterpriseDir().createEnterprise("System Core", EnterpriseType.SYSTEM_CORE);
        Organization networkOrg = orgService.createOrgFromEnterprise("Network Admin", systemCore);
        UserAccount sysAdmin = userAccountService.createUserFromOrganization("admin", "admin", Role.SYS_ADMIN, networkOrg); // system admin account

        // Google
        Enterprise google = network.getEnterpriseDir().createEnterprise("Google", EnterpriseType.BUYER);
        Organization googleProcurement = orgService.createOrgFromEnterprise("Procurement", google);
        UserAccount googleProcurementManager = userAccountService.createUserFromOrganization("isaac", "isaac", Role.MANAGER, googleProcurement);

        // FedEx
        Enterprise fedEx = network.getEnterpriseDir().createEnterprise("FedEx", EnterpriseType.LOGISTICS);
        Organization fedExShipping = orgService.createOrgFromEnterprise("Shipping", fedEx);
        UserAccount fedExShippingCoordinator = userAccountService.createUserFromOrganization("CarrierA", "CarrierA", Role.SHIPPING_COORDINATOR, fedExShipping);

        // Asus
        Enterprise asus = network.getEnterpriseDir().createEnterprise("ASUS", EnterpriseType.VENDOR);
        Organization asusSales = orgService.createOrgFromEnterprise("Sales", fedEx);
        UserAccount asusSalesManager = userAccountService.createUserFromOrganization("SalesManagerA", "SalesManagerA", Role.MANAGER, asusSales);

        
        
        
        // Add new delivery Request
        DeliveryController deliveryController = new DeliveryController();
        
        ShipmentDirectory fedEx_shipmentDirectory = new ShipmentDirectory(fedEx);
        network.getShipmentDirectories().addShipmentDirectory(fedEx_shipmentDirectory);
        ArrayList<ShipmentItem> items = new ArrayList<>();
        ShipmentItem itemA = new ShipmentItem(new Product("Asus Laptop"), 1);
        ShipmentItem itemB = new ShipmentItem(new Product("Asus adaptor"), 1);
        items.add(itemA);
        items.add(itemB);
        
        Date currentDate = new Date();
        

        deliveryController.requestShipping(items, fedEx, asusSalesManager, googleProcurementManager, currentDate, currentDate, fedEx_shipmentDirectory, "Laptops");

        
        
        return network;
    }
}
