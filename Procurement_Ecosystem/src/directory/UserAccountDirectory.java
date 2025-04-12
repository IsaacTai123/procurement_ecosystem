/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package directory;

import enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.SysAdmin;
import model.user.UserAccount;
import registry.UserRegistry;

/**
 *
 * @author tisaac
 */
public class UserAccountDirectory {

    private final List<UserAccount> userAccountList;

    public UserAccountDirectory() {
        userAccountList = new ArrayList<>();
    }

    public UserAccount createUserAccount (String name, String pw, Role type) {
        UserAccount account = null;

        switch (type) {
            case SYS_ADMIN:
                account = new SysAdmin(name, pw);
                break;
            default:

        }
        
        userAccountList.add(account);
        return account;
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
