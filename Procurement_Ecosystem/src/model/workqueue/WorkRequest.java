/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import enums.RequestStatus;
import java.util.Date;
import model.user.UserAccount;

/**
 *
 * @author linweihong
 */
public abstract class WorkRequest {
    private String id;
    private UserAccount sender;
    private UserAccount receiver;
    private Date requestDate;
    private RequestStatus status = RequestStatus.PENDING; // enum: PENDING, APPROVED, REJECTED, COMPLETED
    

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
   
    
    
    
    
}
