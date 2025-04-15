/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ecosystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import directory.UserAccountDirectory;
import enums.Role;
import model.user.UserAccount;

/**
 *
 * @author linweihong
 */
public class Organization {
    private final String name;
    private final Enterprise enterprise;
    private final UserAccountDirectory userAccountList;

    public Organization(String name, Enterprise enterprise) {
        this.name = name;
        this.enterprise = enterprise;
        this.userAccountList = new UserAccountDirectory();
    }

    public String getName() {
        return name;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public UserAccountDirectory getUserAccountDir() {
        return userAccountList;
    }

    public List<UserAccount> getAllUserAccounts() {
        return userAccountList.getUserAccountList().stream()
                .collect(Collectors.toList());
    }
}
