/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.user;

import enums.Role;
import model.ecosystem.Enterprise;
import model.ecosystem.Organization;
import util.IdGenerateUtil;

/**
 *
 * @author linweihong
 */
public class UserAccount {
    
    // every object of subclass of UserAccount share the same counter, but capture the current counter value as its own userId 

    private final String userId; // Unique ID for each user
    private String userName;
    private Role userType;
    private String password;
    private Organization org;
    private Enterprise enterprise;
    private String email;


    public UserAccount (String name, Role type, String password, String email) {
        this.userId = IdGenerateUtil.generateUserId();
        this.userName = name;
        this.userType = type;
        this.password = password;
        this.email = email;
    }

    public UserAccount (String name, Role type, String password, Organization org, Enterprise enterprise) {
        this.userId = IdGenerateUtil.generateUserId();
        this.userName = name;
        this.userType = type;
        this.password = password;
        this.org = org;
        this.enterprise = enterprise;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return userName;
    }
    
    public Role getUserType() {
        return userType;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(Role userType) {
        this.userType = userType;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public Enterprise getEnterprise() {
        return org.getEnterprise();
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    @Override
    public String toString() {
        return getUsername();
    }

}
