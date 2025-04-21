/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.delivery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseOrder;
import model.user.UserAccount;
import model.workqueue.DeliveryRequest;

/**
 *
 * @author linweihong
 */
public class ShipmentDirectory {
    
    private ArrayList<Shipment> shipments;
    private Enterprise enterprise;
    
    
    public ShipmentDirectory(Enterprise enterprise) {
        shipments = new ArrayList();
        this.enterprise = enterprise;
    }
    

    public Shipment newShipment(UserAccount sender, UserAccount receiver, String shipDate, String expectedArrival, PurchaseOrder po, DeliveryRequest deliveryReq) {
        Shipment shipment = new Shipment(sender, receiver, shipDate, expectedArrival, po, deliveryReq);

        shipments.add(shipment);
        
        return shipment;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public ArrayList<Shipment> getShipments() {
        return shipments;
    }
    
    public List<Shipment> getShipmentsByBuyerEnterprise(Enterprise buyerEnterprise) {
        return shipments.stream()
                .filter(shipment -> shipment.getReceiverEnterprise().equals(buyerEnterprise))
                .toList(); // Use .collect(Collectors.toList()) if not on Java 17+
    }
    
    
    
    
}
