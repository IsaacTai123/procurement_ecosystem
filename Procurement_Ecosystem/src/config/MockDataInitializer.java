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


import directory.OrganizationDirectory;
import enums.EnterpriseType;
import enums.Role;
import model.ecosystem.Ecosystem;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.ecosystem.Organization;
import model.user.UserAccount;
import directory.UserAccountDirectory;
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

        
        
        return network;
    }
}
