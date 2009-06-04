/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlConfigurtionConsole.java
 *
 * Created on May 12, 2009, 12:24:17 AM
 */
package com.advoss.network.analyzer.gui;

import com.advoss.network.analyzer.NetworkAnalyzer;
import com.advoss.util.IconicListCellRenderer;
import com.advoss.util.MibFileListRenderer;
import com.advoss.util.SwingUtilities;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.ict.oamp.service.client.ElementManager;
import org.ict.oamp.service.client.MibFile;
import org.ict.oamp.service.client.VarInfoValue;

/**
 *
 * @author Alam Sher
 */
public class PnlConfigurtionConsole extends javax.swing.JPanel {
    private boolean doNotUpdate = false;
    
    private DefaultListModel listModelElementManagers = new DefaultListModel();
    private DefaultListModel listModelMibs = new DefaultListModel();
    private DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"#", "Name", "OID", "Value", ""}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) {
            if(col == 3) {
                return true;
            }
            return false;
        }
    };

    /** Creates new form PnlConfigurtionConsole */
    public PnlConfigurtionConsole() {
        initComponents();
        customize();
    }

    private void customize() {
        SwingUtilities.customizeJTable(tblVariableInfos, true, false, false, new int[]{20, 180, 200, 100, 0});
        SwingUtilities.hideJTableColumn(tblVariableInfos, 4);
        tblVariableInfos.getSelectionModel().addListSelectionListener(new SelectionListener(tblVariableInfos, this));
        tableModel.addTableModelListener(new TableModelListener() {
            VarInfoValue currentVarInfoValue = null;
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    if (e.getColumn() == 3 && !doNotUpdate) {
                        int firstRow = e.getFirstRow();
                        int lastRow = e.getLastRow();
                        if (firstRow == lastRow) {
                            try {
                                currentVarInfoValue = (VarInfoValue) tblVariableInfos.getValueAt(lastRow, 4);
                                String updateValue = tblVariableInfos.getValueAt(lastRow, 3).toString();
                                setVariableValue(currentVarInfoValue, updateValue, currentVarInfoValue.getValue(), tblVariableInfos.getSelectedRow());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
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
                VarInfoValue variable = (VarInfoValue) tblVariableInfos.getValueAt(row, 4);
                txtVariableDescription.setText(getVariableDescriotion(variable.getVarInfo().getTag()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateMibVariables(MibFile mibFile, ElementManager selectedElement) {
        try {
            VarInfoValue variables[] = NetworkAnalyzer.getInstance().getClient().updateVariables(mibFile.getVariables(), selectedElement.getElementId());
            for (int i = 0; i < variables.length; i++) {
                tableModel.addRow(new Object[]{(i + 1), getVariableName(variables[i].getVarInfo().getTag()), variables[i].getVarInfo().getTag(), variables[i].getValue(), variables[i]});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            SwingUtilities.showErrorMessage(ex.getMessage());
        }
    }

    public void setVariableValue(VarInfoValue currentVarInfo, String newValue, String oldValue, int row) {
        try {
            if(lstElementManagers.getSelectedValue() != null) {
                currentVarInfo.setValue(newValue);
                ElementManager selectedElement = (ElementManager) lstElementManagers.getSelectedValue();
                VarInfoValue[] newValues = NetworkAnalyzer.getInstance().getClient().setVariableValue(new VarInfoValue[]{currentVarInfo}, selectedElement.getElementId());
                if(newValues != null && newValues.length == 1) {
                    doNotUpdate = true;
                    tblVariableInfos.setValueAt(newValues[0], row, 4);
                    tblVariableInfos.setValueAt(newValues[0].getValue(), row, 3);
                    doNotUpdate = false;
                } else {
                    doNotUpdate = true;
                    currentVarInfo.setValue(oldValue);
                    tblVariableInfos.setValueAt(currentVarInfo, row, 4);
                    tblVariableInfos.setValueAt(oldValue, row, 3);
                    doNotUpdate = false;
                }
            }
        } catch (Exception ex) {
            SwingUtilities.showErrorMessage(ex.getMessage());
        }
    }

    private String getVariableName(String oid) {
        if (lstElementMibs.getSelectedValue() == null) {
            return "";
        }
        MibFile selectedMib = (MibFile) lstElementMibs.getSelectedValue();
        for (int i = 0; i < selectedMib.getVariables().length; i++) {
            if (selectedMib.getVariables()[i].getTag().equalsIgnoreCase(oid)) {
                return selectedMib.getVariables()[i].getKey();
            }
        }
        return "";
    }

    private String getVariableDescriotion(String oid) {
        if (lstElementMibs.getSelectedValue() == null) {
            return "";
        }
        MibFile selectedMib = (MibFile) lstElementMibs.getSelectedValue();
        for (int i = 0; i < selectedMib.getVariables().length; i++) {
            if (selectedMib.getVariables()[i].getTag().equalsIgnoreCase(oid)) {
                return selectedMib.getVariables()[i].getLongDescription();
            }
        }
        return "";
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
        pnlVariableControls = new javax.swing.JPanel();
        btnRefreshElements = new javax.swing.JButton();
        btnRefreshVariables = new javax.swing.JButton();
        pnlVariableDescription = new javax.swing.JPanel();
        scrollPantVariableDescription = new javax.swing.JScrollPane();
        txtVariableDescription = new javax.swing.JTextArea();

        setLayout(new java.awt.BorderLayout());

        pnlMain.setLayout(new java.awt.BorderLayout());

        splitMain.setDividerLocation(200);

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
        lstElementMibs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstElementMibs.setCellRenderer(new MibFileListRenderer());
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

        splitPaneMibDetails.setDividerLocation(500);

        pnlMibsList.setLayout(new java.awt.BorderLayout());

        pnlMibVariableInfos.setLayout(new java.awt.BorderLayout());

        tblVariableInfos.setModel(tableModel);
        tblVariableInfos.getTableHeader().setReorderingAllowed(false);
        scrollPaneVariableInfos.setViewportView(tblVariableInfos);

        pnlMibVariableInfos.add(scrollPaneVariableInfos, java.awt.BorderLayout.CENTER);

        pnlMibsList.add(pnlMibVariableInfos, java.awt.BorderLayout.CENTER);

        pnlVariableControls.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnRefreshElements.setText("Refresh Elements");
        btnRefreshElements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshElementsActionPerformed(evt);
            }
        });
        pnlVariableControls.add(btnRefreshElements);

        btnRefreshVariables.setText("Refresh Variables");
        pnlVariableControls.add(btnRefreshVariables);

        pnlMibsList.add(pnlVariableControls, java.awt.BorderLayout.PAGE_START);

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

        add(pnlMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void lstElementManagersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstElementManagersValueChanged
        listModelMibs.removeAllElements();
        if (lstElementManagers.getSelectedValue() != null) {
            ElementManager element = (ElementManager) lstElementManagers.getSelectedValue();
            for (int i = 0; i < element.getMibFiles().length; i++) {
                listModelMibs.addElement(element.getMibFiles()[i]);
            }
            if (listModelMibs.getSize() > 0) {
                lstElementMibs.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_lstElementManagersValueChanged

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
        if (lstElementMibs.getSelectedValue() != null) {
            MibFile selectedMib = (MibFile) lstElementMibs.getSelectedValue();
            if (selectedMib == null) {
                return;
            }
            updateMibVariables(selectedMib, element);
        }
}//GEN-LAST:event_lstElementMibsValueChanged

    private void btnRefreshElementsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshElementsActionPerformed
        loadData();
    }//GEN-LAST:event_btnRefreshElementsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefreshElements;
    private javax.swing.JButton btnRefreshVariables;
    private javax.swing.JList lstElementManagers;
    private javax.swing.JList lstElementMibs;
    private javax.swing.JPanel pnlElementManagers;
    private javax.swing.JPanel pnlElementMibs;
    private javax.swing.JPanel pnlElements;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlMibVariableInfos;
    private javax.swing.JPanel pnlMibs;
    private javax.swing.JPanel pnlMibsList;
    private javax.swing.JPanel pnlVariableControls;
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

    private class SelectionListener implements ListSelectionListener {

        JTable table = null;
        PnlConfigurtionConsole panel = null;
        // It is necessary to keep the table since it is not possible
        // to determine the table from the event's source

        SelectionListener(JTable table, PnlConfigurtionConsole panel) {
            this.table = table;
            this.panel = panel;
        }

        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRows[] = table.getSelectedRows();
                if (selectedRows.length > 0) {
                    java.util.Arrays.sort(selectedRows);
                    int largetsRow = selectedRows[selectedRows.length - 1];
                    panel.showVariableDescription(largetsRow);
                }
            }
        }
    }
}
