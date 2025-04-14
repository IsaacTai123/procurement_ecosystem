/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.delivery;

import java.util.ArrayList;
import java.util.Date;
import model.ecosystem.Enterprise;
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
    

    public void newShipment(UserAccount sender, UserAccount receiver, Date shipDate, Date expectedArrival, String title, DeliveryRequest deliveryReq) {
        Shipment shipment = new Shipment(sender, receiver, shipDate, expectedArrival, title, deliveryReq);

        shipments.add(shipment);
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public ArrayList<Shipment> getShipments() {
        return shipments;
    }
    
    
    
    
    
    
}
