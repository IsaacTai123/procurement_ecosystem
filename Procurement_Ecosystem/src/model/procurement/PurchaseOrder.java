package model.procurement;

import java.util.ArrayList;

import java.util.List;

import model.delivery.Shipment;
import model.ecosystem.Enterprise;
import model.user.UserAccount;
import model.workqueue.DeliveryRequest;
import util.IdGenerateUtil;
import util.TimeUtil;

/**
 * @author tisaac
 */
public class PurchaseOrder {
    private String id;
    private String quotationId;
    private String linkedPRId;
    private UserAccount buyerAccount;    
    private UserAccount vendorAccount;
    private Enterprise logistics;
    private List<PurchaseItem> purchaseItems;
    private String shippingAddress;
    private double totalAmount;
    private String remarks;
    private String purchasedDate;
    private String purchasedTime;
    private boolean isIssued;
    private boolean isDelivered;
    private Shipment shipment;
    private DeliveryRequest deliveryRequest;





    public PurchaseOrder(String quotationId, String linkedPRId, UserAccount buyerAccount, UserAccount vendorAccount, List<PurchaseItem> purchaseItems, String shippingAddress, double totalAmount, String remarks) {
        this.id = IdGenerateUtil.generateIdByActionAndTimestamp("ORDER");
        this.quotationId = quotationId;
        this.linkedPRId = linkedPRId;
        this.buyerAccount = buyerAccount;
        this.vendorAccount = vendorAccount;
        this.purchaseItems = purchaseItems;
        this.shippingAddress = shippingAddress;
        this.totalAmount = totalAmount;
        this.remarks = remarks;
        this.purchasedDate = TimeUtil.getCurrentDate();
        this.purchasedTime = TimeUtil.getCurrentTime();
        this.isIssued = false;
        this.isDelivered = false;
        this.logistics = null;
        this.shipment = null;
        this.deliveryRequest = null;


        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public UserAccount getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(UserAccount buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public Enterprise getLogistics() {
        return logistics;
    }

    public void setLogistics(Enterprise logistics) {
        this.logistics = logistics;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public void setPurchaseItems(ArrayList<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isIsIssued() {
        return isIssued;
    }

    public void setIsIssued(boolean isIssued) {
        this.isIssued = isIssued;
    }

    public boolean isIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public String getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(String purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getPurchasedTime() {
        return purchasedTime;
    }

    public void setPurchasedTime(String purchasedTime) {
        this.purchasedTime = purchasedTime;
    }

    public UserAccount getVendorAccount() {
        return vendorAccount;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public DeliveryRequest getDeliveryRequest() {
        return deliveryRequest;
    }

    public void setDeliveryRequest(DeliveryRequest deliveryRequest) {
        this.deliveryRequest = deliveryRequest;
    }

    public String getAddress() {
        return shippingAddress;
    }

    public String getLinkedPRId() {
        return linkedPRId;
    }




    @Override
    public String toString() {
        return this.id;
    }

    
    

}
