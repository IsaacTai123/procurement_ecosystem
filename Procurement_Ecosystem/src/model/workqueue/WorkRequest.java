/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import common.Result;
import directory.GlobalUserAccountDirectory;
import enums.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.user.UserAccount;
import util.IdGenerateUtil;
import util.ResultUtil;

/**
 *
 * @author linweihong
 */
public abstract class WorkRequest {
    protected String id;
    protected UserAccount sender;
    protected Date requestDate;
    protected RequestStatus status = RequestStatus.PENDING; // enum: PENDING, APPROVED, REJECTED, COMPLETED
    protected List<WorkflowStep> workflowSteps;

    // Constructor
    public WorkRequest() {
        this.id = IdGenerateUtil.generateWorkRequestId();
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

    protected void addStep(OrganizationType org, Role role, StepType type, boolean isActive) {
        WorkflowStep step = new WorkflowStep(org, role, type, isActive);
        workflowSteps.add(step);
    }

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

    public List<WorkflowStep> getWorkflowSteps() {
        return workflowSteps;
    }

    public WorkflowStep getCurrentActiveStep() {
        return workflowSteps.stream()
                .filter(s -> s.isActive() && s.getStatus() == ApprovalStatus.PENDING)
                .findFirst()
                .orElse(null);
    }

    public WorkflowStep getNextPendingStep() {
        return workflowSteps.stream()
                .filter(s -> !s.isActive() && s.getStatus() == ApprovalStatus.PENDING)
                .findFirst()
                .orElse(null);
    }

    public void createRequesterStep(UserAccount requestor) {
        WorkflowStep step = new WorkflowStep(null, null, StepType.REQUESTOR, true);
        setSender(requestor);
        step.setAssignedUser(requestor);
        step.setOrgType(requestor.getOrg().getTypeName());
        step.setRequiredRole(requestor.getUserType());
        step.setActive(true);
        workflowSteps.add(0, step); // Add to the beginning of the list to maintain approval sequence
    }

    public Result<Void> advanceToNextStep(GlobalUserAccountDirectory allUsersDir, String remarks, ApprovalStatus status) {
        WorkflowStep current = getCurrentActiveStep();
        if (current == null) { return ResultUtil.failure("No active step available or found"); }

        // Set the current step to completed
        current.setStatus(status);
        current.setActive(false);
        current.setActionTime(LocalDateTime.now());
        current.setRemarks(remarks);

        WorkflowStep next = getNextPendingStep();
        if (next == null) {
            return ResultUtil.failure("No next step available or found");
        }
        next.resolveAssignedUser(allUsersDir);
        next.setActive(true);

        return ResultUtil.success("Successfully advanced to the next step");
    }
}
