package model.quotation;
import enums.RequestStatus;
import model.ecosystem.Enterprise;
import util.IdGenerateUtil;

/**
 *
 * @author qiyaochen
 */
public class Quotation {
    private String id;
    private Enterprise vendor;
    private String remarks;
    private RequestStatus status;
    private boolean selected;
    private double price;
    private String description;


    public Quotation(Enterprise vendor, String remarks, double price, String description) {
        this.id = IdGenerateUtil.generateIdByActionAndTimestamp("Quotation");
        this.vendor = vendor;
        this.remarks = remarks;
        this.price = price;
        this.description = description;
        this.status = RequestStatus.PENDING;
        this.selected = false;
    }

    public String getId() {
        return id;
    }

    public Enterprise getVendor() {
        return vendor;
    }

    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
