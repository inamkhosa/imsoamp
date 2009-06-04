/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.cm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ict.oamp.manager.OAMPManager;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class ConfigurationUpdateServlet extends OAMPManager {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Enumeration parameters = request.getParameterNames();
            int elementId = -1;
            String oid = null;
            String value = null;
            int type = -1;

            while(parameters.hasMoreElements()) {
                String param = parameters.nextElement().toString();
                if(param.contains("elementId")) {
                    elementId = Integer.parseInt(request.getParameter(param));
                } else if (param.contains("tag")) {
                    oid = request.getParameter(param);
                } else if (param.contains("txtValue")) {
                    value = request.getParameter(param);
                } else if (param.contains("type")) {
                    type = Integer.parseInt(request.getParameter(param));
                }
            }
            ConfigurationsDAO dao = new ConfigurationsDAO();
            String updatedValue = dao.updateStats(elementId, oid, type, value);
            if(updatedValue.equalsIgnoreCase(value)) {
                out.println(getResponseHTML("true", updatedValue));
            } else {
                out.println(getResponseHTML("false", "Unable to update selected variable."));
            }
        } catch(Exception ex) {
            Debug.printStackTrace(ex);
            out.println(getResponseHTML("false", ex.toString()));
        } finally {
            out.close();
        }
    }

    private String getResponseHTML(String status, String value) {
        String response = "";
            response += "<html>";
            response += "<head>";
            response += "</head>";
            response += "<body>";
            response += "<script>";
            response += "parent.updateResults(" + status + ",'" + value + "');";
            response += "</script>";
            response += "</body>";
            response += "</html>";
        return response;
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
