/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ecosystem;

import common.AppContext;
import directory.OrganizationDirectory;
import directory.PurchaseOrderDirectory;
import directory.PurchaseRequestDirectory;
import enums.EnterpriseType;
import enums.OrganizationType;
import enums.Role;
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
    private PurchaseRequestDirectory purchaseRequestList;
    private PurchaseOrderDirectory purchaseOrderList;
    
    public Enterprise(String name, EnterpriseType type) {
        this.name = name;
        this.type = type; 
        organizationList = new OrganizationDirectory();
        purchaseRequestList = new PurchaseRequestDirectory();
        purchaseOrderList = new PurchaseOrderDirectory();
        
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

    public PurchaseRequestDirectory getPurchaseRequestList() {
        return purchaseRequestList;
    }

    public PurchaseOrderDirectory getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(PurchaseOrderDirectory purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }

    public UserAccount findUserByOrgRoleAndEnt(OrganizationType org, Role role) {
        return AppContext.getNetwork().getGlobalUserAccountDir()
                .findUserByOrgAndRole(org, role, this)
                .orElseThrow(() -> new IllegalStateException("User not found with given org, role, and enterprise"));
    }
    
    @Override
    public String toString() {
        return name;  
    }
}
