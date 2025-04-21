/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import common.AppContext;
import common.Result;
import model.ecosystem.Enterprise;
import model.quotation.RFQ;
import service.VendorService;
import service.procurement.RFQService;
import util.ResultUtil;

/**
 *
 * @author linweihong
 */
public class VendorController {

    private VendorService getService() {
        return new VendorService(
                AppContext.getUser(),
                AppContext.getNetwork()
        );
    }

    private static class VendorControllerHolder {
        private static final VendorController INSTANCE = new VendorController();
    }

    public static VendorController getInstance() {
        return VendorControllerHolder.INSTANCE;
    }

    public Result<Void> handleSubmitQuotation(RFQ rfq, Enterprise vendor, String priceStr) {
        // check price
        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            return ResultUtil.failure("Invalid price format");
        }

        // logic to submit quotation
        return getService().submitQuotation(rfq, price, vendor);
    }
}
