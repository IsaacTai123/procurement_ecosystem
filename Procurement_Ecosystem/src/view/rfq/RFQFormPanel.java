/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.rfq;

import common.AppContext;
import common.Result;
import common.dto.SpecDTO;
import controller.procurement.ProcurementController;
import directory.RFQDirectory;
import enums.EnterpriseType;
import interfaces.IDataRefreshCallback;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseItem;
import model.procurement.PurchaseRequest;
import model.quotation.RFQ;
import util.NavigationUtil;
import util.UIUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author qiyaochen
 */
public class RFQFormPanel extends javax.swing.JPanel {

    private PurchaseRequest purchaseRequest;
    private IDataRefreshCallback callback;
    private RFQ rfq;
    private RFQDirectory rfqDir;
    /**
     * Creates new form RFQFormPanel
     */
    public RFQFormPanel(PurchaseRequest purchaseRequest, IDataRefreshCallback callback) {
        initComponents();
        this.purchaseRequest = purchaseRequest;
        this.callback = callback;
        this.rfqDir = AppContext.getNetwork().getRfqDirectory();

        setupListeners();
        initRFQ();
        initUI();
    }

    private void initUI() {
        UIUtil.setEnterpriseTitle(lbTitle, AppContext.getUserEnterprise().getName());
        UIUtil.clearTable(tblPurchaseItems);
        showVendorOption();
        reloadPurchaseItemTable();
        toggleFields(false); // disable all spec fields by default
    }

    private void initRFQ() {
        // Create RFQ base on purchase request
        rfq = rfqDir.addRFQ(
                purchaseRequest.getId(),
                purchaseRequest.getPurchaseItems().getPurchaseItemList());
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> handleBackbtn());
        btnSave.addActionListener(e -> handleSavebtn());
        btnUpdate.addActionListener(e -> handleUpdatebtn());
        btnSubmitRFQ.addActionListener(e -> handleSubmitbtn());
        tblPurchaseItems.getSelectionModel().addListSelectionListener(n -> {
            if (!n.getValueIsAdjusting()) {
                handleItemSelection();
            }
        });
    }

    private void handleBackbtn() {
        NavigationUtil.getInstance().goBack();
        callback.refreshData();

        // Temporarily solution: if rfq object vendor size is 0 then remove it
        if (rfq.getVendor() == null) {
            rfqDir.removeRFQ(rfq);
        }
    }

    private void handleSubmitbtn() {
        String remarks = txtRemarks.getText();
        String vendorStr = (String) cmbVendors.getSelectedItem();
        String Deadline = txtDeadline.getText(); // TODO: Change to use DatePicker (JDatePicker)
        Result<Void> r = ProcurementController.getInstance().handleRFQSubmit(rfq, vendorStr, Deadline, remarks);
        if (!r.isSuccess()) {
            UIUtil.showError(this, r.getMessage());
            return;
        }
        UIUtil.showInfo(this, r.getMessage());
        handleBackbtn();
    }

    private void handleSavebtn() {
        PurchaseItem item = UIUtil.getSelectedTableObject(tblPurchaseItems, 0, PurchaseItem.class, this, "Please select an item to continue")
                .orElse(null);

        Result<Void> result = ProcurementController.getInstance().handleUpdatePurchaseItem(item, getSpecData());
        if (!result.isSuccess()) {
            UIUtil.showError(this, result.getMessage());
            return;
        }
        UIUtil.showInfo(this, result.getMessage());

        // Set all field to ineditable
        toggleFields(false);
    }

    public SpecDTO getSpecData() {
        String modelNumber = txtNumber.getText();
        String category = txtCategory.getText();
        String color = txtColor.getText();
        String size = txtSize.getText();
        String material = txtMaterial.getText();
        String remarks = txtRemarks.getText();

        return new SpecDTO(modelNumber, color, size, material, category, remarks);
    }

    private void handleUpdatebtn() {
        toggleFields(true);
    }

    private void handleItemSelection() {
        UIUtil.getSelectedTableObject(tblPurchaseItems, 0, PurchaseItem.class, this, "Please select an item to continue")
                .ifPresent(i -> {
                    UIUtil.setTextFields(Map.of(
                            txtNumber, i.getSpec().getModelNumber(),
                            txtCategory, i.getSpec().getCategory(),
                            txtMaterial, i.getSpec().getMaterial(),
                            txtColor, i.getSpec().getColor(),
                            txtSize, i.getSpec().getSize()
                    ));
                });
    }

    private void showVendorOption() {
        List<Enterprise> ents = AppContext.getNetwork().getEnterpriseDir().getEnterprisesList()
                .stream()
                .filter(ent -> ent.getType() == EnterpriseType.VENDOR)
                .collect(Collectors.toList());

        UIUtil.populateComboBox(cmbVendors, ents,
                ent -> ent.getName(),
                "-- Select Vendor --");
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
                txtNumber,
                txtCategory,
                txtMaterial,
                txtColor,
                txtSize,
                btnSave
        );
        UIUtil.setEnabled(!enable, btnUpdate, btnSubmitRFQ);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbSpec = new javax.swing.JLabel();
        lbRFQStatus = new javax.swing.JLabel();
        lbVendor = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        lbTitle = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPurchaseItems = new javax.swing.JTable();
        cmbVendors = new javax.swing.JComboBox<>();
        lbVendor1 = new javax.swing.JLabel();
        txtDeadline = new javax.swing.JTextField();
        lbPurchaseItems = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnSubmitRFQ = new javax.swing.JButton();
        txtNumber = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCategory = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSize = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtRemarks = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        lbSpec.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        lbSpec.setText("Spec");

        lbRFQStatus.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        lbRFQStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbRFQStatus.setText("Status: ");

        lbVendor.setText("Deadline");

        btnSave.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        btnSave.setText("Save");

        lbTitle.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("RFQ Form");

        btnUpdate.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        btnUpdate.setText("Update");

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

        btnSubmitRFQ.setFont(new java.awt.Font("Helvetica Neue", 1, 20)); // NOI18N
        btnSubmitRFQ.setText("Submit RFQ");

        jLabel4.setText("Model Num:");

        jLabel5.setText("Category:");

        jLabel6.setText("Material:");

        jLabel7.setText("Color:");

        jLabel8.setText("Size:");

        jLabel9.setText("Remarks");

        btnBack.setText("<<Back");

        jLabel1.setText("yyyy-MM-dd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbSpec)
                .addGap(163, 163, 163))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(59, 59, 59)
                        .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel1))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnUpdate)
                        .addGap(22, 22, 22)
                        .addComponent(btnSave))
                    .addComponent(txtColor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(txtMaterial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(txtNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(txtCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(txtSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(btnSubmitRFQ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(68, 68, 68))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(396, 396, 396)
                .addComponent(lbRFQStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbSpec)
                    .addComponent(btnBack))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(92, 92, 92)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdate)
                            .addComponent(btnSave)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbVendor)
                            .addComponent(txtDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbVendors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbVendor1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbPurchaseItems)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSubmitRFQ, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 64, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(lbRFQStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSubmitRFQ;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbVendors;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbPurchaseItems;
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
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtRemarks;
    private javax.swing.JTextField txtSize;
    // End of variables declaration//GEN-END:variables
}
