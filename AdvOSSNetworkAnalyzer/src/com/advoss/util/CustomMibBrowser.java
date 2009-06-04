/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advoss.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.snmp.SnmpNotificationType;

/**
 *
 * @author Alam Sher
 */
public class CustomMibBrowser {

    public static void main(String args[]) throws Exception {
        MibLoader loader = new MibLoader();
        loader.addDir(new File("E:/Projects/OAMP/fault-mibs"));
        File[] mibFiles = FileManager.getFilesList("E:/Projects/OAMP/fault-mibs");
        for(int i=0; i<mibFiles.length; i++) {
            loader.load(mibFiles[i]);
        }
        Mib[] loadedMibs = loader.getAllMibs();
        for (int i = 0; i < loadedMibs.length; i++) {
            System.out.println("Loaded Mib : " + loadedMibs[i].getName());
            Vector<MibSymbol> symbols = new Vector<MibSymbol>(loadedMibs[i].getAllSymbols());

            for (int y = 0; y < symbols.size(); y++) {
                if (symbols.get(y) instanceof MibValueSymbol) {
                    MibValueSymbol mibValueSymbol = (MibValueSymbol) symbols.get(y);
                    System.out.println(mibValueSymbol.getName());
                    System.out.println(mibValueSymbol.getValue());
                    System.out.println(mibValueSymbol.getType().getName());
                    System.out.println(mibValueSymbol.getType().getClass().getName());
                    System.out.println(mibValueSymbol.getType().toString());
//                    if(mibValueSymbol.getType() instanceof SnmpObjectType) {
//                        SnmpObjectType objectType = (SnmpObjectType) mibValueSymbol.getType();
//                        System.out.println(objectType.getSyntax().getClass().getName());
//                        System.out.println(objectType.getDescription());
//                        System.out.println(objectType.toString());
//                    } else if (mibValueSymbol.getType() instanceof SnmpModuleIdentity) {
//                        SnmpModuleIdentity module = (SnmpModuleIdentity) mibValueSymbol.getType();
//                        System.out.println(module);
//                    }
                    if (mibValueSymbol.getType() instanceof SnmpNotificationType) {
                        SnmpNotificationType notificationType = (SnmpNotificationType) mibValueSymbol.getType();
                        ArrayList<MibValue> objectValues = notificationType.getObjects();
                        for (int j = 0; j < objectValues.size(); j++) {
                            System.out.println("Getting reference Symbol for :" + objectValues.get(j).getName() + " = " + loadedMibs[i].getSymbolByValue(objectValues.get(j)).getValue());
                        }
                    }
                }
            }
        }
    }
}
