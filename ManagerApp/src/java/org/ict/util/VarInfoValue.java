/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.util;

import org.ict.oamp.utils.VarInfo;

/**
 *
 * @author Alam Sher
 */
public class VarInfoValue {
    private VarInfo varInfo = null;
    private String value = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public VarInfo getVarInfo() {
        return varInfo;
    }

    public void setVarInfo(VarInfo varInfo) {
        this.varInfo = varInfo;
    }
}
