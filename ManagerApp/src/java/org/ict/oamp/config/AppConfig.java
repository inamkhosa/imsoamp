/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.config;

import org.ict.util.Debug;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author alamsher
 */
public class AppConfig {

    private static AppConfig config = null;

    private Document document = null;
    private SAXBuilder builder = new SAXBuilder();

    private String applicationName = null;
    private String applicationVersion = null;

    private String databaseHost = "localhost";
    private int databasePort = 3306;
    private String databaseUsername = "root";
    private String databasePassword = "rootroot";
    private String databaseSchema = "oamp";

    private AppConfig() {
        initialize();
    }

    public static AppConfig getInstance() {
        if(config == null) {
            config = new AppConfig();
        }
        return config;
    }

    private void initialize() {
        try {
            document = builder.build(getClass().getResourceAsStream("appconfig.xml"));
            Element root = document.getRootElement();
            applicationName = root.getChildTextTrim("Name");
            applicationVersion = root.getChildTextTrim("Version");
            Element database = root.getChild("Database");
            databaseHost = database.getChildTextTrim("Host");
            databasePort = Integer.parseInt(database.getChildTextTrim("Port"));
            databaseSchema = database.getChildTextTrim("Schema");
            databaseUsername = database.getChildTextTrim("Username");
            databasePassword = database.getChildTextTrim("Password");
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
        }
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public String getDatabaseSchema() {
        return databaseSchema;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }
}
