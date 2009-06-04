/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.util;

/**
 *
 * @author alam sher
 */
public class Debug {

    public static boolean debug = true;
    
    public static void print(String message) {
        if(debug){
            System.out.println(getDebugStamp() + message);
        }
    }

    public static void printStackTrace(Exception ex) {
        if(debug) {
            System.out.print(getDebugStamp());
            ex.printStackTrace();
        }
    }

    private static String getDebugStamp() {
        return "DEBUG: [ " + CommonFunctions.formatDate(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss"), new java.util.Date()) + " ] > " + "\n";
    }
}
