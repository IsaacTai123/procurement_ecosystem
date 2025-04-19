package model.procurement;

import model.ecosystem.Organization;
import model.product.Product;
import model.product.Spec;

import java.util.List;

/**
 * @author tisaac
 */
public class PurchaseItem {
    private Product product;
    private int quantity;
    private double unitPrice; // from vendor quotation or internal estimate
    private Spec spec;
//    private Organization vendor; // TODO: vendor that provided the quotation ??

    public PurchaseItem(Product product, int quantity, double unitPrice, Spec spec) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.spec = spec;
    }

    public double getTotalPrice() {
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

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return product.getName();
    }
}
