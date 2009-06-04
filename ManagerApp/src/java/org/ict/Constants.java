/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ict;

/**
 *
 * @author Alam Sher
 */
public class Constants {

    public static final String[] DATABASE_TYPES = {
        "Oracle",
        "MySQL",
        "Microsoft SQL Server"
    };
    public static final int DATABASE_ORACLE = 0;
    public static final int DATABASE_MYSQL = 1;
    public static final int DATABASE_SQLSERVER = 2;
    public static final int[] DATABASE_PORTS = {1521, 3306, 1433};
    public static final String[] DATABASE_CLASS_NAMES = {
        "oracle.jdbc.driver.OracleDriver",
        "com.mysql.jdbc.Driver",
        "net.sourceforge.jtds.jdbc.Driver"
    };
}
