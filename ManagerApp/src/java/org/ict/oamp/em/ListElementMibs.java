/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.em;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ict.oamp.manager.OAMPManager;
import org.ict.oamp.mib.MibFile;
import org.ict.oamp.trapserver.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class ListElementMibs extends OAMPManager {

    private static final String forwardURL = "/secure/em/ListElementMibs.jsp";

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
            int elementId = Integer.parseInt(request.getParameter("elementId"));
            Vector<MibFile> mibFiles = OAMPManager.getRegisteredElement(elementId).getMibFiles();
            Collections.sort(mibFiles, new Comparator() {

                public int compare(Object o1, Object o2) {
                    MibFile file1 = (MibFile) o1;
                    MibFile file2 = (MibFile) o2;
                    return file1.getMibName().compareTo(file2.getMibName());
                }
            });
            request.setAttribute("elementId", elementId);
            request.setAttribute("mibFiles", mibFiles);
            request.getRequestDispatcher(forwardURL).forward(request, response);
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            request.getRequestDispatcher(forwardURL + "?error=" + ex.getMessage()).forward(request, response);
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
