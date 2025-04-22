/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import common.AppContext;
import common.Result;
import directory.GlobalUserAccountDirectory;
import enums.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ecosystem.Enterprise;
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

    public void markAsCompleted() {
        // Mark the purchase request as completed
        setStatus(RequestStatus.COMPLETED);
    }

    public void markAsRejected() {
        // Mark the purchase request as rejected
        setStatus(RequestStatus.REJECTED);
    }

    public void markAsApproved() {
        // Mark the purchase request as approved
        setStatus(RequestStatus.APPROVED);
    }

    public void markAsReceived() {
        // Mark the purchase request as received
        setStatus(RequestStatus.RECEIVED);
    }

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

    public WorkflowStep getNextPendingStep(OrganizationType orgType) {
        return workflowSteps.stream()
                .filter(s -> !s.isActive() && s.getStatus() == ApprovalStatus.PENDING)
                .filter(s -> s.getOrganizationType() == orgType)
                .findFirst()
                .orElse(null);
    }

    public WorkflowStep getPendingStep() {
        return workflowSteps.stream()
                .filter(s -> s.getStatus() == ApprovalStatus.PENDING)
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

    // need to add enterprise as a condition
    public Result<Void> advanceToNextStep(UserAccount currentUser, GlobalUserAccountDirectory allUsersDir,
                                          String remarks, ApprovalStatus status, OrganizationType nextOrgType, EnterpriseType nextStepEnt) {
        WorkflowStep current = getCurrentActiveStep();
        if (current == null) { return ResultUtil.failure("No active step available or found"); }

        // Check if current user is authorized to operate this step
        if (current.getAssignedUser() == null || !current.getAssignedUser().getUserId().equals(currentUser.getUserId())) {
            return ResultUtil.failure("You are not authorized to perform this action");
        }

        // Set the current step to completed
        current.setStatus(status);
        current.setActive(false);
        current.setActionTime(LocalDateTime.now());
        current.setRemarks(remarks);

        WorkflowStep next = getNextPendingStep(nextOrgType);
        if (next == null) {
            return ResultUtil.success("All steps completed");
        }
        next.resolveAssignedUser(allUsersDir, nextStepEnt);
        next.setActive(true);

        return ResultUtil.success("Successfully submit and advanced to the next step");
    }

    public Result<Void> rejectCurrentStepAndTerminate(UserAccount currentUser, String remarks) {
        WorkflowStep current = getCurrentActiveStep();
        if (current == null) {
            return ResultUtil.failure("No active step found.");
        }

        // Check if the current user is authorized to perform this step
        if (current.getAssignedUser() == null || !current.getAssignedUser().getUserId().equals(currentUser.getUserId())) {
            return ResultUtil.failure("You are not authorized to reject this step.");
        }

        // Mark the current step as rejected and deactivate it
        current.markAsRejected();
        current.setActive(false);
        current.setRemarks(remarks);

        // Optionally mark the whole request as rejected (if applicable)
        this.markAsRejected(); // Assuming your WorkRequest has a status field

        return ResultUtil.success("Step rejected. Workflow has been terminated.");
    }

    /**
     * Forcefully completes the current active workflow step without verifying user identity
     * or transitioning to the next step. This method is intended for exceptional cases
     * where the normal approval flow should be bypassed (e.g., skipping IT approval).
     *
     * @param remarks any comments or explanation for forcefully completing the step
     * @param status  the status to assign to the current step (e.g., APPROVED, REJECTED, OMITTED)
     * @return a Result indicating success or failure of the operation
     */
    public Result<Void> forceCompleteCurrentStep(String remarks, ApprovalStatus status) {
        WorkflowStep current = getCurrentActiveStep();
        if (current == null) {
            return ResultUtil.failure("No active step found");
        }

        current.setStatus(status);
        current.setActive(false);
        current.setRemarks(remarks);
        current.setActionTime(LocalDateTime.now());

        return ResultUtil.success("Current step completed without user or next step validation");
    }

    /**
     * Forwards the active flag from the current workflow step to the next pending step.
     * This method does not perform any validation or status updates. It is intended for
     * cases where the flow needs to be manually pushed forward without changing approval state.
     *
     * @param nextOrgType the organization type used to determine the next step
     * @param allUsersDir user directory used to resolve the assigned user for the next step
     * @return a Result indicating success or failure of the operation
     */
    public Result<Void> forwardToNextStep(OrganizationType nextOrgType, GlobalUserAccountDirectory allUsersDir,
                                          EnterpriseType nextStepEnt) {
        WorkflowStep next = getNextPendingStep(nextOrgType);
        WorkflowStep current = getCurrentActiveStep();

        // Check if there are any pending steps to forward
        if (next == null) {
            return ResultUtil.failure("You are the last step, no need to forward");
        }

        next.resolveAssignedUser(allUsersDir, nextStepEnt);
        next.setActive(true);

        // Deactivate the current step
        if (current == null) {
            return ResultUtil.failure("No active step found");
        }
        current.setActive(false);

        return ResultUtil.success("Workflow forwarded to the next step");
    }

    public Boolean isCompleted() {
        return workflowSteps.stream()
                .noneMatch(step ->
                        step.getStatus() == ApprovalStatus.PENDING ||
                        step.getStatus() == ApprovalStatus.REJECTED);
    }
}
