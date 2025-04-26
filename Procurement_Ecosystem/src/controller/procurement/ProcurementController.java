/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.procurement;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import common.AppContext;
import common.Result;
import common.dto.SpecDTO;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseItem;
import model.product.Spec;
import model.quotation.Quotation;
import model.quotation.RFQ;
import model.procurement.PurchaseRequest;
import service.procurement.RFQService;
import util.ResultUtil;

import javax.swing.*;

/**
 *
 * @author tisaac
 */
public class ProcurementController {

    private RFQService getService() {
        return new RFQService(
                AppContext.getUser(),
                AppContext.getNetwork()
        );
    }

    private static class ProcurementControllerHolder {
        private static final ProcurementController INSTANCE = new ProcurementController();
    }

    public static ProcurementController getInstance() {
        return ProcurementControllerHolder.INSTANCE;
    }

    public void handleRFQForm(PurchaseRequest pr, ArrayList<Enterprise> vendors) {
        // create RFQRequest
    }

    // TODO: Should use DTO to prevent they didn't submit the form
    public Result<Void> handleRFQSubmit(RFQ rfq, String vendor, String deadline, String remark) {
        rfq.setRemarks(remark);

        try {
            LocalDate localDate = LocalDate.parse(deadline);
            rfq.setDeadline(localDate);
        } catch (DateTimeParseException e) {
            return ResultUtil.failure("Invalid date format. Please use yyyy-MM-dd.");
        }
        return getService().submitRFQ(rfq, vendor);
    }

    public Result<Void> handleQuotationReject(Quotation q, String remark) {
        if (remark == null || remark.isEmpty()) {
            return ResultUtil.failure("Remarks cannot be empty.");
        }
        return getService().rejectQuotation(q, remark);
    }

    public Result<Void> handleQuotationForward(Quotation q, RFQ rfq) {
        return getService().fowardQuotation(q, rfq);
    }

    public Result<Void> handleQuotationAccept(Quotation q, String remark) {
        String tmp = remark.trim();
        if (remark.isEmpty()) {
            return ResultUtil.failure("Remarks cannot be empty.");
        }
        q.setRemarks(tmp);

        return getService().acceptQuotation(q);
    }

    public Result<Void> handlePOSubmit(RFQ rfq, Quotation quotation, String price, String remarks, String address) {
        // Validate price
        double parsedPrice;
        try {
            parsedPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            return ResultUtil.failure("Invalid price format. Please enter a valid number.");
        }

        // Validate remarks
        if (remarks == null || remarks.isEmpty()) {
            return ResultUtil.failure("Remarks cannot be empty.");
        }

        // Submit PO
        return getService().submitePO(rfq, quotation, parsedPrice, remarks, address);
    }

    public void compareQuotations(ArrayList<Quotation> quotations) {
        // logic to select quotation
    }

    public Result<Void> handleUpdatePurchaseItem(PurchaseItem item, SpecDTO specDTO) {
        // Validate spec data
        Spec spec = new Spec(specDTO.getModelNumber(), specDTO.getColor(), specDTO.getSize(),
                specDTO.getMaterial(), specDTO.getCategory(), specDTO.getRemarks());

        item.setSpec(spec);
        return ResultUtil.success("Purchase item updated successfully.");
    }
}
