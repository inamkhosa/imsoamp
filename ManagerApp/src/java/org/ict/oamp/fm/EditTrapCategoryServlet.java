/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.fm;

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
public class EditTrapCategoryServlet extends OAMPManager {

    public static final int ACTION_UNKNOWN = -1;
    public static final int ACTION_ADD = 0;
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_DELETE = 2;
    public static final int ACTION_DELETE_ALL = 3;
    public static final int ACTION_POPULATE = 4;

    public String managerURL = "/trapcategorymanager";
    public String editElementURL = "/secure/fm/EditTrapCategory.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int action = ACTION_UNKNOWN;
        try {
            action = Integer.parseInt(request.getParameter("action"));
        } catch (Exception ex) {
            action = ACTION_UNKNOWN;
        }
        try {
            TrapCategoryDAO categoryDAO = new TrapCategoryDAO();
            if (action == ACTION_ADD) {
                categoryDAO.addTrapCategory(request.getParameter("categoryName"), request.getParameter("description"), request.getParameter("colorCode"), request.getParameterValues("selectedTypes"));
                TrapCategory category = categoryDAO.getTrapCategory(request.getParameter("categoryName"));
                request.setAttribute("category", category);
                request.setAttribute("availableTypes", categoryDAO.getAvailableTrapTypes(category.getCategoryId()));
                request.getRequestDispatcher(editElementURL + "?action=" + ACTION_EDIT + "&message=Trap Category added successfully.&action=" + ACTION_ADD).forward(request, response);
                return;
            } else if (action == ACTION_EDIT) {
                TrapCategory category = categoryDAO.editTrapCategory(Integer.parseInt(request.getParameter("categoryId")), request.getParameter("categoryName"), request.getParameter("description"), request.getParameter("colorCode"), request.getParameterValues("selectedTypes"));
                request.setAttribute("category", category);
                request.setAttribute("availableTypes", categoryDAO.getAvailableTrapTypes(category.getCategoryId()));
                request.getRequestDispatcher(editElementURL + "?message=Changes saved successfully.&action=" + ACTION_EDIT).forward(request, response);
                return;
            } else if (action == ACTION_POPULATE) {
                TrapCategory category = categoryDAO.populateTrapCategory(Integer.parseInt(request.getParameter("categoryId")));
                request.setAttribute("category", category);
                request.setAttribute("availableTypes", categoryDAO.getAvailableTrapTypes(category.getCategoryId()));
                request.getRequestDispatcher(editElementURL + "?action=" + ACTION_EDIT).forward(request, response);
                return;
            } else if (action == ACTION_DELETE) {
                categoryDAO.deleteTrapCategory(Integer.parseInt(request.getParameter("categoryId")));
                request.getRequestDispatcher(managerURL + "?message=Trap Category deleted successfully.").forward(request, response);
                return;
            } else if (action == ACTION_DELETE_ALL) {
                categoryDAO.deleteTrapCategories();
                request.getRequestDispatcher(managerURL + "?message=All Trap Categories deleted successfully.").forward(request, response);
                return;
            } else {
                request.getRequestDispatcher(managerURL + "?error=Unknown Operation Request. Please try again.").forward(request, response);
                return;
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            if (action != ACTION_DELETE && action != ACTION_DELETE_ALL) {
                request.getRequestDispatcher(editElementURL + "?error=" + ex.toString()).forward(request, response);
            } else {
                request.getRequestDispatcher(managerURL + "?error=" + ex.toString()).forward(request, response);
            }
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
