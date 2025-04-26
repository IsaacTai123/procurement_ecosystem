/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.quotation;

import common.AppContext;
import common.Result;
import controller.procurement.ProcurementController;
import directory.RFQDirectory;
import enums.EnterpriseType;
import enums.RFQStatus;
import interfaces.IDataRefreshCallback;
import interfaces.IDataRefreshCallbackAware;
import model.quotation.Quotation;
import model.quotation.RFQ;
import util.NavigationUtil;
import util.UIUtil;

import javax.swing.event.ListSelectionListener;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author tisaac
 */
public class ManageQuotationPanel extends javax.swing.JPanel implements IDataRefreshCallbackAware {

    // TEMP: For testing multiple rfqDir and refresh behavior
    private RFQDirectory rfqDir;
    private int currentIndex = 0;
    private RFQ selectedRFQ;
    private IDataRefreshCallback callback;
    private boolean isProgrammaticSelection = false;
    private final ListSelectionListener rfqSelectionListener = e -> {
        if (!e.getValueIsAdjusting() && !isProgrammaticSelection) {
            enableQuotationFeature(true);
            refreshQuotationTable();
        }
    };

    /**
     * Creates new form QuotationPanel
     */
    public ManageQuotationPanel() {
        initComponents();
        setupListener();
        this.rfqDir = AppContext.getNetwork().getRfqDirectory();
        initUI();
    }

    private void initUI() {
        UIUtil.setEnterpriseTitle(lbTitle, AppContext.getUserEnterprise().getName());
        refreshOngoingRFQTable();
        enableQuotationFeature(false);
    }

    private void enableQuotationFeature(boolean enable) {
        if (enable && AppContext.getUserEnterprise().getType() == EnterpriseType.BUYER) {
            UIUtil.show(
                    btnReject,
                    btnForward,
                    txtRemark,
                    lbReject
            );
        } else {
            UIUtil.hide(
                    btnReject,
                    btnForward,
                    txtRemark,
                    lbReject
            );
        }
    }

    private void setupListener() {
        btnView.addActionListener(e -> handleView());
        btnReject.addActionListener(e -> handleReject());
        btnForward.addActionListener(e -> handleForward());
        btnOngoing.addActionListener(e -> handleOngoingRFQ());
        btnClosed.addActionListener(e -> handleClosedRFQ());
        btnBack.addActionListener(e -> handleBack());
        tblRFQ.getSelectionModel().addListSelectionListener(rfqSelectionListener);
    }

    private void handleOngoingRFQ() {
        UIUtil.clearTable(tblQuotation);
        refreshOngoingRFQTable();
        enableQuotationFeature(false);
    }

    private void handleClosedRFQ() {
        UIUtil.clearTable(tblQuotation);
        refreshClosedRFQTable();
    }

    private void handleForward() {
        Quotation selectedQ = getSelectedQuotation();
        if (selectedQ == null) {
            return;
        }

        Result<Void> r = ProcurementController.getInstance().handleQuotationForward(
                selectedQ,
                selectedRFQ
        );

        if (!r.isSuccess()) {
            UIUtil.showError(this, r.getMessage());
            return;
        }
        UIUtil.showInfo(this, r.getMessage());
        refreshQuotationTable();
    }

    // Show finished RFQ
    private void refreshClosedRFQTable() {
        isProgrammaticSelection = true;

        // Get unfinished RFQ list
        List<RFQ> rfq = AppContext.getNetwork().getRfqDirectory().getRFQList().stream()
                .filter(r -> r.getStatus() == RFQStatus.CLOSED)
                .collect(Collectors.toList());

        UIUtil.reloadTable(
                tblRFQ,
                rfq,
                e -> new Object[] {
                        e,
                        e.getVendor(),
                        e.getStatus()
                }
        );
        isProgrammaticSelection = false;
    }


    // Show unfinished RFQ
    private void refreshOngoingRFQTable() {
        isProgrammaticSelection = true;
        // Get unfinished RFQ list
        List<RFQ> rfq = AppContext.getNetwork().getRfqDirectory().getRFQList().stream()
                        .filter(r -> r.getStatus() == RFQStatus.RECEIVED)
                        .collect(Collectors.toList());

        UIUtil.reloadTable(
                tblRFQ,
                rfq,
                e -> new Object[] {
                        e,
                        e.getVendor(),
                        e.getStatus()
                }
        );
        isProgrammaticSelection = false;
    }

    private void refreshQuotationTable() {
        selectedRFQ = UIUtil.getSelectedTableObject(
                tblRFQ,
                0,
                RFQ.class,
                this,
                "Please select a RFQ"
        ).orElse(null);

        if (selectedRFQ == null) {
            return;
        }

        UIUtil.reloadTable(tblQuotation,
                selectedRFQ.getQuotations().getQuotationList(),
                q -> new Object[]{
                        q,
                        q.getVendor(),
                        q.getPrice(),
                        q.getStatus()
                }
        );
    }

    private Quotation getSelectedQuotation() {
        return UIUtil.getSelectedTableObject(
                tblQuotation,
                0,
                Quotation.class,
                this,
                "Please select a quotation"
        ).orElse(null);
    }

    private void handleReject() {
        Quotation selectedQ = getSelectedQuotation();
        if (selectedQ == null) {
            return;
        }

        Result<Void> r = ProcurementController.getInstance().handleQuotationReject(
                selectedQ,
                txtRemark.getText()
        );

        if (!r.isSuccess()) {
            UIUtil.showError(this, r.getMessage());
            return;
        }
        UIUtil.showInfo(this, r.getMessage());
        UIUtil.clearTextComponents(txtRemark);
        refreshQuotationTable();
    }

    private void handleView() {
        Quotation selectedQ = getSelectedQuotation();
        if (selectedQ == null) {
            return;
        }

        NavigationUtil.getInstance().showCard(
                new QuotationViewDetailPanel(selectedQ),
                "Quotation View Detail"
        );
    }

    private void handleBack() {
        NavigationUtil.getInstance().goBack();
        callback.refreshData();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblQuotation = new javax.swing.JTable();
        lbTitle = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRFQ = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbReject = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtRemark = new javax.swing.JTextArea();
        btnOngoing = new javax.swing.JButton();
        btnClosed = new javax.swing.JButton();

        tblQuotation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Vendor Name", "Remarks", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblQuotation);
        if (tblQuotation.getColumnModel().getColumnCount() > 0) {
            tblQuotation.getColumnModel().getColumn(0).setMinWidth(140);
            tblQuotation.getColumnModel().getColumn(1).setResizable(false);
            tblQuotation.getColumnModel().getColumn(2).setResizable(false);
            tblQuotation.getColumnModel().getColumn(3).setResizable(false);
        }

        lbTitle.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Quotation Service");

        btnBack.setText("<<Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnReject.setBackground(new java.awt.Color(255, 0, 0));
        btnReject.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        btnReject.setText("Reject");
        btnReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRejectActionPerformed(evt);
            }
        });

        btnView.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnView.setText("View Details");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        btnForward.setBackground(new java.awt.Color(102, 204, 0));
        btnForward.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        btnForward.setText("Forward to Finance ");
        btnForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForwardActionPerformed(evt);
            }
        });

        tblRFQ.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Enterprise", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane2.setViewportView(tblRFQ);
        if (tblRFQ.getColumnModel().getColumnCount() > 0) {
            tblRFQ.getColumnModel().getColumn(0).setResizable(false);
            tblRFQ.getColumnModel().getColumn(0).setPreferredWidth(160);
            tblRFQ.getColumnModel().getColumn(1).setResizable(false);
            tblRFQ.getColumnModel().getColumn(1).setPreferredWidth(70);
            tblRFQ.getColumnModel().getColumn(2).setResizable(false);
            tblRFQ.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 3, 13)); // NOI18N
        jLabel1.setText("RFQ List");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 3, 13)); // NOI18N
        jLabel2.setText("Quotation List");

        lbReject.setFont(new java.awt.Font("Helvetica Neue", 3, 13)); // NOI18N
        lbReject.setText("Left a remark before reject:");

        txtRemark.setColumns(20);
        txtRemark.setRows(5);
        jScrollPane3.setViewportView(txtRemark);

        btnOngoing.setText("On Going");

        btnClosed.setText("Closed");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(249, 249, 249)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbReject)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnOngoing, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(btnClosed, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(158, 158, 158)
                                .addComponent(btnForward))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(369, 369, 369)))
                            .addComponent(btnView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBack)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(28, 28, 28))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnOngoing)
                                    .addComponent(btnClosed))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbReject))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnForward, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReject, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnRejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRejectActionPerformed
    }//GEN-LAST:event_btnRejectActionPerformed

    private void btnForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForwardActionPerformed
    }//GEN-LAST:event_btnForwardActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClosed;
    private javax.swing.JButton btnForward;
    private javax.swing.JButton btnOngoing;
    private javax.swing.JButton btnReject;
    private javax.swing.JButton btnView;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbReject;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JTable tblQuotation;
    private javax.swing.JTable tblRFQ;
    private javax.swing.JTextArea txtRemark;
    // End of variables declaration//GEN-END:variables
}
