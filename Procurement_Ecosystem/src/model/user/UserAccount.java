/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.user;

import Constants.Role;

/**
 *
 * @author linweihong
 */
public abstract class UserAccount {
    
    // every object of subclass of UserAccount share the same counter, but capture the current counter value as its own userId 
    
    private static int userIdCounter = 0; // Static ID Counter (private: encapsulation, to avoid being modified)
    protected final int userId;
    protected String userName;
    protected Role userType;
    protected String password;

    public UserAccount (String name, Role type, String password){
       this.userId = userIdCounter++; // assign the original value, then add 1
       this.userName = name;
       this.userType = type;
       this.password = password;
       
    }
    
    
    
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
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

        return getUserName();
    }

}
