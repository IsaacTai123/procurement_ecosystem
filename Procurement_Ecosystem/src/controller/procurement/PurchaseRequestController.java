package controller.procurement;

import common.Result;
import common.dto.PurchaseItemDTO;
import common.dto.PurchaseRequestDTO;
import common.dto.SpecDTO;
import directory.PurchaseItemDirectory;
import model.procurement.PurchaseRequest;
import model.product.Product;
import model.product.Spec;
import service.procurement.PurchaseRequestService;
import util.ResultUtil;
import util.UIUtil;

import java.util.List;

/**
 * @author tisaac
 */
public class PurchaseRequestController {

    private final PurchaseRequestService prService = new PurchaseRequestService();

    private PurchaseRequestController() {}

    private static class PurchaseRequestControllerHolder {
        private static final PurchaseRequestController INSTANCE = new PurchaseRequestController();
    }

    public static PurchaseRequestController getInstance() {
        return PurchaseRequestControllerHolder.INSTANCE;
    }

    public Result<Void> handlePRSubmit(PurchaseRequestDTO dto) {
        // create PurchaseRequest with the given DTO

        // convert DTO to PurchaseItem
        PurchaseItemDirectory items = new PurchaseItemDirectory();

        dto.getPurchaseItems().stream()
                .forEach(itemDTO -> {
                    Product product = new Product(itemDTO.getName());
                    SpecDTO specDTO = itemDTO.getSpec();
                    Spec spec = new Spec(specDTO.getModelNumber(), specDTO.getColor(), specDTO.getSize(),
                            specDTO.getMaterial(), specDTO.getCategory(), specDTO.getRemarks());

                    items.newPurchaseItem(product, itemDTO.getQuantityAsInt(), itemDTO.getUnitPriceAsDouble(), spec);
                });

        PurchaseRequest pr = new PurchaseRequest(dto.getReason());
        return prService.submitPR(pr);
    }

    public Result<List<PurchaseRequest>> handleMyRequests() {
        return null;
    }

    // Handle purchaseItem
    public Result<PurchaseRequestDTO> handlePurchaseItem(PurchaseRequestDTO prDTO, PurchaseItemDTO itemDTO) {


        Result<Void> r = validatePurchaseItemDTO(itemDTO);
        if (!r.isSuccess()) {
            return ResultUtil.failure(r.getMessage(), null);
        }

        prDTO.addPurchaseItem(itemDTO);
        return ResultUtil.success("Purchase item added successfully.", prDTO);
    }

    private Result<Void> validatePurchaseItemDTO(PurchaseItemDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            return ResultUtil.failure("Product name cannot be blank.");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(dto.getQuantity());
            if (quantity <= 0) {
                return ResultUtil.failure("Quantity must be greater than 0.");
            }
        } catch (NumberFormatException e) {
            return ResultUtil.failure("Quantity must be a valid integer.");
        }

        double unitPrice;
        try {
            unitPrice = Double.parseDouble(dto.getUnitPrice());
            if (unitPrice < 0) {
                return ResultUtil.failure("Unit price cannot be negative.");
            }
        } catch (NumberFormatException e) {
            return ResultUtil.failure("Unit price must be a valid number.");
        }

        return ResultUtil.success("Valid purchase item.");
    }

}
