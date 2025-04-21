/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.procurement;

import directory.PurchaseItemDirectory;
import enums.OrganizationType;
import enums.Role;
import enums.StepType;
import model.product.Product;
import model.product.Spec;
import model.workqueue.WorkRequest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tisaac
 */
public class PurchaseRequest extends WorkRequest {
    private final String reason;
    private PurchaseItemDirectory purchaseItems;
    private List<String> linkedRFQIds;

    public PurchaseRequest(String reason) {
        this.reason = reason;
        this.linkedRFQIds = new ArrayList<>();
        this.purchaseItems = new PurchaseItemDirectory();
    }

    @Override
    protected void initWorkflowSteps() {
        // Initialize the workflow steps for the purchase request
        // Requestor would be create during the run-time
        addStep(OrganizationType.IT, Role.MANAGER, StepType.APPROVER, false); // IT
        addStep(OrganizationType.PROCUREMENT, Role.SPECIALIST, StepType.APPROVER, false); // Procurement
    }

    public String getReason() {
        return reason;
    }

    // Calculates the total estimated budget based on the purchase items
    public double getEstimatedBudget() {
        double estimatedBudget = 0;
        for (PurchaseItem item : purchaseItems.getPurchaseItemList()) {
            estimatedBudget += item.getTotalPrice();
        }

        return estimatedBudget;
    }

    public PurchaseItemDirectory getPurchaseItems() {
        return purchaseItems;
    }

    public List<String> getLinkedRFQIds() {
        return linkedRFQIds;
    }

    public void addLinkedRFQId(String rfqId) {
        linkedRFQIds.add(rfqId);
    }

    public void addPurchaseItem(Product item, int quantity, double unitPrice, Spec spec) {
        purchaseItems.newPurchaseItem(item, quantity, unitPrice, spec);
    }

    @Override
    public String toString() {
        return id;
    }
}
