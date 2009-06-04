/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.em;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ict.oamp.manager.OAMPManager;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class ElementManagerServlet extends OAMPManager {
    public String forwardURL = "/secure/em/ElementManager.jsp";
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
            ElementDAO elementDAO = new ElementDAO();
            request.setAttribute("elements", elementDAO.getElementManagers());
            request.getRequestDispatcher(forwardURL).forward(request, response);
            return;
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            request.getRequestDispatcher(forwardURL + "?error=" + ex.toString()).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
