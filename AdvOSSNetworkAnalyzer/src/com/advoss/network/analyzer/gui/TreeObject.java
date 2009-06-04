/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.advoss.network.analyzer.gui;

/**
 *
 * @author Alam Sher
 */
public class TreeObject {
    private String objectName = null;
    private String objectId = null;

    public TreeObject() {

    }

    public TreeObject(String objectName, String objectId) {
        this.objectName = objectName;
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String toString() {
        return objectName;
    }
}
