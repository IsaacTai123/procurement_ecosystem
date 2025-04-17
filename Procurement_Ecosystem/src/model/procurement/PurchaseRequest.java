/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.procurement;

import directory.PurchaseItemDirectory;
import enums.ApprovalStatus;
import enums.OrganizationType;
import enums.StepType;
import model.user.UserAccount;
import model.workqueue.WorkRequest;
import model.workqueue.WorkflowStep;

import java.util.List;
import java.util.Map;

/**
 *
 * @author tisaac
 */
public class PurchaseRequest extends WorkRequest {
    private final String description;
    private PurchaseItemDirectory purchaseItems;

//    public PurchaseRequest(String description, String purchaseItems, PurchaseItemDirectory items, int par) {
//        this.description = description;
//        this.purchaseItems = purchaseItems;
//    }
    
    public PurchaseRequest(String description) {
        this.description = description;
        this.purchaseItems = null; // just for mock testing
    }

    @Override
    protected void initWorkflowSteps() {
        // Initialize the workflow steps for the purchase request
        workflowSteps.add(new WorkflowStep(null, null, StepType.REQUESTOR)); // Requestor (null by default)
        workflowSteps.add(new WorkflowStep(OrganizationType.IT, null, StepType.APPROVAL)); // IT
        workflowSteps.add(new WorkflowStep(OrganizationType.PROCUREMENT, null, StepType.APPROVAL)); // Procurement
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

    public PurchaseItemDirectory getPurchaseItems() {
        return purchaseItems;
    }
}
