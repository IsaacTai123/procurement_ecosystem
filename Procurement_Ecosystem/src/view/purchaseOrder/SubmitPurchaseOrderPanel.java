/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.purchaseOrder;

import common.AppContext;
import common.Result;
import controller.procurement.ProcurementController;
import directory.RFQDirectory;
import enums.RFQStatus;
import enums.RequestStatus;
import interfaces.IDataRefreshCallback;
import interfaces.IDataRefreshCallbackAware;
import model.procurement.PurchaseOrder;
import model.quotation.Quotation;
import model.quotation.RFQ;
import util.NavigationUtil;
import util.UIUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author tisaac
 */
public class SubmitPurchaseOrderPanel extends javax.swing.JPanel implements IDataRefreshCallbackAware {

    private IDataRefreshCallback callback;
    private RFQDirectory rfq;
    private boolean isLoadingCombo = false;
    private Optional<String> selectedQuotationId;
    /**
     * Creates new form SubmitPurchaseOrderPanel
     */
    public SubmitPurchaseOrderPanel() {
        initComponents();
        this.rfq = AppContext.getNetwork().getRfqDirectory();
        setupListeners();

        initUI();
    }

    private void initUI() {
        UIUtil.setEnterpriseTitle(lbTitle, AppContext.getUser().getEnterprise().getName());
        UIUtil.clearTable(tblQuotation, tblPO);
        loadRFQComboBox();
        toggleButtons(false);
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> handleBackbtn());
        btnCreatePO.addActionListener(e -> handleCreatePObtn());

        cmbRFQ.addActionListener(e -> {
            if (isLoadingCombo) return;
            refreshQuotationTable();
        });

        tblQuotation.getSelectionModel().addListSelectionListener(n -> {
            toggleButtons(true);
        });

        btnReload.addActionListener(e -> refreshPOTable());
    }

    private void handleCreatePObtn() {
        UIUtil.getSelectedTableObject(
                tblQuotation, 0,
                Quotation.class, this, "Please select a quotation to create a Purchase Order."
        ).ifPresent(q -> {
            // create a new Purchase Order
            // get selected RFQ
            RFQ selectedRFQ = getSelectedRFQ();
            Result<Void> r = ProcurementController.getInstance().handlePOSubmit(selectedRFQ, q, txtPrice.getText(), txtRemarks.getText(), txtAddress.getText());
            if (!r.isSuccess()) {
                UIUtil.showError(this, r.getMessage());
                return;
            }
            UIUtil.showInfo(this, r.getMessage());

            // refresh the table
            clearAllFields();
        });
    }

    private void loadRFQComboBox() {
        isLoadingCombo = true;

        // Get all closed RFQ
        List<RFQ> closedRFQ = rfq.getRFQList().stream()
                .filter(rfq -> rfq.getStatus() == RFQStatus.CLOSED)
                .collect(Collectors.toList());

        // Filter with Quotation status is approved inside closed RFQ
        List<RFQ> ready4PO = closedRFQ.stream()
                .filter(rfq -> rfq.getQuotations().isQuotationApproved())
                .collect(Collectors.toList());

        UIUtil.populateComboBox(
                cmbRFQ,
                ready4PO,
                rfq -> rfq.getId(),
                "-- Select RFQ --"
        );
        isLoadingCombo = false;
    }

    private RFQ getSelectedRFQ() {
        // Get selected RFQ from ComboBox
        String selectedRFQId = (String) cmbRFQ.getSelectedItem();
        if (selectedRFQId == null || selectedRFQId.startsWith("--")) {
            UIUtil.clearTable(tblQuotation, tblPO);
            return null;
        }
        return AppContext.getNetwork().getRfqDirectory().getRFQById(selectedRFQId);
    }

    private void refreshQuotationTable() {
        RFQ selectedRFQ = getSelectedRFQ();
        if (selectedRFQ == null) {
            return;
        }

        List<Quotation> ready4PO = selectedRFQ.getQuotations().getQuotationList().stream()
                .filter(q -> q.getStatus() == RequestStatus.APPROVED)
                .collect(Collectors.toList());

        UIUtil.reloadTable(tblQuotation,
                ready4PO,
                q -> new Object[]{
                        q,
                        q.getVendor(),
                        q.getPrice(),
                        q.getStatus()
                }
        );
    }

    // Show PO that has the same quotationId as RFQ and the status is "Completed"
    private void refreshPOTable() {
        // Get the selected quotation ID from the Quotation table
        selectedQuotationId = UIUtil.getSelectedTableValue(
                tblQuotation, 0, this, "Please select a quotation to create a Purchase Order."
        );

        if (selectedQuotationId.isEmpty()) return;
        System.out.println("Quotation ID from Quotation Table: " + selectedQuotationId.get());
        System.out.println("PO size: " + AppContext.getUserEnterprise().getPurchaseOrderList().getPurchaseOrderList().size());

        PurchaseOrder po = AppContext.getUserEnterprise()
                .getPurchaseOrderList()
                .findRequestsById(selectedQuotationId.get());

        if (po == null) {
            UIUtil.clearTable(tblPO);
            UIUtil.showWarning(this, "No Purchase Order created yet! Please create one.");
            return;
        }

        // Step 5: Reload the table with the filtered list
        UIUtil.reloadTable(
                tblPO,
                List.of(po),
                o -> new Object[]{o, o.getVendorAccount(), o.getTotalAmount()}
        );
    }

    private void handleBackbtn() {
        NavigationUtil.getInstance().goBack();
        callback.refreshData();
    }

    private void clearAllFields() {
        UIUtil.clearTextComponents(txtPrice, txtRemarks, txtAddress);
    }

    // Get all Purchase Orders under the current user's enterprise
    private List<PurchaseOrder> getAllPurchaseOrders() {
        return AppContext.getUserEnterprise()
                .getPurchaseOrderList()
                .getPurchaseOrderList();
    }

    // Find the completed quotation ID inside the selected RFQ
    private String findCompletedQuotationId(RFQ rfq) {
        return rfq.getQuotations().findCompletedQuotationId();
    }

    // Filter Purchase Orders that are linked to the given quotation ID
    // because when creating PO we linked that quotation with PO
    private List<PurchaseOrder> filterPOsByQuotationId(List<PurchaseOrder> poList, String quotationId) {
        return poList.stream()
                .filter(po -> po.getQuotationId().equals(quotationId))
                .collect(Collectors.toList());
    }

    private void toggleButtons(boolean enabled) {
        UIUtil.setEnabled(enabled, txtAddress, txtPrice, txtRemarks);
    }

    @Override
    public void setCallback(IDataRefreshCallback callback) {
        this.callback = callback;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQuotation = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPO = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnCreatePO = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbRFQ = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRemarks = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();

        lbTitle.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Submit Purchase Order");

        tblQuotation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Vendor", "Price", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblQuotation);
        if (tblQuotation.getColumnModel().getColumnCount() > 0) {
            tblQuotation.getColumnModel().getColumn(0).setMinWidth(100);
            tblQuotation.getColumnModel().getColumn(1).setResizable(false);
            tblQuotation.getColumnModel().getColumn(2).setResizable(false);
            tblQuotation.getColumnModel().getColumn(3).setResizable(false);
        }

        tblPO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Vendor", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblPO);
        if (tblPO.getColumnModel().getColumnCount() > 0) {
            tblPO.getColumnModel().getColumn(0).setMinWidth(120);
            tblPO.getColumnModel().getColumn(1).setResizable(false);
            tblPO.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 3, 14)); // NOI18N
        jLabel1.setText("Quotation Approved By Finance Waiting for PO");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 3, 14)); // NOI18N
        jLabel2.setText("PO List");

        btnCreatePO.setText("Create Purchase Order");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 0));
        jLabel3.setText("Only work when at least one of Quotation under RFQ status is Complete");

        jLabel4.setText("RFQ:");

        jLabel5.setText("Price");

        jLabel6.setText("Remarks");

        jLabel7.setText("Address");

        btnBack.setText("<<Back");

        btnReload.setText("Reload");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(99, 99, 99)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(76, 76, 76)
                        .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(76, 76, 76)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCreatePO, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(32, 32, 32))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbRFQ, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(32, 32, 32)
                .addComponent(btnReload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbRFQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnReload)
                                .addGap(130, 130, 130))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCreatePO)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreatePO;
    private javax.swing.JButton btnReload;
    private javax.swing.JComboBox<String> cmbRFQ;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTable tblPO;
    private javax.swing.JTable tblQuotation;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtRemarks;
    // End of variables declaration//GEN-END:variables
}
