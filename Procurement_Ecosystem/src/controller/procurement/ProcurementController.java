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
