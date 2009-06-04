/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgTrapDefinitionManager.java
 *
 * Created on Apr 5, 2009, 6:03:14 PM
 */
package com.advoss.network.analyzer.gui;

import com.advoss.network.analyzer.Constants;
import com.advoss.network.analyzer.NetworkAnalyzer;
import com.advoss.util.SwingUtilities;
import java.awt.CardLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import org.ict.oamp.service.client.TrapTableDefinition;
import org.ict.oamp.service.client.TrapBindingDefinition;
import org.ict.oamp.service.client.TrapDefinition;
import org.ict.oamp.service.client.TrapGenericType;
import org.ict.oamp.service.client.TrapService;
import org.ict.oamp.service.client.TrapTableColumnDefinition;
import org.ict.oamp.service.client.TrapType;

/**
 *
 * @author Alam Sher
 */
public class DlgTrapDefinitionManager extends javax.swing.JDialog {

    private static final String SELECT_TABLE_STRING = "Select Database Table...";
    private DefaultComboBoxModel comboModelTrapTypes = new DefaultComboBoxModel(new String[]{TrapType._V1TRAP, TrapType._TRAP, TrapType._NOTIFICATION, TrapType._INFORM});
    private DefaultComboBoxModel comboModelGenericTrapTypes = new DefaultComboBoxModel(new String[]{TrapGenericType._COLD_START, TrapGenericType._WARM_START, TrapGenericType._LINK_DOWN, TrapGenericType._LINK_UP, TrapGenericType._AUTHENTICATION_FAILURE, TrapGenericType._EGP_NEIGHBOR_LOSS, TrapGenericType._ENTERPRISE_SPECIFIC});
    private DefaultComboBoxModel comboModelTablesList = new DefaultComboBoxModel();
    private DefaultComboBoxModel comboModelDataTypes = new DefaultComboBoxModel();
    private JComboBox cmbDataTypes = new JComboBox(comboModelDataTypes);
    private DefaultTableModel tableModelVariableBindings = new DefaultTableModel(new Object[]{"OID", "Type", "Name", "Description", ""}, 0) {

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };
    private DefaultTableModel tableModelDatabaseTableMappings = new DefaultTableModel(new Object[]{"Variable Name", "Variable Type", "Column Name", "Column Type", "Length"}, 0) {

        @Override
        public boolean isCellEditable(int row, int col) {
            if (rdoUseTable.isSelected()) {
                return false;
            } else {
                if (col >= 3) {
                    return true;
                }
            }
            return false;
        }
    };
    private int action = Constants.ACTION_ADD;
    private TrapDefinition trapDefinition = null;

    /** Creates new form DlgTrapDefinitionManager */
    public DlgTrapDefinitionManager(java.awt.Frame parent, boolean modal, int action, TrapDefinition trapDefinition) {
        super(parent, modal);
        initComponents();
        this.action = action;
        this.trapDefinition = trapDefinition;
        customize();
    }

    private void customize() {
        SwingUtilities.customizeJTable(tblVariableBindings, true, false, true);
        SwingUtilities.hideJTableColumn(tblVariableBindings, 4);
        SwingUtilities.addComboToColumn(tblDatabaseTableContens, cmbDataTypes, 3);
        if (action == Constants.ACTION_EDIT) {
            loadData();
        }
        loadTableContents();
    }

    private void loadData() {
        if(trapDefinition != null) {
            txtTrapDefinitionName.setText(trapDefinition.getTrapDefinitionName());
            cmbTrapType.setSelectedItem(trapDefinition.getTrapType().toString());
            if(trapDefinition.getTrapType() == TrapType.V1TRAP) {
                txtEnterpriseId.setText(trapDefinition.getEnterprise());
                cmbGenericTrapType.setSelectedItem(trapDefinition.getTrapGenericType().toString());
                txtSpecificTrapId.setText(String.valueOf(trapDefinition.getTrapSpecificType()));
            } else {
                txtSnmpOID.setText(trapDefinition.getSmpOid());
            }
            txtTrapDescription.setText(trapDefinition.getDescription());
            if(trapDefinition.getTrapBindingDefinitions() != null) {
                Vector<TrapBindingDefinition> bindingDefinitions = new Vector<TrapBindingDefinition>(Arrays.asList(trapDefinition.getTrapBindingDefinitions()));
                for(int i=0; i<bindingDefinitions.size(); i++) {
                    TrapBindingDefinition bindingDefinition = bindingDefinitions.get(i);
                    tableModelVariableBindings.addRow(new Object[]{bindingDefinition.getVariableOid(), bindingDefinition.getVariableType(), bindingDefinition.getVariableName(), bindingDefinition.getBindingDescription(), bindingDefinition});
                }
            }
            rdoUseTable.setSelected(true);
            cmbTablesList.setSelectedItem(trapDefinition.getTrapTableDefinition().getTableName());
        }
    }

    private boolean validateData() {
        try {
            if(txtTrapDefinitionName.getText().trim().length() == 0) {
                SwingUtilities.showErrorMessage("Trap Definition Name can't be left empty.");
                return false;
            }
            if(cmbTrapType.getSelectedItem().toString().equalsIgnoreCase(TrapType._V1TRAP)) {
                if(txtEnterpriseId.getText().trim().length() == 0) {
                    SwingUtilities.showErrorMessage("Enterprise Id can't be left empty.");
                    return false;
                } else if (txtSpecificTrapId.getText().trim().length() == 0) {
                    SwingUtilities.showErrorMessage("Specific Trap Id can't be left empty.");
                    return false;
                }
            } else {
                if(txtSnmpOID.getText().trim().length() == 0) {
                    SwingUtilities.showErrorMessage("Trap SNMP Oid can't be left empty.");
                    return false;
                }
            }
            if(tblVariableBindings.getRowCount() == 0) {
                SwingUtilities.showErrorMessage("One or more Variable Bindings must be defined.");
                return false;
            }
            if(rdoCreateTable.isSelected()) {
                if(txtTableName.getText().trim().length() == 0) {
                    SwingUtilities.showErrorMessage("Database Table name can't be left empty.");
                    return false;
                }
            } else {
                if(cmbTablesList.getSelectedItem() == null || cmbTablesList.getSelectedItem().toString().equalsIgnoreCase(SELECT_TABLE_STRING)) {
                    SwingUtilities.showErrorMessage("Database Table name can't be left empty.");
                    return false;
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
            SwingUtilities.showErrorMessage("Error occured while saving Trap Definition:\n" + ex.getMessage());
        }
        return true;
    }

    private void loadTableContents() {
        try {
            tableModelDatabaseTableMappings.setRowCount(0);
            comboModelDataTypes.removeAllElements();
        } catch (Exception e) {
        }
        if (rdoCreateTable.isSelected()) {
            try {
                TrapService client = NetworkAnalyzer.getInstance().getClient();
                String[] dataTypes = client.getAvailableDataTypes();
                if (dataTypes != null) {
                    for (int i = 0; i < dataTypes.length; i++) {
                        comboModelDataTypes.addElement(dataTypes[i]);
                    }
                }
                tableModelDatabaseTableMappings.addRow(new Object[]{"trap_id", "Integer32", "trap_id", dataTypes[0], ""});
                for (int i = 0; i < tableModelVariableBindings.getRowCount(); i++) {
                    TrapBindingDefinition definition = (TrapBindingDefinition) tblVariableBindings.getValueAt(i, 4);
                    tableModelDatabaseTableMappings.addRow(new Object[]{definition.getVariableName(), definition.getVariableType(), definition.getVariableName(), dataTypes[0], ""});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.showErrorMessage(ex.getMessage());
            }
        } else {
            try {
                if(cmbTablesList.getSelectedItem() == null || cmbTablesList.getSelectedItem().toString().equalsIgnoreCase(SELECT_TABLE_STRING)) {
                    return;
                }
                TrapService client = NetworkAnalyzer.getInstance().getClient();
                String[] columns = client.getTableColumnsList(cmbTablesList.getSelectedItem().toString());
                if (columns != null) {
                    int mappingsFound = 0;
                    boolean trapIdColumnAdded = false;
                    for(int i=0; i<tblVariableBindings.getRowCount(); i++) {
                        TrapBindingDefinition definition = (TrapBindingDefinition) tblVariableBindings.getValueAt(i, 4);
                        for(int y=0; y<columns.length; y++) {
                            String columnName = columns[y].split(",")[0];
                            String columnDataType = columns[y].split(",")[1];
                            String columnLength = columns[y].split(",")[2];
                            if(columnName.equalsIgnoreCase("trap_id") && !trapIdColumnAdded) {
                                tableModelDatabaseTableMappings.addRow(new Object[]{"trap_id", "Integer32", columnName, columnDataType, columnLength});
                                trapIdColumnAdded = true;
                            } else if(definition.getVariableName().equalsIgnoreCase(columnName)) {
                                mappingsFound++;
                                tableModelDatabaseTableMappings.addRow(new Object[]{definition.getVariableName(), definition.getVariableType(), columnName, columnDataType, columnLength});
                            }
                        }
                    }
                    if(mappingsFound < tblVariableBindings.getRowCount()) {
                        SwingUtilities.showWarningMessage("Selected table many not be able to save variable bindings properly.\nPlease select some other table or create a new one.");
                    } else {
                        boolean trapIdColumnFound = false;
                        for(int i=0; i<columns.length; i++) {
                            if(columns[i].split(",")[0].equalsIgnoreCase("trap_id")) {
                                trapIdColumnFound = true;
                                break;
                            }
                        }
                        if(!trapIdColumnFound) {
                            SwingUtilities.showWarningMessage("Selected table many not be able to save variable bindings properly.\nPlease select some other table or create a new one.");
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.showErrorMessage(ex.getMessage());
            }
        }
    }

    private void loadTableList() {
        try {
            comboModelTablesList.removeAllElements();
            comboModelTablesList.addElement(SELECT_TABLE_STRING);
            TrapService client = NetworkAnalyzer.getInstance().getClient();
            String[] tablesList = client.getTablesList();
            if (tablesList != null) {
                for (int i = 0; i < tablesList.length; i++) {
                    comboModelTablesList.addElement(tablesList[i]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            SwingUtilities.showErrorMessage(ex.getMessage());
        }
    }

    private TrapDefinition createNewTrapDefinition() throws Exception {
        trapDefinition = new TrapDefinition();
        trapDefinition.setTrapDefinitionName(txtTrapDefinitionName.getText().trim());
        trapDefinition.setTrapType(TrapType.fromValue(cmbTrapType.getSelectedItem().toString()));
        if(trapDefinition.getTrapType() == TrapType.V1TRAP) {
            trapDefinition.setEnterprise(txtEnterpriseId.getText().trim());
            trapDefinition.setTrapGenericType(TrapGenericType.fromValue(cmbGenericTrapType.getSelectedItem().toString()));
            trapDefinition.setTrapSpecificType(Integer.parseInt(txtSpecificTrapId.getText().trim()));
        } else {
            trapDefinition.setSmpOid(txtSnmpOID.getText().trim());
        }
        trapDefinition.setDescription(txtTrapDescription.getText().trim());
        TrapBindingDefinition[] bindingDefinitions = new TrapBindingDefinition[tblVariableBindings.getRowCount()];
        for(int i=0;i<tblVariableBindings.getRowCount(); i++) {
            TrapBindingDefinition bindingDefinition = (TrapBindingDefinition) tblVariableBindings.getValueAt(i, 4);
            bindingDefinitions[i] = bindingDefinition;
        }
        trapDefinition.setTrapBindingDefinitions(bindingDefinitions);
        trapDefinition.setCreateTable(rdoCreateTable.isSelected());
        if(trapDefinition.isCreateTable()) {
            TrapTableDefinition tableDefinition = new TrapTableDefinition();
            tableDefinition.setTableName(txtTableName.getText().trim());
            TrapTableColumnDefinition columnDefinitions[] = new TrapTableColumnDefinition[tblDatabaseTableContens.getRowCount()];
            for(int i=0; i<tblDatabaseTableContens.getRowCount(); i++) {
                TrapTableColumnDefinition columnDefinition = new TrapTableColumnDefinition();
                columnDefinition.setColumnName(tblDatabaseTableContens.getValueAt(i, 2).toString());
                columnDefinition.setDataType(tblDatabaseTableContens.getValueAt(i, 3).toString());
                try {
                    columnDefinition.setLength(Integer.parseInt(tblDatabaseTableContens.getValueAt(i, 4).toString()));
                } catch (Exception ex){
                }
                columnDefinitions[i] = columnDefinition;
            }
            tableDefinition.setTrapTableColumns(columnDefinitions);
            trapDefinition.setTrapTableDefinition(tableDefinition);
        } else {
            TrapTableDefinition tableDefinition = new TrapTableDefinition();
            tableDefinition.setTableName(cmbTablesList.getSelectedItem().toString());
            trapDefinition.setTrapTableDefinition(tableDefinition);
        }
        TrapService client = NetworkAnalyzer.getInstance().getClient();
        trapDefinition = client.addTrapDefinition(trapDefinition, trapDefinition.isCreateTable());
        return trapDefinition;
    }

    private TrapDefinition updateTrapDefinition() throws Exception {
        trapDefinition.setTrapDefinitionName(txtTrapDefinitionName.getText().trim());
        trapDefinition.setTrapType(TrapType.fromValue(cmbTrapType.getSelectedItem().toString()));
        if(trapDefinition.getTrapType() == TrapType.V1TRAP) {
            trapDefinition.setEnterprise(txtEnterpriseId.getText().trim());
            trapDefinition.setTrapGenericType(TrapGenericType.fromValue(cmbGenericTrapType.getSelectedItem().toString()));
            trapDefinition.setTrapSpecificType(Integer.parseInt(txtSpecificTrapId.getText().trim()));
            trapDefinition.setSmpOid(null);
        } else {
            trapDefinition.setSmpOid(txtSnmpOID.getText().trim());
            trapDefinition.setEnterprise(null);
            trapDefinition.setTrapGenericType(null);
            trapDefinition.setTrapSpecificType(0);
        }
        trapDefinition.setDescription(txtTrapDescription.getText().trim());
        TrapBindingDefinition[] bindingDefinitions = new TrapBindingDefinition[tblVariableBindings.getRowCount()];
        for(int i=0;i<tblVariableBindings.getRowCount(); i++) {
            TrapBindingDefinition bindingDefinition = (TrapBindingDefinition) tblVariableBindings.getValueAt(i, 4);
            bindingDefinitions[i] = bindingDefinition;
        }
        trapDefinition.setTrapBindingDefinitions(bindingDefinitions);
        trapDefinition.setCreateTable(rdoCreateTable.isSelected());
        if(trapDefinition.isCreateTable()) {
            TrapTableDefinition tableDefinition = new TrapTableDefinition();
            tableDefinition.setTableName(txtTableName.getText().trim());
            TrapTableColumnDefinition columnDefinitions[] = new TrapTableColumnDefinition[tblDatabaseTableContens.getRowCount()];
            for(int i=0; i<tblDatabaseTableContens.getRowCount(); i++) {
                TrapTableColumnDefinition columnDefinition = new TrapTableColumnDefinition();
                columnDefinition.setColumnName(tblDatabaseTableContens.getValueAt(i, 2).toString());
                columnDefinition.setDataType(tblDatabaseTableContens.getValueAt(i, 3).toString());
                try {
                    columnDefinition.setLength(Integer.parseInt(tblDatabaseTableContens.getValueAt(i, 4).toString()));
                } catch (Exception ex){
                }
                columnDefinitions[i] = columnDefinition;
            }
            tableDefinition.setTrapTableColumns(columnDefinitions);
            trapDefinition.setTrapTableDefinition(tableDefinition);
        } else {
            TrapTableDefinition tableDefinition = new TrapTableDefinition();
            tableDefinition.setTableName(cmbTablesList.getSelectedItem().toString());
            trapDefinition.setTrapTableDefinition(tableDefinition);
        }
        TrapService client = NetworkAnalyzer.getInstance().getClient();
        trapDefinition = client.editTrapDefinition(trapDefinition);
        return trapDefinition;
    }

    private void showDefinitionCriteria(String name) {
        CardLayout cl = (CardLayout) pnlTrapDefinitionCriteria.getLayout();
        cl.show(pnlTrapDefinitionCriteria, name);
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

        btnGroupDbTable = new javax.swing.ButtonGroup();
        pnlMain = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        pnlGeneralTrapInformation = new javax.swing.JPanel();
        lblTrapId = new javax.swing.JLabel();
        lblTrapIdValue = new javax.swing.JLabel();
        lblTrapType = new javax.swing.JLabel();
        cmbTrapType = new javax.swing.JComboBox();
        pnlTrapDefinitionCriteria = new javax.swing.JPanel();
        pnlV1TrapDefinitionCriteria = new javax.swing.JPanel();
        lblEnterpriseId = new javax.swing.JLabel();
        txtEnterpriseId = new javax.swing.JTextField();
        lblGenericTrapId = new javax.swing.JLabel();
        lblSpecificTrapId = new javax.swing.JLabel();
        txtSpecificTrapId = new javax.swing.JTextField();
        cmbGenericTrapType = new javax.swing.JComboBox();
        pnlV2V3DefinitionCriteria = new javax.swing.JPanel();
        lblSnmpOID = new javax.swing.JLabel();
        txtSnmpOID = new javax.swing.JTextField();
        lblV2V3Blank = new javax.swing.JLabel();
        pnlTrapDescription = new javax.swing.JPanel();
        scrollPaneTrapDescription = new javax.swing.JScrollPane();
        txtTrapDescription = new javax.swing.JTextArea();
        lblTrapDefinitionName = new javax.swing.JLabel();
        txtTrapDefinitionName = new javax.swing.JTextField();
        pnlVariableBindings = new javax.swing.JPanel();
        pnlVariableBindingsList = new javax.swing.JPanel();
        scrollPaneVariableBindings = new javax.swing.JScrollPane();
        tblVariableBindings = new javax.swing.JTable();
        pnlVariableBindingsControls = new javax.swing.JPanel();
        btnNewBinding = new javax.swing.JButton();
        btnEditBinding = new javax.swing.JButton();
        btnRemoveBindings = new javax.swing.JButton();
        pnlPersistanceSettings = new javax.swing.JPanel();
        pnlPersistanceMain = new javax.swing.JPanel();
        pnlDatabaseTable = new javax.swing.JPanel();
        rdoCreateTable = new javax.swing.JRadioButton();
        rdoUseTable = new javax.swing.JRadioButton();
        cmbTablesList = new javax.swing.JComboBox();
        txtTableName = new javax.swing.JTextField();
        pnlDatabaseTableContents = new javax.swing.JPanel();
        scrollPaneTableContents = new javax.swing.JScrollPane();
        tblDatabaseTableContens = new javax.swing.JTable();
        pnlDatabaseTableControls = new javax.swing.JPanel();
        btnRefreshMappings = new javax.swing.JButton();
        pnlControls = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMain.setLayout(new java.awt.BorderLayout());

        pnlGeneralTrapInformation.setLayout(new java.awt.GridBagLayout());

        lblTrapId.setText("Trap Definition Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlGeneralTrapInformation.add(lblTrapId, gridBagConstraints);

        lblTrapIdValue.setText("[Automatic]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlGeneralTrapInformation.add(lblTrapIdValue, gridBagConstraints);

        lblTrapType.setText("Trap Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlGeneralTrapInformation.add(lblTrapType, gridBagConstraints);

        cmbTrapType.setModel(comboModelTrapTypes);
        cmbTrapType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTrapTypeItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlGeneralTrapInformation.add(cmbTrapType, gridBagConstraints);

        pnlTrapDefinitionCriteria.setBorder(javax.swing.BorderFactory.createTitledBorder("Trap Definition Criteria"));
        pnlTrapDefinitionCriteria.setLayout(new java.awt.CardLayout());

        pnlV1TrapDefinitionCriteria.setLayout(new java.awt.GridBagLayout());

        lblEnterpriseId.setText("Enterprise Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV1TrapDefinitionCriteria.add(lblEnterpriseId, gridBagConstraints);

        txtEnterpriseId.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV1TrapDefinitionCriteria.add(txtEnterpriseId, gridBagConstraints);

        lblGenericTrapId.setText("Generic Trap Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV1TrapDefinitionCriteria.add(lblGenericTrapId, gridBagConstraints);

        lblSpecificTrapId.setText("Specific Trap Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV1TrapDefinitionCriteria.add(lblSpecificTrapId, gridBagConstraints);

        txtSpecificTrapId.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV1TrapDefinitionCriteria.add(txtSpecificTrapId, gridBagConstraints);

        cmbGenericTrapType.setModel(comboModelGenericTrapTypes);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV1TrapDefinitionCriteria.add(cmbGenericTrapType, gridBagConstraints);

        pnlTrapDefinitionCriteria.add(pnlV1TrapDefinitionCriteria, "V1TrapDefinition");

        pnlV2V3DefinitionCriteria.setLayout(new java.awt.GridBagLayout());

        lblSnmpOID.setText("SNMP OID:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV2V3DefinitionCriteria.add(lblSnmpOID, gridBagConstraints);

        txtSnmpOID.setColumns(20);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV2V3DefinitionCriteria.add(txtSnmpOID, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlV2V3DefinitionCriteria.add(lblV2V3Blank, gridBagConstraints);

        pnlTrapDefinitionCriteria.add(pnlV2V3DefinitionCriteria, "V2V3TrapDefinition");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlGeneralTrapInformation.add(pnlTrapDefinitionCriteria, gridBagConstraints);

        pnlTrapDescription.setBorder(javax.swing.BorderFactory.createTitledBorder("Trap Description"));
        pnlTrapDescription.setLayout(new java.awt.BorderLayout());

        txtTrapDescription.setColumns(20);
        txtTrapDescription.setRows(5);
        scrollPaneTrapDescription.setViewportView(txtTrapDescription);

        pnlTrapDescription.add(scrollPaneTrapDescription, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlGeneralTrapInformation.add(pnlTrapDescription, gridBagConstraints);

        lblTrapDefinitionName.setText("Trap Definition Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlGeneralTrapInformation.add(lblTrapDefinitionName, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlGeneralTrapInformation.add(txtTrapDefinitionName, gridBagConstraints);

        tabbedPane.addTab("General Trap Informaiton", pnlGeneralTrapInformation);

        pnlVariableBindings.setLayout(new java.awt.BorderLayout());

        pnlVariableBindingsList.setLayout(new java.awt.BorderLayout());

        tblVariableBindings.setModel(tableModelVariableBindings);
        tblVariableBindings.getTableHeader().setReorderingAllowed(false);
        scrollPaneVariableBindings.setViewportView(tblVariableBindings);

        pnlVariableBindingsList.add(scrollPaneVariableBindings, java.awt.BorderLayout.CENTER);

        pnlVariableBindings.add(pnlVariableBindingsList, java.awt.BorderLayout.CENTER);

        btnNewBinding.setText("New Binding");
        btnNewBinding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBindingActionPerformed(evt);
            }
        });
        pnlVariableBindingsControls.add(btnNewBinding);

        btnEditBinding.setText("Edit Binding");
        btnEditBinding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditBindingActionPerformed(evt);
            }
        });
        pnlVariableBindingsControls.add(btnEditBinding);

        btnRemoveBindings.setText("Remove Binding");
        btnRemoveBindings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveBindingsActionPerformed(evt);
            }
        });
        pnlVariableBindingsControls.add(btnRemoveBindings);

        pnlVariableBindings.add(pnlVariableBindingsControls, java.awt.BorderLayout.SOUTH);

        tabbedPane.addTab("Variable Bindings", pnlVariableBindings);

        pnlPersistanceSettings.setLayout(new java.awt.BorderLayout());

        pnlPersistanceMain.setLayout(new java.awt.GridBagLayout());

        pnlDatabaseTable.setBorder(javax.swing.BorderFactory.createTitledBorder("Database Table"));
        pnlDatabaseTable.setLayout(new java.awt.GridBagLayout());

        btnGroupDbTable.add(rdoCreateTable);
        rdoCreateTable.setSelected(true);
        rdoCreateTable.setText("Create new table");
        rdoCreateTable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoCreateTableItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDatabaseTable.add(rdoCreateTable, gridBagConstraints);

        btnGroupDbTable.add(rdoUseTable);
        rdoUseTable.setText("Use existing table");
        rdoUseTable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdoUseTableItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDatabaseTable.add(rdoUseTable, gridBagConstraints);

        cmbTablesList.setModel(comboModelTablesList);
        cmbTablesList.setEnabled(false);
        cmbTablesList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTablesListItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDatabaseTable.add(cmbTablesList, gridBagConstraints);

        txtTableName.setText("tbl_new");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDatabaseTable.add(txtTableName, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlPersistanceMain.add(pnlDatabaseTable, gridBagConstraints);

        pnlDatabaseTableContents.setBorder(javax.swing.BorderFactory.createTitledBorder("Database Table Fields/Mappings"));
        pnlDatabaseTableContents.setLayout(new java.awt.BorderLayout());

        tblDatabaseTableContens.setModel(tableModelDatabaseTableMappings);
        tblDatabaseTableContens.getTableHeader().setReorderingAllowed(false);
        scrollPaneTableContents.setViewportView(tblDatabaseTableContens);

        pnlDatabaseTableContents.add(scrollPaneTableContents, java.awt.BorderLayout.CENTER);

        pnlDatabaseTableControls.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnRefreshMappings.setText("Refresh Fields/Mappings");
        btnRefreshMappings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshMappingsActionPerformed(evt);
            }
        });
        pnlDatabaseTableControls.add(btnRefreshMappings);

        pnlDatabaseTableContents.add(pnlDatabaseTableControls, java.awt.BorderLayout.SOUTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlPersistanceMain.add(pnlDatabaseTableContents, gridBagConstraints);

        pnlPersistanceSettings.add(pnlPersistanceMain, java.awt.BorderLayout.CENTER);

        tabbedPane.addTab("Persistance Settings", pnlPersistanceSettings);

        pnlMain.add(tabbedPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        pnlControls.add(btnSave);

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

    private void cmbTrapTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTrapTypeItemStateChanged
        if (cmbTrapType.getSelectedItem().toString().equals(TrapType._V1TRAP)) {
            showDefinitionCriteria("V1TrapDefinition");
        } else {
            showDefinitionCriteria("V2V3TrapDefinition");
        }
    }//GEN-LAST:event_cmbTrapTypeItemStateChanged

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnNewBindingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewBindingActionPerformed
        DlgVariableBindingManager dialog = new DlgVariableBindingManager(FrmNetworkAnalyzer.getInstance(), true, Constants.ACTION_ADD, null);
        SwingUtilities.centerDialog(dialog);
        dialog.setVisible(true);
        TrapBindingDefinition bindingDefinition = dialog.getBindingDefinition();
        if (bindingDefinition != null) {
            tableModelVariableBindings.addRow(new Object[]{bindingDefinition.getVariableOid(), bindingDefinition.getVariableType(), bindingDefinition.getVariableName(), bindingDefinition.getBindingDescription(), bindingDefinition});
        }
    }//GEN-LAST:event_btnNewBindingActionPerformed

    private void btnEditBindingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditBindingActionPerformed
        if (tblVariableBindings.getSelectedRowCount() <= 0) {
            return;
        }
        TrapBindingDefinition bindingDefinition = null;
        try {
            bindingDefinition = (TrapBindingDefinition) tblVariableBindings.getValueAt(tblVariableBindings.getSelectedRow(), 4);
        } catch (Exception ex) {
        }
        if (bindingDefinition != null) {
            DlgVariableBindingManager dialog = new DlgVariableBindingManager(FrmNetworkAnalyzer.getInstance(), true, Constants.ACTION_EDIT, bindingDefinition);
            SwingUtilities.centerDialog(dialog);
            dialog.setVisible(true);
            bindingDefinition = dialog.getBindingDefinition();
            int row = tblVariableBindings.getSelectedRow();
            tblVariableBindings.setValueAt(bindingDefinition.getVariableOid(), row, 0);
            tblVariableBindings.setValueAt(bindingDefinition.getVariableType(), row, 1);
            tblVariableBindings.setValueAt(bindingDefinition.getVariableName(), row, 2);
            tblVariableBindings.setValueAt(bindingDefinition.getBindingDescription(), row, 3);
            tblVariableBindings.setValueAt(bindingDefinition, row, 4);
        }
    }//GEN-LAST:event_btnEditBindingActionPerformed

    private void btnRemoveBindingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveBindingsActionPerformed
        if (tblVariableBindings.getSelectedRowCount() == 1) {
            int selectedRow = tblVariableBindings.getSelectedRow();
            tableModelVariableBindings.removeRow(selectedRow);
            if (trapDefinition != null) {
                if (trapDefinition.getTrapBindingDefinitions() != null) {
                    try {
                        List<TrapBindingDefinition> definitions = Arrays.asList(trapDefinition.getTrapBindingDefinitions());
                        definitions.remove(selectedRow);
                        trapDefinition.setTrapBindingDefinitions((TrapBindingDefinition[]) definitions.toArray());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }//GEN-LAST:event_btnRemoveBindingsActionPerformed

    private void rdoCreateTableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdoCreateTableItemStateChanged
        cmbTablesList.setEnabled(!rdoCreateTable.isSelected());
        txtTableName.setEditable(rdoCreateTable.isSelected());
        if (rdoCreateTable.isSelected()) {
            txtTableName.setText("tbl_new");
            try {
                cmbTablesList.setSelectedIndex(0);
            } catch (Exception ex){
            }
        }
        loadTableContents();
    }//GEN-LAST:event_rdoCreateTableItemStateChanged

    private void rdoUseTableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdoUseTableItemStateChanged
        cmbTablesList.setEnabled(rdoUseTable.isSelected());
        txtTableName.setEditable(!rdoUseTable.isSelected());
        if (rdoUseTable.isSelected()) {
            txtTableName.setText("");
            loadTableList();
            tableModelDatabaseTableMappings.setRowCount(0);
        }
    }//GEN-LAST:event_rdoUseTableItemStateChanged

    private void cmbTablesListItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTablesListItemStateChanged
        if(evt.getStateChange() == evt.SELECTED) {
            if (cmbTablesList.getSelectedItem() != null && !cmbTablesList.getSelectedItem().toString().equalsIgnoreCase(SELECT_TABLE_STRING)) {
                loadTableContents();
            }
        }
    }//GEN-LAST:event_cmbTablesListItemStateChanged

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(validateData()) {
            if(action == Constants.ACTION_ADD) {
                try {
                    if (createNewTrapDefinition() != null) {
                        this.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.showErrorMessage(ex.getMessage());
                }
            } else {
                try {
                    if(updateTrapDefinition() != null) {
                        this.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.showErrorMessage(ex.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefreshMappingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshMappingsActionPerformed
        if(rdoUseTable.isSelected()) {
            if(cmbTablesList.getSelectedItem() == null || cmbTablesList.getSelectedItem().toString().equalsIgnoreCase(SELECT_TABLE_STRING)) {
                try {
                    tableModelDatabaseTableMappings.setRowCount(0);
                } catch (Exception ex) {
                }
                 return;
            }
        }
        loadTableContents();
}//GEN-LAST:event_btnRefreshMappingsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnEditBinding;
    private javax.swing.ButtonGroup btnGroupDbTable;
    private javax.swing.JButton btnNewBinding;
    private javax.swing.JButton btnRefreshMappings;
    private javax.swing.JButton btnRemoveBindings;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbGenericTrapType;
    private javax.swing.JComboBox cmbTablesList;
    private javax.swing.JComboBox cmbTrapType;
    private javax.swing.JLabel lblEnterpriseId;
    private javax.swing.JLabel lblGenericTrapId;
    private javax.swing.JLabel lblSnmpOID;
    private javax.swing.JLabel lblSpecificTrapId;
    private javax.swing.JLabel lblTrapDefinitionName;
    private javax.swing.JLabel lblTrapId;
    private javax.swing.JLabel lblTrapIdValue;
    private javax.swing.JLabel lblTrapType;
    private javax.swing.JLabel lblV2V3Blank;
    private javax.swing.JPanel pnlControls;
    private javax.swing.JPanel pnlDatabaseTable;
    private javax.swing.JPanel pnlDatabaseTableContents;
    private javax.swing.JPanel pnlDatabaseTableControls;
    private javax.swing.JPanel pnlGeneralTrapInformation;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlPersistanceMain;
    private javax.swing.JPanel pnlPersistanceSettings;
    private javax.swing.JPanel pnlTrapDefinitionCriteria;
    private javax.swing.JPanel pnlTrapDescription;
    private javax.swing.JPanel pnlV1TrapDefinitionCriteria;
    private javax.swing.JPanel pnlV2V3DefinitionCriteria;
    private javax.swing.JPanel pnlVariableBindings;
    private javax.swing.JPanel pnlVariableBindingsControls;
    private javax.swing.JPanel pnlVariableBindingsList;
    private javax.swing.JRadioButton rdoCreateTable;
    private javax.swing.JRadioButton rdoUseTable;
    private javax.swing.JScrollPane scrollPaneTableContents;
    private javax.swing.JScrollPane scrollPaneTrapDescription;
    private javax.swing.JScrollPane scrollPaneVariableBindings;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tblDatabaseTableContens;
    private javax.swing.JTable tblVariableBindings;
    private javax.swing.JTextField txtEnterpriseId;
    private javax.swing.JTextField txtSnmpOID;
    private javax.swing.JTextField txtSpecificTrapId;
    private javax.swing.JTextField txtTableName;
    private javax.swing.JTextField txtTrapDefinitionName;
    private javax.swing.JTextArea txtTrapDescription;
    // End of variables declaration//GEN-END:variables
}
