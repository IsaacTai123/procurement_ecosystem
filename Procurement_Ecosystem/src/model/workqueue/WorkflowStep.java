package model.workqueue;

import enums.ApprovalStatus;
import enums.OrganizationType;
import enums.RequestStatus;
import model.user.UserAccount;

import java.time.LocalDateTime;

/**
 * Represents a single step in a workflow approval or processing sequence.
 * <p>
 * Each step is associated with an organization (e.g., IT, Procurement),
 * and can optionally be assigned to a specific user.
 * The status and remarks provide tracking of progress and comments for the step.
 * </p>
 *
 * <p>This class is typically used as part of a list within a WorkRequest to model its flow.</p>
 *
 * @author tisaac
 */
public class WorkflowStep {
    private OrganizationType organization;   // The organization responsible for this step (e.g., IT, Procurement)
    private UserAccount assignedUser;         // The specific user assigned to handle this step (optional)
    private ApprovalStatus status;            // The current status of this step (e.g., PENDING, COMPLETED, SKIPPED)
    private boolean active;                    // Indicates whether this step is currently enabled for action
    private LocalDateTime actionTime;         // The time when this step was last acted upon (e.g., approved, rejected)
    private String remarks;                    // Comments or notes related to this step (e.g., approval reason, rejection notes)

    public WorkflowStep(OrganizationType organization, UserAccount assignedUser) {
        this.organization = organization;
        this.assignedUser = assignedUser;
        this.actionTime = LocalDateTime.now(); // Initialize with the current time
        this.status = ApprovalStatus.PENDING; // Default status
    }

    public OrganizationType getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationType organization) {
        this.organization = organization;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

