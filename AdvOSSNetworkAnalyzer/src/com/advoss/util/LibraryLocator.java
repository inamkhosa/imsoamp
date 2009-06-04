/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.advoss.util;
import java.io.File;
/**
 *
 * @author Alam Sher
 */
public class LibraryLocator {
    public static void main(String args[]) {
        File[] files = FileManager.getDirectoryContents("E:\\NetBeansProjects\\libs");
        for(int i=0; i<files.length; i++) {
            System.out.println(files[i].getName());
        }
    }
}
