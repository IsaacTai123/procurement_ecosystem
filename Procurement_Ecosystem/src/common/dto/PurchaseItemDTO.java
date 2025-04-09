package common.dto;

import model.product.Product;
import model.product.Spec;

/**
 * @author tisaac
 */
public class PurchaseItemDTO {
    private String productName;
    private int quantity;
    private double unitPrice;
    private SpecDTO spec;

    public PurchaseItemDTO(String productName, SpecDTO spec, int quantity, double unitPrice) {
        this.productName = productName;
        this.spec = spec;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public SpecDTO getSpec() {
        return spec;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
