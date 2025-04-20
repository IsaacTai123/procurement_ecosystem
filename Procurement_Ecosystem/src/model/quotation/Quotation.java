/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.quotation;
import enums.ContractStatus;
import model.ecosystem.Enterprise;
import util.IdGenerateUtil;

/**
 *
 * @author qiyaochen
 */
public class Quotation {
    private String id;
    private Enterprise vendor;
    private String remarks;
    private ContractStatus status;
    private boolean selected;
    private double price;
    private String description;

    public Quotation(Enterprise vendor, String remarks, double price) {
        this.id = IdGenerateUtil.generateIdByActionAndTimestamp("Quotation");
        this.vendor = vendor;
        this.remarks = remarks;
        this.price = price;
        this.description = description;
        this.status = ContractStatus.DRAFT;
        this.selected = false;
    }

    public String getId() {
        return id;
    }

    public Enterprise getVendor() {
        return vendor;
    }

    public String getRemarks() {
        return remarks;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
