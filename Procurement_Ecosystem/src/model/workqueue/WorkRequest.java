/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import Constants.RequestStatus;
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
    private RequestStatus status;
    // enum: PENDING, APPROVED, REJECTED, COMPLETED
    // getters, setters
}
