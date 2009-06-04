/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.manager;

import org.ict.oamp.mib.MibFile;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ict.oamp.em.ElementDAO;
import org.ict.oamp.mib.MibFileType;
import org.ict.oamp.utils.LoggerModule;
import org.ict.oamp.utils.VarInfo;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class OAMPManagerInitializer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/plain");
        try {
            if (OAMPManager.snmpManager == null) {
                OAMPManager.applicationBaseURL = request.getServerName() + ":" + request.getServerPort();
                OAMPManager.logger = new LoggerModule();
                OAMPManager.logger.init();

                OAMPManager.snmpManager = SnmpManager.getInstance();
                OAMPManager.snmpManager.Initialize();

                OAMPManager.snmpManager.getMibModule().loadAllMibs(getServletContext().getRealPath("/") + "/secure/mibs/");

                initElementManagers();
                OAMPManager.initialized = true;
                Debug.print("OAMP Manager Initialized for service access...");
            }
            writer.print("OK");
        } catch (Exception ex) {
            writer.print("ERROR: " + ex.toString());
            Debug.printStackTrace(ex);
        }
    }

    private void initElementManagers() throws Exception {
        ElementDAO elementDAO = new ElementDAO();
        Vector<ElementManager> elementManagers = elementDAO.getElementManagers();
        for (int i = 0; i < elementManagers.size(); i++) {
            try {
                ElementManager element = elementManagers.get(i);
                OAMPManager.snmpManager.registerElementManager(element);
            } catch (Exception ex) {
                Debug.printStackTrace(ex);
            }
        }
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
