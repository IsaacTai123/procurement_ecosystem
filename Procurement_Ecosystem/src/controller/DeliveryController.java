/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.ecosystem.Enterprise;
import model.product.Product;
import model.workqueue.DeliveryRequest;

/**
 *
 * @author linweihong
 */
public class DeliveryController {
    
    public DeliveryRequest requestShipping(Product product, Enterprise logistics) {
        // logic to create delivery request
        DeliveryRequest deliveryReq = new DeliveryRequest();      
        deliveryReq.setProduct(product);
        deliveryReq.setLogisticsPartner(logistics);
        return deliveryReq;
    }
    
    
    
    
    
    
    
}
