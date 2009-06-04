/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict.util;

import java.text.SimpleDateFormat;

/**
 *
 * @author Alam Sher
 */
public class CommonFunctions {

    public static String quotedString(String text) {
        if (text == null) {
            return "''";
        } else {
            text.replaceAll("'", "''");
            text = "'" + text + "'";
            return text;
        }
    }

    public static String formatDate(java.text.SimpleDateFormat formatType,
            java.util.Calendar dateTime) {
        if (dateTime == null) {
            return "Unknown";
        }
        return formatType.format(dateTime.getTime());
    }

    public static String formatDate(java.text.SimpleDateFormat formatType,
            java.util.Date dateTime) {
        if (dateTime == null) {
            return "Unknown";
        }
        return formatType.format(dateTime);
    }

    public static java.util.Date parseDateTimePattern(String pattern,
            String dateTime) {
        java.util.Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            date = dateFormat.parse(dateTime);
        } catch (Exception ex) {
            return date;
        }
        return date;
    }

    public static java.util.Calendar parseDateTime(String pattern,
            String dateTime) {
        java.util.Calendar cal = null;
        java.util.Date date = parseDateTimePattern(pattern, dateTime);
        if (date != null) {
            cal = java.util.Calendar.getInstance();
            cal.setTime(date);
        }
        return cal;
    }

    public static String prepareErrorMessage(String className, String methodName, Exception exception) {
        String errorMessage = "Error @ ";
        errorMessage += (className == null || className.trim().equals("") ? "?class::" : className + "::");
        errorMessage += (methodName == null || methodName.trim().equals("") ? "?method>" : methodName + ">" );
        errorMessage += (exception != null ? "[" + exception.toString() + "]{" + exception.getMessage() + "}" : "?");
        errorMessage += (exception != null && exception.getCause() != null ? "(Cause = " + exception.getCause().getMessage() + ")" : "");
        return errorMessage;
    }

    public static String capitalize(Object value) {
        if(value == null) {
            return "";
        } else {
            String firstChar = value.toString().substring(0, 1);
            String remainder = value.toString().substring(1);
            value = firstChar.toUpperCase() + remainder;
        }
        return value.toString();
    }
}
