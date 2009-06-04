/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmNetworkAnalyzer.java
 *
 * Created on Mar 25, 2009, 11:15:04 PM
 */
package com.advoss.network.analyzer.gui;

import com.advoss.network.analyzer.Constants;
import com.advoss.network.analyzer.NetworkAnalyzer;
import com.advoss.util.SwingUtilities;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.ict.oamp.service.client.Trap;

/**
 *
 * @author Alam Sher
 */
public class FrmNetworkAnalyzer extends javax.swing.JFrame {

    private DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode(new TreeObject("Network Analyzer", null));
    private DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot);
    private static FrmNetworkAnalyzer frame = null;
    private static final String PANEL_SERVICE_STATUS = "PANEL_SERVICE_STATUS";
    private static final String PANEL_SERVER_CONSOLE = "PANEL_SERVER_CONSOLE";
    private static final String PANEL_TRAPS_CONSOLE = "PANEL_TRAPS_CONSOLE";
    private static final String PANEL_STATS_CONSOLE = "PANEL_STATS_CONSOLE";
    private static final String PANEL_CONFIGURATIONS_CONSOLE = "PANEL_CONFIGURATIONS_CONSOLE";
    private PnlServerConsole pnlServerConsole = new PnlServerConsole();
    private PnlTrapConsole pnlTrapsConsole = new PnlTrapConsole();
    private PnlPerformanceStatsConsole pnlStatsConsole = new PnlPerformanceStatsConsole();
    private PnlConfigurtionConsole pnlConfigConsole = new PnlConfigurtionConsole();
    
    /** Creates new form FrmNetworkAnalyzer */
    private FrmNetworkAnalyzer() {
        initComponents();
        customize();
        pack();
    }

    public static FrmNetworkAnalyzer getInstance() {
        if (frame == null) {
            frame = new FrmNetworkAnalyzer();
        }
        return frame;
    }

    private void customize() {
        setTitle(Constants.APP_TITLE + " " + Constants.APP_VERSION);
        splitPane.setLeftComponent(pnlViewNevigator);
        splitPane.setRightComponent(pnlViewDetails);
        createNevigationTree();
        pnlViewDetails.add(pnlServerConsole, PANEL_SERVER_CONSOLE);
        pnlViewDetails.add(pnlTrapsConsole, PANEL_TRAPS_CONSOLE);
        pnlViewDetails.add(pnlStatsConsole, PANEL_STATS_CONSOLE);
        pnlViewDetails.add(pnlConfigConsole, PANEL_CONFIGURATIONS_CONSOLE);
    }

    private void createNevigationTree() {
        DefaultMutableTreeNode treeServerConsole = new DefaultMutableTreeNode(new TreeObject("Server Console", PANEL_SERVER_CONSOLE));
        DefaultMutableTreeNode treeTrapsConsole = new DefaultMutableTreeNode(new TreeObject("Traps Console", PANEL_TRAPS_CONSOLE));
        DefaultMutableTreeNode treeStatsConsole = new DefaultMutableTreeNode(new TreeObject("Stats Console", PANEL_STATS_CONSOLE));
        DefaultMutableTreeNode treeConfigConsole = new DefaultMutableTreeNode(new TreeObject("Configurations Console", PANEL_CONFIGURATIONS_CONSOLE));
        treeRoot.add(treeServerConsole);
        treeRoot.add(treeTrapsConsole);
        treeRoot.add(treeStatsConsole);
        treeRoot.add(treeConfigConsole);
    }

    public void showView(String viewId) {
        CardLayout cl = (CardLayout) (pnlViewDetails.getLayout());
        cl.show(pnlViewDetails, viewId);
    }

    public void updateConnection(boolean success) {
        pnlServerConsole.updateConnection(success);
    }

    public void addTrap(Trap trap) {
        pnlTrapsConsole.addTrap(trap);
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
        splitPane = new javax.swing.JSplitPane();
        pnlViewNevigator = new javax.swing.JPanel();
        scrollPaneNavigator = new javax.swing.JScrollPane();
        treeNavigator = new javax.swing.JTree();
        pnlViewDetails = new javax.swing.JPanel();
        mnuBar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuItemConnect = new javax.swing.JMenuItem();
        mnuItemExit = new javax.swing.JMenuItem();
        mnuTools = new javax.swing.JMenu();
        mnuItemClientConfigurations = new javax.swing.JMenuItem();
        mnuLookAndFeel = com.advoss.util.SwingUtilities.getLookAndFeelMenu(this);
        mnuHelp = new javax.swing.JMenu();
        mnuItenAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlMain.setLayout(new java.awt.BorderLayout());

        splitPane.setDividerLocation(200);
        splitPane.setLastDividerLocation(150);
        splitPane.setOneTouchExpandable(true);

        pnlViewNevigator.setPreferredSize(new java.awt.Dimension(125, 322));
        pnlViewNevigator.setLayout(new java.awt.BorderLayout());

        treeNavigator.setModel(treeModel);
        treeNavigator.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeNavigatorValueChanged(evt);
            }
        });
        scrollPaneNavigator.setViewportView(treeNavigator);

        pnlViewNevigator.add(scrollPaneNavigator, java.awt.BorderLayout.CENTER);

        splitPane.setLeftComponent(pnlViewNevigator);

        pnlViewDetails.setLayout(new java.awt.CardLayout());
        splitPane.setRightComponent(pnlViewDetails);

        pnlMain.add(splitPane, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

        mnuFile.setText("File");

        mnuItemConnect.setText("Connect");
        mnuItemConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConnectActionPerformed(evt);
            }
        });
        mnuFile.add(mnuItemConnect);

        mnuItemExit.setText("Exit");
        mnuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemExitActionPerformed(evt);
            }
        });
        mnuFile.add(mnuItemExit);

        mnuBar.add(mnuFile);

        mnuTools.setText("Tools");

        mnuItemClientConfigurations.setText("Client Configurations");
        mnuItemClientConfigurations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemClientConfigurationsActionPerformed(evt);
            }
        });
        mnuTools.add(mnuItemClientConfigurations);

        mnuBar.add(mnuTools);

        mnuLookAndFeel.setText("Themes");
        mnuBar.add(mnuLookAndFeel);

        mnuHelp.setText("Help");

        mnuItenAbout.setText("About");
        mnuHelp.add(mnuItenAbout);

        mnuBar.add(mnuHelp);

        setJMenuBar(mnuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemExitActionPerformed
        if (SwingUtilities.showWarnMessage("Do you really want to exit?") == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_mnuItemExitActionPerformed

    private void treeNavigatorValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeNavigatorValueChanged
        if(evt.getNewLeadSelectionPath().getLastPathComponent() != null) {
            DefaultMutableTreeNode selectedNodeObject = (DefaultMutableTreeNode) evt.getNewLeadSelectionPath().getLastPathComponent();
            showView(((TreeObject)selectedNodeObject.getUserObject()).getObjectId());
        }
    }//GEN-LAST:event_treeNavigatorValueChanged

    private void mnuItemConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConnectActionPerformed
        if(mnuItemConnect.getText().equals("Connect")) {
            if(NetworkAnalyzer.getInstance().connect()) {
                pnlServerConsole.updateConnection(true);
                mnuItemConnect.setText("Disconnect");
            }
        } else {
            NetworkAnalyzer.getInstance().disconnect();
            pnlServerConsole.updateConnection(false);
            mnuItemConnect.setText("Connect");
        }

    }//GEN-LAST:event_mnuItemConnectActionPerformed

    private void mnuItemClientConfigurationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemClientConfigurationsActionPerformed
        DlgClientConfigurations dialog = new DlgClientConfigurations(this, true);
        dialog.setSize(400, 250);
        SwingUtilities.centerDialog(dialog);
        dialog.setVisible(true);
    }//GEN-LAST:event_mnuItemClientConfigurationsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar mnuBar;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuHelp;
    private javax.swing.JMenuItem mnuItemClientConfigurations;
    private javax.swing.JMenuItem mnuItemConnect;
    private javax.swing.JMenuItem mnuItemExit;
    private javax.swing.JMenuItem mnuItenAbout;
    private javax.swing.JMenu mnuLookAndFeel;
    private javax.swing.JMenu mnuTools;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlViewDetails;
    private javax.swing.JPanel pnlViewNevigator;
    private javax.swing.JScrollPane scrollPaneNavigator;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTree treeNavigator;
    // End of variables declaration//GEN-END:variables
}