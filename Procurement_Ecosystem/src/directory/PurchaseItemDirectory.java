package directory;

import model.procurement.PurchaseItem;

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

    public List<PurchaseItem> getPurchaseItemList() {
        return purchaseItemList;
    }
}
