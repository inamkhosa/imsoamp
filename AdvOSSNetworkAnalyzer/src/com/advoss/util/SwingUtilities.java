/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.advoss.util;

import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.l2fprod.common.swing.*;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;



/*
 * @author Alam Sher
 *
 */
public class SwingUtilities {

public static final int TABLE_CELL_LEFT_ALIGN = 0;
public static final int TABLE_CELL_RIGHT_ALIGN = 1;
public static final int TABLE_CELL_MIDDLE_ALIGN = 2;
private static UIManager.LookAndFeelInfo[] installedLookAndFeel = null;
private static JMenu menuLookAndFeel = new JMenu();

public static void centerFrame(JFrame component) {
java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
java.awt.Dimension frameSize = component.getSize();
if (frameSize.height > screenSize.height) {
  frameSize.height = screenSize.height;
}
if (frameSize.width > screenSize.width) {
  frameSize.width = screenSize.width;
}
component.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
}

public static void centerFrame(javax.swing.JInternalFrame internalFrame, javax.swing.JFrame jFrame) {
internalFrame.setVisible(false);
internalFrame.setLocation((jFrame.getWidth() - internalFrame.getWidth()) / 2, (jFrame.getHeight() - internalFrame.getHeight()) / 2 - 21);
internalFrame.setVisible(true);
}

public static void addComboToColumn(javax.swing.JTable jTable, javax.swing.JComboBox jComboBox, int columnIndex) {
jTable.getColumnModel().getColumn(columnIndex).setCellEditor(new javax.swing.DefaultCellEditor(jComboBox));
}

public static void hideJTableColumn(javax.swing.JTable jTable, int columnIndex) {
jTable.getColumnModel().getColumn(columnIndex).setMinWidth(0);
jTable.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
jTable.getColumnModel().getColumn(columnIndex).setWidth(0);
jTable.getColumnModel().getColumn(columnIndex).setPreferredWidth(0);
}

public static void addTextFieldToColumn(javax.swing.JTable jTable, javax.swing.JTextField textField, int columnIndex) {
jTable.getColumnModel().getColumn(columnIndex).setCellEditor(new javax.swing.DefaultCellEditor(textField));
}

public static void removeAllColumnsAndRows(javax.swing.JTable jTable) {
DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
for (int i = 0; i < tableModel.getRowCount(); i++) {
  tableModel.removeRow(i);
}
TableColumnModel tableColumnModel = jTable.getColumnModel();
for (int i = 0; i < tableColumnModel.getColumnCount(); i++) {
  tableColumnModel.removeColumn(tableColumnModel.getColumn(i));
}
}

public static void centerComponent(java.awt.Component component) {
java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
java.awt.Dimension frameSize = component.getSize();
if (frameSize.height > screenSize.height) {
  frameSize.height = screenSize.height;
}
if (frameSize.width > screenSize.width) {
  frameSize.width = screenSize.width;
}
component.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
}

public static void centerDialog(javax.swing.JDialog dialog) {
java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
java.awt.Dimension dialogSize = dialog.getSize();
if (dialogSize.getHeight() > screenSize.height) {
  dialogSize.height = screenSize.height;
}
if (dialogSize.getWidth() > screenSize.width) {
  dialogSize.width = screenSize.width;
}
dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
}

public static void setDirectory(JTextField txtField, JComponent component) {
String defaultDir = "";
if (txtField.getText() != null) {
  defaultDir = txtField.getText();
}
JDirectoryChooser directoryChooser = new JDirectoryChooser(defaultDir);
if (directoryChooser.showOpenDialog(component) == JDirectoryChooser.APPROVE_OPTION) {
  try {
    txtField.setText(directoryChooser.getSelectedFile().getCanonicalPath());
  } catch (IOException ex) {
  }
}
}

public static void customizeJTable(javax.swing.JTable jTable, boolean columnResize, boolean columnReorder, boolean singleSelection, int columnWidths[]) {
customizeJTable(jTable, columnResize, columnReorder, singleSelection);
if (columnWidths != null) {
  setJTableColumnWidth(jTable, columnWidths);
}
}

public static void customizeJTable(javax.swing.JTable jTable, boolean columnResize, boolean columnReorder, boolean singleSelection) {
jTable.getTableHeader().setReorderingAllowed(columnReorder);
jTable.getTableHeader().setResizingAllowed(columnResize);
if (singleSelection) {
  jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
} else {
  jTable.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
}
}

public static void customizeJTable(javax.swing.JTable jTable, boolean columnResize, boolean columnReorder, boolean singleSelection, boolean manageAutomaticWidths) {
jTable.getTableHeader().setReorderingAllowed(columnReorder);
jTable.getTableHeader().setResizingAllowed(columnResize);
if (singleSelection) {
  jTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
} else {
  jTable.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
}
if (manageAutomaticWidths) {
  setJTableColumnWidth(jTable);
}
}

public static void setJTableColumnWidth(javax.swing.JTable jTable) {
int columnCount = jTable.getColumnCount();
for (int iCounter = 0; iCounter < columnCount; iCounter++) {
  TableColumn column = jTable.getColumnModel().getColumn(iCounter);
  column.setPreferredWidth(column.getHeaderValue().toString().length() + 5);
}
}

public static void setJTableColumnWidth(javax.swing.JTable jTable, int[] columnWidths) {
for (int iCounter = 0; iCounter < columnWidths.length; iCounter++) {
  jTable.getColumnModel().getColumn(iCounter).setPreferredWidth(columnWidths[iCounter]);
}
}

public static void setJTableColumnWidth(javax.swing.JTable jTable, int columnWidth, int column) {
jTable.getColumnModel().getColumn(column).setPreferredWidth(columnWidth);
}

  public static void setCellAlignment(javax.swing.JTable jTable, int columnID, int alignment) {
    switch (alignment) {
      case TABLE_CELL_LEFT_ALIGN:

        jTable.getColumnModel().getColumn(columnID).setCellRenderer(jTable.getDefaultRenderer(Number.class));
        break;
      case TABLE_CELL_RIGHT_ALIGN:
        jTable.getColumnModel().getColumn(columnID).setCellRenderer(jTable.getDefaultRenderer(Number.class));
        break;
      case TABLE_CELL_MIDDLE_ALIGN:
        jTable.getColumnModel().getColumn(columnID).setCellRenderer(jTable.getDefaultRenderer(String.class));
        break;
    }
  }

  public static void addCheckBoxToJTableColumn(javax.swing.JTable jTable, JCheckBox jCheckbox, int columnIndex) {
    jTable.getColumnModel().getColumn(columnIndex).setCellRenderer(jTable.getDefaultRenderer(Boolean.class));
    jTable.getColumnModel().getColumn(columnIndex).setCellEditor(new javax.swing.DefaultCellEditor(jCheckbox));
    jTable.getColumnModel().getColumn(columnIndex).sizeWidthToFit();

  }

  public static void addCheckBoxToJTableColumnHeader(javax.swing.JTable jTable, ItemListener itemListener, int columnIndex) {
    TableColumn column = jTable.getColumnModel().getColumn(columnIndex);
    column.setHeaderRenderer(new JCheckBoxHeader(itemListener));
    column.setCellEditor(jTable.getDefaultEditor(Boolean.class));
  }

public static void showErrorMessage(String errMsg) {
JOptionPane.showMessageDialog(null, errMsg, "Error Occured !!", JOptionPane.ERROR_MESSAGE);
}

public static void showWarningMessage(String errMsg) {
JOptionPane.showMessageDialog(null, errMsg, "Attention !!", JOptionPane.WARNING_MESSAGE);
}

public static int showWarnMessage(String warnMsg) {
return JOptionPane.showConfirmDialog(null, warnMsg, "Attention !!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
}

public static void showSuccessMessage(String succMsg) {
JOptionPane.showMessageDialog(null, succMsg, "Operation Completed !!", JOptionPane.INFORMATION_MESSAGE);
}

public static boolean listContains(javax.swing.JList list, Object element) {
javax.swing.DefaultListModel listModel = (javax.swing.DefaultListModel) list.getModel();
for (int iCounter = 0; iCounter < listModel.getSize(); iCounter++) {
  if (listModel.getElementAt(iCounter).equals(element)) {
    return true;
  }
}
return false;
}

public static JMenu getLookAndFeelMenu(JFrame frame) {
ButtonGroup bGroup = new ButtonGroup();
final JFrame lookAndFeelFrame = frame;
menuLookAndFeel.setMnemonic('L');
menuLookAndFeel.setText("Look And Feel");
int iLookAndFeelCounter = 0;
installedLookAndFeel = UIManager.getInstalledLookAndFeels();
if (installedLookAndFeel != null) {
  JRadioButtonMenuItem[] mnuItemLookAndFeel = new JRadioButtonMenuItem[installedLookAndFeel.length];
  for (iLookAndFeelCounter = 0; iLookAndFeelCounter < installedLookAndFeel.length; iLookAndFeelCounter++) {
    mnuItemLookAndFeel[iLookAndFeelCounter] = new JRadioButtonMenuItem(installedLookAndFeel[iLookAndFeelCounter].getName());
//        mnuItemLookAndFeel[iLookAndFeelCounter].setAccelerator(javax.swing.KeyStroke.getKeyStroke(Character.digit(iLookAndFeelCounter, 10), java.awt.event.KeyEvent.ALT_MASK, false));
    mnuItemLookAndFeel[iLookAndFeelCounter].setName(String.valueOf(iLookAndFeelCounter));
    if (UIManager.getLookAndFeel().getClass().getName().equals(installedLookAndFeel[iLookAndFeelCounter].getClassName())) {
      mnuItemLookAndFeel[iLookAndFeelCounter].setSelected(true);
    }
    mnuItemLookAndFeel[iLookAndFeelCounter].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JRadioButtonMenuItem source = (JRadioButtonMenuItem) e.getSource();
        int i = Integer.parseInt(source.getName());
        try {
          UIManager.setLookAndFeel(installedLookAndFeel[i].getClassName());
          javax.swing.SwingUtilities.updateComponentTreeUI(lookAndFeelFrame);
        } catch (UnsupportedLookAndFeelException ex) {
          showErrorMessage(ex.getMessage());
        } catch (IllegalAccessException ex) {
        } catch (InstantiationException ex) {
        } catch (ClassNotFoundException ex) {
        }
      }
    });
    menuLookAndFeel.add(mnuItemLookAndFeel[iLookAndFeelCounter]);
    bGroup.add(mnuItemLookAndFeel[iLookAndFeelCounter]);
  }
}
return menuLookAndFeel;
}

}

