/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ict.oamp.em;

import java.io.Serializable;
import java.util.Vector;
import org.ict.oamp.manager.ElementManager;
import org.ict.oamp.mib.MibFile;

/**
 *
 * @author Alam Sher
 */
public class ElementMibBinding implements Serializable {
    private ElementManager elementManager = null;
    private Vector <MibFile> mibFiles = null;

    public ElementMibBinding() {
    }

    public ElementManager getElementManager() {
        return elementManager;
    }

    public void setElementManager(ElementManager elementManager) {
        this.elementManager = elementManager;
    }

    public Vector<MibFile> getMibFiles() {
        return mibFiles;
    }

    public void setMibFiles(Vector<MibFile> mibFiles) {
        this.mibFiles = mibFiles;
    }
}
