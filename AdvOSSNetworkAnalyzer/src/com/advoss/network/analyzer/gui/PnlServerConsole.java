/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlServerConsole.java
 *
 * Created on Apr 5, 2009, 7:07:44 PM
 */
package com.advoss.network.analyzer.gui;

import com.advoss.network.analyzer.Constants;
import com.advoss.network.analyzer.NetworkAnalyzer;
import com.advoss.util.CommonFunctions;
import com.advoss.util.SwingUtilities;
import java.awt.Color;
import java.awt.Component;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.ict.oamp.service.client.TrapDefinition;
import org.ict.oamp.service.client.TrapService;
import org.ict.oamp.service.client.TrapType;

/**
 *
 * @author Alam Sher
 */
public class PnlServerConsole extends javax.swing.JPanel {

    private DefaultTableModel tableModelTrapDefinitions =
            new DefaultTableModel(new Object[]{"Definition Name", "Trap Type", "Enterprise", "Generic Type", "Specific Type", "SNMP OID", "Trap Table", ""}, 0) {

                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };

    /** Creates new form PnlServerConsole */
    public PnlServerConsole() {
        initComponents();
        customize();
    }

    private void customize() {
        SwingUtilities.customizeJTable(tblTrapDefinitions, true, false, false);
        SwingUtilities.hideJTableColumn(tblTrapDefinitions, 7);
        btnRunServer.setEnabled(false);
        btnRefresh.setEnabled(false);
        btnNewTrapDefinition.setEnabled(false);
        btnEditTrapDefinition.setEnabled(false);
        btnDeleteTrapDefinition.setEnabled(false);
    }

    public void loadTrapDefinitions() {
        try {
            tableModelTrapDefinitions.setRowCount(0);
            TrapService client = NetworkAnalyzer.getInstance().getClient();
            TrapDefinition[] definitions = client.getTrapDefinitions();
            Vector<TrapDefinition> trapDefinitions = new Vector<TrapDefinition>(Arrays.asList(definitions));
            for (int i = 0; i < trapDefinitions.size(); i++) {
                TrapDefinition trapDefinition = trapDefinitions.get(i);
                tableModelTrapDefinitions.addRow(new Object[]{trapDefinition.getTrapDefinitionName(), trapDefinition.getTrapType(), trapDefinition.getEnterprise(), trapDefinition.getTrapGenericType(), trapDefinition.getTrapSpecificType(), trapDefinition.getSmpOid(), trapDefinition.getTrapTableDefinition().getTableName(), trapDefinition});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            SwingUtilities.showErrorMessage(ex.getMessage());
        }
    }

    public void updateConnection(boolean success) {
        btnRunServer.setEnabled(success);
        btnRefresh.setEnabled(success);
        btnNewTrapDefinition.setEnabled(success);
        btnEditTrapDefinition.setEnabled(success);
        btnDeleteTrapDefinition.setEnabled(success);
        tableModelTrapDefinitions.setRowCount(0);
        if (success) {
            try {
                lblStatusValue.setText(NetworkAnalyzer.getInstance().getClient().getStatusDescription());
                lblElapsedTimeValue.setText(CommonFunctions.getUpTime(NetworkAnalyzer.getInstance().getClient().getElapsedTime()));
                loadTrapDefinitions();
            } catch (Exception ex) {
                SwingUtilities.showErrorMessage("Unable to update server connection status:\n" + ex.toString());
                updateConnection(false);
            }
        } else {
            lblStatusValue.setText("Not available...");
            lblElapsedTimeValue.setText("Unknown");
        }
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

        pnlMain = new javax.swing.JPanel();
        pnlServerStatus = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        lblStatusValue = new javax.swing.JLabel();
        lblElapsedTime = new javax.swing.JLabel();
        lblElapsedTimeValue = new javax.swing.JLabel();
        btnRunServer = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        pnlTrapDefinitions = new javax.swing.JPanel();
        scrollPaneTrapDefinitions = new javax.swing.JScrollPane();
        tblTrapDefinitions = new javax.swing.JTable(){
            //    public Component prepareRenderer
            //    (TableCellRenderer renderer,int Index_row, int Index_col) {
                //        Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                //
                //        if (!isCellSelected(Index_row, Index_col)) {
                    //            try {
                        //                TrapDefinition definition = (TrapDefinition) tblTrapDefinitions.getValueAt(Index_row, 7);
                        //                if(definition.getTrapType() == TrapType.V1TRAP) {
                            //                    comp.setBackground(Color.RED);
                            //                    comp.setForeground(Color.BLACK);
                            //                } else if (definition.getTrapType() == TrapType.TRAP || definition.getTrapType() == TrapType.NOTIFICATION) {
                            //                    comp.setBackground(Color.ORANGE);
                            //                    comp.setForeground(Color.BLACK);
                            //                } else if (definition.getTrapType() == TrapType.INFORM) {
                            //                    comp.setBackground(Color.GREEN);
                            //                    comp.setForeground(Color.BLACK);
                            //                }
                        //            } catch (Exception ex){
                        //                ex.printStackTrace();
                        //            }
                    //        } else {
                    //            comp.setBackground(Color.white);
                    //            comp.setForeground(Color.DARK_GRAY);
                    //        }
                //        return comp;
                //    }
        };
        pnlTrapDefinitionsControl = new javax.swing.JPanel();
        btnNewTrapDefinition = new javax.swing.JButton();
        btnEditTrapDefinition = new javax.swing.JButton();
        btnDeleteTrapDefinition = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnlMain.setBorder(javax.swing.BorderFactory.createTitledBorder("Server Console"));
        pnlMain.setLayout(new java.awt.BorderLayout());

        pnlServerStatus.setBorder(javax.swing.BorderFactory.createTitledBorder("Trap Server Status"));
        pnlServerStatus.setLayout(new java.awt.GridBagLayout());

        lblStatus.setText("Status:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlServerStatus.add(lblStatus, gridBagConstraints);

        lblStatusValue.setText("Not available...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlServerStatus.add(lblStatusValue, gridBagConstraints);

        lblElapsedTime.setText("Elapsed Time:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlServerStatus.add(lblElapsedTime, gridBagConstraints);

        lblElapsedTimeValue.setText("Unknown");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlServerStatus.add(lblElapsedTimeValue, gridBagConstraints);

        btnRunServer.setText("Run Server");
        btnRunServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunServerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlServerStatus.add(btnRunServer, gridBagConstraints);

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlServerStatus.add(btnRefresh, gridBagConstraints);

        pnlMain.add(pnlServerStatus, java.awt.BorderLayout.NORTH);

        pnlTrapDefinitions.setBorder(javax.swing.BorderFactory.createTitledBorder("Trap Definitions"));
        pnlTrapDefinitions.setLayout(new java.awt.BorderLayout());

        tblTrapDefinitions.setModel(tableModelTrapDefinitions);
        tblTrapDefinitions.getTableHeader().setReorderingAllowed(false);
        scrollPaneTrapDefinitions.setViewportView(tblTrapDefinitions);

        pnlTrapDefinitions.add(scrollPaneTrapDefinitions, java.awt.BorderLayout.CENTER);

        pnlTrapDefinitionsControl.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnNewTrapDefinition.setText("New Trap Definition");
        btnNewTrapDefinition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTrapDefinitionActionPerformed(evt);
            }
        });
        pnlTrapDefinitionsControl.add(btnNewTrapDefinition);

        btnEditTrapDefinition.setText("Edit Trap Definition");
        btnEditTrapDefinition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditTrapDefinitionActionPerformed(evt);
            }
        });
        pnlTrapDefinitionsControl.add(btnEditTrapDefinition);

        btnDeleteTrapDefinition.setText("Delete Trap Definition");
        btnDeleteTrapDefinition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTrapDefinitionActionPerformed(evt);
            }
        });
        pnlTrapDefinitionsControl.add(btnDeleteTrapDefinition);

        pnlTrapDefinitions.add(pnlTrapDefinitionsControl, java.awt.BorderLayout.PAGE_END);

        pnlMain.add(pnlTrapDefinitions, java.awt.BorderLayout.CENTER);

        add(pnlMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        updateConnection(true);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnRunServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunServerActionPerformed
        if (btnRunServer.getText().equals("Run Server")) {
            try {
                if (NetworkAnalyzer.getInstance().getClient().startService()) {
                    btnRunServer.setText("Stop Server");
                    updateConnection(true);
                }
            } catch (Exception ex) {
                SwingUtilities.showErrorMessage(ex.toString());
            }
        } else {
            try {
                if (SwingUtilities.showWarnMessage("This will shutdown the trap server. Are you sure?") != JOptionPane.OK_OPTION) {
                    return;
                }
                if (NetworkAnalyzer.getInstance().getClient().stopService()) {
                    btnRunServer.setText("Run Server");
                    updateConnection(true);
                }
            } catch (Exception ex) {
                SwingUtilities.showErrorMessage(ex.toString());
            }
        }
    }//GEN-LAST:event_btnRunServerActionPerformed

    private void btnNewTrapDefinitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTrapDefinitionActionPerformed
        DlgTrapDefinitionManager dialog = new DlgTrapDefinitionManager(FrmNetworkAnalyzer.getInstance(), true, Constants.ACTION_ADD, null);
        SwingUtilities.centerDialog(dialog);
        dialog.setVisible(true);
        loadTrapDefinitions();
    }//GEN-LAST:event_btnNewTrapDefinitionActionPerformed

    private void btnEditTrapDefinitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditTrapDefinitionActionPerformed
        if (tblTrapDefinitions.getSelectedRowCount() <= 0) {
            return;
        }
        if (tblTrapDefinitions.getSelectedRowCount() > 1) {
            SwingUtilities.showErrorMessage("Only one Trap Definition can be updated at a time.");
            return;
        }
        TrapDefinition trapDefinition = (TrapDefinition) tblTrapDefinitions.getValueAt(tblTrapDefinitions.getSelectedRow(), 7);
        if (trapDefinition != null) {
            DlgTrapDefinitionManager dialog = new DlgTrapDefinitionManager(FrmNetworkAnalyzer.getInstance(), true, Constants.ACTION_EDIT, trapDefinition);
            SwingUtilities.centerDialog(dialog);
            dialog.setVisible(true);
            loadTrapDefinitions();
        }
    }//GEN-LAST:event_btnEditTrapDefinitionActionPerformed

    private void btnDeleteTrapDefinitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTrapDefinitionActionPerformed
        if (tblTrapDefinitions.getSelectedRowCount() > 0) {
            if (SwingUtilities.showWarnMessage("Selected Trap Definiton(s) will be deleted permanently. Are you sure?") != JOptionPane.OK_OPTION) {
                return;
            }
            try {
                TrapService client = NetworkAnalyzer.getInstance().getClient();
                int selectedRows[] = tblTrapDefinitions.getSelectedRows();
                for (int i = 0; i < selectedRows.length; i++) {
                    TrapDefinition trapDefinition = (TrapDefinition) tblTrapDefinitions.getValueAt(selectedRows[i], 7);
                    client.deleteTrapDefinition(trapDefinition);
                }
                loadTrapDefinitions();
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.showErrorMessage(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteTrapDefinitionActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteTrapDefinition;
    private javax.swing.JButton btnEditTrapDefinition;
    private javax.swing.JButton btnNewTrapDefinition;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRunServer;
    private javax.swing.JLabel lblElapsedTime;
    private javax.swing.JLabel lblElapsedTimeValue;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatusValue;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlServerStatus;
    private javax.swing.JPanel pnlTrapDefinitions;
    private javax.swing.JPanel pnlTrapDefinitionsControl;
    private javax.swing.JScrollPane scrollPaneTrapDefinitions;
    private javax.swing.JTable tblTrapDefinitions;
    // End of variables declaration//GEN-END:variables
}
