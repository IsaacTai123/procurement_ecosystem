package directory;

import model.ecosystem.Organization;
import registry.OrganizationRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * GlobalOrganizationDirectory serves as a global container for all organizations created across all enterprises.
 * <p>
 * It is not responsible for directly creating or managing organizations. Instead, it is used by
 * {@link service.OrganizationService} to store newly created organizations and synchronize them with
 * the {@link registry.OrganizationRegistry}.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *   <li>Collect and store all {@link model.Organization} instances in the system</li>
 *   <li>Enable system-wide visibility and consistency across enterprise boundaries</li>
 *   <li>Support global organization lookups via the registry</li>
 * </ul>
 *
 * <h3>Usage:</h3>
 * This directory is managed exclusively by {@code OrganizationService} and should not be updated directly.
 *
 * <h3>Example (via Service):</h3>
 * <pre>{@code
 * OrganizationService orgService = new OrganizationService(globalOrgDir);
 * orgService.createOrganization("IT", enterprise);
 * }</pre>
 *
 * @author tisaac
 */
public class GlobalOrganizationDirectory {
    private final List<Organization> organizationDir;
    private final OrganizationRegistry orgRegistry;

    public GlobalOrganizationDirectory(OrganizationRegistry orgRegistry) {
        organizationDir = new ArrayList<>();
        this.orgRegistry = orgRegistry;
    }

    public void AddOrganization(Organization org) {
        organizationDir.add(org);
        orgRegistry.register(org);
    }

    public void deleteOrganization(String orgName) {
        organizationDir.removeIf(org -> org.getName().equals(orgName));
        orgRegistry.unregister(orgName);
    }

    public List<Organization> getOrganizationDir() {
        return organizationDir;
    }
}
