/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.oamp.fm;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import org.ict.oamp.dao.BaseDAO;
import org.ict.util.CommonFunctions;
import org.ict.util.Debug;
import org.ict.util.ResultSetObject;

/**
 *
 * @author Alam Sher
 */
public class TrapCategoryDAO extends BaseDAO {

    public void addTrapCategory(String categoryName, String description, String colorCode, String[] trap_types) throws Exception {
        try {
            connectDatabase();
            Properties props = new Properties();
            props.put("category_name", CommonFunctions.quotedString(categoryName));
            props.put("description", CommonFunctions.quotedString(description));
            props.put("color_code", CommonFunctions.quotedString(colorCode));
            dbManager.insertSql("tbl_trap_categories", props);
            dbManager.commitTransaction();
            TrapCategory category = getTrapCategory(categoryName);
            updateTrapTypes(category.getCategoryId(), trap_types);
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "addTrapCategory(...)", exception));
        } finally {
            disconnectDatabase();
        }
    }

    public TrapCategory getTrapCategory(int categoryId) throws Exception {
        TrapCategory category = new TrapCategory();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_trap_categories", "*", "category_id = " + categoryId, "category_name");
            if (rs.next()) {
                category.setCategoryId(categoryId);
                category.setCategoryName(rs.getRs().getString("category_name"));
                category.setDescription(rs.getRs().getString("description"));
                category.setColorCode(rs.getRs().getString("color_code"));
                category.setTraps(getAssociatedTrapTypes(categoryId));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getTrapCategory(categoryId)", exception));
        } finally {
            disconnectDatabase();
        }
        return category;
    }

    public TrapCategory getTrapCategory(String categoryName) throws Exception {
        TrapCategory category = new TrapCategory();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_trap_categories", "*", "category_name = " + CommonFunctions.quotedString(categoryName), "category_name");
            if (rs.next()) {
                category.setCategoryId(rs.getRs().getInt("category_id"));
                category.setCategoryName(categoryName);
                category.setDescription(rs.getRs().getString("description"));
                category.setColorCode(rs.getRs().getString("color_code"));
                category.setTraps(getAssociatedTrapTypes(category.getCategoryId()));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getTrapCategory(categoryName)", exception));
        } finally {
            disconnectDatabase();
        }
        return category;
    }

    public Vector getTrapCategories() throws Exception {
        Vector categories = new Vector();
        try {
            connectDatabase();
            ResultSetObject rs = dbManager.queryNew("tbl_trap_categories", "*", null, "category_name");
            while (rs.next()) {
                TrapCategory category = new TrapCategory();
                category.setCategoryId(rs.getRs().getInt("category_id"));
                category.setCategoryName(rs.getRs().getString("category_name"));
                category.setDescription(rs.getRs().getString("description"));
                category.setColorCode(rs.getRs().getString("color_code"));
                category.setTraps(getAssociatedTrapTypes(category.getCategoryId()));
                categories.add(category);
            }
            rs.close();
            return categories;
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getTrapCategories()", exception));
        } finally {
            disconnectDatabase();
        }
    }

    public void deleteTrapCategory(int categoryId) throws Exception {
        try {
            connectDatabase();
            dbManager.deleteSql("tbl_trap_categories", "category_id = " + categoryId);
            dbManager.commitTransaction();
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "deleteTrapCategory(categoryID)", e));
        } finally {
            disconnectDatabase();
        }
    }

    public void deleteTrapCategories() throws Exception {
        try {
            connectDatabase();
            dbManager.deleteSql("tbl_trap_categories", null);
            dbManager.commitTransaction();
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "deleteTrapCategories()", e));
        } finally {
            disconnectDatabase();
        }
    }

    public TrapCategory editTrapCategory(int categoryId, String categoryName, String description, String colorCode, String[] trapTypes) throws Exception {
        try {
            connectDatabase();
            Properties props = new Properties();
            props.put("category_name", CommonFunctions.quotedString(categoryName));
            props.put("description", CommonFunctions.quotedString(description));
            props.put("color_code", CommonFunctions.quotedString(colorCode));
            dbManager.updateSql("tbl_trap_categories", props, "category_id=" + categoryId);
            dbManager.commitTransaction();
            return updateTrapTypes(categoryId, trapTypes);
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "editTrapCategory(...)", e));
        } finally {
            disconnectDatabase();
        }
    }

    public TrapCategory updateTrapTypes(int categoryId, String[] trapTypes) throws Exception {
        try {
            connectDatabase();
            dbManager.deleteSql("tbl_trap_category_mapping", "category_id=" + categoryId);
            for(int i=0; i<trapTypes.length; i++) {
                Properties props = new Properties();
                props.put("category_id", categoryId);
                props.put("trap_type", CommonFunctions.quotedString(trapTypes[i]));
                dbManager.insertSql("tbl_trap_category_mapping", props);
            }
            dbManager.commitTransaction();
            return getTrapCategory(categoryId);
        } catch (Exception e) {
            Debug.printStackTrace(e);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "updateTrapTypes(...)", e));
        } finally {
            disconnectDatabase();
        }
    }


    public TrapCategory populateTrapCategory(int categoryId) throws Exception {
        TrapCategory category = new TrapCategory();
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("tbl_trap_categories", "*", "category_id = " + categoryId, null);
            if (rs.next()) {
                category.setCategoryId(categoryId);
                category.setCategoryName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                category.setColorCode(rs.getString("color_code"));
                category.setTraps(getAssociatedTrapTypes(categoryId));
            }
            rs.close();
        } catch (Exception exception) {
            Debug.printStackTrace(exception);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "populateTrapCategory(categoryId)", exception));
        } finally {
            disconnectDatabase();
        }
        return category;
    }

    public Vector<String> getAssociatedTrapTypes(int categoryId) throws Exception {
        Vector<String> trapTypes = new Vector<String>();
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("tbl_trap_category_mapping", "*", "category_id=" + categoryId, null);
            while(rs.next()) {
                trapTypes.add(rs.getString("trap_type"));
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getAssociatedTrapTypes(categoryId)", ex));
        } finally {
            disconnectDatabase();
        }
        return trapTypes;
    }

    public Vector<String> getAvailableTrapTypes(int categoryId) throws Exception {
        Vector<String> trapTypes = new Vector<String>();
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("trap_table", "distinct (trap_type)", null, null);
            while(rs.next()) {
                trapTypes.add(rs.getString("trap_type"));
            }
            Vector<String> associatedTypes = getAssociatedTrapTypes(categoryId);
            for(int i=0; i<associatedTypes.size(); i++) {
                if(trapTypes.contains(associatedTypes.get(i))) {
                    trapTypes.removeElement(associatedTypes.get(i));
                }
            }
            if(trapTypes.size() > 0) {
                connectDatabase();
                String whereClause = "trap_type in(";
                for(int i=0; i<trapTypes.size(); i++) {
                    whereClause += CommonFunctions.quotedString(trapTypes.get(i));
                    if(i < trapTypes.size() - 1) {
                        whereClause += ",";
                    }
                }
                whereClause += ")";
                ResultSet rs2 = dbManager.query("tbl_trap_category_mapping", "trap_type", whereClause, "trap_type");
                while(rs2.next()) {
                    String type = rs2.getString("trap_type");
                    if(trapTypes.contains(type)) {
                        trapTypes.removeElement(type);
                    }
                }
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getAssociatedTrapTypes(categoryId)", ex));
        } finally {
            disconnectDatabase();
        }
        return trapTypes;
    }

    public Vector<String> getAvailableTrapTypes() throws Exception {
        Vector<String> trapTypes = new Vector<String>();
        try {
            connectDatabase();
            ResultSet rs = dbManager.query("trap_table", "distinct (trap_type)", null, null);
            while(rs.next()) {
                trapTypes.add(rs.getString("trap_type"));
            }
            rs.close();
            if(trapTypes.size() > 0) {
                String whereClause = "trap_type in(";
                for(int i=0; i<trapTypes.size(); i++) {
                    whereClause += CommonFunctions.quotedString(trapTypes.get(i));
                    if(i < trapTypes.size() - 1) {
                        whereClause += ",";
                    }
                }
                whereClause += ")";
                rs = dbManager.query("tbl_trap_category_mapping", "trap_type", whereClause, "trap_type");
                while(rs.next()) {
                    if(trapTypes.contains(rs.getString("trap_type"))) {
                        trapTypes.removeElement(rs.getString("trap_type"));
                    }
                }
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getAssociatedTrapTypes()", ex));
        } finally {
            disconnectDatabase();
        }
        return trapTypes;
    }

    public Hashtable<String, String> getColorTable() throws Exception {
        Hashtable<String, String> table = new Hashtable<String, String>();
        try {
            Vector<TrapCategory> categories = getTrapCategories();
            for(int i=0; i<categories.size(); i++) {
                Vector<String> trapTypes = getAssociatedTrapTypes(categories.get(i).getCategoryId());
                for(int y=0; y<trapTypes.size(); y++) {
                    table.put(trapTypes.get(y), categories.get(i).getColorCode());
                }
            }
        } catch (Exception ex) {
            Debug.printStackTrace(ex);
            throw new Exception(CommonFunctions.prepareErrorMessage(this.getClass().getName(), "getColorTable()", ex));
        } finally {
            disconnectDatabase();
        }
        return table;
    }
}
