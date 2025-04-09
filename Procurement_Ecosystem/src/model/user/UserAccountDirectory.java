/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.user;

import enums.Role;
import java.util.ArrayList;
import model.SysAdmin;

/**
 *
 * @author weihonglin
 */
public class UserAccountDirectory {

    ArrayList<UserAccount> useraccountlist;

    public UserAccountDirectory() {

        useraccountlist = new ArrayList();

    }

    public UserAccount newUserAccount(String name, Role type, String pw) {
        UserAccount account = null;

        switch (type) {
            case ADMIN:
                account = new SysAdmin(name, pw);
                break;
//            case BRANCHMANAGER:
//                account = new BranchManager(name, pw);
//                break;
//            case CUSTOMER:
//                account = new Customer(name, pw);
                
        }
        
        
        useraccountlist.add(account);
        return account;

    }
    
    public void deleteUserAccount(UserAccount account){
        useraccountlist.remove(account);
        
    }
    
    public UserAccount searchUserByName(String name) {

        for (UserAccount userAccount : useraccountlist) {
            if (userAccount.getUserName().equals(name)) {
                return userAccount;
            }
        }
        return null;
    }

    
    
    
    
    
    public ArrayList<UserAccount> getUserAccountList() {
        return useraccountlist;
    }
    
    
    
    
}
