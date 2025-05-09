/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.purchaseOrder;

import interfaces.IDataRefreshCallbackAware;
import view.purchaseRequest.*;
import common.Result;
import common.Session;
import controller.DeliveryController;
import controller.procurement.PurchaseRequestController;
import directory.PurchaseOrderDirectory;
import interfaces.IDataRefreshCallback;
import java.util.ArrayList;
import java.util.Date;
import model.procurement.PurchaseRequest;
import model.user.UserAccount;
import util.NavigationUtil;
import util.UIUtil;

import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.delivery.Shipment;
import model.delivery.ShipmentDirectory;
import model.delivery.ShipmentItem;
import model.ecosystem.Enterprise;
import model.ecosystem.Network;
import model.procurement.PurchaseItem;
import model.procurement.PurchaseOrder;
import model.product.Product;
import model.workqueue.DeliveryRequest;
import util.TimeUtil;

/**
 *
 * @author tisaac
 */
public class MyPurchaseOrdersPanel extends javax.swing.JPanel implements IDataRefreshCallbackAware {

    private UserAccount currentUser;
    private Network network;
    private Enterprise vendor;
    private PurchaseOrderDirectory purchaseOrderDirectory;
    private List<Enterprise> allLogistics;
    private Enterprise selectedLogistics;
    private IDataRefreshCallback callback;
   
    
    /**
     * Creates new form MyPurchaseRequestsPanel
     */
    public MyPurchaseOrdersPanel() {
        initComponents();
        this.currentUser = Session.getCurrentUser();
        this.network = Session.getCurrentNetwork();
        this.vendor = currentUser.getEnterprise(); // e.g. asus/tsmc (vendor)
        this.purchaseOrderDirectory = vendor.getPurchaseOrderList();
        this.allLogistics = network.getEnterpriseDir().getAllLogisticsEnterprises();
        initUI();
        
        populateTable();
        populateLogisticsCombo();
        
        // pop out alert window if there is an unassigned PO (to logistics)
        long unassignedCount = purchaseOrderDirectory.countUnassignedLogistics();

        if (unassignedCount > 0) {
            JOptionPane.showMessageDialog(null,
                    "You have " + unassignedCount + " unassigned Purchase Orders.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        
    }

    public void initUI() {
        UIUtil.setEnterpriseTitle(lbTitle, currentUser.getEnterprise().getName());
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lbTitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPO = new javax.swing.JTable();
        btnIssueDelivery = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        cmbLogistics = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        lbTitle.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Purchase Order");

        tblPO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "PO Id", "Buyer", "Request Date", "Logistics", "Issue Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblPO);
        if (tblPO.getColumnModel().getColumnCount() > 0) {
            tblPO.getColumnModel().getColumn(0).setMinWidth(30);
            tblPO.getColumnModel().getColumn(0).setPreferredWidth(140);
            tblPO.getColumnModel().getColumn(1).setPreferredWidth(20);
            tblPO.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblPO.getColumnModel().getColumn(3).setPreferredWidth(20);
            tblPO.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        btnIssueDelivery.setText("Issue Delivery Request");
        btnIssueDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIssueDeliveryActionPerformed(evt);
            }
        });

        btnBack.setText("<<Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnView.setText("View Detail");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        cmbLogistics.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Logistics:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnIssueDelivery)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbLogistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBack)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbLogistics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(btnIssueDelivery))
                .addContainerGap(155, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        
        // get selected PO
        int row = tblPO.getSelectedRow();
        if(row < 0) {
            JOptionPane.showMessageDialog(null, "Please select a row from the table first", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        PurchaseOrder po = (PurchaseOrder)tblPO.getValueAt(row, 0);
        
        String msg = String.format(
                                    """
                    🧾 Purchase Order Summary

                    🆔 Order ID: %s
                    📄 Quotation ID: %s

                    👤 Buyer: %s
                    🏢 Buyer Org: %s

                    🧑 Vendor: %s
                    🏢 Vendor Org: %s

                    🚚 Logistics: %s
                    📦 Delivery Status: %s
                    📨 Delivery Request ID: %s

                    📍 Shipping Address: %s
                    💬 Remarks: %s

                    💰 Total Amount: $%.2f
                    📅 Date: %s
                    🕒 Time: %s
                                        """,
                po.getId(),
                po.getQuotationId(),
                po.getBuyerAccount().getUsername(),
                po.getBuyerAccount().getEnterprise().getName(),
                po.getVendorAccount().getUsername(),
                po.getVendorAccount().getEnterprise().getName(),
                po.getLogistics() != null ? po.getLogistics().getName() : "Not assigned",
                po.isIsDelivered() ? "Delivered" : "Not Delivered",
                po.getDeliveryRequest() != null ? po.getDeliveryRequest().getId() : "N/A",
                po.getAddress(),
                po.getRemarks(),
                po.getTotalAmount(),
                po.getPurchasedDate(),
                po.getPurchasedTime()
        );

        JOptionPane.showMessageDialog(null, msg, "📋 PO Details", JOptionPane.INFORMATION_MESSAGE);


        
        
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnIssueDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIssueDeliveryActionPerformed
        // TODO add your handling code here:
        
        // Vendor (asus) issue a delivery request (PO) to a logistics -- start
        // get selected PO
        int row = tblPO.getSelectedRow();
        if(row < 0) {
            JOptionPane.showMessageDialog(null, "Please select a row from the table first", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        PurchaseOrder po = (PurchaseOrder)tblPO.getValueAt(row, 0);
        
        
        DeliveryController deliveryController = new DeliveryController();
        
        selectedLogistics = (Enterprise) cmbLogistics.getSelectedItem();
   

        // 1. ShipmentDirectories include different logistics's shipment directory
        // 2. get FedEx's shipment directory
        ShipmentDirectory shipmentDirectory = network.getShipmentDirectories().getShipmentDirectory(selectedLogistics); 
        
        ArrayList<ShipmentItem> items = new ArrayList<>();
        for (PurchaseItem purchaseItem : po.getPurchaseItems()) {
            
            ShipmentItem shipmentItem = new ShipmentItem(purchaseItem.getProduct(), purchaseItem.getQuantity());

            items.add(shipmentItem);
        }
        
        // connect PO to Logistics
        po.setLogistics(selectedLogistics);
        po.setIsIssued(true);

        Map<String, Object> result = deliveryController.requestShipping(network, items, selectedLogistics, currentUser, po.getBuyerAccount(), "", "", shipmentDirectory, po);
        // connect PO to deliveryRequest

        DeliveryRequest deliveryRequest = (DeliveryRequest) result.get("deliveryReq");
        Shipment shipment = (Shipment) result.get("shipment");
        System.out.println("check  shipment" + shipment);

        po.setDeliveryRequest(deliveryRequest);
        po.setShipment(shipment);
        
        // Vendor (asus) issue a delivery request (PO) to a logistics -- end
        
        populateTable();
        
    }//GEN-LAST:event_btnIssueDeliveryActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        NavigationUtil.getInstance().goBack();
        callback.refreshData();
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnIssueDelivery;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<Object> cmbLogistics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTable tblPO;
    // End of variables declaration//GEN-END:variables

    // clean all data and add all data in vitalSignsHistory
    private void populateTable() {
        
        DefaultTableModel model = (DefaultTableModel) tblPO.getModel(); // get table schema to control the table
        model.setRowCount(0); // clean all data in the table
        
        System.out.println("purchaseOrderDirectory" + purchaseOrderDirectory);

        
        
        for (PurchaseOrder purchaseOrder: purchaseOrderDirectory.getPurchaseOrderList()){

            
            Object[] row = new Object[5];
            row[0] = purchaseOrder;
            row[1] = purchaseOrder.getBuyerAccount();
            row[2] = purchaseOrder.getPurchasedDate();
            row[3] = purchaseOrder.getLogistics();
            row[4] = purchaseOrder.isIsIssued();
            
            
            model.addRow(row);
            
        }
    }
    
    
    public void populateLogisticsCombo() {

        cmbLogistics.removeAllItems();

        for (Enterprise logistics : allLogistics) {
            cmbLogistics.addItem(logistics);
        }

    }


    @Override
    public void setCallback(IDataRefreshCallback callback) {
        this.callback = callback;
    }
}
