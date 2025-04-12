/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.delivery;

import java.util.ArrayList;
import java.util.Date;
import model.user.UserAccount;

/**
 *
 * @author linweihong
 */
public class ShipmentHistory {
    private ArrayList<Shipment> shipments;
    
    
    public ShipmentHistory() {
        shipments = new ArrayList();
    }
    
    public void newShipment(UserAccount sender, UserAccount receiver, Date shipDate, Date expectedArrival) {
        Shipment shipment = new Shipment(sender, receiver, shipDate, expectedArrival);
        shipments.add(shipment);
    }
    
    
}
