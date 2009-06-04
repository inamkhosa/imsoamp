/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advoss.network.analyzer.config;

import com.advoss.network.analyzer.Constants;
import com.advoss.util.FileManager;
import java.io.File;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
/**
 *
 * @author Alam Sher
 */
public class Configuration {

    public static final String XML_ELEMENT_ROOT = "<NetworkAnalyzer>";
    public static final String XML_ELEMENT_1 = "<EndPointAddress>";
    public static final String XML_ELEMENT_1_END = "</EndPointAddress>";
    public static final String XML_ELEMENT_ROOT_END = "</NetworkAnalyzer>";

    private static Configuration configuration = null;

    private String endPointAddress = "http://localhost:8084/oampmanager/TrapService";
    
    private static Element rootElement = null;
    private static SAXBuilder saxBuilder = null;
    private static Document document = null;

    private Configuration() {
        saxBuilder = new SAXBuilder();
    }

    public static Configuration getInstance() {
        if(configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    public void loadConfigurations() {
        try {
            if(!FileManager.fileExists(Constants.CONFIG_FILE)) {
                FileManager.createFolder(Constants.CONFIG_DIR);
                new File(Constants.CONFIG_FILE);
                saveConfigurations();
            }
            document = saxBuilder.build(new File(Constants.CONFIG_FILE));
            rootElement = document.getRootElement();
            if(rootElement.getChildText("EndPointAddress") != null) {
                endPointAddress = rootElement.getChildText("EndPointAddress");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveConfigurations() {
        try {
            String xmlString = XML_ELEMENT_ROOT + "\n";
            xmlString += "\t" + XML_ELEMENT_1 + endPointAddress + XML_ELEMENT_1_END + "\n";
            xmlString += XML_ELEMENT_ROOT_END;
            FileManager.writeToFile(Constants.CONFIG_FILE, xmlString, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getEndPointAddress() {
        return endPointAddress;
    }

    public void setEndPointAddress(String endPointAddress) {
        this.endPointAddress = endPointAddress;
    }
}
