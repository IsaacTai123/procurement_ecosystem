/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ecosystem;

import java.util.ArrayList;
import java.util.List;

import directory.UserAccountDirectory;
import enums.Role;
import model.user.UserAccount;

/**
 *
 * @author linweihong
 */
public class Organization {
    private final String name;
    private final UserAccountDirectory userAccountList;

    public Organization(String name) {
        this.name = name;
        this.userAccountList = new UserAccountDirectory();
    }

    public String getName() {
        return name;
    }

    public UserAccountDirectory getUserAccountDir() {
        return userAccountList;
    }
}
