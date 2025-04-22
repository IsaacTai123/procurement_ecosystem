package directory;

import enums.RequestStatus;
import model.quotation.Quotation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author tisaac
 */
public class QuotationDirectory {
    private final List<Quotation> quotations;

    public QuotationDirectory() {
        this.quotations = new ArrayList<>();
    }

    public void addQuotation(Quotation quotation) {
        quotations.add(quotation);
    }

    public List<Quotation> getQuotationList() {
        return this.quotations;
    }

    public Quotation getBestQuotation() {
        return quotations.stream()
                .min(Comparator.comparingDouble(Quotation::getPrice))
                .orElse(null);
    }

    public Quotation findQuotationById(String id) {
        return quotations
                .stream()
                .filter(q -> q.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Find the completed quotation, this will be used to link purchase order with RFQ
    public String findCompletedQuotationId() {
        return quotations
                .stream()
                .filter(q -> q.getStatus() == RequestStatus.COMPLETED)
                .map(Quotation::getId)
                .findFirst()
                .orElse(null);
    }

    public Boolean isQuotationApproved() {
        return quotations
                .stream()
                .anyMatch(q -> q.getStatus() == RequestStatus.APPROVED);
    }
}
