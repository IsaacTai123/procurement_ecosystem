/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.procurement;

import java.util.ArrayList;
import java.util.List;

import common.Result;
import common.dto.PurchaseItemDTO;
import common.dto.PurchaseRequestDTO;
import common.dto.SpecDTO;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseItem;
import model.product.Product;
import model.product.Spec;
import model.user.UserAccount;
import model.vendor.Quotation;
import model.procurement.PurchaseRequest;
import service.procurement.PurchaseRequestService;

/**
 *
 * @author tisaac
 */
public class ProcurementController {

    public Result<Void> handlePRConfirm() {
        // logic to confirm PR
        return null;
    }

    public void issueRFQ(PurchaseRequest pr, ArrayList<Enterprise> vendors) {
        // create RFQRequest
    }

    public void compareQuotations(ArrayList<Quotation> quotations) {
        // logic to select quotation
    }
}
