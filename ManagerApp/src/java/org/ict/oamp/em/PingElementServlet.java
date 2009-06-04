/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.em;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ict.oamp.manager.OAMPManager;

/**
 *
 * @author Alam Sher
 */
public class PingElementServlet extends OAMPManager {
   
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
            int elementId;
            int index;
            elementId = Integer.parseInt(request.getParameter("elementId"));
            index = Integer.parseInt(request.getParameter("index"));
            boolean result = true/*OAMPManager.getRegisteredElement(elementId).ping()*/;
            out.print(getResponseHTML(index, result));
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            out.close();
        }
    }

    private String getResponseHTML(int index, boolean status) {
        String response = "";
            response += "<html>";
            response += "<head>";
            response += "</head>";
            response += "<body>";
            response += "<script>";
            response += "parent.updateStatus(" + index + "," + status + ");";
            response += "</script>";
            response += "</body>";
            response += "</html>";
        return response;
    }
}
