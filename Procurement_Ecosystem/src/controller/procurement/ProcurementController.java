/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.procurement;

import java.util.ArrayList;
import java.util.List;

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
    private PurchaseRequestService prService = new PurchaseRequestService();

    public void handlePRSubmit(PurchaseRequestDTO dto) {
        // create PurchaseRequest with the given DTO
        List<PurchaseItem> items = new ArrayList<>();
        for (PurchaseItemDTO itemDTO : dto.getPurchaseItems()) {
            SpecDTO specDTO = itemDTO.getSpec();
            Spec spec = new Spec(specDTO.getModelNumber(), specDTO.getColor(), specDTO.getSize(),
                    specDTO.getMaterial(), specDTO.getCategory(), specDTO.getAdditionalNotes());

            Product product = new Product();
            product.setName(itemDTO.getProductName());

            PurchaseItem item = new PurchaseItem(product, spec, itemDTO.getQuantity(), itemDTO.getUnitPrice());
            items.add(item);
        }

        PurchaseRequest pr = new PurchaseRequest(dto.getDescription(), items);
        prService.submitPR(pr);
    }

    public void issueRFQ(PurchaseRequest pr, ArrayList<Enterprise> vendors) {
        // create RFQRequest
    }

    public void compareQuotations(ArrayList<Quotation> quotations) {
        // logic to select quotation
    }
}
