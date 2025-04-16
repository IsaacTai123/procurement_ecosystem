/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.procurement;

import directory.PurchaseItemDirectory;
import enums.ApprovalStatus;
import model.user.UserAccount;
import model.workqueue.ApprovalStep;
import model.workqueue.WorkRequest;

import java.util.List;
import java.util.Map;

/**
 *
 * @author tisaac
 */
public class PurchaseRequest extends WorkRequest {
    private final String description;
    private PurchaseItemDirectory purchaseItems;
    private List<ApprovalStep> actionSteps;

//    public PurchaseRequest(String description, String purchaseItems, PurchaseItemDirectory items, int par) {
//        this.description = description;
//        this.purchaseItems = purchaseItems;
//    }
    
    public PurchaseRequest(String description) {
        this.description = description;
        this.purchaseItems = null; // just for mock testing
    }

    public String getDescription() {
        return description;
    }

    // Calculates the total estimated budget based on the purchase items
    public double getEstimatedBudget() {
        double estimatedBudget = 0;
        for (PurchaseItem item : purchaseItems.getPurchaseItemList()) {
            estimatedBudget += item.getPrice();
        }

        return estimatedBudget;
    }

    public List<ApprovalStep> getActionSteps() {
        return actionSteps;
    }

    public void setActionSteps(List<ApprovalStep> actionSteps) {
        this.actionSteps = actionSteps;
    }

    public PurchaseItemDirectory getPurchaseItems() {
        return purchaseItems;
    }
}
