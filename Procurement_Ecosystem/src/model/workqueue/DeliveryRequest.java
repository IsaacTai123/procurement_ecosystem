/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import java.util.ArrayList;
import java.util.HashMap;
import model.delivery.ShipmentItem;
import model.ecosystem.Enterprise;
import model.product.Product;

/**
 *
 * @author linweihong
 */
public class DeliveryRequest extends WorkRequest {
    private ArrayList<ShipmentItem> items;
    private Enterprise logisticsPartner;

    @Override
    protected void initWorkflowSteps() {

    }
    
    public ArrayList<ShipmentItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShipmentItem> items) {
        this.items = items;
    }

    public Enterprise getLogisticsPartner() {
        return logisticsPartner;
    }

    public void setLogisticsPartner(Enterprise logisticsPartner) {
        this.logisticsPartner = logisticsPartner;
    }
    
    
    
}
