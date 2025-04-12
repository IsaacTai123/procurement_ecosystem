/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.user;

import enums.Role;

/**
 *
 * @author linweihong
 */
public abstract class UserAccount {
    
    // every object of subclass of UserAccount share the same counter, but capture the current counter value as its own userId 

    protected final String userId; // Unique ID for each user
    protected String userName;
    protected Role userType;
    protected String password;

    public UserAccount (String name, Role type, String password) {
        this.userId = "";
        this.userName = name;
        this.userType = type;
        this.password = password;
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

    
    
    
    @Override
    public String toString() {
        return getUsername();
    }

}
