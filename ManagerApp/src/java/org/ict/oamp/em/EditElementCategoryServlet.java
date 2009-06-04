/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.em;

import org.ict.oamp.manager.ElementCategory;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.ict.oamp.manager.ElementManager;
import org.ict.oamp.manager.OAMPManager;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class EditElementCategoryServlet extends OAMPManager {

    public static final int ACTION_UNKNOWN = -1;
    public static final int ACTION_ADD = 0;
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_DELETE = 2;
    public static final int ACTION_DELETE_ALL = 3;
    public static final int ACTION_POPULATE = 4;
    public static final int ACTION_ADD_MIB = 5;
    public static final int ACTION_DELETE_MIB = 6;
    public static final int ACTION_DELETE_ALL_MIBS = 7;
    public String managerURL = "/secure/elementcategorymanager";
    public String editElementURL = "/secure/em/EditElementCategory.jsp";

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
        String categoryName = null;
        String description = null;
        FileItem iconFile = null;
        int categoryId = -1;
        try {
            if (ServletFileUpload.isMultipartContent(request)) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        if (item.getFieldName().equalsIgnoreCase("categoryName")) {
                            categoryName = item.getString();
                        } else if (item.getFieldName().equalsIgnoreCase("description")) {
                            description = item.getString();
                        } else if (item.getFieldName().equalsIgnoreCase("categoryId")) {
                            if(action != ACTION_ADD){
                                categoryId = Integer.parseInt(item.getString());
                            }
                        }
                    } else {
                        iconFile = item;
                    }
                }
            }
            ElementCategoryDAO categoryDAO = new ElementCategoryDAO();
            if (action == ACTION_ADD) {
                categoryDAO.addElementCategory(categoryName, description);
                ElementCategory category = categoryDAO.getElementCategory(categoryName);
                categoryDAO.addIconFile(iconFile, getServletContext().getRealPath("/") + "/secure/icons/", category);
                request.setAttribute("category", category);
                request.getRequestDispatcher(editElementURL + "?action=" + ACTION_EDIT + "&message=Element Category added successfully.&action=" + ACTION_ADD).forward(request, response);
                return;
            } else if (action == ACTION_EDIT) {
                ElementCategory category = categoryDAO.editElementCategory(categoryId, categoryName, description);
                if (iconFile != null) {
                    categoryDAO.addIconFile(iconFile, getServletContext().getRealPath("/") + "/secure/icons/", category);
                }
                request.setAttribute("category", category);
                request.getRequestDispatcher(editElementURL + "?message=Changes saved successfully.&action=" + ACTION_EDIT).forward(request, response);
                return;
            } else if (action == ACTION_POPULATE) {
                ElementCategory category = categoryDAO.populateElementCategory(Integer.parseInt(request.getParameter("categoryId")));
                request.setAttribute("category", category);
                request.getRequestDispatcher(editElementURL + "?action=" + ACTION_EDIT).forward(request, response);
                return;
            } else if (action == ACTION_DELETE) {
                ElementDAO elementDAO = new ElementDAO();
                Vector<ElementManager> elements = elementDAO.getElementManagers(Integer.parseInt(request.getParameter("categoryId")));
                categoryDAO.deleteElementCategory(Integer.parseInt(request.getParameter("categoryId")));
                for (int i = 0; i < elements.size(); i++) {
                    OAMPManager.unregisterElement(elements.get(i).getElementId());
                }
                request.getRequestDispatcher(managerURL + "?message=Element Category deleted successfully.").forward(request, response);
                return;
            } else if (action == ACTION_DELETE_ALL) {
                categoryDAO.deleteElementCategories();
                OAMPManager.deleteAllElements();
                request.getRequestDispatcher(managerURL + "?message=All Element Categories deleted successfully.").forward(request, response);
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
