/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.manager;

import org.ict.oamp.mib.MibFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ict.oamp.em.ElementDAO;
import org.ict.oamp.mib.MibFileType;
import org.ict.oamp.utils.LoggerModule;
import org.ict.oamp.utils.MibModule;
import org.ict.oamp.utils.VarInfo;
import org.ict.util.CommonFunctions;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class OAMPManager extends HttpServlet {

    public static SnmpManager snmpManager = null;
//    public static MibModule mibModule = null;
    public static LoggerModule logger = null;
    public static boolean initialized = false;
    public static String applicationBaseURL = null;
    
    @Override
    public void init() throws ServletException {
        Debug.print("Manager Initializing...");
        super.init();
        try {
            if (snmpManager == null) {
                logger = new LoggerModule();
                logger.init();

                snmpManager = SnmpManager.getInstance();
                snmpManager.Initialize();

                snmpManager.getMibModule().loadAllMibs(getServletContext().getRealPath("/") + "/secure/mibs/");
                initElementManagers();
                initialized = true;
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new ServletException(CommonFunctions.prepareErrorMessage(getClass().getName(), "init", ex));
        }
    }

    public static Vector<ElementManager> getRegisteredElements() {
        return snmpManager.getRegisteredElements();
    }

    public static ElementManager getRegisteredElement(int elementId) {
        for (int i = 0; i < snmpManager.getRegisteredElements().size(); i++) {
            if (snmpManager.getRegisteredElements().get(i).getElementId() == elementId) {
                return snmpManager.getRegisteredElements().get(i);
            }
        }
        return null;
    }

    public static ElementManager getRegisteredElement(String destination) {
        for (int i = 0; i < snmpManager.getRegisteredElements().size(); i++) {
            if (snmpManager.getRegisteredElements().get(i).getDestinationAddresss().equalsIgnoreCase(destination)) {
                return snmpManager.getRegisteredElements().get(i);
            }
        }
        return null;
    }

    public static void registerElement(ElementManager registeredElement) {
        snmpManager.registerElementManager(registeredElement);
    }

    public static void unregisterElement(int elementId) {
        for(int i=0; i<snmpManager.getRegisteredElements().size(); i++) {
            if(snmpManager.getRegisteredElements().get(i).getElementId() == elementId) {
                snmpManager.unregisterElementManager(snmpManager.getRegisteredElements().get(i));
                break;
            }
        }
    }

    public static void updateRegisteredElement(ElementManager element) {
        snmpManager.updateElementManager(element);
    }

    public static String getApplicationBaseURL() {
        return applicationBaseURL;
    }

//    public static void updateRegisteredElement(ElementManager element, Vector<VarInfo> variables, MibFileType type) {
//        for(int emIndex=0; emIndex<registeredElements.size(); emIndex++) {
//            if(registeredElements.get(emIndex).getElementId() == element.getElementId()) {
//                Vector<VarInfo> varInfos = new Vector<VarInfo>();
//                if(type == MibFileType.CONFIGURATION_MIB) {
//                    varInfos = element.getConfigurationVariables();
//                } else if (type == MibFileType.PERFORMANCE_MIB) {
//                    varInfos = element.getPerformanceVariables();
//                } else {
//                    varInfos = element.getFaultVariables();
//                }
//                for(int i=0; i<variables.size(); i++) {
//                    for(int y=0; y<varInfos.size(); y++) {
//                        if(variables.get(i).equals(varInfos.get(y))) {
//                            varInfos.set(y, variables.get(i));
//                        }
//                    }
//                }
//                if(type == MibFileType.CONFIGURATION_MIB) {
//                    element.setConfigurationVariables(varInfos);
//                } else if (type == MibFileType.FAULT_MIB) {
//                    element.setFaultVariables(varInfos);
//                } else {
//                    element.setPerformanceVariables(varInfos);
//                }
//                registeredElements.setElementAt(element, emIndex);
//                break;
//            }
//        }
//    }

    public static void deleteAllElements() {
        snmpManager.unregisterAllElementManager();
    }

    private void initElementManagers() throws Exception {
        ElementDAO elementDAO = new ElementDAO();
        Vector<ElementManager> elementManagers = elementDAO.getElementManagers();
        for (int i = 0; i < elementManagers.size(); i++) {
            try {
                ElementManager element = elementManagers.get(i);
                snmpManager.registerElementManager(element);
//                Vector<MibFile> mibFiles = element.getMibFiles();
//                for (int mibIndex = 0; mibIndex < mibFiles.size(); mibIndex++) {
//                    HashMap<Object, VarInfo> mibVars = null;
//                    MibFile mibFile = mibFiles.get(mibIndex);
//                    try {
//                        mibVars = mibModule.getMibVariables(mibFile.getFileName(), false);
//                    } catch (Exception ex) {
//                        Debug.printStackTrace(ex);
//                    }
//                    if (mibVars != null) {
//                        for (VarInfo var : mibVars.values()) {
//                            if (mibFile.getMibType() == MibFileType.PERFORMANCE_MIB) {
//                                element.AddPerformanceVariable(var);
//                            } else if (mibFile.getMibType() == MibFileType.CONFIGURATION_MIB) {
//                                element.AddConfigurationsVariable(var);
//                            } else if (mibFile.getMibType() == MibFileType.FAULT_MIB) {
//                                element.AddFaultVariable(var);
//                            }
//                        }
//                    }
//                }
//                registeredElements.add(element);
            } catch (Exception ex) {
                Debug.printStackTrace(ex);
                throw new Exception(CommonFunctions.prepareErrorMessage(getClass().getName(), "initElementManagers()", ex));
            }
        }
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        applicationBaseURL = request.getServerName() + ":" + request.getServerPort();
        response.sendRedirect(request.getContextPath() + "/secure/index.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
