/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ecosystem;

import java.util.List;
import java.util.stream.Collectors;

import directory.UserAccountDirectory;
import enums.OrganizationType;
import model.user.UserAccount;

/**
 *
 * @author linweihong
 */
public class Organization {
    private final OrganizationType name;
    private final Enterprise enterprise;
    private final UserAccountDirectory userAccountList;

    public Organization(OrganizationType name, Enterprise enterprise) {
        this.name = name;
        this.enterprise = enterprise;
        this.userAccountList = new UserAccountDirectory();
    }

    public OrganizationType getTypeName() {
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
