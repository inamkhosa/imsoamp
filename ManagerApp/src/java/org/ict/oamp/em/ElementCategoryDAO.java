/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.em;

import org.ict.oamp.manager.ElementCategory;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.fileupload.FileItem;
import org.ict.oamp.dao.BaseDAO;
import org.ict.util.CommonFunctions;
import org.ict.util.Debug;
import org.ict.util.FileManager;
import org.ict.util.ResultSetObject;

/**
 *
 * @author Alam Sher
 */
public class ElementCategoryDAO extends BaseDAO {

    public void addElementCategory(String categoryName, String description) throws Exception {
        try {
            connectDatabase();
            Properties props = new Properties();
            props.put("category_name", CommonFunctions.quotedString(categoryName));
            props.put("description", CommonFunctions.quotedString(description));
            dbManager.insertSql("tbl_element_categories", props);
            dbManager.commitTransaction();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "addElementCategory(...)", exception));
        } finally {
            disconnectDatabase();
        }
    }

    public ElementCategory getElementCategory(int categoryId) throws Exception {
        ElementCategory category = new ElementCategory();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_element_categories", "*", "category_id = " + categoryId, "category_name");
            if (rs.next()) {
                category.setCategoryId(categoryId);
                category.setCategoryName(rs.getRs().getString("category_name"));
                category.setDescription(rs.getRs().getString("description"));
                ElementDAO elementDAO = new ElementDAO();
                category.setElements(elementDAO.getElementManagers(categoryId));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getElementCategory(categoryId)", exception));
        } finally {
            disconnectDatabase();
        }
        return category;
    }

    public ElementCategory getElementCategory(String categoryName) throws Exception {
        ElementCategory category = new ElementCategory();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_element_categories", "*", "category_name = " + CommonFunctions.quotedString(categoryName), "category_name");
            if (rs.next()) {
                category.setCategoryId(rs.getRs().getInt("category_id"));
                category.setCategoryName(categoryName);
                category.setDescription(rs.getRs().getString("description"));
                ElementDAO elementDAO = new ElementDAO();
                category.setElements(elementDAO.getElementManagers(category.getCategoryId()));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getElementCategory(categoryName)", exception));
        } finally {
            disconnectDatabase();
        }
        return category;
    }

    public Vector getElementCategories() throws Exception {
        Vector categories = new Vector();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_element_categories", "*", null, "category_name");
            while (rs.next()) {
                ElementCategory category = new ElementCategory();
                category.setCategoryId(rs.getRs().getInt("category_id"));
                category.setCategoryName(rs.getRs().getString("category_name"));
                category.setDescription(rs.getRs().getString("description"));
                ElementDAO elementDAO = new ElementDAO();
                category.setElements(elementDAO.getElementManagers(category.getCategoryId()));
                categories.add(category);
            }
            rs.close();
            return categories;
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getElementCategories()", exception));
        } finally {
            disconnectDatabase();
        }
    }

    public void deleteElementCategory(int categoryId) throws Exception {
        try {
            connectDatabase();
            dbManager.deleteSql("tbl_element_categories", "category_id = " + categoryId);
            dbManager.commitTransaction();
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "deleteElementCategory(categoryID)", e));
        } finally {
            disconnectDatabase();
        }
    }

    public void deleteElementCategories() throws Exception {
        try {
            connectDatabase();
            dbManager.deleteSql("tbl_element_categories", null);
            dbManager.commitTransaction();
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "deleteElementCategories()", e));
        } finally {
            disconnectDatabase();
        }
    }

    public ElementCategory editElementCategory(int categoryId, String categoryName, String description) throws Exception {
        try {
            connectDatabase();
            Properties props = new Properties();
            props.put("category_name", CommonFunctions.quotedString(categoryName));
            props.put("description", CommonFunctions.quotedString(description));
            dbManager.updateSql("tbl_element_categories", props, "category_id=" + categoryId);
            dbManager.commitTransaction();
            return getElementCategory(categoryId);
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "editElementCategory(...)", e));
        } finally {
            disconnectDatabase();
        }
    }

    public ElementCategory populateElementCategory(int categoryId) throws Exception {
        ElementCategory category = new ElementCategory();
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("tbl_element_categories", "*", "category_id = " + categoryId, null);
            if (rs.next()) {
                category.setCategoryId(categoryId);
                category.setCategoryName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                ElementDAO elementDAO = new ElementDAO();
                category.setElements(elementDAO.getElementManagers(categoryId));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "populateElementCategory(categoryId)", exception));
        } finally {
            disconnectDatabase();
        }
        return category;
    }

    public void addIconFile(FileItem iconFile, String iconFileDir, ElementCategory category) throws Exception {
        try {
            if (iconFile == null) {
                throw new Exception("No Icon file found in request. Please try again.");
            }
            String fileName = category.getCategoryId() + ".bmp";
            FileManager.uploadFile(iconFile, iconFileDir, fileName);
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "addIconFile(request,response,dir)", exception));
        } finally {
            disconnectDatabase();
        }
    }
}
