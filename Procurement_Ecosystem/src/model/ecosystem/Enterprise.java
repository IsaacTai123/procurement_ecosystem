/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ecosystem;

import Constants.EnterpriseType;
import java.util.ArrayList;

/**
 *
 * @author linweihong
 */
public class Enterprise {
    private String name;
    private ArrayList<Organization> organizations;
    private EnterpriseType type; // enum: BUYER, VENDOR, LOGISTICS
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(ArrayList<Organization> organizations) {
        this.organizations = organizations;
    }

    public EnterpriseType getType() {
        return type;
    }

    public void setType(EnterpriseType type) {
        this.type = type;
    }
    
    
    
}
