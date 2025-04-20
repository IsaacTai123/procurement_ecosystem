package directory;


import model.procurement.PurchaseItem;
import model.quotation.RFQ;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tisaac
 */
public class RFQDirectory {
    private List<RFQ> rfqList;

    public RFQDirectory() {
        this.rfqList = new ArrayList<>();
    }

    public RFQ addRFQ(String linkedPRId, List<PurchaseItem> purchaseItems) {
        RFQ rfq = new RFQ(linkedPRId, purchaseItems);
        this.rfqList.add(rfq);
        return rfq;
    }

    public List<RFQ> getRFQList() {
        return rfqList;
    }

    public RFQ getRFQById(String rfqId) {
        return rfqList.stream()
                .filter(rfq -> rfq.getId().equals(rfqId))
                .findFirst()
                .orElse(null);
    }

    public RFQ removeRFQ(RFQ rfq) {
        if (rfqList.remove(rfq)) {
            return rfq;
        }
        return null;
    }
}
