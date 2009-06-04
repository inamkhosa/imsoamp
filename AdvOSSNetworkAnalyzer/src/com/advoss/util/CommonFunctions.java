/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advoss.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import org.ict.oamp.service.client.Trap;
import org.ict.oamp.service.client.TrapBinding;
import org.ict.oamp.service.client.TrapGenericType;
import org.ict.oamp.service.client.TrapType;
import org.ict.oamp.service.client.VariableType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author alam.sher
 */
public class CommonFunctions {

    
    public static boolean validateIPAddress(String iPaddress) {
        try {
            final Pattern IP_PATTERN =
                    Pattern.compile("b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).)" + "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)b");
            return IP_PATTERN.matcher(iPaddress).matches();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static String getValue(Object anObject) {
        if (anObject == null) {
            return "";
        } else {
            return String.valueOf(anObject);
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

    public static String getUpTime(long timeInSeconds) {
        String retValue = "";
        long hours, minutes;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        timeInSeconds = timeInSeconds - (minutes * 60);
        if (hours != 0) {
            retValue += hours + " Hr(s) ";
        }
        if (minutes != 0) {
            retValue += minutes + " Min(s) ";
        }
        if (retValue.equals("")) {
            retValue = "Less than a min";
        }
        return retValue;
    }

    public static double roundDouble(double arg, int places) {
        double tmp = (double) arg * (pow(10, places));
        int tmp1 = (int) Math.floor(tmp + 0.5);
        double tmp2 = (double) tmp1 / (pow(10, places));
        return tmp2;
    }

    public static int pow(int arg, int times) {
        int ret = 1;
        for (int i = 1; i <= times; i++) {
            ret = ret * arg;
        }
        return ret;
    }

    public static String quotedString(String value) {
        if (value != null) {
            if(value.indexOf("'") != -1){
                value = value.replaceAll("'", "''");
            }
            return "'" + value + "'";
        }
        return "";
    }

    public static boolean isToday(Calendar dateTime) {
        try {
            Calendar today = Calendar.getInstance();
            if(today.get(Calendar.DAY_OF_MONTH) == dateTime.get(Calendar.DAY_OF_MONTH) &&
                    today.get(Calendar.MONTH) == dateTime.get(Calendar.MONTH) &&
                    today.get(Calendar.YEAR) == dateTime.get(Calendar.YEAR)) {
                    return true;
            }
        } catch (Exception ex) {
        }
        return false;
    }

    public static boolean connectToServer() {
        try {
            URL url = new URL("http://localhost:8084/oampmanager/initmanager");
            URLConnection con = url.openConnection();
            BufferedReader bReader = new BufferedReader(
                    new InputStreamReader(
                    con.getInputStream()));
            String line = null;
            StringBuffer sBuf = new StringBuffer();
            while ((line = bReader.readLine()) != null) {
                sBuf.append(line);
            }
            if(sBuf.toString().equalsIgnoreCase("OK")) {
                return true;
            } else if (sBuf.toString().startsWith("ERROR")){
                com.advoss.util.SwingUtilities.showErrorMessage("Error occured while connecting to server : \n" + sBuf.toString());
                return false;
            }
        } catch (Exception ex) {
            com.advoss.util.SwingUtilities.showErrorMessage("Error occured while connecting to server : \n" + ex.toString());
        }
        return false;
    }

    public static Trap generateTrapObject(String trapData) {
        try {
             SAXBuilder builder = new SAXBuilder();
             Document document =builder.build(new StringReader(trapData));
             Element root = document.getRootElement();
             Trap trap = new Trap();
             trap.setTrapId(Integer.parseInt(root.getChildTextTrim("TrapId")));
             trap.setTrapDefinitionId(Integer.parseInt(root.getChildTextTrim("TrapDefinitionId")));
             trap.setTrapType(TrapType.fromValue(root.getChildTextTrim("TrapType")));
             if(trap.getTrapType() == TrapType.V1TRAP) {
                 trap.setEnterprise(root.getChildTextTrim("Enterprise"));
                 trap.setGenericType(TrapGenericType.fromValue(root.getChildTextTrim("GenericType")));    
                 trap.setSpecificType(Integer.parseInt(root.getChildTextTrim("SpecificType")));    
             } else {
                trap.setSnmpOID(root.getChildTextTrim("SnmpOID"));
             }
             trap.setTrapHost(root.getChildText("TrapHost"));
             trap.setRequestId(Integer.parseInt(root.getChildTextTrim("RequestId")));
             try {
                trap.setTrapTimeStamp(CommonFunctions.parseDateTime("hh:mm:ss.SS", root.getChildTextTrim("UpTime")));
             } catch (Exception ex){
             }
             try {
                trap.setTrapTimeStamp(CommonFunctions.parseDateTime("yyyy-MMp-dd hh:mm:ss.SS", root.getChildTextTrim("StorageTime")));
             } catch (Exception ex){
             }
             trap.setSecurityName(root.getChildTextTrim("SecurityName"));
             trap.setSecurityModel(root.getChildTextTrim("SecurityModel"));
             trap.setSecurityLevel(root.getChildTextTrim("SecurityLevel"));
             List<Element> bindingsList = root.getChild("Bindings").getChildren("Binding");
             TrapBinding[] trapBindings = new TrapBinding[bindingsList.size()];
             for(int i=0; i<bindingsList.size(); i++) {
                TrapBinding binding = new TrapBinding();
                Element bindingElement = bindingsList.get(i);
                binding.setTrapId(trap.getTrapId());
                binding.setTrapBindingId(Integer.parseInt(bindingElement.getChildTextTrim("TrapBindingId")));
                binding.setOid(bindingElement.getChildTextTrim("OID"));
                binding.setName(bindingElement.getChildTextTrim("Name"));
                binding.setType(VariableType.fromValue(bindingElement.getChildTextTrim("Type")));
                binding.setDescription(bindingElement.getChildTextTrim("Description"));
                binding.setValue(bindingElement.getChildTextTrim("Value"));
                trapBindings[i] = binding;
             }
             trap.setBindings(trapBindings);
             return trap;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
