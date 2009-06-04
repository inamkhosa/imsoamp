/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.fm;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import org.ict.oamp.dao.BaseDAO;
import org.ict.util.CommonFunctions;
import org.ict.util.Debug;

/**
 *
 * @author alam sher
 */
public class TrapDAO extends BaseDAO {

    public Vector getTraps(String trapHost) throws Exception {
        Vector traps = new Vector();
        try {
            connectDatabase();
            String whereClause = null;
            if (trapHost != null && !trapHost.equalsIgnoreCase("all")) {
                whereClause = "trap_host = " + CommonFunctions.quotedString(trapHost);
            }
            ResultSet rs = dbManager.query("trap_table", "*", whereClause, "trap_id");
            while (rs.next()) {
                Trap trap = new Trap();
                trap.setTrapId(rs.getInt("trap_id"));
                trap.setTrapHost(rs.getString("trap_host"));
                trap.setType(rs.getString("trap_type"));
                try {
                    trap.setTimestamp(rs.getTimestamp("trap_timestamp"));
                } catch (Exception ex) {
                }
                trap.setRequestId(rs.getInt("request_id"));
                trap.setSecurityName(rs.getString("security_name"));
                trap.setSecurityModel(rs.getInt("security_model"));
                trap.setSecurityLevel(rs.getInt("security_level"));
                traps.add(trap);
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getTraps()", ex));
        } finally {
            disconnectDatabase();
        }
        return traps;
    }

    public Vector getTraps(int categoryId) throws Exception {
        Vector traps = new Vector();
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("trap_table", "*", "category_id=" + categoryId, "trap_id");
            while (rs.next()) {
                Trap trap = new Trap();
                trap.setTrapId(rs.getInt("trap_id"));
                trap.setTrapHost(rs.getString("trap_host"));
                trap.setType(rs.getString("trap_type"));
                try {
                    trap.setTimestamp(rs.getTimestamp("trap_timestamp"));
                } catch (Exception ex) {
                }
                trap.setRequestId(rs.getInt("request_id"));
                trap.setSecurityName(rs.getString("security_name"));
                trap.setSecurityModel(rs.getInt("security_model"));
                trap.setSecurityLevel(rs.getInt("security_level"));
                traps.add(trap);
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getTraps()", ex));
        } finally {
            disconnectDatabase();
        }
        return traps;
    }

    public Trap getTrapDetails(int trapId) throws Exception {
        Trap trap = null;
        try {
            connectDatabase();
            String whereClause = "trap_id = " + trapId;
            ResultSet rs = dbManager.query("trap_table", "*", whereClause, "trap_id");
            trap = new Trap();
            if (rs.next()) {
                trap.setTrapId(rs.getInt("trap_id"));
                trap.setTrapHost(rs.getString("trap_host"));
                trap.setType(rs.getString("trap_type"));
                try {
                    trap.setTimestamp(rs.getTimestamp("trap_timestamp"));
                } catch (Exception ex) {
                }
                trap.setRequestId(rs.getInt("request_id"));
                trap.setSecurityName(rs.getString("security_name"));
                trap.setSecurityModel(rs.getInt("security_model"));
                trap.setSecurityLevel(rs.getInt("security_level"));
            }
            rs.close();
            rs = dbManager.query("trap_vb_table", "*", "trap_id = " + trapId, null);
            Hashtable variables = new Hashtable();
            while (rs.next()) {
                String varOID = rs.getString("vb_oid");
                String varVal = rs.getString("vb_value");
                if (!variables.containsKey(varOID)) {
                    variables.put(varOID, varVal);
                }
            }
            rs.close();
            trap.setVariables(variables);
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getTrapDetails(trapId)", ex));
        } finally {
            disconnectDatabase();
        }
        return trap;
    }
}
