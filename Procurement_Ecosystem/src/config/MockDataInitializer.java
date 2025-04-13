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


import enums.EnterpriseType;
import enums.Role;
import model.ecosystem.Ecosystem;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.ecosystem.Organization;
import model.user.UserAccount;
import service.OrganizationService;
import service.UserAccountService;


/**
 *
 * @author tisaac
 */
public class MockDataInitializer {

    public static Network initialize() {
        Ecosystem eco = Ecosystem.getInstance();
        eco.init(new UserAccount("admin", Role.SYS_ADMIN, "admin"));

        Network network = new Network("Tech");
        eco.AddNetwork(network);

        OrganizationService orgService = network.getOrgService();
        UserAccountService userAccountService = network.getUserAccountService();

        // Google
        Enterprise google = network.getEnterpriseDir().createEnterprise("Google", EnterpriseType.BUYER);

        Organization googleIT = orgService.createOrgFromEnterprise("IT", google);
        UserAccount googleITManager = userAccountService.createUserFromOrganization("Alvin", "Alvin", Role.MANAGER, googleIT);

        Organization googleProcurement = orgService.createOrgFromEnterprise("Procurement", google);
        UserAccount googleProcurementManager = userAccountService.createUserFromOrganization("isaac", "isaac", Role.MANAGER, googleProcurement);

        
        
        return network;
    }
}
