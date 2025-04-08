/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import model.ecosystem.Enterprise;
import model.inventory.Product;

/**
 *
 * @author linweihong
 */
public class DeliveryRequest extends WorkRequest {
    private Product product;
    private Enterprise logisticsPartner;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Enterprise getLogisticsPartner() {
        return logisticsPartner;
    }

    public void setLogisticsPartner(Enterprise logisticsPartner) {
        this.logisticsPartner = logisticsPartner;
    }
    
    
    
}
