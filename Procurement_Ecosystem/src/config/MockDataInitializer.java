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
import enums.OrganizationType;
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
import service.OrganizationService;
import service.UserAccountService;
import util.TestShipment;


/**
 *
 * @author tisaac
 */
public class MockDataInitializer {

    public static Network initialize() {
        Ecosystem eco = Ecosystem.getInstance();
        eco.init(new UserAccount("admin", Role.SYS_ADMIN, "admin"));
        Network network = eco.AddNetwork("Tech");

        OrganizationService orgService = network.getOrgService();
        UserAccountService userAccountService = network.getUserAccountService();

        // GoogleX (IT: A001/A001) | (Procurement: A002/isaac)
        Enterprise google = network.getEnterpriseDir().createEnterprise("Google", EnterpriseType.BUYER);

        Organization googleIT = orgService.createOrgFromEnterprise(OrganizationType.IT, google);
        UserAccount googleITManager = userAccountService.createUserFromOrganization("Alvin", "A001", Role.MANAGER, googleIT, google);

        Organization googleProcurement = orgService.createOrgFromEnterprise(OrganizationType.PROCUREMENT, google);
        UserAccount googleProcurementManager = userAccountService.createUserFromOrganization("Isaac", "isaac", Role.SPECIALIST, googleProcurement, google);

        
        // FedEx (A003/A003)
        Enterprise fedEx = network.getEnterpriseDir().createEnterprise("FedEx", EnterpriseType.LOGISTICS);
        Organization fedExShipping = orgService.createOrgFromEnterprise(OrganizationType.LOGISTICS, fedEx);
        UserAccount fedExShippingCoordinator = userAccountService.createUserFromOrganization("A003", "A003", Role.SHIPPING_COORDINATOR, fedExShipping, google);

        // Asus (sales: A004/A004) | (specialist: A005/A005)
        Enterprise asus = network.getEnterpriseDir().createEnterprise("ASUS", EnterpriseType.VENDOR);
        Organization asusSales = orgService.createOrgFromEnterprise(OrganizationType.SALES, asus); 
        UserAccount asusSalesManager = userAccountService.createUserFromOrganization("Asus_SalesManagerA", "A004", Role.MANAGER, asusSales, google);
        UserAccount asusSpecialist = userAccountService.createUserFromOrganization("Peter", "peter", Role.SPECIALIST, asusSales, google);

        
        
        // Goolge procurement give a PO to Asus(vendor)
        new TestShipment().sendPOToVendor(asus);
        
        // Asus(vendor) give FedEx a new delivery Request
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

        System.out.println("get po first product: " + asus.getPurchaseOrderList());
        
        
        return network;
    }
}
