package model.procurement;

import model.product.Product;
import model.product.Spec;

/**
 * @author tisaac
 */
public class PurchaseItem {
    private Product product;
    private Spec spec;
    private int quantity;
    private double unitPrice; // from vendor quotation or internal estimate

    public PurchaseItem(Product product, Spec spec, int quantity, double unitPrice) {
        this.product = product;
        this.spec = spec;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getPrice() {
        return unitPrice * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Product setProduct(Product product) {
        this.product = product;
        return product;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }
}
