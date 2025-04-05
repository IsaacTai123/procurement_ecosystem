/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import Constants.Role;

/**
 *
 * @author linweihong
 */
public class SysAdmin extends UserAccount{
    
    public SysAdmin(String name, String password){
        super(name, Role.ADMIN, password); // Calls the constructor of UserAccount
    
    }
    
}
