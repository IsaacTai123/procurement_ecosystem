/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import model.delivery.Shipment;
import model.delivery.ShipmentDirectory;
import model.delivery.ShipmentItem;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseOrder;
import model.product.Product;
import model.user.UserAccount;
import model.workqueue.DeliveryRequest;

/**
 *
 * @author linweihong
 */
public class DeliveryController {
    

    public Map<String, Object> requestShipping(ArrayList<ShipmentItem> items, Enterprise logistics, UserAccount sender, UserAccount receiver, String shipDate, String expectedArrival, ShipmentDirectory shipments, PurchaseOrder po) {

        // logic to create delivery request and place a shipment
        DeliveryRequest deliveryReq = new DeliveryRequest();
        deliveryReq.setItems(items);
        deliveryReq.setLogisticsPartner(logistics);
        
        Shipment shipment = shipments.newShipment(sender, receiver, shipDate, expectedArrival, po, deliveryReq);

        Map<String, Object> result = new HashMap<>();
        result.put("deliveryReq", deliveryReq);
        result.put("shipment", shipment);
        return result;


    }

   

    
    
}
