/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.procurement;

import directory.PurchaseItemDirectory;
import enums.OrganizationType;
import enums.Role;
import enums.StepType;
import model.workqueue.WorkRequest;
import model.workqueue.WorkflowStep;

/**
 *
 * @author tisaac
 */
public class PurchaseRequest extends WorkRequest {
    private final String reason;
    private PurchaseItemDirectory purchaseItems;

    public PurchaseRequest(String reason) {
        this.reason = reason;
    }
    
//    public PurchaseRequest(String reason) {
//        this.reason = reason;
//        this.purchaseItems = null; // just for mock testing
//    }

    @Override
    protected void initWorkflowSteps() {
        // Initialize the workflow steps for the purchase request
        addStep(null, null, StepType.REQUESTOR, true); // Requestor (null by default)
        addStep(OrganizationType.IT, Role.MANAGER, StepType.APPROVAL, false); // IT
        addStep(OrganizationType.PROCUREMENT, Role.SPECIALIST, StepType.APPROVAL, false); // Procurement
    }

    public String getReason() {
        return reason;
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
