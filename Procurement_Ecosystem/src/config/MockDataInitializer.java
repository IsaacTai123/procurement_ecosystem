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
import java.util.Map;
import model.delivery.Shipment;
import model.delivery.ShipmentDirectory;
import model.delivery.ShipmentItem;
import model.procurement.PurchaseOrder;
import model.product.Product;
import model.workqueue.DeliveryRequest;
import service.OrganizationService;
import service.UserAccountService;
import util.DBApiUtil;
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

                // GoogleX (IT: A001/A001)       | (Procurement: A002/isaac) | (Specialist: A003/peter)
                //         (Finance: A004/peter) | (Warehouse: A005/A005)
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
                
                Organization googleWarehouse = orgService.createOrgFromEnterprise(OrganizationType.WAREHOUSE, google);
                UserAccount googleWarehouseSpecialist = userAccountService.createUserFromOrganization("A005", "A005",
                                Role.SPECIALIST, googleWarehouse, google);


                // Asus (sales: A006/A006) | (specialist: A007/peter)
                Enterprise asus = network.getEnterpriseDir().createEnterprise("ASUS", EnterpriseType.VENDOR);
                Organization asusSales = orgService.createOrgFromEnterprise(OrganizationType.SALES, asus);
                UserAccount asusSalesManager = userAccountService.createUserFromOrganization("Asus_SalesManagerA",
                                "A006", Role.MANAGER, asusSales, asus);
                UserAccount asusSpecialist = userAccountService.createUserFromOrganization("asusSpecialist", "peter",
                                Role.SPECIALIST, asusSales, asus);
                
                
                // FedEx (A008/A008)
                Enterprise fedEx = network.getEnterpriseDir().createEnterprise("FedEx", EnterpriseType.LOGISTICS);
                Organization fedExShipping = orgService.createOrgFromEnterprise(OrganizationType.LOGISTICS, fedEx);
                UserAccount fedExShippingCoordinator = userAccountService.createUserFromOrganization("A008", "A008",
                                Role.SHIPPING_COORDINATOR, fedExShipping, fedEx);
                
                // test dynamically add a new enterprise > org > user account from db data
                
                Map<String, String> dbResult = DBApiUtil.getUserInfo();
                        
                String enterpriseName = dbResult.get("enterpriseName");
                String enterpriseType = dbResult.get("enterpriseType");
                String orgType = dbResult.get("orgType");
                String userName = dbResult.get("userName");
                String userPassword = dbResult.get("userPassword");
                String userType = dbResult.get("userType");
                
                // UPS (A009/A009): from db
                Enterprise newEnterprise = network.getEnterpriseDir().createEnterprise(enterpriseName, EnterpriseType.valueOf(enterpriseType.toUpperCase()));
                Organization newOrg = orgService.createOrgFromEnterprise(OrganizationType.valueOf(orgType.toUpperCase()), newEnterprise);
                UserAccount newUserAccount = userAccountService.createUserFromOrganization(userName, userPassword,
                                Role.valueOf(userType.toUpperCase()), newOrg, newEnterprise);
                
                
                
                // Goolge procurement give two PO to Asus(vendor)
                TestShipment testShipment = new TestShipment();
                PurchaseOrder po1 = testShipment.sendPOToVendor(googleProcurementManager,asusSalesManager);
                PurchaseOrder po2 = testShipment.sendPOToVendor(googleSpecialist,asusSalesManager);
                PurchaseOrder po3 = testShipment.sendPOToVendor(googleProcurementManager,asusSpecialist);
//                System.out.println("testShipment");
                
                // Asus(vendor) give FedEx a new delivery Request
                DeliveryController deliveryController = new DeliveryController();
                
                ShipmentDirectory fedEx_shipmentDirectory = new ShipmentDirectory(fedEx); // one logistics will have only one shipment directory
                network.getShipmentDirectories().addShipmentDirectory(fedEx_shipmentDirectory);
                
                ArrayList<ShipmentItem> items = new ArrayList<>();
                ShipmentItem itemA = new ShipmentItem(new Product("Asus Laptop"), 1);
                ShipmentItem itemB = new ShipmentItem(new Product("Asus adaptor"), 1);
                items.add(itemA);
                items.add(itemB);


                Map<String, Object> result = deliveryController.requestShipping(items, fedEx, asusSalesManager, googleProcurementManager,
                                "2024.05.06", "2024.05.11", fedEx_shipmentDirectory, po3);

                // connect PO to deliveryRequest

                DeliveryRequest deliveryRequest = (DeliveryRequest) result.get("request");
                Shipment newShipment = (Shipment) result.get("shipment");

                po3.setDeliveryRequest(deliveryRequest);
                po3.setShipment(newShipment);
                
                
                
                System.out.println("get po first product: " + asus.getPurchaseOrderList());

                return network;
        }
}
