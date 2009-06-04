/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.mib;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.Vector;
import org.ict.oamp.dao.BaseDAO;
import org.ict.oamp.manager.OAMPManager;
import org.ict.util.CommonFunctions;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class MibFileDAO extends BaseDAO {

    public void addMibFile(int elementId, String mibName, String fileName, MibFileType fileType) throws Exception {
        try {
            connectDatabase();
            Properties props = new Properties();
            props.put("mib_name", CommonFunctions.quotedString(mibName));
            props.put("element_id", String.valueOf(elementId));
            props.put("mib_type", CommonFunctions.quotedString(fileType.toString()));
            props.put("mib_file_name", CommonFunctions.quotedString(fileName));
            dbManager.insertSql("tbl_mibfiles", props);
            dbManager.commitTransaction();
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "addMibFile(elementID,mibName,fileName,fileType)", ex));
        } finally {
            disconnectDatabase();
        }
    }

    public Vector getMibFiles(int elementId) throws Exception {
        Vector files = new Vector();
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("tbl_mibfiles", "*", "element_id=" + elementId, "mib_file_id");
            while (rs.next()) {
                String mibName = rs.getString("mib_name");
                MibFile mibFile = OAMPManager.snmpManager.getMibModule().getLoadedMibFile(mibName);
                mibFile.setElementId(rs.getInt("element_id"));
                mibFile.setId(rs.getInt("mib_file_id"));
                mibFile.setMibType(MibFileType.valueOf(rs.getString("mib_type")));
                files.add(mibFile);
            }
            return files;
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "getMibFiles(elementId)", ex));
        } finally {
            disconnectDatabase();
        }
    }

    public MibFile getMibFile(int mibId) throws Exception {
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("tbl_mibfiles", "*", "mib_file_id=" + mibId, "mib_file_id");
            if (rs.next()) {
                String mibName = rs.getString("mib_name");
                MibFile mibFile = OAMPManager.snmpManager.getMibModule().getLoadedMibFile(mibName);
                mibFile.setId(mibId);
                mibFile.setMibType(MibFileType.valueOf(rs.getString("mib_type")));
                return mibFile;
            }
            return null;
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "getMibFile(mibId)", ex));
        } finally {
            disconnectDatabase();
        }
    }

    public int getMibFileId(int elementId, String mibName) throws Exception {
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("tbl_mibfiles", "*", "mib_name= '" + mibName + "' and element_id = " + elementId, "mib_file_id");
            if (rs.next()) {
                return rs.getInt("mib_file_id");
            }
            return -1;
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "getMibFile(mibId)", ex));
        } finally {
            disconnectDatabase();
        }
    }

    public void deleteMibFiles(int elementId) throws Exception {
        try {
            connectDatabase();
            dbManager.deleteSql("tbl_mibfiles", "element_id =" + elementId);
            dbManager.commitTransaction();
            OAMPManager.getRegisteredElement(elementId).removeAllMibFiles();
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "deleteMibFiles(elementId)", ex));
        } finally {
            disconnectDatabase();
        }
    }

    public void deleteMibFile(int mibFileId, int elementId) throws Exception {
        try {
            MibFile mibFile = getMibFile(mibFileId);
            connectDatabase();
            dbManager.deleteSql("tbl_mibfiles", "mib_file_id =" + mibFileId + " and element_id=" + elementId);
            dbManager.commitTransaction();
            OAMPManager.getRegisteredElement(elementId).removeMibFile(mibFile);
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "deleteMibFiles(elementId)", ex));
        } finally {
            disconnectDatabase();
        }
    }
}
