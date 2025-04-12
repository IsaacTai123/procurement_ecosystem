package controller.procurement;

import common.Result;
import common.dto.PurchaseItemDTO;
import common.dto.PurchaseRequestDTO;
import common.dto.SpecDTO;
import model.procurement.PurchaseItem;
import model.procurement.PurchaseRequest;
import model.product.Product;
import model.product.Spec;
import service.procurement.PurchaseRequestService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tisaac
 */
public class PurchaseRequestController {
    private final PurchaseRequestService prService = new PurchaseRequestService();

    public Result<Void> handlePRSubmit(PurchaseRequestDTO dto) {
        // create PurchaseRequest with the given DTO
        List<PurchaseItem> items = new ArrayList<>();
        for (PurchaseItemDTO itemDTO : dto.getPurchaseItems()) {
            SpecDTO specDTO = itemDTO.getSpec();
            Spec spec = new Spec(specDTO.getModelNumber(), specDTO.getColor(), specDTO.getSize(),
                    specDTO.getMaterial(), specDTO.getCategory(), specDTO.getAdditionalNotes());

            Product product = new Product(itemDTO.getProductName());

            PurchaseItem item = new PurchaseItem(product, itemDTO.getQuantity(), itemDTO.getUnitPrice(), spec);
            items.add(item);
        }

        PurchaseRequest pr = new PurchaseRequest(dto.getDescription(), items);
        return prService.submitPR(pr);
    }

    public Result<List<PurchaseRequest>> handleMyRequests() {
        return null;
    }


}
