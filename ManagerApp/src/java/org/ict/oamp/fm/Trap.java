/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.fm;

import java.util.Hashtable;

/**
 *
 * @author Alam Sher
 */
public class Trap {
    private int trapId;
    private int requestId;
    private String trapHost;
    private java.sql.Timestamp timestamp;
    private String type;
    private String securityName;
    private int securityModel;
    private int securityLevel;
    private Hashtable variables = new Hashtable();
    private String colorCode = "";

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(java.sql.Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getTrapHost() {
        return trapHost;
    }

    public void setTrapHost(String trapHost) {
        this.trapHost = trapHost;
    }

    public int getTrapId() {
        return trapId;
    }

    public void setTrapId(int trapId) {
        this.trapId = trapId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public int getSecurityModel() {
        return securityModel;
    }

    public void setSecurityModel(int securityModel) {
        this.securityModel = securityModel;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public Hashtable getVariables() {
        return variables;
    }

    public void setVariables(Hashtable variables) {
        this.variables = variables;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
