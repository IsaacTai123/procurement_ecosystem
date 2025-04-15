package directory;

import common.NetworkManager;
import model.ecosystem.Ecosystem;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.ecosystem.Organization;
import registry.OrganizationRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tisaac
 */
public class OrganizationDirectory {
    private final List<Organization> organizationList;

    public OrganizationDirectory() {
        this.organizationList = new ArrayList<>();
    }

    public Organization createOrganization(String name, Enterprise enterprise) {
        Organization org = new Organization(name, enterprise);
        organizationList.add(org);
        return org;
    }

    public void deleteOrganization(Organization org) {
        organizationList.remove(org);
    }


    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    public Organization findOrganizationByName(String name) {
        return organizationList.stream()
                .filter(org -> org.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
