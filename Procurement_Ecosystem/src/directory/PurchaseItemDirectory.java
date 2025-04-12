package directory;

import model.procurement.PurchaseItem;
import model.procurement.PurchaseRequest;
import model.product.Product;
import model.product.Spec;

import java.util.List;
import java.util.Optional;

/**
 * @author tisaac
 */
public class PurchaseItemDirectory {

    private final List<PurchaseItem> purchaseItemList;

    public PurchaseItemDirectory(List<PurchaseItem> purchaseItemList) {
        this.purchaseItemList = purchaseItemList;
    }

    public Optional<PurchaseItem> findByProductId(String productId) {
        return purchaseItemList.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    public PurchaseItem createPurchaseItem(Product product, int quantity, double unitPrice, Spec spec) {
        PurchaseItem item = new PurchaseItem(product, quantity, unitPrice, spec);
        purchaseItemList.add(item);
        return item;
    }

    public List<PurchaseItem> getPurchaseItemList() {
        return purchaseItemList;
    }
}
