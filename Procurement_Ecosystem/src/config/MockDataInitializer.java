/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/
package config;

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
                UserAccount googleITManager = userAccountService.createUserFromOrganization("Alvin", "A001",
                                Role.MANAGER, googleIT, google);

                Organization googleProcurement = orgService.createOrgFromEnterprise(OrganizationType.PROCUREMENT,
                                google);

                UserAccount googleProcurementManager = userAccountService.createUserFromOrganization("isaac", "isaac",
                                Role.SPECIALIST, googleProcurement, google);
                UserAccount googleSpecialist = userAccountService.createUserFromOrganization("A003", "peter",
                                Role.SPECIALIST, googleProcurement, google);

                Organization googleFinance = orgService.createOrgFromEnterprise(OrganizationType.FINANCE, google);
                UserAccount googleFinanceSpecialist = userAccountService.createUserFromOrganization("A004", "peter",
                                Role.SPECIALIST, googleFinance, google);

                // FedEx (A005/A005)
                Enterprise fedEx = network.getEnterpriseDir().createEnterprise("FedEx", EnterpriseType.LOGISTICS);
                Organization fedExShipping = orgService.createOrgFromEnterprise(OrganizationType.LOGISTICS, fedEx);
                UserAccount fedExShippingCoordinator = userAccountService.createUserFromOrganization("A005", "A005",
                                Role.SHIPPING_COORDINATOR, fedExShipping, google);

                // Asus (sales: A006/A006) | (specialist: A007/A007)
                Enterprise asus = network.getEnterpriseDir().createEnterprise("ASUS", EnterpriseType.VENDOR);
                Organization asusSales = orgService.createOrgFromEnterprise(OrganizationType.SALES, asus);
                UserAccount asusSalesManager = userAccountService.createUserFromOrganization("Asus_SalesManagerA",
                                "A006", Role.MANAGER, asusSales, asus);
                UserAccount asusSpecialist = userAccountService.createUserFromOrganization("Test", "peter",
                                Role.SPECIALIST, asusSales, asus);

                // Goolge procurement give a PO to Asus(vendor)
                new TestShipment().sendPOToVendor(googleProcurementManager,asusSalesManager);

                // Asus(vendor) give FedEx a new delivery Request
                DeliveryController deliveryController = new DeliveryController();

                ShipmentDirectory fedEx_shipmentDirectory = new ShipmentDirectory(fedEx); // one logistics will have only one shipment directory
                network.getShipmentDirectories().addShipmentDirectory(fedEx_shipmentDirectory);
                ArrayList<ShipmentItem> items = new ArrayList<>();
                ShipmentItem itemA = new ShipmentItem(new Product("Asus Laptop"), 1);
                ShipmentItem itemB = new ShipmentItem(new Product("Asus adaptor"), 1);
                items.add(itemA);
                items.add(itemB);


                deliveryController.requestShipping(items, fedEx, asusSalesManager, googleProcurementManager,
                                "2024.05.06", "2024.05.11", fedEx_shipmentDirectory, "Laptops");

                System.out.println("get po first product: " + asus.getPurchaseOrderList());

                return network;
        }
}
