/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.quotation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import common.AppContext;
import directory.RFQDirectory;
import enums.RequestStatus;
import model.quotation.Quotation;
import model.quotation.RFQ;
import util.NavigationUtil;
import util.UIUtil;


/**
 *
 * @author qiyaochen
 */
public class ManageQuotationPanel extends javax.swing.JPanel {
    
    // TEMP: For testing multiple rfqDir and refresh behavior
    private RFQDirectory rfqDir;
    private int currentIndex = 0;
    private RFQ rfq;

    /**
     * Creates new form QuotationPanel
     */
    public ManageQuotationPanel() {
        initComponents();
        this.rfqDir = AppContext.getNetwork().getRfqDirectory();

        refreshQuotationTable();
        populateRFQListTable();
        forwardBtn.setEnabled(false);
        
        quotationTable.getSelectionModel().addListSelectionListener(e -> {
            int row = quotationTable.getSelectedRow();
            if (row >= 0) {
                String status = (String) quotationTable.getModel().getValueAt(row, 3);
                forwardBtn.setEnabled(!"REJECTED".equalsIgnoreCase(status));
            } else {
                forwardBtn.setEnabled(false);
            }
        });
    }
    
    private void refreshQuotationTable() {
        DefaultTableModel model = (DefaultTableModel) quotationTable.getModel();
        model.setRowCount(0); // clear existing
        rfq = UIUtil.getSelectedTableObject(
                RFQListTable,
                0,
                RFQ.class,
                this,
                "Please select a RFQ"
        ).orElse(null);

        if (rfq == null) {
            return;
        }

        for (Quotation q : rfq.getQuotations().getQuotationList()) {
            model.addRow(new Object[]{
                q.getId(),
                q.getVendor().getName(),
                q.getRemarks(),
                q.getStatus().toString()
            });
        }

        quotationTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = (String) table.getModel().getValueAt(row, 3); // status column
                if ("REJECTED".equals(status) || "FORWARDED".equals(status)) {
                    c.setForeground(java.awt.Color.GRAY);
                } else {
                    c.setForeground(java.awt.Color.BLACK);
                }
                return c;
            }
        });
    }
        
        private void loadNextRFQ() {
            currentIndex++;
            if (currentIndex < rfqDir.getRFQList().size()) {
                rfq = rfqDir.getRFQList().get(currentIndex);
                refreshQuotationTable();
                forwardBtn.setEnabled(false);
                populateRFQListTable();
            } else {
                JOptionPane.showMessageDialog(this, "No more rfqDir available.");
                ((DefaultTableModel) quotationTable.getModel()).setRowCount(0);
            }
        }
        
        private void populateRFQListTable() {
            DefaultTableModel model = (DefaultTableModel) RFQListTable.getModel();
            model.setRowCount(0); // Clear old data
        
            for (RFQ r : rfqDir.getRFQList()) {
                // Join vendor names from all quotations
                StringBuilder vendorNames = new StringBuilder();
                for (Quotation q : r.getQuotations().getQuotationList()) {
                    if (vendorNames.length() > 0) vendorNames.append(", ");
                    vendorNames.append(q.getVendor().getName());
                }

                String prId = (r.getId() != null && !r.getId().isEmpty()) ? r.getId() : "(No ID)";
                System.out.println("Loading RFQ ID into table: " + prId);
                String vendors = vendorNames.length() > 0 ? vendorNames.toString() : "(No vendors)";
                boolean isForwarded = r.getQuotations().getSelectedQuotation() != null && r.getQuotations().getSelectedQuotation().getStatus() == RequestStatus.PENDING;
                if (isForwarded) {
                    vendors += " (Forwarded)";
                }
                boolean allHandled = r.getQuotations().getQuotationList().stream().allMatch(q -> q.getStatus() != RequestStatus.RECEIVED);
                if (allHandled) {
                    vendors = "<html><font color='gray'>" + vendors + "</font></html>";
                    prId = "<html><font color='gray'>" + prId + "</font></html>";
                }

                model.addRow(new Object[]{
                    rfq,       // ID column
                    vendors     // Enterprise/Vendor names column
                });
            }
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
        quotationTable = new javax.swing.JTable();
        lbTitle = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        rejectBtn = new javax.swing.JButton();
        viewBtn = new javax.swing.JButton();
        forwardBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        RFQListTable = new javax.swing.JTable();
        loadBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        quotationTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(quotationTable);

        lbTitle.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Quotation Service");

        btnBack.setText("<<Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        rejectBtn.setBackground(new java.awt.Color(255, 0, 0));
        rejectBtn.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        rejectBtn.setText("Reject");
        rejectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rejectBtnActionPerformed(evt);
            }
        });

        viewBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        viewBtn.setText("View Details");
        viewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBtnActionPerformed(evt);
            }
        });

        forwardBtn.setBackground(new java.awt.Color(204, 204, 204));
        forwardBtn.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        forwardBtn.setText("Forward to Finance ");
        forwardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardBtnActionPerformed(evt);
            }
        });

        RFQListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Enterprise"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(RFQListTable);
        if (RFQListTable.getColumnModel().getColumnCount() > 0) {
            RFQListTable.getColumnModel().getColumn(1).setPreferredWidth(175);
        }

        loadBtn.setBackground(new java.awt.Color(204, 204, 204));
        loadBtn.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        loadBtn.setText("Select");
        loadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("RFQ List");

        jLabel2.setText("Quotation List");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(forwardBtn)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(viewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rejectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(loadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBack)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(loadBtn)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rejectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(forwardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        NavigationUtil.getInstance().goBack();
    }//GEN-LAST:event_btnBackActionPerformed

    private void viewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = quotationTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a quotation to view.");
            return;
        }

        Quotation q = rfq.getQuotations().getQuotationList().get(selectedRow);
        StringBuilder sb = new StringBuilder();
        sb.append("Vendor: ").append(q.getVendor()).append("\n");
        sb.append("Price: $").append(q.getPrice()).append("\n");
        sb.append("Selected: ").append(q.isSelected() ? "Yes" : "No");

        JOptionPane.showMessageDialog(this, sb.toString(), "Quotation Details", JOptionPane.INFORMATION_MESSAGE);
    
    }//GEN-LAST:event_viewBtnActionPerformed

    private void rejectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rejectBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = quotationTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a quotation to reject.");
            return;
        }

        Quotation selected = rfq.getQuotations().getQuotationList().get(selectedRow);
        selected.setStatus(RequestStatus.REJECTED);
        selected.setSelected(false);

        JOptionPane.showMessageDialog(this, "Quotation has been rejected.");

        refreshQuotationTable();
    
    }//GEN-LAST:event_rejectBtnActionPerformed

    private void forwardBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardBtnActionPerformed
        // TODO add your handling code here:
        int selectedRow = quotationTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a quotation to forward.");
            return;
        }
        
        boolean alreadyForwarded = rfq.getQuotations().getQuotationList().stream()
            .anyMatch(q -> q.getStatus() == RequestStatus.RECEIVED);
        
        Quotation selected = rfq.getQuotations().getQuotationList().get(selectedRow);
 
        if (selected.getStatus() == RequestStatus.REJECTED) {
            JOptionPane.showMessageDialog(this, "Rejected quotation cannot be forwarded.");
            return;
        }

        if (alreadyForwarded) {
            JOptionPane.showMessageDialog(this, "Only one quotation can be forwarded per RFQ.");
            return;
        }
        
        // Check if already forwarded to prevent duplicates
        if (selected.getStatus() == RequestStatus.RECEIVED) {
            JOptionPane.showMessageDialog(this, "This quotation is already forwarded.");
            return;
        }

        selected.setStatus(RequestStatus.RECEIVED);
        rfq.getQuotations().setSelectedQuotation(selected);
        selected.setSelected(true); // 👈 让 FinancePanel 能识别这条报价是“被选中并转发”的

        JOptionPane.showMessageDialog(this, "Quotation forwarded to finance.");

        refreshQuotationTable();
        populateRFQListTable();
        forwardBtn.setEnabled(false);
        // Don’t load next RFQ automatically if user wants to stay on the page
        // loadNextRFQ();
    }//GEN-LAST:event_forwardBtnActionPerformed

    private void loadBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBtnActionPerformed
        // TODO add your handling code here:
        int selected = RFQListTable.getSelectedRow();
        if (selected < 0) {
            JOptionPane.showMessageDialog(this, "Select a PR to view rfqDir.");
            return;
        }
        rfq = rfqDir.getRFQList().get(selected);   // load from selected row
        currentIndex = selected;           // sync index
        refreshQuotationTable();                   // reload right table
        forwardBtn.setEnabled(false);      // reset button state

    }//GEN-LAST:event_loadBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable RFQListTable;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton forwardBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JButton loadBtn;
    private javax.swing.JTable quotationTable;
    private javax.swing.JButton rejectBtn;
    private javax.swing.JButton viewBtn;
    // End of variables declaration//GEN-END:variables
}
