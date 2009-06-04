/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.dao;

import org.ict.util.Debug;
import org.ict.util.DbManager;
import org.ict.util.*;
import java.io.Serializable;
import org.ict.Constants;
import org.ict.exceptions.ConnectionCreateException;
import org.ict.oamp.config.AppConfig;

/**
 *
 * @author Alam Sher
 */
public abstract class BaseDAO implements Serializable {

    protected DbManager dbManager = null;
    protected AppConfig appConfig = null;

    public BaseDAO() {
        appConfig = AppConfig.getInstance();
        dbManager = new DbManager();
    }

    public DbManager connectDatabase() throws Exception {
        try {
            dbManager.connectDatabase(appConfig.getDatabaseHost(), appConfig.getDatabasePort(), appConfig.getDatabaseSchema(), appConfig.getDatabaseUsername(), appConfig.getDatabasePassword(), Constants.DATABASE_MYSQL, false);
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new ConnectionCreateException(dbManager.getExceptionMessage().getMessage());
        }
        return dbManager;
    }

    public boolean disconnectDatabase() {
        if (dbManager == null) {
            dbManager = new DbManager();
            return true;
        } else {
            return dbManager.disconnectDatabase();
        }
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public DbManager getDbManager() {
        return dbManager;
    }

    public void setDbManager(DbManager dbManager) {
        this.dbManager = dbManager;
    }
}
