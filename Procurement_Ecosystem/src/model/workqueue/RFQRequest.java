/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import java.util.ArrayList;
import model.ecosystem.Enterprise;

/**
 *
 * @author linweihong
 */
public class RFQRequest extends WorkRequest {
    private PurchaseRequest linkedPR;
    private ArrayList<Enterprise> vendors;
    // getters, setters
}
