package model.workqueue;

import directory.GlobalUserAccountDirectory;
import enums.*;
import model.ecosystem.Enterprise;
import model.user.UserAccount;

import java.time.LocalDateTime;

/**
 * Represents a single step in a workflow approval or processing sequence.
 *
 * <p>
 * Each step is associated with an {@link OrganizationType} (e.g., IT, Procurement),
 * and may optionally be assigned to a specific {@link UserAccount} once the responsible user is determined.
 * </p>
 *
 * <p>
 * The {@link StepType} distinguishes between different kinds of workflow steps,
 * such as the initial requester action or subsequent approval stages.
 * </p>
 *
 * <p>
 * This class tracks important metadata including the step’s {@link ApprovalStatus},
 * remarks for audit purposes, and timestamps for when actions are performed.
 * </p>
 *
 * <p>
 * Typically used as part of a list within a {@code WorkRequest} to define its approval or processing flow.
 * </p>
 *
 * @author tisaac
 */
public class WorkflowStep {
    private OrganizationType orgType;   // The organization responsible for this step (e.g., IT, Procurement)
    private Role requiredRole;
    private UserAccount assignedUser;         // The specific user assigned to handle this step (optional)
    private StepType stepType;               // The type of step (e.g., REQUESTOR, APPROVER)
    private ApprovalStatus status;            // The current status of this step (e.g., PENDING, COMPLETED, SKIPPED)
    private boolean active;                    // Indicates whether this step is currently enabled for action
    private LocalDateTime actionTime;         // The time when this step was last acted upon (e.g., approved, rejected)
    private String remarks;                    // Comments or notes related to this step (e.g., approval reason, rejection notes)

    public WorkflowStep(OrganizationType orgType, Role requiredRole, StepType stepType, boolean isActive) {
        this.orgType = orgType;
        this.requiredRole = requiredRole;
        this.stepType = stepType;
        this.actionTime = LocalDateTime.now(); // Initialize with the current time
        this.status = ApprovalStatus.PENDING; // Default status
        this.active = isActive; // Set the active status
    }

    public OrganizationType getOrganizationType() {
        return orgType;
    }

    public void setOrgType(OrganizationType orgType) {
        this.orgType = orgType;
    }

    public OrganizationType getOrgType() {
        return orgType;
    }

    public Role getRequiredRole() {
        return requiredRole;
    }

    public void setRequiredRole(Role requiredRole) {
        this.requiredRole = requiredRole;
    }

    public UserAccount getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(UserAccount assignedUser) {
        this.assignedUser = assignedUser;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public StepType getStepType() {
        return stepType;
    }

    public void resolveAssignedUser(GlobalUserAccountDirectory allUsersDir, Enterprise ent) {
        allUsersDir.findUserByOrgAndRole(orgType, requiredRole, ent)
                .ifPresent(this::setAssignedUser);
    }
}

