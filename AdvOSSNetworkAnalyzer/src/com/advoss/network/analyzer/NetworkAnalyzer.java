/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advoss.network.analyzer;

import com.advoss.network.analyzer.config.Configuration;
import com.advoss.network.analyzer.gui.FrmNetworkAnalyzer;
import com.advoss.util.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.rpc.Stub;
import org.ict.oamp.service.client.TrapService;
import org.ict.oamp.service.client.TrapServiceService;
import org.ict.oamp.service.client.TrapServiceService_Impl;

/**
 *
 * @author Alam Sher
 */
public class NetworkAnalyzer {

    private static NetworkAnalyzer networkAnalyzer = null;
    private TrapServiceService clientService = null;
    private TrapService client = null;
    
    private NetworkAnalyzer() {
        clientService =  new TrapServiceService_Impl();
    }

    public static NetworkAnalyzer getInstance() {
        if(networkAnalyzer == null) {
            networkAnalyzer = new NetworkAnalyzer();
        }
        return networkAnalyzer;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        try {
            Configuration.getInstance().loadConfigurations();
        } catch (Exception e) {
            SwingUtilities.showErrorMessage("Error occured while loading configurations:\n" + e.toString());
        }
        FrmNetworkAnalyzer frame = FrmNetworkAnalyzer.getInstance();
        SwingUtilities.centerFrame(frame);
        frame.setVisible(true);
    }

    public TrapService getClient() {
        return client;
    }

    public boolean connect () {
        try {
            client = clientService.getTrapServicePort();
            
            ((Stub) client)._setProperty(javax.xml.rpc.Stub.ENDPOINT_ADDRESS_PROPERTY, Configuration.getInstance().getEndPointAddress());
            client.getStatus();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            SwingUtilities.showErrorMessage("Error occured wile connection to Trap Server : \n" + ex.toString());
        }
        return false;
    }

    public void disconnect() {
        client = null;
    }

    public String getServerBaseURL() {
        try {
            String url = Configuration.getInstance().getEndPointAddress();
            url = url.substring(0, url.lastIndexOf("/"));
            return url;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
