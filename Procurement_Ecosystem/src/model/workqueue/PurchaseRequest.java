/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.workqueue;

import model.procurement.PurchaseItem;

import java.util.List;

/**
 *
 * @author linweihong
 */
public class PurchaseRequest extends WorkRequest {
    private String description;
    private List<PurchaseItem> purchaseItems;
    private double estimatedBudget; // total cost of all items

}
