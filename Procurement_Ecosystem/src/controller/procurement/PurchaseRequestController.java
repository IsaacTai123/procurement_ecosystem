package controller.procurement;

import common.Result;
import common.dto.PurchaseItemDTO;
import common.dto.PurchaseRequestDTO;
import common.dto.SpecDTO;
import directory.PurchaseItemDirectory;
import enums.RequestStatus;
import model.procurement.PurchaseRequest;
import model.product.Product;
import model.product.Spec;
import service.procurement.PurchaseRequestService;
import util.ResultUtil;
import util.UIUtil;

import java.util.List;
import java.util.stream.Collectors;

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
        // validate the purchase request
        Result<Void> r = validatePurchaseRequestDTO(dto);
        if (!r.isSuccess()) {
            return r;
        }

        // convert DTO to Entity
        PurchaseRequest pr = new PurchaseRequest(dto.getReason());

        dto.getPurchaseItems().stream()
                .forEach(itemDTO -> {
                    Product product = new Product(itemDTO.getName());
                    SpecDTO specDTO = itemDTO.getSpec();
                    Spec spec = new Spec(specDTO.getModelNumber(), specDTO.getColor(), specDTO.getSize(),
                            specDTO.getMaterial(), specDTO.getCategory(), specDTO.getRemarks());

                    pr.getPurchaseItems().newPurchaseItem(product, itemDTO.getQuantityAsInt(), itemDTO.getUnitPriceAsDouble(), spec);
                });

        return prService.submitPR(pr);
    }

    public Result<List<PurchaseRequest>> handleUserPR(String userId, RequestStatus isCompleted) {
        List<PurchaseRequest> pr = prService.getPRbyUserId(userId);
        if (pr == null || pr.isEmpty()) {
            return ResultUtil.failure("No purchase requests found for the user.");
        }

        // filter by completed status
        if (isCompleted != RequestStatus.COMPLETED) {
            pr.stream()
                    .filter(p -> p.getStatus() != RequestStatus.COMPLETED)
                    .collect(Collectors.toList());
        } else {
            pr.stream()
                    .filter(p -> p.getStatus() == RequestStatus.COMPLETED)
                    .collect(Collectors.toList());
        }


        return ResultUtil.success("Purchase requests retrieved successfully.", pr);
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

    public Result<PurchaseRequestDTO> handleUpdatePurchaseItem(PurchaseRequestDTO prDTO, PurchaseItemDTO editingItemDTO, PurchaseItemDTO newItemDTO) {
        Result<Void> r = validatePurchaseItemDTO(newItemDTO);
        if (!r.isSuccess()) {
            return ResultUtil.failure(r.getMessage(), null);
        }

        updatePurchaseItemFields(editingItemDTO, newItemDTO);
        return ResultUtil.success("Purchase item updated successfully.", prDTO);
    }

    private void updatePurchaseItemFields(PurchaseItemDTO target, PurchaseItemDTO source) {
        target.setName(source.getName());
        target.setQuantity(source.getQuantity());
        target.setUnitPrice(source.getUnitPrice());
        target.setSpec(source.getSpec());
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

    private Result<Void> validatePurchaseRequestDTO(PurchaseRequestDTO dto) {
        if (dto.getReason() == null || dto.getReason().isBlank()) {
            return ResultUtil.failure("Reason cannot be blank.");
        }

        if (dto.getPurchaseItems().isEmpty()) {
            return ResultUtil.failure("At least one purchase item is required.");
        }

        return ResultUtil.success("Valid purchase request.");
    }

}
