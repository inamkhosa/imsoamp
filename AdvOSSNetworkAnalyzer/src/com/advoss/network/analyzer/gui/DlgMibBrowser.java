/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgMibBrowser.java
 *
 * Created on May 11, 2009, 11:39:59 AM
 */
package com.advoss.network.analyzer.gui;

import com.advoss.network.analyzer.NetworkAnalyzer;
import com.advoss.util.IconicListCellRenderer;
import com.advoss.util.SwingUtilities;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.ict.oamp.service.client.ElementManager;
import org.ict.oamp.service.client.MibFile;
import org.ict.oamp.service.client.VarInfo;

/**
 *
 * @author Alam Sher
 */
public class DlgMibBrowser extends javax.swing.JDialog {

    private boolean cancelled = false;
    private Vector<VarInfo> selectedVariables = new Vector<VarInfo>();
    private ElementManager selectedElement = null;
    private DefaultListModel listModelElementManagers = new DefaultListModel();
    private DefaultListModel listModelMibs = new DefaultListModel();
    private DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"#", "Name", "OID", ""}, 0) {

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };

    /** Creates new form DlgMibBrowser */
    public DlgMibBrowser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        customize();
    }

    private void customize() {
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        SwingUtilities.customizeJTable(tblVariableInfos, true, false, false, new int[]{20, 180, 200, 0});
        SwingUtilities.hideJTableColumn(tblVariableInfos, 3);
        tblVariableInfos.getSelectionModel().addListSelectionListener(new SelectionListener(tblVariableInfos, this));
        loadData();
    }

    private void loadData() {
        try {
            ElementManager[] elements = NetworkAnalyzer.getInstance().getClient().getRegisteredElements();
            listModelElementManagers.removeAllElements();
            listModelMibs.removeAllElements();
            if (elements != null) {
                for (int i = 0; i < elements.length; i++) {
                    listModelElementManagers.addElement(elements[i]);
                }
                if (elements.length > 0) {
                    lstElementManagers.setSelectedIndex(0);
                }
            }
        } catch (Exception ex) {
            SwingUtilities.showErrorMessage(ex.toString());
        }
    }

    public void showVariableDescription(int row) {
        if (row >= 0) {
            try {
                VarInfo variable = (VarInfo) tblVariableInfos.getValueAt(row, 3);
                txtVariableDescription.setText(variable.getLongDescription());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Vector<VarInfo> getSelectedVariables() {
        return this.selectedVariables;
    }

    public ElementManager getSelectedElement() {
        return this.selectedElement;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        splitMain = new javax.swing.JSplitPane();
        pnlElements = new javax.swing.JPanel();
        pnlElementManagers = new javax.swing.JPanel();
        scrollPaneElementManagers = new javax.swing.JScrollPane();
        lstElementManagers = new javax.swing.JList();
        pnlElementMibs = new javax.swing.JPanel();
        scrollPaneElementMibs = new javax.swing.JScrollPane();
        lstElementMibs = new javax.swing.JList();
        pnlMibs = new javax.swing.JPanel();
        splitPaneMibDetails = new javax.swing.JSplitPane();
        pnlMibsList = new javax.swing.JPanel();
        pnlMibVariableInfos = new javax.swing.JPanel();
        scrollPaneVariableInfos = new javax.swing.JScrollPane();
        tblVariableInfos = new javax.swing.JTable();
        pnlVariableDescription = new javax.swing.JPanel();
        scrollPantVariableDescription = new javax.swing.JScrollPane();
        txtVariableDescription = new javax.swing.JTextArea();
        pnlControls = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMain.setLayout(new java.awt.BorderLayout());

        splitMain.setDividerLocation(150);

        pnlElements.setLayout(new java.awt.GridLayout(0, 1));

        pnlElementManagers.setBorder(javax.swing.BorderFactory.createTitledBorder("Element Managaers"));
        pnlElementManagers.setLayout(new java.awt.BorderLayout());

        lstElementManagers.setModel(listModelElementManagers);
        lstElementManagers.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstElementManagers.setCellRenderer(new IconicListCellRenderer());
        lstElementManagers.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstElementManagersValueChanged(evt);
            }
        });
        scrollPaneElementManagers.setViewportView(lstElementManagers);

        pnlElementManagers.add(scrollPaneElementManagers, java.awt.BorderLayout.CENTER);

        pnlElements.add(pnlElementManagers);

        pnlElementMibs.setBorder(javax.swing.BorderFactory.createTitledBorder("Element Manager's Mibs"));
        pnlElementMibs.setLayout(new java.awt.BorderLayout());

        lstElementMibs.setModel(listModelMibs);
        lstElementMibs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstElementMibsValueChanged(evt);
            }
        });
        scrollPaneElementMibs.setViewportView(lstElementMibs);

        pnlElementMibs.add(scrollPaneElementMibs, java.awt.BorderLayout.CENTER);

        pnlElements.add(pnlElementMibs);

        splitMain.setLeftComponent(pnlElements);

        pnlMibs.setLayout(new java.awt.BorderLayout());

        splitPaneMibDetails.setDividerLocation(450);

        pnlMibsList.setLayout(new java.awt.BorderLayout());

        pnlMibVariableInfos.setLayout(new java.awt.BorderLayout());

        tblVariableInfos.setModel(tableModel);
        tblVariableInfos.getTableHeader().setReorderingAllowed(false);
        scrollPaneVariableInfos.setViewportView(tblVariableInfos);

        pnlMibVariableInfos.add(scrollPaneVariableInfos, java.awt.BorderLayout.CENTER);

        pnlMibsList.add(pnlMibVariableInfos, java.awt.BorderLayout.CENTER);

        splitPaneMibDetails.setLeftComponent(pnlMibsList);

        pnlVariableDescription.setLayout(new java.awt.BorderLayout());

        txtVariableDescription.setColumns(20);
        txtVariableDescription.setEditable(false);
        txtVariableDescription.setRows(5);
        scrollPantVariableDescription.setViewportView(txtVariableDescription);

        pnlVariableDescription.add(scrollPantVariableDescription, java.awt.BorderLayout.CENTER);

        splitPaneMibDetails.setRightComponent(pnlVariableDescription);

        pnlMibs.add(splitPaneMibDetails, java.awt.BorderLayout.CENTER);

        splitMain.setRightComponent(pnlMibs);

        pnlMain.add(splitMain, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        pnlControls.add(btnRefresh);

        btnAdd.setText("Add Selected");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        pnlControls.add(btnAdd);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        pnlControls.add(btnCancel);

        getContentPane().add(pnlControls, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstElementManagersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstElementManagersValueChanged
        listModelMibs.removeAllElements();
        if (lstElementManagers.getSelectedValue() != null) {
            ElementManager element = (ElementManager) lstElementManagers.getSelectedValue();
            for (int i = 0; i < element.getMibFiles().length; i++) {
                listModelMibs.addElement(element.getMibFiles()[i].getMibName());
            }
        }
    }//GEN-LAST:event_lstElementManagersValueChanged

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.cancelled = true;
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (tblVariableInfos.getSelectedRowCount() > 0) {
            int[] selectedRows = tblVariableInfos.getSelectedRows();
            for (int i = 0; i < selectedRows.length; i++) {
                selectedVariables.add((VarInfo) tblVariableInfos.getValueAt(selectedRows[i], 3));
            }
            selectedElement = (ElementManager) lstElementManagers.getSelectedValue();
            cancelled = false;
            this.dispose();
        } else {
            SwingUtilities.showWarningMessage("Please selected one or more variables..");
            return;
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void lstElementMibsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstElementMibsValueChanged
        tableModel.setRowCount(0);
        txtVariableDescription.setText("");
        ElementManager element = null;
        if (lstElementManagers.getSelectedValue() != null) {
            element = (ElementManager) lstElementManagers.getSelectedValue();
        }
        if (element == null) {
            return;
        }
        if (lstElementMibs.getSelectedValues() != null) {
            String mibNames[] = new String[lstElementMibs.getSelectedValues().length];
            for(int i=0; i<mibNames.length; i++) {
                mibNames[i] = lstElementMibs.getSelectedValues()[i].toString();
            }
            Vector<VarInfo> allVariables = new Vector<VarInfo>();
            for (int i = 0; i < mibNames.length; i++) {
                MibFile mib = null;
                for (int mibIndex = 0; mibIndex < element.getMibFiles().length; mibIndex++) {
                    if (element.getMibFiles()[mibIndex].getMibName().equalsIgnoreCase(mibNames[i])) {
                        mib = element.getMibFiles()[mibIndex];
                    }
                }
                if (mib != null) {
                    for (int varIndex = 0; varIndex < mib.getVariables().length; varIndex++) {
                        allVariables.add(mib.getVariables()[varIndex]);
                    }
                }
                Collections.sort(allVariables, new Comparator() {

                    public int compare(Object o1, Object o2) {
                        VarInfo var1 = (VarInfo) o1;
                        VarInfo var2 = (VarInfo) o2;
                        return var1.getTag().compareTo(var2.getTag());
                    }
                });
            }
            for (int i = 0; i < allVariables.size(); i++) {
                tableModel.addRow(new Object[]{i + 1, allVariables.get(i).getKey(), allVariables.get(i).getTag(), allVariables.get(i)});
            }
        }
    }//GEN-LAST:event_lstElementMibsValueChanged

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadData();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private class SelectionListener implements ListSelectionListener {

        JTable table = null;
        DlgMibBrowser dialog = null;
        // It is necessary to keep the table since it is not possible
        // to determine the table from the event's source

        SelectionListener(JTable table, DlgMibBrowser panel) {
            this.table = table;
            this.dialog = panel;
        }

        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRows[] = table.getSelectedRows();
                if (selectedRows.length > 0) {
                    java.util.Arrays.sort(selectedRows);
                    int largetsRow = selectedRows[selectedRows.length - 1];
                    dialog.showVariableDescription(largetsRow);
                }
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JList lstElementManagers;
    private javax.swing.JList lstElementMibs;
    private javax.swing.JPanel pnlControls;
    private javax.swing.JPanel pnlElementManagers;
    private javax.swing.JPanel pnlElementMibs;
    private javax.swing.JPanel pnlElements;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMibVariableInfos;
    private javax.swing.JPanel pnlMibs;
    private javax.swing.JPanel pnlMibsList;
    private javax.swing.JPanel pnlVariableDescription;
    private javax.swing.JScrollPane scrollPaneElementManagers;
    private javax.swing.JScrollPane scrollPaneElementMibs;
    private javax.swing.JScrollPane scrollPaneVariableInfos;
    private javax.swing.JScrollPane scrollPantVariableDescription;
    private javax.swing.JSplitPane splitMain;
    private javax.swing.JSplitPane splitPaneMibDetails;
    private javax.swing.JTable tblVariableInfos;
    private javax.swing.JTextArea txtVariableDescription;
    // End of variables declaration//GEN-END:variables
}

