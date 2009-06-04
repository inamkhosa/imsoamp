/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advoss.util;

import java.io.File;
import net.percederberg.mibble.MibLoader;

/**
 *
 * @author Alam Sher
 */
public class MibManager {

    private static MibManager manager = null;
    private MibLoader mibLoader = null;

    private MibManager() {
        mibLoader = new MibLoader();
    }

    public static MibManager getInstance() {
        if (manager == null) {
            manager = new MibManager();
        }
        return manager;
    }

    public void loadMibs(File directory) {
        try {
            mibLoader.addDir(directory);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
