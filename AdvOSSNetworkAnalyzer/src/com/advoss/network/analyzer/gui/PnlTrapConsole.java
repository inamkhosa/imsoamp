/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlTrapConsole.java
 *
 * Created on Apr 20, 2009, 4:11:16 PM
 */
package com.advoss.network.analyzer.gui;

import com.advoss.network.trap.TrapReceiver;
import com.advoss.util.CommonFunctions;
import com.advoss.util.SwingUtilities;
import com.sun.net.ssl.internal.ssl.Debug;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.ict.oamp.service.client.Trap;
import org.ict.oamp.service.client.TrapBinding;
import org.ict.oamp.service.client.TrapType;

/**
 *
 * @author Alam Sher
 */
public class PnlTrapConsole extends javax.swing.JPanel {

    private boolean accepting = false;
    private DefaultTableModel tableModelTraps = new DefaultTableModel(new String[]{"#", "Trap Host", "Trap PDU", ""}, 0) {

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    private DefaultTableModel tableModelTrapVariabileBindings = new DefaultTableModel(new String[]{"OID", "Name", "Type", "Value", "Description", ""}, 0) {

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };

    /** Creates new form PnlTrapConsole */
    public PnlTrapConsole() {
        initComponents();
        customize();
    }

    private void customize() {
        SwingUtilities.customizeJTable(tblTraps, true, false, true, new int[]{10, 50, 800});
        SwingUtilities.hideJTableColumn(tblTraps, 3);
        SwingUtilities.customizeJTable(tblTrapVariableBindings, true, false, true);
        SwingUtilities.hideJTableColumn(tblTrapVariableBindings, 5);
        tblTraps.getSelectionModel().addListSelectionListener(new SelectionListener(tblTraps, this));
    }

    public void addTrap(Trap trap) {
        try {
            tableModelTraps.addRow(new Object[]{tblTraps.getRowCount(), trap.getTrapHost(), getTrapSummary(trap), trap});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
        tblTraps.scrollRectToVisible(tblTraps.getCellRect(tblTraps.getRowCount()-1, tblTraps.getColumnCount(), true));
        } catch (Exception ex){
        }
    }

    public String getTrapSummary(Trap trap) {
        String data = "";
        try {
            data = "[" +
                    " TrapType = " + trap.getTrapType() + " ; " +
                    " Request ID =  " + trap.getRequestId() + " ; " +
                    " Trap Host = " + trap.getTrapHost() + " ; ";
            if (trap.getTrapType() == TrapType.V1TRAP) {
                data += " Enterprise = " + trap.getEnterprise() + " ; " +
                        " Generic Type = " + trap.getGenericType() + " ; " +
                        " Specific Type = " + trap.getSpecificType() + " ; ";

            } else {
                data += " SNMP OID = " + trap.getSnmpOID() + " ; ";
            }
            data += " { " +
                    "Total Bindings = " + (trap.getBindings() != null ? trap.getBindings().length : "0") +
                    " }" +
                    " ]";
        } catch (Exception ex) {
        }
        return data;
    }

    public void showData(int row) {
        if (tblTraps.getValueAt(row, 3) != null) {
            try {
                Trap trap = (Trap) tblTraps.getValueAt(row, 3);
                cleanupDetails();
                lblTrapTypeValue.setText(trap.getTrapType().toString());
                lblRequestIdValue.setText(String.valueOf(trap.getRequestId()));
                if (trap.getTrapType() == TrapType.V1TRAP) {
                    lblEnterpriseValue.setText(trap.getEnterprise());
                    lblGenericTypeValue.setText(trap.getGenericType().toString());
                    lblSpecificTypeValue.setText(String.valueOf(trap.getSpecificType()));
                } else {
                    lblSnmpOIDValue.setText(trap.getSnmpOID());
                }
                lblUpTimeValue.setText(CommonFunctions.formatDate(new SimpleDateFormat("hh:mm:ss.S"), trap.getTrapTimeStamp()));
                lblStorageTimeValue.setText(CommonFunctions.formatDate(new SimpleDateFormat("hh:mm:ss.S"), trap.getStoradeTimeStamp()));
                lblSecurityNameValue.setText(trap.getSecurityName());
                lblSecurityModelValue.setText(trap.getSecurityModel());
                lblSecurityLevelValue.setText(trap.getSecurityLevel());
                if (trap.getBindings() != null && trap.getBindings().length > 0) {
                    for (int i = 0; i < trap.getBindings().length; i++) {
                        TrapBinding binding = trap.getBindings()[i];
                        tableModelTrapVariabileBindings.addRow(new Object[]{binding.getOid(), binding.getName(), binding.getType(), binding.getValue(), binding.getDescription(), binding});
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                splitPaneTrapDetails.revalidate();
                pnlTrapDetails.revalidate();
            } catch (Exception ex){
            }
        }
    }

    public boolean isAccepting() {
        return accepting;
    }

    public void cleanup() {
        tableModelTraps.setRowCount(0);
        cleanupDetails();
    }

    public void cleanupDetails() {
        tableModelTrapVariabileBindings.setRowCount(0);
        lblTrapTypeValue.setText("");
        lblRequestIdValue.setText("");
        lblEnterpriseValue.setText("");
        lblGenericTypeValue.setText("");
        lblSpecificTypeValue.setText("");
        lblSnmpOIDValue.setText("");
        lblUpTimeValue.setText("");
        lblStorageTimeValue.setText("");
        lblSecurityNameValue.setText("");
        lblSecurityModelValue.setText("");
        lblSecurityLevelValue.setText("");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        toolBar = new javax.swing.JToolBar();
        btnCaptureTraps = new javax.swing.JButton();
        btnConfigureFilters = new javax.swing.JButton();
        splitPaneTraps = new javax.swing.JSplitPane();
        pnlTraps = new javax.swing.JPanel();
        scrollPabeTrapsTable = new javax.swing.JScrollPane();
        tblTraps = new javax.swing.JTable();
        pnlTrapDetails = new javax.swing.JPanel();
        splitPaneTrapDetails = new javax.swing.JSplitPane();
        pnlTrapGeneralDetails = new javax.swing.JPanel();
        lblTrapType = new javax.swing.JLabel();
        lblTrapTypeValue = new javax.swing.JLabel();
        lblRequestId = new javax.swing.JLabel();
        lblRequestIdValue = new javax.swing.JLabel();
        lblGenericType = new javax.swing.JLabel();
        lblGenericTypeValue = new javax.swing.JLabel();
        lblSpecificType = new javax.swing.JLabel();
        lblSpecificTypeValue = new javax.swing.JLabel();
        lblSnmpOID = new javax.swing.JLabel();
        lblSnmpOIDValue = new javax.swing.JLabel();
        lblUpTime = new javax.swing.JLabel();
        lblUpTimeValue = new javax.swing.JLabel();
        lblStorageTime = new javax.swing.JLabel();
        lblStorageTimeValue = new javax.swing.JLabel();
        lblSecurityName = new javax.swing.JLabel();
        lblSecurityNameValue = new javax.swing.JLabel();
        lblSecurityModel = new javax.swing.JLabel();
        lblSecurityModelValue = new javax.swing.JLabel();
        lblSecurityLevel = new javax.swing.JLabel();
        lblSecurityLevelValue = new javax.swing.JLabel();
        lblSpacer = new javax.swing.JLabel();
        lblEnterprise = new javax.swing.JLabel();
        lblEnterpriseValue = new javax.swing.JLabel();
        pnlTrapBindings = new javax.swing.JPanel();
        scrollPaneVariableBindings = new javax.swing.JScrollPane();
        tblTrapVariableBindings = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        toolBar.setRollover(true);

        btnCaptureTraps.setText("Capture");
        btnCaptureTraps.setFocusable(false);
        btnCaptureTraps.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCaptureTraps.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCaptureTraps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaptureTrapsActionPerformed(evt);
            }
        });
        toolBar.add(btnCaptureTraps);

        btnConfigureFilters.setText("Configure Filters");
        btnConfigureFilters.setFocusable(false);
        btnConfigureFilters.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfigureFilters.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(btnConfigureFilters);

        add(toolBar, java.awt.BorderLayout.NORTH);

        splitPaneTraps.setDividerLocation(375);
        splitPaneTraps.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlTraps.setBorder(javax.swing.BorderFactory.createTitledBorder("Recived Traps"));
        pnlTraps.setLayout(new java.awt.BorderLayout());

        tblTraps.setModel(tableModelTraps);
        tblTraps.getTableHeader().setReorderingAllowed(false);
        scrollPabeTrapsTable.setViewportView(tblTraps);

        pnlTraps.add(scrollPabeTrapsTable, java.awt.BorderLayout.CENTER);

        splitPaneTraps.setLeftComponent(pnlTraps);

        pnlTrapDetails.setLayout(new java.awt.BorderLayout());

        splitPaneTrapDetails.setDividerLocation(250);

        pnlTrapGeneralDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Trap General Information"));
        pnlTrapGeneralDetails.setLayout(new java.awt.GridBagLayout());

        lblTrapType.setText("Trap Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblTrapType, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblTrapTypeValue, gridBagConstraints);

        lblRequestId.setText("Request ID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblRequestId, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblRequestIdValue, gridBagConstraints);

        lblGenericType.setText("Generic Trap Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblGenericType, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblGenericTypeValue, gridBagConstraints);

        lblSpecificType.setText("Specific Trap Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblSpecificType, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblSpecificTypeValue, gridBagConstraints);

        lblSnmpOID.setText("SNMP OID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblSnmpOID, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblSnmpOIDValue, gridBagConstraints);

        lblUpTime.setText("Up Time:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblUpTime, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblUpTimeValue, gridBagConstraints);

        lblStorageTime.setText("Storage Time:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblStorageTime, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblStorageTimeValue, gridBagConstraints);

        lblSecurityName.setText("Security Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblSecurityName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblSecurityNameValue, gridBagConstraints);

        lblSecurityModel.setText("Security Model:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblSecurityModel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblSecurityModelValue, gridBagConstraints);

        lblSecurityLevel.setText("Security Level:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblSecurityLevel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        pnlTrapGeneralDetails.add(lblSecurityLevelValue, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlTrapGeneralDetails.add(lblSpacer, gridBagConstraints);

        lblEnterprise.setText("Enterprise Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblEnterprise, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlTrapGeneralDetails.add(lblEnterpriseValue, gridBagConstraints);

        splitPaneTrapDetails.setLeftComponent(pnlTrapGeneralDetails);

        pnlTrapBindings.setBorder(javax.swing.BorderFactory.createTitledBorder("Trap's associated Variaible Bindings"));
        pnlTrapBindings.setLayout(new java.awt.BorderLayout());

        tblTrapVariableBindings.setModel(tableModelTrapVariabileBindings);
        tblTrapVariableBindings.getTableHeader().setReorderingAllowed(false);
        scrollPaneVariableBindings.setViewportView(tblTrapVariableBindings);

        pnlTrapBindings.add(scrollPaneVariableBindings, java.awt.BorderLayout.CENTER);

        splitPaneTrapDetails.setRightComponent(pnlTrapBindings);

        pnlTrapDetails.add(splitPaneTrapDetails, java.awt.BorderLayout.CENTER);

        splitPaneTraps.setRightComponent(pnlTrapDetails);

        add(splitPaneTraps, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCaptureTrapsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaptureTrapsActionPerformed
        if (btnCaptureTraps.getText().contains("Capture")) {
            if (TrapReceiver.getInstance().initialize()) {
                cleanup();
                accepting = true;
                btnCaptureTraps.setText("Freeze");
            } else {
                SwingUtilities.showErrorMessage("Unable to initialize the Trap Receiver:\n" + TrapReceiver.getInstance().getExceptionMessage());
            }
        } else {
            TrapReceiver.getInstance().stopReceiver();
            accepting = false;
            btnCaptureTraps.setText("Capture Traps");
        }
    }//GEN-LAST:event_btnCaptureTrapsActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCaptureTraps;
    private javax.swing.JButton btnConfigureFilters;
    private javax.swing.JLabel lblEnterprise;
    private javax.swing.JLabel lblEnterpriseValue;
    private javax.swing.JLabel lblGenericType;
    private javax.swing.JLabel lblGenericTypeValue;
    private javax.swing.JLabel lblRequestId;
    private javax.swing.JLabel lblRequestIdValue;
    private javax.swing.JLabel lblSecurityLevel;
    private javax.swing.JLabel lblSecurityLevelValue;
    private javax.swing.JLabel lblSecurityModel;
    private javax.swing.JLabel lblSecurityModelValue;
    private javax.swing.JLabel lblSecurityName;
    private javax.swing.JLabel lblSecurityNameValue;
    private javax.swing.JLabel lblSnmpOID;
    private javax.swing.JLabel lblSnmpOIDValue;
    private javax.swing.JLabel lblSpacer;
    private javax.swing.JLabel lblSpecificType;
    private javax.swing.JLabel lblSpecificTypeValue;
    private javax.swing.JLabel lblStorageTime;
    private javax.swing.JLabel lblStorageTimeValue;
    private javax.swing.JLabel lblTrapType;
    private javax.swing.JLabel lblTrapTypeValue;
    private javax.swing.JLabel lblUpTime;
    private javax.swing.JLabel lblUpTimeValue;
    private javax.swing.JPanel pnlTrapBindings;
    private javax.swing.JPanel pnlTrapDetails;
    private javax.swing.JPanel pnlTrapGeneralDetails;
    private javax.swing.JPanel pnlTraps;
    private javax.swing.JScrollPane scrollPabeTrapsTable;
    private javax.swing.JScrollPane scrollPaneVariableBindings;
    private javax.swing.JSplitPane splitPaneTrapDetails;
    private javax.swing.JSplitPane splitPaneTraps;
    private javax.swing.JTable tblTrapVariableBindings;
    private javax.swing.JTable tblTraps;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables

    private class SelectionListener implements ListSelectionListener {

        JTable table = null;
        PnlTrapConsole panel = null;
        // It is necessary to keep the table since it is not possible
        // to determine the table from the event's source

        SelectionListener(JTable table, PnlTrapConsole panel) {
            this.table = table;
            this.panel = panel;
        }

        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRows[] = table.getSelectedRows();
                if (selectedRows.length > 0) {
                    java.util.Arrays.sort(selectedRows);
                    int largetsRow = selectedRows[selectedRows.length - 1];
                    panel.showData(largetsRow);
                }
            }
        }
    }
}
