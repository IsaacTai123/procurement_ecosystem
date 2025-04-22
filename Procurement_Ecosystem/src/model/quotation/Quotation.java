package model.quotation;
import enums.OrganizationType;
import enums.RequestStatus;
import enums.Role;
import enums.StepType;
import model.ecosystem.Enterprise;
import model.workqueue.WorkRequest;
import util.IdGenerateUtil;

/**
 *
 * @author qiyaochen
 */
public class Quotation extends WorkRequest {
    private String id;
    private String linkedRFQId;
    private Enterprise vendor;
    private String remarks;
    private double price;
    private boolean selected;
    private String terms;


    public Quotation(Enterprise vendor, String remarks, double price, String linkedRFQId, String terms) {
        this.id = IdGenerateUtil.generateIdByActionAndTimestamp("Quotation");
        this.vendor = vendor;
        this.remarks = remarks;
        this.price = price;
        this.status = RequestStatus.PENDING;
        this.selected = false;
        this.linkedRFQId = linkedRFQId;
    }

    @Override
    protected void initWorkflowSteps() {
        addStep(OrganizationType.PROCUREMENT, Role.SPECIALIST, StepType.APPROVER, false);
        addStep(OrganizationType.FINANCE, Role.ANALYST, StepType.APPROVER, false);
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

    public String getLinkedRFQId() {
        return linkedRFQId;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
    
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id;
    }
}
