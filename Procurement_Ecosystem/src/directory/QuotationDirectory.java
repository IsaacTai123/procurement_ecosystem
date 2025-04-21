package directory;

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

    public Quotation getSelectedQuotation() {
        for (Quotation q : quotations) {
            if (q.isSelected()) {
                return q;
            }
        }
        return null; // 如果没有被选中的 quotation
    }

    public void setSelectedQuotation(Quotation selectedQuotation) {
        for (Quotation q : getQuotationList()) {
            q.setSelected(false); //
        }
        selectedQuotation.setSelected(true); // 选中传入的 quotation
    }
}
