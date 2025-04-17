/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import enums.ApprovalStatus;
import enums.RequestStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.user.UserAccount;

/**
 *
 * @author linweihong
 */
public abstract class WorkRequest {
    protected String id;
    protected UserAccount sender;
    protected UserAccount receiver;
    protected Date requestDate;
    protected RequestStatus status = RequestStatus.PENDING; // enum: PENDING, APPROVED, REJECTED, COMPLETED
    protected List<WorkflowStep> workflowSteps;

    // Constructor
    public WorkRequest() {
        this.requestDate = new Date();
        this.workflowSteps = new ArrayList<>();
        initWorkflowSteps();  // // Let each subclass define its own workflow steps
    }

    /**
     * Initializes the workflow steps for this specific type of WorkRequest.
     * <p>
     * Each subclass must implement this method to define its own approval or processing workflow,
     * by adding one or more {@link WorkflowStep} instances to the {@code workflowSteps} list.
     * This method is automatically called during object construction.
     * </p>
     *
     * <p>Example usage in a subclass:</p>
     * <pre>{@code
     * @Override
     * protected void initWorkflowSteps() {
     *     workflowSteps.add(new WorkflowStep(OrganizationType.IT, null));
     *     workflowSteps.add(new WorkflowStep(OrganizationType.PROCUREMENT, null));
     * }
     * }</pre>
     */
    protected abstract void initWorkflowSteps(); // Subclasses must implement this

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public WorkflowStep getCurrentStep() {
        return workflowSteps.stream()
                .filter(s -> s.isActive() && s.getStatus() == ApprovalStatus.PENDING)
                .findFirst()
                .orElse(null);
    }
}
