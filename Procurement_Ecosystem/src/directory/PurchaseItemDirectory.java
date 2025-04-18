package directory;

import model.procurement.PurchaseItem;
import model.product.Product;
import model.product.Spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author tisaac
 */
public class PurchaseItemDirectory {

    private final List<PurchaseItem> purchaseItemList;

    public PurchaseItemDirectory() {
        this.purchaseItemList = new ArrayList<>();
    }

    public Optional<PurchaseItem> findByProductId(String productId) {
        return purchaseItemList.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    public PurchaseItem newPurchaseItem(Product product, int quantity, double unitPrice, Spec spec) {
        PurchaseItem item = new PurchaseItem(product, quantity, unitPrice, spec);
        purchaseItemList.add(item);
        return item;
    }

    public List<PurchaseItem> getPurchaseItemList() {
        return purchaseItemList;
    }
}
