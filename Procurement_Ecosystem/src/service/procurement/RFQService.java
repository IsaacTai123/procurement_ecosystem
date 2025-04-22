package service.procurement;

import common.AppContext;
import common.Result;
import directory.PurchaseRequestDirectory;
import enums.OrganizationType;
import enums.RFQStatus;
import enums.Role;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.procurement.PurchaseItem;
import model.procurement.PurchaseOrder;
import model.quotation.Quotation;
import model.quotation.RFQ;
import model.user.UserAccount;
import util.ResultUtil;

/**
 * @author tisaac
 */
public class RFQService {
    private UserAccount currentUser;
    private Network currentNetwork;

    public RFQService(UserAccount user, Network network) {
        this.currentUser = user;
        this.currentNetwork = network;
    }

    public Result<Void> submitRFQ(RFQ rfq, String vendor) {
        // Get Enterprise from vendor
        Enterprise v = AppContext.getNetwork().getEnterpriseDir().findEnterpriseByName(vendor);
        rfq.setVendor(v);
        rfq.setStatus(RFQStatus.SENT);

        // Link RFQ to Purchase Request
        currentUser.getEnterprise().getPurchaseRequestList()
                .findRequestById(rfq.getLinkedPRId())
                .addLinkedRFQId(rfq.getId());

        return ResultUtil.success("RFQ submitted successfully");
    }

    public  Result<Void> submitePO(RFQ rfq, Quotation quotation, double price, String remarks, String address) {
        // Linked PR to PO
        PurchaseOrder po = new PurchaseOrder(
                quotation.getId(),
                rfq.getLinkedPRId(),
                quotation.getSender(),
                quotation.getVendor().findUserByOrgRoleAndEnt(OrganizationType.SALES, Role.SPECIALIST),
                rfq.getPurchaseItems(),
                address,
                price,
                remarks
        );

        // Print PO
        System.out.println("Purchase Order ID: " + po.getId());
        System.out.println("Purchase quotationID: " + po.getQuotationId());
        System.out.println("Purchase Order Vendor: " + po.getVendorAccount().getUsername());
        System.out.println("Purchase Order Address: " + po.getAddress());
        System.out.println("Purchase Order Remarks: " + po.getRemarks());
        System.out.println("Purchase Order Items: ");
        for (PurchaseItem item : po.getPurchaseItems()) {
            System.out.println(" - " + item.getProduct().getName() + ": " + item.getQuantity());
        }

        System.out.println("Purchase Order size: " + AppContext.getUserEnterprise().getPurchaseOrderList().getPurchaseOrderList().size());
        AppContext.getUserEnterprise().getPurchaseOrderList().addPurchaseOrder(po);
        System.out.println("Purchase Order size: " + AppContext.getUserEnterprise().getPurchaseOrderList().getPurchaseOrderList().size());
        return ResultUtil.success("Purchase Order submitted successfully");
    }
}
