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

    public double getSubtotal() {
        return quantity * unitPrice;
    }
}
