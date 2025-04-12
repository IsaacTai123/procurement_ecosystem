package model.workqueue;

import enums.ApprovalStatus;

import java.time.LocalDateTime;

/**
 * @author tisaac
 */
public class ApprovalStep {
    private String organization;
    private String approverName;
    private ApprovalStatus status;
    private LocalDateTime actionTime;

    public ApprovalStep(String organization, String approverName, ApprovalStatus status) {
        this.organization = organization;
        this.approverName = approverName;
        this.status = status;
        this.actionTime = LocalDateTime.now();
    }
}
