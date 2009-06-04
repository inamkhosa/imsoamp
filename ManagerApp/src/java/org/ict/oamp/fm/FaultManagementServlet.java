/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.fm;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ict.oamp.em.ElementDAO;
import org.ict.oamp.manager.OAMPManager;

/**
 *
 * @author Alam Sher
 */
public class FaultManagementServlet extends OAMPManager {

    public String forwardURL = "/secure/fm/FaultManager.jsp";
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
            TrapDAO trapDAO = new TrapDAO();
            ElementDAO elementDAO = new ElementDAO();
            request.setAttribute("elements", elementDAO.getElementManagers());
            Vector<Trap> traps = trapDAO.getTraps(request.getParameter("selectedElement"));
            request.setAttribute("traps", traps);
            try {
                request.setAttribute("selected", OAMPManager.getRegisteredElement(request.getParameter("selectedElement")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            TrapCategoryDAO dao = new TrapCategoryDAO();
            Hashtable<String, String> table = dao.getColorTable();
            for(int i=0; i<traps.size(); i++) {
                if(table.containsKey(traps.get(i).getType())) {
                    traps.get(i).setColorCode(table.get(traps.get(i).getType()));
                }
            }
            request.getRequestDispatcher(forwardURL).forward(request, response);
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
            request.getRequestDispatcher(forwardURL + "?error=" + ex.toString()).forward(request, response);
            return;
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
