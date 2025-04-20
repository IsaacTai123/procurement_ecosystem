/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.purchaseRequest;

import common.AppContext;
import common.Result;
import common.Session;
import controller.procurement.PurchaseRequestController;
import enums.ApprovalStatus;
import enums.OrganizationType;
import enums.RequestStatus;
import enums.Role;
import interfaces.IDataRefreshCallback;
import interfaces.IDataRefreshCallbackAware;
import model.ecosystem.Enterprise;
import model.procurement.PurchaseRequest;
import model.user.UserAccount;
import util.NavigationUtil;
import util.UIUtil;
import view.RFQFormPanel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author tisaac
 */
public class ManagePurchaseRequestsPanel extends javax.swing.JPanel implements IDataRefreshCallbackAware, IDataRefreshCallback {
    private IDataRefreshCallback callback;
    private PurchaseRequestController prController = PurchaseRequestController.getInstance();
    /**
     * Creates new form ManagePurchaseRequestsPanel
     */
    public ManagePurchaseRequestsPanel() {
        initComponents();
        setupListeners();
        configureButtonsForCurrentUser();

        UIUtil.clearTable(tblPR);
        refreshPRTable();
    }

    private void configureButtonsForCurrentUser() {
        OrganizationType userOrg = AppContext.getUser().getOrg().getTypeName();

        // If organization is IT, hide the forward and create RFQ buttons
        if (userOrg == OrganizationType.IT) {
            UIUtil.hide(btnForward, btnCreateRFQ);
        }
    }

    private void setupListeners() {
        btnBack.addActionListener(e -> handleBackBtn());
        btnApprove.addActionListener(e -> handleApproveBtn());
        btnForward.addActionListener(e -> handleForwardBtn());
        btnCreateRFQ.addActionListener(e -> handleCreateRFQBtn());
    }

    private void handleBackBtn() {
        NavigationUtil.getInstance().goBack();
        callback.refreshData();
    }

    private void handleApproveBtn() {
        getSelectedPR()
                .ifPresent(pr -> {
                    Result<Void> r = (AppContext.getUser().getOrg().getTypeName() == OrganizationType.IT)
                            ? prController.handlePRApproveByIT(pr)
                            : prController.handlePRApproveByProcurement(pr);

                    if (!r.isSuccess()) {
                        UIUtil.showError(this, r.getMessage());
                        return;
                    }
                    UIUtil.showInfo(this, r.getMessage());
                    refreshPRTable();
                });
    }

    private void handleForwardBtn() {
        getSelectedPR()
                .ifPresent(pr -> {
                    Result<Void> r = prController.handlePRForwardToIT(pr);
                    if (!r.isSuccess()) {
                        UIUtil.showError(this, r.getMessage());
                        return;
                    }
                    UIUtil.showInfo(this, r.getMessage());
                    refreshPRTable();
                });
    }

    private void handleCreateRFQBtn() {
        getSelectedPR()
                .ifPresent(pr -> {
                    Result<Void> r = prController.handleCreateRFQ(pr);
                    if (!r.isSuccess()) {
                        UIUtil.showError(this, r.getMessage());
                        return;
                    }
                    NavigationUtil.getInstance().showCard(new RFQFormPanel(pr, () -> refreshData()), "Create RFQ");
                });
    }

    private Optional<PurchaseRequest> getSelectedPR() {
        return UIUtil.getSelectedTableObject(tblPR, 0, PurchaseRequest.class, this, "Please select a PR to approve");
    }

    private void refreshPRTable() {
        List<PurchaseRequest> requests = AppContext.getUser()
                .getEnterprise()
                .getPurchaseRequestList()
                .getPurchaseRequestList()
                .stream()
                .filter(pr -> pr.getStatus() != RequestStatus.COMPLETED) // Filter out PR that are not completed
                .collect(Collectors.toList());

        UIUtil.reloadTable(tblPR, requests, pr -> {
            String approvalStatus = (pr.getCurrentActiveStep() == null)
                    ? ApprovalStatus.APPROVED.toString()
                    : pr.getCurrentActiveStep().getStatus().toString();

            return new Object[]{
                    pr,
                    pr.getSender().getUsername(),
                    pr.getRequestDate(),
                    pr.getStatus(),
                    approvalStatus,
                    pr.getRfqId().size()
            };
        });
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPR = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnApprove = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        btnCreateRFQ = new javax.swing.JButton();
        lbFoward = new javax.swing.JLabel();
        lbRFQ = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        lbTitle.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Purchase Request Service");

        tblPR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "PR Id", "Requestor", "Request Date", "Request Status", "Approval Status", "RFQ Count"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblPR);
        if (tblPR.getColumnModel().getColumnCount() > 0) {
            tblPR.getColumnModel().getColumn(0).setResizable(false);
            tblPR.getColumnModel().getColumn(1).setResizable(false);
            tblPR.getColumnModel().getColumn(2).setResizable(false);
            tblPR.getColumnModel().getColumn(3).setResizable(false);
            tblPR.getColumnModel().getColumn(4).setResizable(false);
            tblPR.getColumnModel().getColumn(5).setResizable(false);
        }

        btnBack.setText("<<Back");

        btnApprove.setText("Approve");

        btnForward.setText("Forward to IT");

        btnCreateRFQ.setText("Create RFQ");

        lbFoward.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        lbFoward.setText("Forward to IT for confirmation");

        lbRFQ.setFont(new java.awt.Font("Helvetica Neue", 2, 13)); // NOI18N
        lbRFQ.setText("File a RFQ based on selected PR");

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 3, 14)); // NOI18N
        jLabel1.setText("Wait for review!!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbRFQ)
                            .addComponent(lbFoward))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnForward, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCreateRFQ, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnApprove))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 838, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBack)
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnApprove)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnForward)
                    .addComponent(lbFoward))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateRFQ)
                    .addComponent(lbRFQ))
                .addContainerGap(61, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApprove;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreateRFQ;
    private javax.swing.JButton btnForward;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbFoward;
    private javax.swing.JLabel lbRFQ;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTable tblPR;
    // End of variables declaration//GEN-END:variables

    @Override
    public void refreshData() {
        refreshPRTable();
    }

    @Override
    public void setCallback(IDataRefreshCallback callback) {
        this.callback = callback;
    }
}
