/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ecosystem;

import directory.*;
import registry.OrganizationRegistry;
import registry.UserRegistry;
import service.procurement.OrganizationService;
import service.procurement.UserAccountService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author linweihong
 */
public class Network {
    private final String name;

    // Directories
    private final OrganizationDirectory orgDir;
    private final UserAccountDirectory useraccountDir;
    private final EnterpriseDirectory enterpriseDir;
    private final ShipmentDirectories shipmentDirectories;



    // Helper classes
    private final UserRegistry userRegistry;
    private final OrganizationRegistry orgRegistry;
    private final UserAccountService userAccountService;
    private final OrganizationService orgService;


    public Network(String name) {
        this.name = name;

        this.userRegistry = new UserRegistry();
        this.orgRegistry = new OrganizationRegistry();

        this.orgDir = new OrganizationDirectory();
        this.useraccountDir = new UserAccountDirectory();
        this.enterpriseDir = new EnterpriseDirectory();

        GlobalUserAccountDirectory globalUserAccountDir = new GlobalUserAccountDirectory(userRegistry);
        this.userAccountService = new UserAccountService(globalUserAccountDir);

        GlobalOrganizationDirectory globalOrgDir = new GlobalOrganizationDirectory(orgRegistry);
        this.orgService = new OrganizationService(globalOrgDir);
        
        this.shipmentDirectories = new ShipmentDirectories();
    }

    public String getName() {
        return name;
    }

    public UserAccountDirectory getUserAccountDir() {
        return useraccountDir;
    }

    public OrganizationDirectory getOrgDir() {
        return orgDir;
    }

    public EnterpriseDirectory getEnterpriseDir() {
        return enterpriseDir;
    }

    public UserRegistry getUserRegistry() {
        return userRegistry;
    }

    public OrganizationRegistry getOrgRegistry() {
        return orgRegistry;
    }

    public UserAccountService getUserAccountService() {
        return userAccountService;
    }

    public OrganizationService getOrgService() {
        return orgService;
    }

    public ShipmentDirectories getShipmentDirectories() {
        return shipmentDirectories;
    }
    
    
}
