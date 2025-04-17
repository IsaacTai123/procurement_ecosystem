/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.quotation;

/**
 *
 * @author qiyaochen
 */
public class Quotation {
    private String vendorName;
    private double price;
    private String description;
    private boolean selected;

    public Quotation(String vendorName, double price, String description) {
        this.vendorName = vendorName;
        this.price = price;
        this.description = description;
        this.selected = false;
    }
    
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getVendorName() { 
        return vendorName; 
    }
    public double getPrice() { 
        return price; 
    }
    public String getDescription() { 
        return description; 
    }

    @Override
    public String toString() {
        return vendorName + " - $" + price;
    }
}
