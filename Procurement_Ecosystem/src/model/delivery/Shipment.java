/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.delivery;

import enums.ShipmentStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseOrder;
import model.product.Product;
import model.user.UserAccount;
import model.workqueue.DeliveryRequest;

/**
 *
 * @author linweihong
 */
public class Shipment {

    private String trackingNumber;
    private ArrayList<ShipmentItem> items;
    private String shipDate;
    private String expectedArrival;
    private ShipmentStatus status;
    private UserAccount sender; // asus sales
    private UserAccount receiver; // google procurement
    private PurchaseOrder po;
    private String purchaseOrderID;
    private DeliveryRequest deliveryReq;

    public Shipment(UserAccount sender, UserAccount receiver, String shipDate, String expectedArrival, PurchaseOrder po, DeliveryRequest deliveryReq) {
        this.trackingNumber = UUID.randomUUID().toString().substring(0, 10).toUpperCase(); // Example: "B6D4D2A7-6"
        this.deliveryReq = deliveryReq;
        this.items = deliveryReq.getItems();
        this.sender = sender;
        this.receiver = receiver;
        this.shipDate = shipDate;
        this.expectedArrival = expectedArrival;
        this.status = ShipmentStatus.PLACED;
        this.po = po;
        this.purchaseOrderID = po.getId();
        
    }

    // Getters and Setters
    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void addItem(Product product, int quantity) {
        this.items.add(new ShipmentItem(product, quantity));
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getExpectedArrival() {
        return expectedArrival;
    }

    public void setExpectedArrival(String expectedArrival) {
        this.expectedArrival = expectedArrival;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public String getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(String purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

   

    public ArrayList<ShipmentItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShipmentItem> items) {
        this.items = items;
    }

    public UserAccount getSender() {
        return sender;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }
    
    public Enterprise getReceiverEnterprise() {
        return receiver.getEnterprise();
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    public DeliveryRequest getDeliveryReq() {
        return deliveryReq;
    }
    
    
    
    

    @Override
    public String toString() {
        return trackingNumber;
    }
    
    
    
    
}
