/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PnlPerformanceStatsConsole.java
 *
 * Created on Apr 21, 2009, 10:12:48 AM
 */
package com.advoss.network.analyzer.gui;

import com.advoss.network.analyzer.NetworkAnalyzer;
import com.advoss.util.CommonFunctions;
import com.advoss.util.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import org.ict.oamp.service.client.ElementManager;
import org.ict.oamp.service.client.VarInfo;
import org.ict.oamp.service.client.VarInfoValue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Alam Sher
 */
public class PnlPerformanceStatsConsole extends javax.swing.JPanel {

    private TimeSeries[] series = null;
    private Vector<VarInfo> selectedVariables = new Vector<VarInfo>();
    private ElementManager selectedElement = null;
    
    private Timer timer = new Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            updateStats(getUpdatedData());
        }
    });
    private DefaultTableModel tableModelStatsVariable = new DefaultTableModel(new Object[]{"Time" , "Variable OID", "Variable Name", "Variable Value", "Description"}, 0) {

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /** Creates new form PnlPerformanceStatsConsole */
    public PnlPerformanceStatsConsole() {
        initComponents();
    }

    public void updateStats(VarInfoValue[] variables) {
        for(int i=0; i<variables.length; i++) {
            series[i].add(new Millisecond(), Integer.parseInt(variables[i].getValue()));
            tableModelStatsVariable.addRow(new Object[]{CommonFunctions.formatDate(new SimpleDateFormat("HH:mm:ss"), Calendar.getInstance()), variables[i].getVarInfo().getTag(), getVariableName(variables[i].getVarInfo().getTag()), variables[i].getValue(), getVariableDescriotion(variables[i].getVarInfo().getTag())});
        }
        try {
            tblStatsVariable.scrollRectToVisible(tblStatsVariable.getCellRect(tblStatsVariable.getRowCount()-1, tblStatsVariable.getColumnCount(), true));
        } catch (Exception ex){
        }
    }

    private String getVariableName(String oid) {
        for(int i=0; i<selectedVariables.size(); i++) {
            if(selectedVariables.get(i).getTag().equalsIgnoreCase(oid)) {
                return selectedVariables.get(i).getKey();
            }
        }
        return "";
    }

    private String getVariableDescriotion(String oid) {
        for(int i=0; i<selectedVariables.size(); i++) {
            if(selectedVariables.get(i).getTag().equalsIgnoreCase(oid)) {
                return selectedVariables.get(i).getLongDescription();
            }
        }
        return "";
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart result = ChartFactory.createTimeSeriesChart(
                "Performance Stats",
                "Time",
                "Value",
                dataset,
                true,
                true,
                false);
        XYPlot plot = (XYPlot) result.getPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        axis = plot.getRangeAxis();
        axis.setRange(0.0, 200.0);
        XYLineAndShapeRenderer renderer
            = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setSeriesFillPaint(0, Color.white);
        renderer.setSeriesFillPaint(1, Color.green);
        renderer.setSeriesFillPaint(2, Color.red);
        renderer.setUseFillPaint(true);
        return result;
    }

    public VarInfoValue[] getUpdatedData() {
        try {
            VarInfo[] currentVariables = new VarInfo[selectedVariables.size()];
            VarInfoValue updatesVaues[] = NetworkAnalyzer.getInstance().getClient().updateVariables(selectedVariables.toArray(currentVariables), selectedElement.getElementId());
            return updatesVaues;
        } catch (Exception ex) {
            ex.printStackTrace();
            SwingUtilities.showErrorMessage(ex.getMessage());
            btnCaptureActionPerformed(null);
        }
        return null;
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolBar = new javax.swing.JToolBar();
        btnCapture = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        splitPane = new javax.swing.JSplitPane();
        pnlStatsChart = new javax.swing.JPanel();
        pnlStatsData = new javax.swing.JPanel();
        scrollPaneTableVariables = new javax.swing.JScrollPane();
        tblStatsVariable = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        toolBar.setRollover(true);

        btnCapture.setText("Capture");
        btnCapture.setFocusable(false);
        btnCapture.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCapture.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCapture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaptureActionPerformed(evt);
            }
        });
        toolBar.add(btnCapture);

        btnSettings.setText("Settings");
        btnSettings.setFocusable(false);
        btnSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });
        toolBar.add(btnSettings);

        add(toolBar, java.awt.BorderLayout.NORTH);

        splitPane.setDividerLocation(200);
        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlStatsChart.setBackground(new java.awt.Color(255, 255, 255));
        pnlStatsChart.setLayout(new java.awt.BorderLayout());
        splitPane.setLeftComponent(pnlStatsChart);

        pnlStatsData.setLayout(new java.awt.BorderLayout());

        tblStatsVariable.setModel(tableModelStatsVariable);
        tblStatsVariable.getTableHeader().setReorderingAllowed(false);
        scrollPaneTableVariables.setViewportView(tblStatsVariable);

        pnlStatsData.add(scrollPaneTableVariables, java.awt.BorderLayout.CENTER);

        splitPane.setRightComponent(pnlStatsData);

        add(splitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCaptureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaptureActionPerformed
        if(timer.isRunning()) {
            btnCapture.setText("Capture");
            timer.stop();
            return;
        }
        tableModelStatsVariable.setRowCount(0);
        series = new TimeSeries[selectedVariables.size()];
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for(int i=0; i<series.length; i++) {
            series[i] = new TimeSeries(selectedVariables.get(i).getKey(), Millisecond.class);
            dataset.addSeries(series[i]);
        }
        pnlStatsChart.removeAll();
        ChartPanel chartPanel = new ChartPanel(createChart(dataset));
        pnlStatsChart.add(chartPanel, BorderLayout.CENTER);
        pnlStatsChart.revalidate();
        timer.start();
        btnCapture.setText("Freeze");
    }//GEN-LAST:event_btnCaptureActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        DlgMibBrowser browser = new DlgMibBrowser(FrmNetworkAnalyzer.getInstance(), true);
        SwingUtilities.centerDialog(browser);
        browser.setVisible(true);
        if(!browser.isCancelled()) {
            selectedVariables = browser.getSelectedVariables();
            selectedElement = browser.getSelectedElement();
        }
    }//GEN-LAST:event_btnSettingsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapture;
    private javax.swing.JButton btnSettings;
    private javax.swing.JPanel pnlStatsChart;
    private javax.swing.JPanel pnlStatsData;
    private javax.swing.JScrollPane scrollPaneTableVariables;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTable tblStatsVariable;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
}
