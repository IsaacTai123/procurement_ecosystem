package service;

import common.NetworkManager;
import model.ecosystem.Ecosystem;
import model.ecosystem.Organization;
import model.user.UserAccount;

import java.util.Optional;

/**
 * @author tisaac
 */
public class UserLookUpService {
    private final Ecosystem ecosystem = Ecosystem.getInstance();

    public UserLookUpService() {
    }

    public Optional<UserAccount> findUserByUserId(String userId, String networkName) {
        return NetworkManager.findByName(networkName)
                .flatMap(network ->
                        network.getEnterpriseDir().getEnterprisesList().stream()
                                .flatMap(ent -> ent.getOrganizationDir().getOrganizationList().stream())
                                .flatMap(org -> org.getUserAccountDir().getUserAccountList().stream())
                                .filter(user -> user.getUserId().equals(userId))
                                .findFirst()
                );
    }

    public Optional<Organization> findOrganizationByName(String orgName, String networkName) {
        return NetworkManager.findByName(networkName)
                .flatMap(network ->
                        network.getEnterpriseDir().getEnterprisesList().stream()
                                .flatMap(ent -> ent.getOrganizationDir().getOrganizationList().stream())
                                .filter(org -> org.getTypeName().equals(orgName))
                                .findFirst()
                );
    }
}
