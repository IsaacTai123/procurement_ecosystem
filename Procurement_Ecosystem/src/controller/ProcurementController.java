/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.ecosystem.Enterprise;
import model.user.UserAccount;
import model.vendor.Quotation;
import model.workqueue.PurchaseRequest;

/**
 *
 * @author linweihong
 */
public class ProcurementController {
    public void createPR(String description, double budget, UserAccount user) {
        // create PurchaseRequest
    }
    public void issueRFQ(PurchaseRequest pr, ArrayList<Enterprise> vendors) {
        // create RFQRequest
    }
    public void compareQuotations(ArrayList<Quotation> quotations) {
        // logic to select quotation
    }
}
