/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.fm;

import java.util.Vector;

/**
 *
 * @author Alam Sher
 */
public class TrapCategory {
    private int categoryId;
    private String categoryName;
    private String description;
    private String colorCode;
    
    private Vector<String> trapTypes;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vector<String> getTraps() {
        return trapTypes;
    }

    public void setTraps(Vector<String> trapTypes) {
        this.trapTypes = trapTypes;
    }

    public void addTrap(String trapType) {
        this.trapTypes.add(trapType);
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
