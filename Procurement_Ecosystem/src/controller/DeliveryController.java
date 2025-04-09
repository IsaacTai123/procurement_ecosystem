/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import model.delivery.Shipment;
import model.delivery.ShipmentHistory;
import model.delivery.ShipmentItem;
import model.ecosystem.Enterprise;
import model.product.Product;
import model.user.UserAccount;
import model.workqueue.DeliveryRequest;

/**
 *
 * @author linweihong
 */
public class DeliveryController {
    
    public DeliveryRequest requestShipping(ArrayList<ShipmentItem> items, Enterprise logistics, UserAccount sender, UserAccount receiver, Date shipDate, Date expectedArrival, ShipmentHistory shipments) {
        // logic to create delivery request and place a shipment
        DeliveryRequest deliveryReq = new DeliveryRequest();
        deliveryReq.setItems(items);
        deliveryReq.setLogisticsPartner(logistics);
        
        shipments.newShipment(sender, receiver, shipDate, expectedArrival);

        return deliveryReq;
    }

   

    
    
}
