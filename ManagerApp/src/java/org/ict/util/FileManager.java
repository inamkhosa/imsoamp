package org.ict.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileManager {

    private String separator = java.io.File.separator;
    private static String exceptionMessage = new String();
    public static final int PATTERN_NONE = 0;
    public static final int PATTERN_STARTS_WITH = 1;
    public static final int PATTERN_ENDS_WITH = 2;
    public static final int PATTERN_CONTAINS = 3;

    /**Creates a new folder
     * @param folderName The name of the folder to be created.
     * @return <code>true</code> if the folder is created successfully;
     * <code>false</code> otherwise
     * */
    public static synchronized boolean createFolder(String folderName) {
        java.io.File objFile = new java.io.File(folderName);
        if (!objFile.isDirectory()) {
            return objFile.mkdirs();
        }
        return true;
    }

    public static synchronized boolean fileExists(String fileName) {
        if (!new java.io.File(fileName).exists()) {
            return false;
        }
        return true;
    }

    public static synchronized boolean folderExists(String folderName) {
        if (!new java.io.File(folderName).exists()) {
            return false;
        }
        return true;
    }

    public static synchronized long getFileSize(String fileName) {
        return new java.io.File(fileName).length();
    }

    public static synchronized boolean copyFile(String fileName,
            String pathName) throws java.io.IOException {
        boolean isCopy = false;
        try {
            if (!new java.io.File(pathName).exists()) {
                if (!new java.io.File(pathName).mkdirs()) {
                    return false;
                }
            }
            java.io.FileInputStream fin = new java.io.FileInputStream(fileName);
            String tempPath = fileName.substring(fileName.lastIndexOf(
                    getPathSeparator()) + 1, fileName.length());
            pathName += getPathSeparator();
            pathName += tempPath;
            java.io.FileOutputStream fout = new java.io.FileOutputStream(
                    pathName);
            java.nio.channels.FileChannel inc = fin.getChannel();
            java.nio.channels.FileChannel outc = fout.getChannel();
            //inc.transferTo(0, inc.size(), outc);
            java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocateDirect((int) inc.size());
            int ret = inc.read(buffer);
            if (ret == -1) {
                isCopy = false;
            }
            buffer.flip();
            outc.write(buffer);
            fout.close();
            fin.close();
            isCopy = true;
            buffer = null;
        } catch (java.io.IOException e) {
            isCopy = false;
            exceptionMessage = e.getMessage();
        }
        return isCopy;
    }

    /**Deletes a file.
     * @param fileName The name of the file to be deleted.
     * @return <code>true</code> if the file is deleted successfully;
     * <code>false</code> otherwise.
     * */
    public static synchronized boolean deleteFile(String fileName) {
        return new java.io.File(fileName).delete();
    }

    /**Moves a file to specified path.
     * @param fileName The name of the file to be moved.
     * @param pathName The pathname where the file is to be moved.
     * @return <code>true</code> if the file is moved successfully;
     * <code>false</code> otherwise.
     * */
    public static synchronized boolean moveFile(String fileName,
            String pathName) {
        try {
            return (copyFile(fileName, pathName) && deleteFile(fileName));
        } catch (java.io.IOException ex) {
            exceptionMessage = ex.getMessage();
            return false;
        }
    }

    /**Write data to the given file.
     * @param fileName The name of the file in which the data is to be written.
     * @param data The data to be written.
     * @param append Boolean value indicating that whether to append the data
     * in the file.
     * @return <code>true</code> if the data is written successfully in the file;
     * <code>false</code> otherwise.
     * */
    public static synchronized boolean writeToFile(String fileName, String data,
            boolean append) {
        boolean isWrite = false;
        int dirIndex = fileName.lastIndexOf(getPathSeparator());
        if (dirIndex != -1) {
            String dir = fileName.substring(0, dirIndex) + getPathSeparator();
            java.io.File fDir = new java.io.File(dir);
            if (!fDir.exists()) {
                if (!fDir.mkdirs()) {
                    return false;
                }
            }
        }
        try {
            java.io.FileOutputStream fout = new java.io.FileOutputStream(
                    fileName, append);
            java.nio.channels.FileChannel fChannelWriter = fout.getChannel();
            byte[] bytesToWrite = data.getBytes();
            java.nio.ByteBuffer bBuffW = java.nio.ByteBuffer.wrap(bytesToWrite);
            fChannelWriter.write(bBuffW);
            fChannelWriter.close();
            fout.close();
            isWrite = true;
        } catch (java.io.IOException ex) {
            exceptionMessage = ex.getMessage();
            isWrite = false;
        }
        return isWrite;
    }

    /**Reads the contents of the file and returns the contents in the form of
     * String.
     * @param fileName The name of the file from which the data is to be read.
     * @return <code>String</code> containing the contents of the file.
     * */
    public static synchronized String readFile(String fileName) {
        String strData = "";
        if (!new java.io.File(fileName).exists()) {
            return strData;
        }
        try {
            java.io.FileInputStream fin = new java.io.FileInputStream(fileName);
            java.nio.channels.FileChannel fChannelReader = fin.getChannel();
            java.nio.ByteBuffer readBuffer = java.nio.ByteBuffer.allocateDirect((int) fChannelReader.size());
            fChannelReader.read(readBuffer);
            byte[] bytesRead = new byte[(int) fChannelReader.size()];
            readBuffer.position(0);
            readBuffer.get(bytesRead, 0, readBuffer.limit());
            strData = new String(bytesRead);
            fChannelReader.close();
            readBuffer.clear();
            fin.close();
            readBuffer = null;
            fChannelReader = null;
            fin = null;
        } catch (java.io.IOException ex) {
            exceptionMessage = ex.getMessage();
//      System.out.println("Error Occured: Unable to read from file (" + fileName + ")");
        }
        return strData;
    }

    /**Returns the path Separator.
     * @return <code>String</code> the path separator character.
     * */
    public static String getPathSeparator() {
        return java.io.File.separator;
    }

    /**Rename the file.
     * @param targetFileName The file to be renamed.
     * @param destFileName The new name of the file.
     * @return <code>true</code> if the file is renamed successfully;
     * <code>false</code> otherwise.
     * */
    public static synchronized boolean renameFile(String targetFileName,
            String destFileName) {
        java.io.File targetFile = new java.io.File(targetFileName);
        java.io.File destinationFileName = new java.io.File(destFileName);
        if (!targetFile.exists()) {
            return false;
        }
        return targetFile.renameTo(destinationFileName);
    }

    public static synchronized java.io.File[] getDirectoryContents(String directory) {
        java.io.File file = new java.io.File(directory);
        java.io.File[] directoryContents = file.listFiles();
        return directoryContents;
    }

    public static synchronized java.io.File[] getFilesList(String directory) {
        java.io.File file = new java.io.File(directory);
        java.io.File[] directoryContents = null;
        directoryContents = file.listFiles(new FilenameFilter() {

            public boolean accept(java.io.File dir, String name) {
                try {
                    if (new java.io.File(dir.getCanonicalPath() +
                            java.io.File.separator + name).isFile()) {
                        return true;
                    }
                } catch (IOException ex) {
                    exceptionMessage = ex.getMessage();
                    return false;
                }
                return false;
            }
        });
        return directoryContents;
    }

    public static synchronized String[] getFiles(String directory) {
        java.io.File file = new java.io.File(directory);
        String[] directoryContents = null;
        directoryContents = file.list(new FilenameFilter() {

            public boolean accept(java.io.File dir, String name) {
                try {
                    if (new java.io.File(dir.getCanonicalPath() +
                            java.io.File.separator + name).isFile()) {
                        return true;
                    }
                } catch (IOException ex) {
                    exceptionMessage = ex.getMessage();
                    return false;
                }
                return false;
            }
        });
        return directoryContents;
    }

    public static synchronized long getFileCount(String directory) {
        String[] listFiles = getFiles(directory);
        if (listFiles == null) {
            return 0;
        }
        return listFiles.length;
    }

    public static synchronized String[] getFiles(String directory,
            int patternType,
            String pattern) {
        final int pattType = patternType;
        final String patt = pattern;
        java.io.File file = new java.io.File(directory);
        String[] directoryContents = null;
        directoryContents = file.list(new FilenameFilter() {

            public boolean accept(java.io.File dir, String name) {
                try {
                    if (new java.io.File(dir.getCanonicalPath() +
                            java.io.File.separator + name).isFile()) {
                        boolean isSuccessfull = false;
                        switch (pattType) {
                            case PATTERN_NONE:
                                isSuccessfull = true;
                                break;
                            case PATTERN_STARTS_WITH:
                                isSuccessfull = name.startsWith(patt);
                                break;
                            case PATTERN_ENDS_WITH:
                                isSuccessfull = name.endsWith(patt);
                                break;
                            case PATTERN_CONTAINS:
                                isSuccessfull = (name.indexOf(patt) != -1);
                                break;
                        }
                        return isSuccessfull;
                    }
                } catch (IOException ex) {
                    exceptionMessage = ex.getMessage();
                    return false;
                }
                return false;
            }
        });
        return directoryContents;
    }

    public static synchronized java.io.File[] getDirectoyList(String directory) {
        java.io.File file = new java.io.File(directory);
        java.io.File[] directoryList = file.listFiles(new FilenameFilter() {

            public boolean accept(java.io.File dir, String name) {
                try {
                    if (new java.io.File(dir.getCanonicalPath() +
                            java.io.File.separator + name).isDirectory()) {
                        return true;
                    }
                } catch (IOException ex) {
                    exceptionMessage = ex.getMessage();
                }
                return false;
            }
        });
        return directoryList;
    }

    public static synchronized String getExceptionMessage() {
        return exceptionMessage;
    }

    public static Vector uploadFiles(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) throws Exception {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        Vector files = new Vector();
        try {
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    if (fileName == null || fileName.trim().length() == 0) {
                        fileName = item.getName();
                    }
                    File uploadedFile = new File(filePath + fileName);
                    Debug.print(uploadedFile.getAbsolutePath());
                    item.write(uploadedFile);
                    files.add(uploadedFile);
                }
            }
            return files;
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage("org.ict.util.FileManager", "uploadFiles(request,response,path,fileName)", ex));
        }
    }

    public static File uploadFile(FileItem fileItem, String filePath, String fileName) throws Exception {
        try {
            if (fileName == null || fileName.trim().length() == 0) {
                fileName = fileItem.getName();
            }
            File uploadedFile = new File(filePath + fileName);
            Debug.print(uploadedFile.getAbsolutePath());
            fileItem.write(uploadedFile);
            return uploadedFile;
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage("org.ict.util.FileManager", "uploadFile(fileItem,filePath,fileName)", ex));
        }
    }
}
