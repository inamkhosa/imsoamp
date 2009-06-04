/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.cm;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ict.oamp.manager.ElementManager;
import org.ict.oamp.manager.OAMPManager;
import org.ict.oamp.utils.VarInfo;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class ConfigurationManagementServlet extends OAMPManager {

    public static String forwardURL = "/secure/cm/ConfigurationManager.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int selectedElementId = -1;
            if (request.getParameter("lstElements") != null) {
                try {
                    selectedElementId = Integer.parseInt(request.getParameter("lstElements"));
                } catch (Exception ex) {
                }
            }
            ElementManager selectedElement = null;
            if(selectedElementId != -1) {
                selectedElement = OAMPManager.getRegisteredElement(selectedElementId);
            }
            if(selectedElement == null) {
                if(OAMPManager.getRegisteredElements().size() > 0) {
                    selectedElement = OAMPManager.getRegisteredElements().get(0);
                    selectedElementId = selectedElement.getElementId();
                }
            }
            ConfigurationsDAO configDAO = new ConfigurationsDAO();
            Vector<VarInfo> variables = configDAO.getLiveStats(selectedElement.getElementId());
//            OAMPManager.updateRegisteredElement(selectedElement, variables, MibFileType.CONFIGURATION_MIB);
            request.setAttribute("selectedElementId", selectedElementId);
            request.getRequestDispatcher(forwardURL).forward(request, response);
            return;
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            request.getRequestDispatcher(forwardURL + "?error=" + ex.toString()).forward(request, response);
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
