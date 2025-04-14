/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.delivery;

import enums.ShipmentStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
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
    private Date shipDate;
    private Date expectedArrival;
    private ShipmentStatus status;
    private UserAccount sender;
    private UserAccount receiver;
    private String title;
    private DeliveryRequest deliveryReq;

    public Shipment(UserAccount sender, UserAccount receiver, Date shipDate, Date expectedArrival, String title, DeliveryRequest deliveryReq) {
        this.trackingNumber = UUID.randomUUID().toString().substring(0, 10).toUpperCase(); // Example: "B6D4D2A7-6"
        this.deliveryReq = deliveryReq;
        this.items = deliveryReq.getItems();
        this.sender = sender;
        this.receiver = receiver;
        this.shipDate = shipDate;
        this.expectedArrival = expectedArrival;
        this.status = ShipmentStatus.PLACED;
        this.title = title;
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

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getExpectedArrival() {
        return expectedArrival;
    }

    public void setExpectedArrival(Date expectedArrival) {
        this.expectedArrival = expectedArrival;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }
    
    

    @Override
    public String toString() {
        return trackingNumber;
    }
    
    
    
    
}
