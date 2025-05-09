/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.rfq;

import common.AppContext;
import interfaces.IDataRefreshCallback;
import model.procurement.PurchaseItem;
import model.quotation.RFQ;
import util.NavigationUtil;
import util.UIUtil;

import java.util.List;
import java.util.Map;

/**
 *
 * @author tisaac
 */
public class RFQViewDetailPanel extends javax.swing.JPanel {

    private RFQ rfq;
    private IDataRefreshCallback callback;
    /**
     * Creates new form RFQViewDetailPanel
     */
    public RFQViewDetailPanel(RFQ rfq, IDataRefreshCallback callback) {
        initComponents();
        this.rfq = rfq;
        this.callback = callback;

        setupListeners();
        initUI();

    }

    private void setupListeners() {
        btnBack.addActionListener(e -> handleBackbtn());
        tblPurchaseItems.getSelectionModel().addListSelectionListener(n -> {
            if (!n.getValueIsAdjusting()) {
                handleItemSelection();
            }
        });
    }

    private void handleBackbtn() {
        NavigationUtil.getInstance().goBack();
        callback.refreshData();
    }

    private void handleItemSelection() {
        UIUtil.getSelectedTableObject(tblPurchaseItems, 0, PurchaseItem.class, this, "Please select an item to continue")
                .ifPresent(i -> {
                    UIUtil.setTextFields(Map.of(txtModelNumber, i.getSpec().getModelNumber(),
                            txtCategory, i.getSpec().getCategory(),
                            txtMaterial, i.getSpec().getMaterial(),
                            txtColor, i.getSpec().getColor(),
                            txtSize, i.getSpec().getSize()
                    ));
                });
    }

    private void initUI() {
        UIUtil.setEnterpriseTitle(lbTitle, AppContext.getUserEnterprise().getName());
        UIUtil.adjustLabelText(lbRFQStatus, rfq.getStatus().toString(), UIUtil.AppendMode.APPEND, ":");
        UIUtil.clearTable(tblPurchaseItems);
        reloadPurchaseItemTable();
        showRFQDetails();
        toggleFields(false); // disable all spec fields by default
    }

    private void showRFQDetails() {
        UIUtil.setTextFields(Map.of(
                txtDeadline, rfq.getDeadline().toString(),
                txtRemarks, rfq.getRemarks()
        ));

        UIUtil.setLabelText(Map.of(
                lbRFQId, rfq.getId()
        ));

        cmbVendors.setSelectedItem(rfq.getVendor().getName());
    }

    private void reloadPurchaseItemTable() {
        List<PurchaseItem> items = rfq.getPurchaseItems();
        UIUtil.reloadTable(
                tblPurchaseItems,
                items,
                e -> new Object[]{
                        e,
                        e.getQuantity(),
                        e.getUnitPrice()
                });
    }

    private void toggleFields(boolean enable) {
        UIUtil.setEnabled(enable,
                txtModelNumber,
                txtCategory,
                txtMaterial,
                txtColor,
                txtSize,
                txtDeadline,
                txtRemarks,
                cmbVendors
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtModelNumber = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCategory = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPurchaseItems = new javax.swing.JTable();
        cmbVendors = new javax.swing.JComboBox<>();
        lbVendor1 = new javax.swing.JLabel();
        txtDeadline = new javax.swing.JTextField();
        lbPurchaseItems = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        txtRemarks = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lbSpec = new javax.swing.JLabel();
        txtSize = new javax.swing.JTextField();
        lbRFQStatus = new javax.swing.JLabel();
        lbVendor = new javax.swing.JLabel();
        lbRFQId = new javax.swing.JLabel();

        jLabel4.setText("Model Num:");

        tblPurchaseItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Name", "Quantity", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPurchaseItems);

        lbVendor1.setText("Vendor");

        lbPurchaseItems.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        lbPurchaseItems.setText("Purchase Items");

        jLabel8.setText("Size:");

        jLabel9.setText("Remarks");

        lbTitle.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("RFQ Form (View Only)");

        btnBack.setText("<<Back");

        jLabel5.setText("Category:");

        jLabel6.setText("Material:");

        jLabel7.setText("Color:");

        lbSpec.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        lbSpec.setText("Spec");

        lbRFQStatus.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        lbRFQStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbRFQStatus.setText("Status: ");

        lbVendor.setText("Deadline");

        lbRFQId.setText("RFQ ID:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbPurchaseItems)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbVendor1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbVendor))
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbVendors, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(lbSpec)
                        .addGap(95, 95, 95))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtColor, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaterial, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtModelNumber, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCategory, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbRFQId))
                .addGap(91, 91, 91))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(396, 396, 396)
                        .addComponent(lbRFQStatus)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbVendor)
                    .addComponent(txtDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbRFQId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbVendors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbVendor1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbPurchaseItems)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbSpec)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtModelNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(txtMaterial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(lbRFQStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox<String> cmbVendors;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbPurchaseItems;
    private javax.swing.JLabel lbRFQId;
    private javax.swing.JLabel lbRFQStatus;
    private javax.swing.JLabel lbSpec;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbVendor;
    private javax.swing.JLabel lbVendor1;
    private javax.swing.JTable tblPurchaseItems;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtDeadline;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtModelNumber;
    private javax.swing.JTextField txtRemarks;
    private javax.swing.JTextField txtSize;
    // End of variables declaration//GEN-END:variables
}
