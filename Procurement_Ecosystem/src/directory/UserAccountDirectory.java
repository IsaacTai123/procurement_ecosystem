/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package directory;

import enums.OrganizationType;
import enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.ecosystem.Enterprise;
import model.ecosystem.Organization;
import model.user.UserAccount;

/**
 *
 * @author tisaac
 */
public class UserAccountDirectory {

    private final List<UserAccount> userAccountList;

    public UserAccountDirectory() {
        userAccountList = new ArrayList<>();
    }

    public UserAccount createUserAccount (String name, String pw, Role type, Organization org, Enterprise ent) {
        UserAccount account = new UserAccount(name, type, pw, org, ent);
        userAccountList.add(account);
        return account;
    }
    
    public void addUserAccount(UserAccount userAccount) {
        userAccountList.add(userAccount);
    }
    
    public void deleteUserAccount(UserAccount account){
        userAccountList.remove(account);
    }
    
    public Optional<UserAccount> findUserByName(String name) {
        return userAccountList.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst();
    }

    public Optional<UserAccount> findUserById(String id) {
        return userAccountList.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst();
    }

    public List<UserAccount> getUserAccountList() {
        return userAccountList;
    }
}
