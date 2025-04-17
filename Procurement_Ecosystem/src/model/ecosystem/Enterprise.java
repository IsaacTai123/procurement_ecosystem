/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ecosystem;

import directory.OrganizationDirectory;
import enums.EnterpriseType;
import model.user.UserAccount;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author linweihong
 */
public class Enterprise {
    private String name;
    private EnterpriseType type; // enum: BUYER, VENDOR, LOGISTICS
    private final OrganizationDirectory organizationList;
    
    public Enterprise(String name) {
        this.name = name;
        this.type = type;
        organizationList = new OrganizationDirectory();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnterpriseType getType() {
        return type;
    }

    public void setType(EnterpriseType type) {
        this.type = type;
    }
    
    public OrganizationDirectory getOrganizationDir() {
        return organizationList;
    }

    public List<UserAccount> getAllUserAccounts() {
        return organizationList.getOrganizationList().stream()
                .flatMap(org -> org.getUserAccountDir().getUserAccountList().stream())
                .collect(Collectors.toList());
    }
    
}
