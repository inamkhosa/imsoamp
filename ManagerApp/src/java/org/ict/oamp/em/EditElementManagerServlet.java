/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.em;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.ict.oamp.manager.ElementCategory;
import org.ict.oamp.manager.ElementManager;
import org.ict.oamp.mib.MibFile;
import org.ict.oamp.manager.OAMPManager;
import org.ict.oamp.mib.MibFileDAO;
import org.ict.util.CommonFunctions;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class EditElementManagerServlet extends OAMPManager {

    public static final int ACTION_UNKNOWN = -1;
    public static final int ACTION_ADD = 0;
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_DELETE = 2;
    public static final int ACTION_DELETE_ALL = 3;
    public static final int ACTION_POPULATE = 4;
    public static final int ACTION_ADD_MIB = 5;
    public static final int ACTION_DELETE_MIB = 6;
    public static final int ACTION_DELETE_ALL_MIBS = 7;

    public String managerURL = "/secure/elementmanager";
    public String editElementURL = "/secure/em/EditElement.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int action = ACTION_UNKNOWN;
        try {
            action = Integer.parseInt(request.getParameter("action"));
        } catch (Exception ex) {
            action = ACTION_UNKNOWN;
        }
        try {
            ElementCategoryDAO categoryDAO = new ElementCategoryDAO();
            request.setAttribute("categories", categoryDAO.getElementCategories());
            ElementDAO elementDAO = new ElementDAO();
            if (action == ACTION_ADD) {
                elementDAO.addElementManager(request.getParameter("elementIdentifier"), request.getParameter("destAddress"), request.getParameter("secName"), request.getParameter("authProtocol"), request.getParameter("authPassword"), request.getParameter("privProtocol"), request.getParameter("privPassword"), request.getParameter("category"));
                ElementManager element = elementDAO.getElementManager(request.getParameter("elementIdentifier"));
                request.setAttribute("element", element);
                OAMPManager.registerElement(element);
                request.getRequestDispatcher(editElementURL + "?action=" + ACTION_EDIT + "&message=Element added successfully.&action=" + ACTION_ADD).forward(request, response);
                return;
            } else if (action == ACTION_EDIT) {
                ElementManager elementManager = elementDAO.editElementManager(Integer.parseInt(request.getParameter("elementId")), request.getParameter("elementIdentifier"), request.getParameter("destAddress"), request.getParameter("secName"), request.getParameter("authProtocol"), request.getParameter("authPassword"), request.getParameter("privProtocol"), request.getParameter("privPassword"), request.getParameter("category"));
                request.setAttribute("element", elementManager);
                OAMPManager.updateRegisteredElement(elementManager);
                request.getRequestDispatcher(editElementURL + "?message=Changes saved successfully.&action=" + ACTION_EDIT).forward(request, response);
                return;
            } else if (action == ACTION_POPULATE) {
                ElementManager elementManager = elementDAO.populateElementManager(Integer.parseInt(request.getParameter("elementId")));
                request.setAttribute("element", elementManager);
                request.getRequestDispatcher(editElementURL + "?action=" + ACTION_EDIT).forward(request, response);
                return;
            } else if (action == ACTION_DELETE) {
                elementDAO.deleteElementManager(Integer.parseInt(request.getParameter("elementId")));
                OAMPManager.unregisterElement(Integer.parseInt(request.getParameter("elementId")));
                request.getRequestDispatcher(managerURL + "?message=Element deleted successfully.").forward(request, response);
                return;
            } else if (action == ACTION_DELETE_ALL) {
                elementDAO.deleteElementManagers();
                OAMPManager.deleteAllElements();
                request.getRequestDispatcher(managerURL + "?message=All elements deleted successfully.").forward(request, response);
                return;
            } else if (action == ACTION_ADD_MIB) {
                int elementId = Integer.parseInt(request.getParameter("elementId"));
                String[] mibFiles = request.getParameterValues("selectedMibs");
                for(int i=0; i<mibFiles.length; i++) {
                    String mibName = mibFiles[i].split(",")[0];
                    String mibFile = mibFiles[i].split(",")[1];
                    elementDAO.addMibFile(elementId, mibName, mibFile);
                }
                request.setAttribute("element", OAMPManager.getRegisteredElement(elementId));
                request.getRequestDispatcher(editElementURL + "?message=Mib file addedd successfully.").forward(request, response);
                return;
            } else if (action == ACTION_DELETE_MIB) {
//                int elementId = Integer.parseInt(request.getParameter("elementId"));
//                int mibFileId = Integer.parseInt(request.getParameter("mibFileId"));
//                MibFileDAO mibFileDAO = new MibFileDAO();
//                mibFileDAO.deleteMibFile(mibFileId, elementId);
//                ElementManager elementManager = elementDAO.populateElementManager(elementId);
//                ElementDAO dao = new ElementDAO();
//                dao.loadMibFiles(elementManager, elementManager.getMibFiles(), getServletContext().getRealPath("/") + "/secure/mibs/");
//                request.setAttribute("element", elementManager);
//                OAMPManager.updateRegisteredElement(elementManager);
//                request.getRequestDispatcher(editElementURL + "?message=Mib file deleted successfully.").forward(request, response);
            } else if (action == ACTION_DELETE_ALL_MIBS) {
//                int elementId = Integer.parseInt(request.getParameter("elementId"));
//                MibFileDAO mibFileDAO = new MibFileDAO();
//                mibFileDAO.deleteMibFiles(elementId);
//                ElementManager elementManager = elementDAO.populateElementManager(elementId);
//                request.setAttribute("element", elementManager);
//                OAMPManager.updateRegisteredElement(elementManager);
//                request.getRequestDispatcher(editElementURL + "?message=Mib file(s) deleted successfully.").forward(request, response);
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
