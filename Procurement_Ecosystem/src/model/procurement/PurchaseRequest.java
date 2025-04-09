/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.procurement;

import model.workqueue.WorkRequest;

import java.util.List;

/**
 *
 * @author tisaac
 */
public class PurchaseRequest extends WorkRequest {
    private final String description;
    private List<PurchaseItem> purchaseItems;

    public PurchaseRequest(String description, List<PurchaseItem> purchaseItems) {
        this.description = description;
        this.purchaseItems = purchaseItems;
    }

    public String getDescription() {
        return description;
    }

    // Calculates the total estimated budget based on the purchase items
    public double getEstimatedBudget() {
        double estimatedBudget = 0;
        for (PurchaseItem item : purchaseItems) {
            estimatedBudget += item.getPrice();
        }

        return estimatedBudget;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }
}
