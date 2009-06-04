/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.pm;

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
public class PMStatsReportServlet extends OAMPManager {

    public static String forwardURL = "/secure/pm/PerformanceStatsReport.jsp";

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
            int elementId = -1;
            try {
                elementId = Integer.parseInt(request.getParameter("elementId"));
            } catch (Exception ex) {
                request.getRequestDispatcher(forwardURL + "?error=No element found in request").forward(request, response);
                return;
            }
            String[] selectedOids = request.getParameterValues("chkVariables");
            if (selectedOids == null || selectedOids.length == 0) {
                request.getRequestDispatcher(forwardURL + "?error=No variables found in request").forward(request, response);
                return;
            }
            boolean cached = true;
            if(request.getParameter("cached") != null && request.getParameter("cached").equalsIgnoreCase("1")) {
                cached = false;
            }
            PerformanceDAO pmDAO = new PerformanceDAO();
            if(cached) {
                Vector <VarInfo> variables = pmDAO.getCachedStats(elementId, selectedOids);
                request.setAttribute("variables", variables);
            } else {
                Vector <VarInfo> variables = pmDAO.getLiveStats(elementId, selectedOids);
                request.setAttribute("variables", variables);
            }
            request.getRequestDispatcher(forwardURL).forward(request, response);
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

    private boolean getLiveStats(int elementId, HttpServletRequest request, HttpServletResponse response, String[] selectedOids) throws IOException, ServletException {
        ElementManager element = getRegisteredElement(elementId);
        if (element == null) {
            try {
                elementId = Integer.parseInt(request.getParameter("elementId"));
            } catch (Exception ex) {
                request.getRequestDispatcher(forwardURL + "?error=Unknown/Un-registered element found in request").forward(request, response);
                return true;
            }
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
//        Vector<VarInfo> result = element.UpdateVariables(selectedVariables);
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i).getTag() + "-" + result.get(i).getValue());
//        }
        return false;
    }
}
