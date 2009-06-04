/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.mib;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
import org.ict.oamp.manager.OAMPManager;
import org.ict.oamp.trapserver.util.Debug;
import org.ict.util.FileManager;

/**
 *
 * @author Alam Sher
 */
public class MibManagerServlet extends OAMPManager {

    private final String forwardURL = "/secure/mm/MibManager.jsp";
    public static final int ACTION_ADD_MIB = 0;
    public static final int ACTION_DELETE_MIB = 1;
    public static final int ACTION_SHOW_MIBS = 2;
    public static final int ACTION_DELETE_ALL_MIB = 3;

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
            request.setAttribute("mibFiles", getLoadedMibs());
            int action = ACTION_SHOW_MIBS;
            if (request.getParameter("action") != null) {
                try {
                    action = Integer.parseInt(request.getParameter("action"));
                } catch (Exception ex) {
                }
            }
            if (ServletFileUpload.isMultipartContent(request)) {
                action = ACTION_ADD_MIB;
            }
            if (action == ACTION_ADD_MIB) {
                FileItem fileItem = null;
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem currentItem = iter.next();
                    if (!currentItem.isFormField()) {
                        fileItem = currentItem;
                        break;
                    }
                }
                if (fileItem != null) {
                    String mibFilesDir = getServletContext().getRealPath("/") + "/secure/mibs/";
//                    if (FileManager.fileExists(mibFilesDir + fileItem.getName())) {
//                        request.getRequestDispatcher(forwardURL + "?error=" + "A Mib with same file name already exists on the server.").forward(request, response);
//                        return;
//                    }
                    File uploadedFile = FileManager.uploadFile(fileItem, mibFilesDir, null);
                    snmpManager.getMibModule().loadMib(uploadedFile);
                    request.setAttribute("mibFiles", getLoadedMibs());
                    request.getRequestDispatcher(forwardURL + "?message=Mib file [" + uploadedFile.getName() + "] loaded successfully.").forward(request, response);
                    return;
                } else {
                    request.getRequestDispatcher(forwardURL + "?error=" + "Unknown or invalid request. Please try again.").forward(request, response);
                    return;
                }
            } else if (action == ACTION_DELETE_MIB) {
                String mibFile = request.getParameter("file");
                String mibName = request.getParameter("mib");
                if(mibName != null) {
                    String mibFilesDir = getServletContext().getRealPath("/") + "/secure/mibs/";
                    snmpManager.getMibModule().unloadMib(mibName);
                    if(FileManager.fileExists(mibFilesDir + mibFile)) {
                        FileManager.deleteFile(mibFilesDir + mibFile);
                    }
                    request.setAttribute("mibFiles", getLoadedMibs());
                    request.getRequestDispatcher(forwardURL + "?message=Mib file [" + mibFile + "] deleted successfully.").forward(request, response);
                    return;
                } else {
                    request.getRequestDispatcher(forwardURL + "?error=Unkown file or invalid requset. Please try again.").forward(request, response);
                    return;
                }
            } else if (action == ACTION_DELETE_ALL_MIB) {
                Vector<MibFile> mibFiles = getLoadedMibs();
                snmpManager.getMibModule().unloadAllMibs();
                String mibFilesDir = getServletContext().getRealPath("/") + "/secure/mibs/";
                for(int i=0; i<mibFiles.size(); i++) {
                    if(FileManager.fileExists(mibFilesDir + mibFiles.get(i).getFileName())) {
                        FileManager.deleteFile(mibFilesDir + mibFiles.get(i).getFileName());
                    }
                }
                request.setAttribute("mibFiles", getLoadedMibs());
                request.getRequestDispatcher(forwardURL + "?message=All mibs deleted successfully.").forward(request, response);
                return;
            }
            request.getRequestDispatcher(forwardURL).forward(request, response);
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            request.getRequestDispatcher(forwardURL + "?error=" + ex.toString()).forward(request, response);
        }
    }

    private Vector<MibFile> getLoadedMibs() throws Exception {
        Vector<MibFile> mibFiles = snmpManager.getMibModule().getLoadedMibFiles();
        Collections.sort(mibFiles, new Comparator() {

            public int compare(Object o1, Object o2) {
                MibFile file1 = (MibFile) o1;
                MibFile file2 = (MibFile) o2;
                return file1.getMibName().compareTo(file2.getMibName());
            }
        });
        return mibFiles;
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
