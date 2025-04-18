package common.dto;

/**
 * @author tisaac
 */
public class PurchaseItemDTO {
    private String name;
    private String quantity;
    private String unitPrice;
    private SpecDTO spec;

    public PurchaseItemDTO(String name, SpecDTO spec, String quantity, String unitPrice) {
        this.name = name;
        this.spec = spec;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getQuantityAsInt() {
        return Integer.parseInt(quantity);
    }

    public double getUnitPriceAsDouble() {
        return Double.parseDouble(unitPrice);
    }

    public String getName() {
        return name;
    }

    public SpecDTO getSpec() {
        return spec;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }
}
