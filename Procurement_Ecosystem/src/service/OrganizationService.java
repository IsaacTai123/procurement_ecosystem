package service;

import directory.GlobalOrganizationDirectory;
import enums.OrganizationType;
import model.ecosystem.Enterprise;
import model.ecosystem.Organization;

/**
 * @author tisaac
 */
public class OrganizationService {
    private final GlobalOrganizationDirectory globalOrgDir;

    public OrganizationService(GlobalOrganizationDirectory globalOrgDir) {
        this.globalOrgDir = globalOrgDir;
    }

    public Organization createOrgFromEnterprise(OrganizationType name, Enterprise enterprise) {
        Organization org = enterprise.getOrganizationDir().createOrganization(name, enterprise);
        globalOrgDir.AddOrganization(org);
        return org;
    }

    public void deleteOrgFromEnterprise(Organization org, Enterprise enterprise) {
        enterprise.getOrganizationDir().deleteOrganization(org);
        globalOrgDir.deleteOrganization(org.getTypeName().toString());
    }
}
