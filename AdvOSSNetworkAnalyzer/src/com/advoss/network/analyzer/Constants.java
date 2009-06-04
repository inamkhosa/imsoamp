/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advoss.network.analyzer;

import java.text.SimpleDateFormat;

/**
 *
 * @author Alam Sher
 */
public class Constants {

    public static final String APP_TITLE = "AdvOSS Network Analyzer Client";
    public static final String APP_VERSION = "1.0 Beta";
    public static final String CONFIG_DIR = "config/";
    public static final String CONFIG_FILE = CONFIG_DIR + "client.xml";
    public static final SimpleDateFormat SIMPLE_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SIMPLE_TIME = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat SIMPLE_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final int ACTION_ADD = 0;
    public static final int ACTION_EDIT = 1;
}
