/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ecosystem;

import directory.*;
import registry.OrganizationRegistry;
import registry.UserRegistry;
import service.OrganizationService;
import service.UserAccountService;

/**
 *
 * @author linweihong
 */
public class Network {
    private final String name;

    // Directories
    private final OrganizationDirectory orgDir;
    private final EnterpriseDirectory enterpriseDir;
    private final ShipmentDirectories shipmentDirectories;
    private final GlobalUserAccountDirectory globalUserAccountDir;
    private final GlobalOrganizationDirectory globalOrgDir;


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
        this.enterpriseDir = new EnterpriseDirectory();

        globalUserAccountDir = new GlobalUserAccountDirectory(userRegistry);
        this.userAccountService = new UserAccountService(globalUserAccountDir);

        globalOrgDir = new GlobalOrganizationDirectory(orgRegistry);
        this.orgService = new OrganizationService(globalOrgDir);
        
        this.shipmentDirectories = new ShipmentDirectories();
    }

    public String getName() {
        return name;
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

    public GlobalUserAccountDirectory getGlobalUserAccountDir() {
        return globalUserAccountDir;
    }

    public GlobalOrganizationDirectory getGlobalOrgDir() {
        return globalOrgDir;
    }

    public OrganizationService getOrgService() {
        return orgService;
    }

    public ShipmentDirectories getShipmentDirectories() {
        return shipmentDirectories;
    }
    
    
}
