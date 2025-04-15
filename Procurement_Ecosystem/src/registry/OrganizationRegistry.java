package registry;

import model.ecosystem.Organization;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tisaac
 */
public class OrganizationRegistry {

    private final Map<String, Organization> organizationIndex;

    public OrganizationRegistry() {
        this.organizationIndex = new HashMap<>();
    }

    public void register(Organization org) {
        organizationIndex.put(org.getTypeName().toString(), org);
    }

    public void unregister(String orgName) {
        organizationIndex.remove(orgName);
    }

    public Organization findByName(String orgName) {
        return organizationIndex.get(orgName);
    }

    public void clear() {
        organizationIndex.clear();
    }
}
