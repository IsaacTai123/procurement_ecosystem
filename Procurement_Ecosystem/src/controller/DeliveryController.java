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
import model.ecosystem.Network;
import model.procurement.PurchaseOrder;
import model.product.Product;
import model.user.UserAccount;
import model.workqueue.DeliveryRequest;

/**
 *
 * @author linweihong
 */
public class DeliveryController {
    

    public Map<String, Object> requestShipping(Network network, ArrayList<ShipmentItem> items, Enterprise logistics, UserAccount sender, UserAccount receiver, String shipDate, String expectedArrival, ShipmentDirectory shipments, PurchaseOrder po) {

        // logic to create delivery request and place a shipment
        DeliveryRequest deliveryReq = new DeliveryRequest();
        deliveryReq.setItems(items);
        deliveryReq.setLogisticsPartner(logistics);
        
        
        if (shipments == null) {
            // if this logistics does not have shipmentDirectory, add a new one for it
            // one logistics will have only one shipment directory
            ShipmentDirectory newShipmentDirectory = new ShipmentDirectory(logistics); 
            network.getShipmentDirectories().addShipmentDirectory(newShipmentDirectory);
            shipments = network.getShipmentDirectories().getShipmentDirectory(logistics);
        }
        
        
        Shipment shipment = shipments.newShipment(sender, receiver, shipDate, expectedArrival, po, deliveryReq);

        Map<String, Object> result = new HashMap<>();
        result.put("deliveryReq", deliveryReq);
        result.put("shipment", shipment);
        return result;


    }

   

    
    
}
