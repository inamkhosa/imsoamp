/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.pm;

import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;
import org.ict.oamp.dao.BaseDAO;
import org.ict.oamp.manager.ElementManager;
import org.ict.oamp.manager.OAMPManager;
import org.ict.oamp.utils.VarInfo;
import org.ict.util.CommonFunctions;
import org.ict.util.Debug;
import org.ict.util.ResultSetObject;

/**
 *
 * @author Alam Sher
 */
public class PerformanceDAO extends BaseDAO {

    public Vector<VarInfo> getLiveStats(int elementId, String[] selectedOids) throws Exception {
        Vector<VarInfo> variables = new Vector<VarInfo>();
        ElementManager element = OAMPManager.getRegisteredElement(elementId);
        if (element == null) {
            throw new Exception("Unknown/Unregistered Element found in request");
        }
        Vector<VarInfo> selectedVariables = new Vector<VarInfo>();
//        Vector<VarInfo> performanceVariabls = element.getPerformanceVariables();
//        for (int i = 0; i < selectedOids.length; i++) {
//            for (int j = 0; j < performanceVariabls.size(); j++) {
//                if (performanceVariabls.get(j).getTag().toString().trim().equalsIgnoreCase(selectedOids[i])) {
//                    selectedVariables.add(performanceVariabls.get(j));
//                }
//            }
//        }
//        variables = element.UpdateVariables(selectedVariables);
//        for (int i = 0; i < variables.size(); i++) {
//            variables.get(i).setUpdatedAt(Calendar.getInstance());
//            Debug.print(variables.get(i).getTag() + "-" + variables.get(i).getValue());
//        }
        updateCache(elementId, variables);
        return variables;
    }

    public Vector<VarInfo> getCachedStats(int elementId, String[] selectedOids) throws Exception {
        Vector<VarInfo> variables = new Vector<VarInfo>();
        try {
//            variables = OAMPManager.getRegisteredElement(elementId).getPerformanceVariables(selectedOids);
            connectDatabase();
            for(int i=0; i<variables.size(); i++) {
                ResultSetObject rsWrapper = dbManager.queryNew("tbl_performance_stats", "*", "elementid = " + elementId + " and oid=" + CommonFunctions.quotedString(variables.get(i).getTag().toString().trim()), null);
                if(rsWrapper.next()) {
                    variables.get(i).setValue(rsWrapper.getRs().getString("value"), rsWrapper.getRs().getInt("type"));
                    Calendar upadatedAt = Calendar.getInstance();
                    if(rsWrapper.getRs().getTimestamp("updated_on") != null) {
                        upadatedAt.setTimeInMillis(rsWrapper.getRs().getTimestamp("updated_on").getTime());
                    } else {
                        upadatedAt = null;
                    }
                    variables.get(i).setUpdatedAt(upadatedAt);
                }
                rsWrapper.close();
            }
            return variables;
        } catch (Exception ex){
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "getCachedStats(elementId, selectedOids)", ex));
        } finally {
            disconnectDatabase();
        }        
    }

    public void updateCache(int elementId, Vector<VarInfo> variables) throws Exception {
        try {
            connectDatabase();
            for(int i=0; i<variables.size(); i++) {
                ResultSetObject rsWrapper = dbManager.queryNew("tbl_performance_stats", "*", "elementid=" + elementId + " and oid='" + variables.get(i).getTag() + "'", null);
                Properties props = new Properties();
                props.put("value", CommonFunctions.quotedString(variables.get(i).getValue().toString()));
                props.put("type", variables.get(i).getType());
                if(variables.get(i).getUpdatedAt() != null) {
                    props.put("updated_on", CommonFunctions.quotedString(CommonFunctions.formatDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"), variables.get(i).getUpdatedAt())));
                }
                if(rsWrapper.next()) {
                    dbManager.updateSql("tbl_performance_stats", props, "elementid=" + elementId + " and oid = '" + variables.get(i).getTag() + "'");
                } else {
                    props.put("elementid", elementId);
                    props.put("oid", CommonFunctions.quotedString(variables.get(i).getTag().toString()));
                    dbManager.insertSql("tbl_performance_stats", props);
                }
            }
            dbManager.commitTransaction();
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "updateCache(elementId, variables)", ex));
        } finally {
            disconnectDatabase();
        }
    }
}
