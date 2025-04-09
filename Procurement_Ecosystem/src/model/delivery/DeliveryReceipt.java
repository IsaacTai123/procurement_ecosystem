/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.delivery;

import enums.ReceiptStatus;
import java.util.Date;
import model.user.UserAccount;


/**
 *
 * @author linweihong
 */
public class DeliveryReceipt {
    private UserAccount confirmedBy;
    private UserAccount reviewedBy;
    private Date receivedDate;
    private String shipmentId;
    private ReceiptStatus status;
    private String conditionNotes;

    public DeliveryReceipt(String shipmentId, UserAccount confirmedBy, Date receivedDate) {
        this.shipmentId = shipmentId;
        this.confirmedBy = confirmedBy;
        this.receivedDate = receivedDate;
        this.status = ReceiptStatus.PENDING_REVIEW;
    }

    // Getters and Setters
    public UserAccount getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(UserAccount confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public UserAccount getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(UserAccount reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }

    public String getConditionNotes() {
        return conditionNotes;
    }

    public void setConditionNotes(String conditionNotes) {
        this.conditionNotes = conditionNotes;
    }
    
    
    
}
