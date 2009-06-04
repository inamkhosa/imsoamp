/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.cm;

import java.util.Calendar;
import java.util.Vector;
import org.ict.oamp.dao.BaseDAO;
import org.ict.oamp.manager.ElementManager;
import org.ict.oamp.manager.OAMPManager;
import org.ict.oamp.utils.VarInfo;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class ConfigurationsDAO extends BaseDAO{

    public Vector<VarInfo> getLiveStats(int elementId, String[] selectedOids) throws Exception {
        Vector<VarInfo> variables = new Vector<VarInfo>();
        ElementManager element = OAMPManager.getRegisteredElement(elementId);
        if (element == null) {
            throw new Exception("Unknown/Unregistered Element found in request");
        }
        Vector<VarInfo> selectedVariables = new Vector<VarInfo>();
        Vector<VarInfo> configVariabls = element.getVariables();
        for (int i = 0; i < selectedOids.length; i++) {
            for (int j = 0; j < configVariabls.size(); j++) {
                if (configVariabls.get(j).getTag().toString().trim().equalsIgnoreCase(selectedOids[i])) {
                    selectedVariables.add(configVariabls.get(j));
                }
            }
        }
//        variables = element.UpdateVariables(selectedVariables);
        for (int i = 0; i < variables.size(); i++) {
            variables.get(i).setUpdatedAt(Calendar.getInstance());
            Debug.print(variables.get(i).getTag() + "-" + variables.get(i).getValue());
        }
        return variables;
    }

    public Vector<VarInfo> getLiveStats(int elementId) throws Exception {
        Vector<VarInfo> variables = new Vector<VarInfo>();
        ElementManager element = OAMPManager.getRegisteredElement(elementId);
        if (element == null) {
            throw new Exception("Unknown/Unregistered Element found in request");
        }
        Vector<VarInfo> configVariables = element.getVariables();
//        variables = element.UpdateVariables(configVariables);
        for (int i = 0; i < variables.size(); i++) {
            variables.get(i).setUpdatedAt(Calendar.getInstance());
            Debug.print(variables.get(i).getTag() + "-" + variables.get(i).getValue());
        }
        return variables;
    }

    public String updateStats(int elementId, String oid, int type, String value) throws Exception{
        String updatedValue = null;
        ElementManager element = OAMPManager.getRegisteredElement(elementId);
        if(element == null) {
            throw new Exception("Unknown/Unregistered Element found in request");
        }
        Vector<VarInfo> variables = element.getVariables();
        Vector<VarInfo> selectedVariables = new Vector<VarInfo>();
        for(int i=0; i<variables.size(); i++){
            if(variables.get(i).getTag().toString().trim().equalsIgnoreCase(oid.trim())) {
                variables.get(i).setValue(value, type);
                selectedVariables.add(variables.get(i));
            }
        }
//        Vector<VarInfo> updatedVariables = element.SetVariables(selectedVariables);
//        for(int i=0; i<updatedVariables.size(); i++) {
//            if(updatedVariables.get(i).getTag().toString().trim().equalsIgnoreCase(oid.trim())) {
//                updatedValue = updatedVariables.get(i).getValue().toString();
//            }
//        }
        return updatedValue;
    }
}
