/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.em;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import org.ict.oamp.dao.BaseDAO;
import org.ict.oamp.manager.ElementManager;
import org.ict.oamp.manager.OAMPManager;
import org.ict.oamp.mib.MibFile;
import org.ict.oamp.mib.MibFileType;
import org.ict.oamp.mib.MibFileDAO;
import org.ict.oamp.utils.VarInfo;
import org.ict.util.CommonFunctions;
import org.ict.util.Debug;
import org.ict.util.ResultSetObject;

/**
 *
 * @author Alam Sher
 */
public class ElementDAO extends BaseDAO {

    public ElementManager getElementManager(int elementId) throws Exception {
        ElementManager element = new ElementManager();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_elements", "*", "element_id = " + elementId, "element_identifier");
            if (rs.next()) {
                element.setElementId(elementId);
                element.setElementIdentifier(rs.getRs().getString("element_identifier"));
                element.setDestinationAddress(rs.getRs().getString("destination_address"));
                element.setSecurityName(rs.getRs().getString("security_name"));
                element.setAuthProtocol(rs.getRs().getString("auth_protocol"));
                element.setAuthPass(rs.getRs().getString("auth_pass"));
                element.setPrivProtocol(rs.getRs().getString("priv_protocol"));
                element.setPrivPass(rs.getRs().getString("priv_pass"));
                try {
                    int categoryId = rs.getRs().getInt("category_id");
                    ElementCategoryDAO categoryDAO = new ElementCategoryDAO();
                    element.setCategory(categoryDAO.getElementCategory(categoryId));
                } catch (Exception e) {
                }
                MibFileDAO mibFileDAO = new MibFileDAO();
                element.setMibFiles(mibFileDAO.getMibFiles(elementId));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getElementManager(emID)", exception));
        } finally {
            disconnectDatabase();
        }
        return element;
    }

    public ElementManager getElementManager(String elementIdentifier) throws Exception {
        ElementManager element = new ElementManager();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_elements", "*", "element_identifier = " + CommonFunctions.quotedString(elementIdentifier), "element_identifier");
            if (rs.next()) {
                element.setElementId(rs.getRs().getInt("element_id"));
                element.setElementIdentifier(elementIdentifier);
                element.setDestinationAddress(rs.getRs().getString("destination_address"));
                element.setSecurityName(rs.getRs().getString("security_name"));
                element.setAuthProtocol(rs.getRs().getString("auth_protocol"));
                element.setAuthPass(rs.getRs().getString("auth_pass"));
                element.setPrivProtocol(rs.getRs().getString("priv_protocol"));
                element.setPrivPass(rs.getRs().getString("priv_pass"));
                try {
                    int categoryId = rs.getRs().getInt("category_id");
                    ElementCategoryDAO categoryDAO = new ElementCategoryDAO();
                    element.setCategory(categoryDAO.getElementCategory(categoryId));
                } catch (Exception e) {
                }
                MibFileDAO mibFileDAO = new MibFileDAO();
                element.setMibFiles(mibFileDAO.getMibFiles(element.getElementId()));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getElementManager(emID)", exception));
        } finally {
            disconnectDatabase();
        }
        return element;
    }

    public Vector getElementManagers() throws Exception {
        Vector elements = new Vector();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_elements", "*", null, "element_identifier");
            while (rs.next()) {
                ElementManager element = new ElementManager();
                element.setElementId(rs.getRs().getInt("element_id"));
                element.setElementIdentifier(rs.getRs().getString("element_identifier"));
                element.setDestinationAddress(rs.getRs().getString("destination_address"));
                element.setSecurityName(rs.getRs().getString("security_name"));
                element.setAuthProtocol(rs.getRs().getString("auth_protocol"));
                element.setAuthPass(rs.getRs().getString("auth_pass"));
                element.setPrivProtocol(rs.getRs().getString("priv_protocol"));
                element.setPrivPass(rs.getRs().getString("priv_pass"));
                elements.add(element);
                try {
                    int categoryId = rs.getRs().getInt("category_id");
                    ElementCategoryDAO categoryDAO = new ElementCategoryDAO();
                    element.setCategory(categoryDAO.getElementCategory(categoryId));
                } catch (Exception e) {
                }
                MibFileDAO mibFileDAO = new MibFileDAO();
                element.setMibFiles(mibFileDAO.getMibFiles(element.getElementId()));
            }
            rs.close();
            return elements;
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getElementManagers()", exception));
        } finally {
            disconnectDatabase();
        }
    }

    public Vector getElementManagers(int categoryId) throws Exception {
        Vector elements = new Vector();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_elements", "*", "category_id = " + categoryId, "element_identifier");
            while (rs.next()) {
                ElementManager element = new ElementManager();
                element.setElementId(rs.getRs().getInt("element_id"));
                element.setElementIdentifier(rs.getRs().getString("element_identifier"));
                element.setDestinationAddress(rs.getRs().getString("destination_address"));
                element.setSecurityName(rs.getRs().getString("security_name"));
                element.setAuthProtocol(rs.getRs().getString("auth_protocol"));
                element.setAuthPass(rs.getRs().getString("auth_pass"));
                element.setPrivProtocol(rs.getRs().getString("priv_protocol"));
                element.setPrivPass(rs.getRs().getString("priv_pass"));
                elements.add(element);
                MibFileDAO mibFileDAO = new MibFileDAO();
                element.setMibFiles(mibFileDAO.getMibFiles(element.getElementId()));
            }
            rs.close();
            return elements;
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getElementManagers(categoryId)", exception));
        } finally {
            disconnectDatabase();
        }
    }

    public void addElementManager(String elementIdentifier, String destAddress, String secName, String authProtocol, String authPassword, String privProtocol, String privPassword, String categoryId) throws Exception {
        try {
            connectDatabase();
            Properties props = new Properties();
            props.put("element_identifier", CommonFunctions.quotedString(elementIdentifier));
            props.put("destination_address", CommonFunctions.quotedString(destAddress));
            props.put("security_name", CommonFunctions.quotedString(secName));
            props.put("auth_protocol", CommonFunctions.quotedString(authProtocol));
            props.put("auth_pass", CommonFunctions.quotedString(authPassword));
            props.put("priv_protocol", CommonFunctions.quotedString(privProtocol));
            props.put("priv_pass", CommonFunctions.quotedString(privPassword));
            try {
                int catId = Integer.parseInt(categoryId);
                props.put("category_id", catId);
            } catch (Exception ex) {
                props.put("category_id", "null");
            }
            dbManager.insertSql("tbl_elements", props);
            dbManager.commitTransaction();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "addElement(...)", exception));
        } finally {
            disconnectDatabase();
        }
    }

    public ElementManager editElementManager(int elementId, String elementIdentifier, String destAddress, String secName, String authProtocol, String authPassword, String privProtocol, String privPassword, String categoryId) throws Exception {
        try {
            connectDatabase();
            Properties props = new Properties();
            props.put("element_identifier", CommonFunctions.quotedString(elementIdentifier));
            props.put("destination_address", CommonFunctions.quotedString(destAddress));
            props.put("security_name", CommonFunctions.quotedString(secName));
            props.put("auth_protocol", CommonFunctions.quotedString(authProtocol));
            props.put("auth_pass", CommonFunctions.quotedString(authPassword));
            props.put("priv_protocol", CommonFunctions.quotedString(privProtocol));
            props.put("priv_pass", CommonFunctions.quotedString(privPassword));
            try {
                int catId = Integer.parseInt(categoryId);
                props.put("category_id", catId);
            } catch (Exception ex) {
                props.put("category_id", "null");
            }
            dbManager.updateSql("tbl_elements", props, "element_id=" + elementId);
            dbManager.commitTransaction();
            ElementManager updateElementManager = getElementManager(elementId);
            MibFileDAO mibFileDAO = new MibFileDAO();
            updateElementManager.setMibFiles(mibFileDAO.getMibFiles(updateElementManager.getElementId()));
            return updateElementManager;
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "editElement(...)", e));
        } finally {
            disconnectDatabase();
        }
    }

    public ElementManager populateElementManager(int elementId) throws Exception {
        ElementManager element = new ElementManager();
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("tbl_elements", "*", "element_id = " + elementId, null);
            if (rs.next()) {
                element.setElementId(elementId);
                element.setElementIdentifier(rs.getString("element_identifier"));
                element.setDestinationAddress(rs.getString("destination_address"));
                element.setSecurityName(rs.getString("security_name"));
                element.setAuthProtocol(rs.getString("auth_protocol"));
                element.setAuthPass(rs.getString("auth_pass"));
                element.setPrivProtocol(rs.getString("priv_protocol"));
                element.setPrivPass(rs.getString("priv_pass"));
                try {
                    int categoryId = rs.getInt("category_id");
                    ElementCategoryDAO categoryDAO = new ElementCategoryDAO();
                    element.setCategory(categoryDAO.getElementCategory(categoryId));
                } catch (Exception e) {
                }
                MibFileDAO mibFileDAO = new MibFileDAO();
                element.setMibFiles(mibFileDAO.getMibFiles(element.getElementId()));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "populateElement(elementID)", exception));
        } finally {
            disconnectDatabase();
        }
        return element;
    }

    public void deleteElementManager(int elementId) throws Exception {
        try {
            connectDatabase();
            dbManager.deleteSql("tbl_elements", "element_id = " + elementId);
            dbManager.commitTransaction();
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "deleteElement(elementID)", e));
        } finally {
            disconnectDatabase();
        }
    }

    public void deleteElementManagers() throws Exception {
        try {
            connectDatabase();
            dbManager.deleteSql("tbl_elements", null);
            dbManager.commitTransaction();
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "deleteAllElements()", e));
        } finally {
            disconnectDatabase();
        }
    }

    public void addMibFile(int elementId, String mibName, String fileName) throws Exception {
        try {
            MibFileDAO mibFileDAO = new MibFileDAO();
            mibFileDAO.addMibFile(elementId, mibName, fileName, MibFileType.PERFORMANCE_MIB);
            MibFile loadedMibFile = OAMPManager.snmpManager.getMibModule().getLoadedMibFile(mibName);
            OAMPManager.getRegisteredElement(elementId).addMibFile(loadedMibFile);
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "addMibFile(elementId,mibName,fileName)", exception));
        } finally {
            disconnectDatabase();
        }
    }

//    public void loadMibFiles(ElementManager manager, Vector<MibFile> files, String directoy) throws Exception {
//        try {
//            for (int i = 0; i < files.size(); i++) {
//                HashMap<Object, VarInfo> table = OAMPManager.mibModule.getMibVariables(directoy + files.get(i).getFileName(), false);
//                Vector<VarInfo> variables = new Vector<VarInfo>(table.values());
//                if (files.get(i).getMibType() == MibFileType.PERFORMANCE_MIB) {
//                    manager.AddPerformanceVariables(variables);
//                } else if (files.get(i).getMibType() == MibFileType.CONFIGURATION_MIB) {
//                    manager.AddConfigurationsVariables(variables);
//                } else if (files.get(i).getMibType() == MibFileType.FAULT_MIB) {
//                    manager.AddFaultVariables(variables);
//                }
//            }
//        } catch (Exception ex) {
//            Debug.printStackTrace(ex);
//            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "loadMibFiles(...)", ex));
//        }
//    }

//    public void unloadMibFile(ElementManager manager, Vector<MibFile> files, String directoy) throws Exception {
//        try {
//            for (int i = 0; i < files.size(); i++) {
//                HashMap<Object, VarInfo> table = OAMPManager.mibModule.getMibVariables(directoy + files.get(i).getFileName(), true);
//                Vector<VarInfo> variables = new Vector<VarInfo>(table.values());
//                if (files.get(i).getMibType() == MibFileType.PERFORMANCE_MIB) {
//                    manager.removeVariables(variables, MibFileType.PERFORMANCE_MIB);
//                } else if (files.get(i).getMibType() == MibFileType.PERFORMANCE_MIB) {
//                    manager.removeVariables(variables, MibFileType.CONFIGURATION_MIB);
//                } else if (files.get(i).getMibType() == MibFileType.FAULT_MIB) {
//                    manager.removeVariables(variables, MibFileType.FAULT_MIB);
//                }
//            }
//        } catch (Exception ex) {
//            Debug.printStackTrace(ex);
//            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "unloadMibFiles(...)", ex));
//        }
//    }
}
