package directory;

import enums.OrganizationType;
import model.ecosystem.Enterprise;
import model.ecosystem.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author tisaac
 */
public class OrganizationDirectory {
    private final List<Organization> organizationList;

    public OrganizationDirectory() {
        this.organizationList = new ArrayList<>();
    }

    public Organization createOrganization(OrganizationType name, Enterprise enterprise) {
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
        System.out.println("organization List size " + organizationList.size());
        return organizationList.stream()
                .filter(org -> Objects.equals(org.getTypeName().toString(), name))
                .findFirst()
                .orElse(null);
    }
}
