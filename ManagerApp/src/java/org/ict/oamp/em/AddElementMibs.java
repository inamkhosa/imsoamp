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
import org.ict.oamp.mib.MibFileDAO;
import org.ict.oamp.mib.MibFileType;
import org.ict.util.Debug;

/**
 *
 * @author Alam Sher
 */
public class AddElementMibs extends OAMPManager {

    private static String forwardURL = "/secure/em/AddElementMibs.jsp";

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
        try {
            int elementId = Integer.parseInt(request.getParameter("elementId"));
            if (request.getParameter("action") != null) {
                String action = request.getParameter("action");
                if (action.equalsIgnoreCase("add")) {
                    String mibFiles[] = request.getParameterValues("mibFiles");
                    if (mibFiles != null && mibFiles.length > 0) {
                        MibFileDAO dao = new MibFileDAO();
                        for (int i = 0; i < mibFiles.length; i++) {
                            MibFile mibFile = snmpManager.getMibModule().getLoadedMibFile(mibFiles[i]);
                            dao.addMibFile(elementId, mibFile.getMibName(), mibFile.getFileName(), MibFileType.PERFORMANCE_MIB);
                            mibFile.setElementId(elementId);
                            mibFile.setId(dao.getMibFileId(elementId, mibFile.getMibName()));
                            getRegisteredElement(elementId).addMibFile(mibFile);
                        }
                    }
                    Vector<MibFile> filteredMibFiles = getFilteredMibFiles(elementId);
                    request.setAttribute("mibFiles", filteredMibFiles);
                    request.setAttribute("elementManager", getRegisteredElement(elementId));
                    request.getRequestDispatcher(forwardURL + "?message=Mib(s) added sucessfully.").forward(request, response);
                    return;
                } else if (action.equalsIgnoreCase("remove")) {
                    int mibId = Integer.parseInt(request.getParameter("mibId"));
                    MibFileDAO dao = new MibFileDAO();
                    MibFile mibFile = dao.getMibFile(mibId);
                    dao.deleteMibFile(mibId, elementId);
                    getRegisteredElement(elementId).removeMibFile(mibFile);
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
                    request.getRequestDispatcher("/secure/em/ListElementMibs.jsp").forward(request, response);
                    return;
                } else if (action.equalsIgnoreCase("removeAll")) {
                    MibFileDAO dao = new MibFileDAO();
                    dao.deleteMibFiles(elementId);
                    getRegisteredElement(elementId).removeAllMibFiles();
                    request.setAttribute("elementId", elementId);
                    request.setAttribute("mibFiles", getRegisteredElement(elementId).getMibFiles());
                    request.getRequestDispatcher("/secure/em/ListElementMibs.jsp").forward(request, response);
                    return;
                }
            }
            Vector<MibFile> filteredMibFiles = getFilteredMibFiles(elementId);
            request.setAttribute("mibFiles", filteredMibFiles);
            request.setAttribute("elementManager", getRegisteredElement(elementId));
            request.getRequestDispatcher(forwardURL).forward(request, response);
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            request.getRequestDispatcher(forwardURL + "?error=" + ex.getMessage()).forward(request, response);
        }
    }

    private Vector<MibFile> getFilteredMibFiles(int elementId) {
        Vector<MibFile> alreadyAssociatedMibs = getRegisteredElement(elementId).getMibFiles();
        Vector<MibFile> allMibFiles = snmpManager.getMibModule().getLoadedMibFiles();
        Vector<MibFile> filteredMibFiles = new Vector<MibFile>();
        for (int i = 0; i < allMibFiles.size(); i++) {
            if (!alreadyAssociatedMibs.contains(allMibFiles.get(i))) {
                filteredMibFiles.add(allMibFiles.get(i));
            }
        }
        Collections.sort(filteredMibFiles, new Comparator() {

            public int compare(Object o1, Object o2) {
                MibFile file1 = (MibFile) o1;
                MibFile file2 = (MibFile) o2;
                return file1.getMibName().compareTo(file2.getMibName());
            }
        });
        return filteredMibFiles;
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
