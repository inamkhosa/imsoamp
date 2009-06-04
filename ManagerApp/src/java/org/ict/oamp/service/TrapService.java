/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.service;

import java.util.Vector;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.ict.oamp.exception.OampIOException;
import org.ict.oamp.manager.ElementManager;
import org.ict.oamp.manager.OAMPManager;
import org.ict.oamp.service.exception.TrapServiceException;
import org.ict.oamp.trapserver.TrapServer;
import org.ict.oamp.trapserver.dao.TrapServerDAO;
import org.ict.oamp.trapserver.trap.Trap;
import org.ict.oamp.trapserver.trap.TrapBinding;
import org.ict.oamp.trapserver.trap.TrapDefinition;
import org.ict.oamp.trapserver.trap.TrapTableDefinition;
import org.ict.oamp.utils.CommandPacket;
import org.ict.oamp.utils.VarInfo;
import org.ict.util.Debug;
import org.ict.util.VarInfoValue;

/**
 *
 * @author Alam Sher
 */
@WebService()
public class TrapService {

    public static final int STATUS_STOPPED = 0;
    public static final int STATUS_RUNNING = 1;
    private int status = STATUS_STOPPED;
    private TrapServer trapServer = null;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getStatusDescription")
    public String getStatusDescription() throws TrapServiceException {
        switch (status) {
            case STATUS_STOPPED:
                return "STOPPED";
            case STATUS_RUNNING:
                return "RUNNING";
            default:
                return "STOPPED";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getStatus")
    public int getStatus() throws TrapServiceException {
        return status;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "startService")
    public boolean startService() throws TrapServiceException {
        if (status == STATUS_STOPPED) {
            try {
                trapServer = TrapServer.getInstance();
                trapServer.getConfig().setRegisteredElements(OAMPManager.getRegisteredElements());
                trapServer.initialize();
                trapServer.startServer();
                status = STATUS_RUNNING;
            } catch (Exception ex) {
                Debug.printStackTrace(ex);
                throw new TrapServiceException(ex.toString());
            }
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "stopService")
    public boolean stopService() throws TrapServiceException {
        try {
            if (status == STATUS_RUNNING) {
                trapServer.stopServer();
                status = STATUS_STOPPED;
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new TrapServiceException(ex.toString());
        }
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTrapDefinitions")
    public java.util.Vector<TrapDefinition> getTrapDefinitions() throws TrapServiceException {
        try {
            TrapServerDAO dao = new TrapServerDAO();
            return dao.getTrapDefinitions();
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new TrapServiceException(ex.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTrapDefinition")
    public TrapDefinition getTrapDefinition(@WebParam(name = "trapDefinitionId") int trapDefinitionId) throws TrapServiceException {
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addTrapDefinition")
    public TrapDefinition addTrapDefinition(@WebParam(name = "trapDefinition") TrapDefinition trapDefinition, boolean createTable) throws TrapServiceException {
        TrapServerDAO dao = new TrapServerDAO();
        try {
            if (createTable) {
                TrapTableDefinition tableDefinition = trapDefinition.getTrapTableDefinition();
                if (dao.createTable(tableDefinition)) {
                    return dao.createTrapDefinition(trapDefinition);
                }
            } else {
                return dao.createTrapDefinition(trapDefinition);
            }
            TrapServer.getInstance().notifyTrapDefinitionUpdate();
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new TrapServiceException(ex.getMessage());
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editTrapDefinition")
    public TrapDefinition editTrapDefinition(@WebParam(name = "trapDefinition") TrapDefinition trapDefinition) throws TrapServiceException {
        TrapServerDAO dao = new TrapServerDAO();
        try {
            if (trapDefinition.isCreateTable()) {
                TrapTableDefinition tableDefinition = trapDefinition.getTrapTableDefinition();
                if (dao.createTable(tableDefinition)) {
                    return dao.editTrapDefinition(trapDefinition);
                }
            } else {
                return dao.editTrapDefinition(trapDefinition);
            }
            TrapServer.getInstance().notifyTrapDefinitionUpdate();
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new TrapServiceException(ex.getMessage());
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deleteTrapDefinition")
    public boolean deleteTrapDefinition(@WebParam(name = "trapDefinition") TrapDefinition trapDefinition) throws TrapServiceException {
        TrapServerDAO dao = new TrapServerDAO();
        try {
            if (dao.deleteTrapDefinition(trapDefinition.getTrapDefinitionId())) {
                TrapServer.getInstance().notifyTrapDefinitionUpdate();
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new TrapServiceException(ex.getMessage());
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getElapsedTime")
    public long getElapsedTime() throws TrapServiceException {
        if (trapServer == null) {
            return 0;
        } else {
            return trapServer.getElapsedTime();
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTablesList")
    public java.util.Vector<String> getTablesList() throws TrapServiceException {
        Vector<String> tablesList = new Vector<String>();
        try {
            TrapServerDAO dao = new TrapServerDAO();
            tablesList = dao.getTablesList();
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new TrapServiceException(ex.getMessage());
        }
        return tablesList;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAvailableDataTypes")
    public java.util.Vector<String> getAvailableDataTypes() throws TrapServiceException {
        Vector<String> dataTypes = new Vector<String>();
        try {
            TrapServerDAO dao = new TrapServerDAO();
            dataTypes = dao.getDataTypes();
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new TrapServiceException(ex.getMessage());
        }
        return dataTypes;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTableColumnsList")
    public java.util.Vector<String> getTableColumnsList(@WebParam(name = "tableName") String tableName) throws TrapServiceException {
        Vector<String> columnsList = new Vector<String>();
        try {
            TrapServerDAO dao = new TrapServerDAO();
            columnsList = dao.getTableColumnsList(tableName);
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new TrapServiceException(ex.getMessage());
        }
        return columnsList;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTrap")
    public Trap getTrap() throws TrapServiceException {
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getTrapBinding")
    public TrapBinding getTrapBinding() throws TrapServiceException {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getRegisteredElements")
    public java.util.Vector<ElementManager> getRegisteredElements() throws TrapServiceException {
        return OAMPManager.getRegisteredElements();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getApplicationBaseURL")
    public String getApplicationBaseURL() throws TrapServiceException {
        return OAMPManager.getApplicationBaseURL();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateVariables")
    public java.util.Vector<VarInfoValue> updateVariables(@WebParam(name = "variables") java.util.Vector<VarInfo> variables, @WebParam(name = "elementId") int elementId) throws TrapServiceException {
        CommandPacket packet = new CommandPacket(CommandPacket.GET_REQUEST);
        CommandPacket result = null;
        for (VarInfo vinfo : variables) {
            System.out.println("Sending Var: " + vinfo.getKey());
            packet.AddVariable(vinfo);
        }
        try {
            result = OAMPManager.snmpManager.sendCommand(OAMPManager.getRegisteredElement(elementId).getElementIdentifier(), packet);
            if (result.getType() == CommandPacket.ERROR_REPORT) {
                System.out.println("Report : " + result.toString());
                Vector<VarInfoValue> valueVariables = new Vector<VarInfoValue>();
                Vector<VarInfo> updatedVariables = result.getAllVariables();
                for (int i = 0; i < updatedVariables.size(); i++) {
                    VarInfoValue valeVariable = new VarInfoValue();
                    valeVariable.setVarInfo(updatedVariables.get(i));
                    valeVariable.setValue(updatedVariables.get(i).getValue());
                }
                return valueVariables;
            }
        } catch (OampIOException oampIOE) {
            Debug.printStackTrace(oampIOE);
        }
        Vector<VarInfoValue> valueVariables = new Vector<VarInfoValue>();
        Vector<VarInfo> updatedVariables = result.getAllVariables();
        for (int i = 0; i < updatedVariables.size(); i++) {
            VarInfoValue valeVariable = new VarInfoValue();
            valeVariable.setVarInfo(updatedVariables.get(i));
            valeVariable.setValue(updatedVariables.get(i).getValue());
            valueVariables.add(valeVariable);
        }
        return valueVariables;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "setVariableValue")
    public java.util.Vector<VarInfoValue> setVariableValue(@WebParam(name = "variableInfoValues") java.util.Vector<VarInfoValue> variableInfoValues, @WebParam(name = "elementId") int elementId) throws TrapServiceException {
        CommandPacket packet = new CommandPacket(CommandPacket.SET_REQUEST);
        CommandPacket result = null;

        for (VarInfoValue vinfo : variableInfoValues) {
            vinfo.getVarInfo().setValue(vinfo.getValue(), vinfo.getVarInfo().getType());
            packet.AddVariable(vinfo.getVarInfo());
        }

        try {
            result = OAMPManager.snmpManager.sendCommand(OAMPManager.getRegisteredElement(elementId).getElementIdentifier(), packet);
        } catch (OampIOException oampIOE) {
            Debug.printStackTrace(oampIOE);
        }

        if (result.getErrorIndex() != 0) {
            Debug.print("--------Error --------");
            Debug.print(result.getVariable(result.getErrorIndex() - 1).toString());
            Debug.print(result.getErrorText());

            Debug.print("Error in PDU at index: " + result.getErrorIndex() +
                    "\nVariable: " + result.getVariable(result.getErrorIndex()) +
                    "\nError: " + result.getErrorText());
        }
        Vector<VarInfoValue> valueVariables = new Vector<VarInfoValue>();
        Vector<VarInfo> updatedVariables = result.getAllVariables();
        for (int i = 0; i < updatedVariables.size(); i++) {
            VarInfoValue valeVariable = new VarInfoValue();
            valeVariable.setVarInfo(updatedVariables.get(i));
            valeVariable.setValue(updatedVariables.get(i).getValue());
            valueVariables.add(valeVariable);
        }
        return valueVariables;
    }
}