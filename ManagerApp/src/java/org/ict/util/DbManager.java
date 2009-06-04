package org.ict.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;
import org.ict.Constants;
import org.ict.exceptions.*;

/**
 * A utility class that provides access to all Database related functionality.
 * @author Sharjeel Butt
 * @version 1.0
 */
public class DbManager implements java.io.Serializable {

    private String userName;
    private String password;
    private String dbUrl;
    private String host;
    private int port;
    private String databaseName;
    private int databaseType;
    private Exception exception;
    private java.sql.Connection connection;

    /**
     * @roseuid 41F8D4030203
     */
    public DbManager() {
        userName = new String();
        password = new String();
        host = new String();
        databaseName = new String();
        exception = new Exception();
        dbUrl = new String();
    }

    /**
     * Connect to the existing connection
     * @param connection Already established connection to the database
     * @param connection
     * @throws NullConnectionException
     * @roseuid 41F8CF6F0177
     * @throws NullConnectionException if the connection is not established.
     * @throws vst.exception.NullConnectionException
     * @roseuid 41F8CF6F0177
     */
    public DbManager(java.sql.Connection connection) throws NullConnectionException {
        this();
        if (connection == null) {
            throw new NullConnectionException();
        }
        this.connection = connection;
    }

    /**
     * Connect to the database using an existing connection.
     * @param connection - Previously established database connection
     * @throws NullConnectionException if the connection is null
     * @throws vst.exception.NullConnectionException
     * @roseuid 41F8C42A0203
     */
    public void connectDatabase(java.sql.Connection connection) throws NullConnectionException {
        if (connection == null) {
            throw new NullConnectionException();
        }
        this.connection = connection;
    }

    public Vector getDataTypes() {
        Vector dataTypes = null;
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet resultSet = dbmd.getTypeInfo();
            if (resultSet != null) {
                dataTypes = new Vector();
                while (resultSet.next()) {
                    dataTypes.add(resultSet.getString("TYPE_NAME").toUpperCase());
                }
                Arrays.sort(dataTypes.toArray());
                resultSet.close();
            }
        } catch (SQLException e) {
            exception = e;
        }
        return dataTypes;
    }

    /**
     * @throws ConnectionCreateException if connection to the database is not established
     * @throws vst.exception.ConnectionCreateException
     * @roseuid 41F8C79003D8
     */
    public boolean connectDatabase(boolean autoCommit) throws ConnectionCreateException {
        return connectDatabase(host, port, databaseName, userName, password, databaseType, autoCommit);
    }

    /**
     * @param userName Database user name
     * @param password Database password
     * @param dbUrl Database connection string different for each database
     * @param autoCommit boolean value indicating the behavior of auto commit functionality of the connection
     * @throws ConnectionCreateException if connection to the database is not established
     * @throws vst.exception.ConnectionCreateException
     * @roseuid 41F8C79003D8
     */
    public boolean connectDatabase(String host, int port, String databaseName, String userName, String password, int databaseType, boolean autoCommit) throws ConnectionCreateException {
        try {
            Class.forName(Constants.DATABASE_CLASS_NAMES[databaseType]);
            this.userName = userName;
            this.password = password;
            this.databaseName = databaseName;
            this.port = port;
            this.host = host;
            this.databaseType = databaseType;
            this.dbUrl = prepareDatabaseURL(databaseType, host, port, databaseName);
            connection = DriverManager.getConnection(dbUrl, userName, password);
            connection.setAutoCommit(autoCommit);
            return true;
        } catch (ClassNotFoundException ex) {
            exception = ex;
            ex.printStackTrace();
            throw new ConnectionCreateException();
        } catch (SQLException ex) {
            exception = ex;
            ex.printStackTrace();
            throw new ConnectionCreateException();
        }
    }

    /**
     * Disconnects from the database.
     * @return <code>true</code> if successfully disconnected from the database, <code>false</code> otherwise.
     * @roseuid 41F8C9240196
     */
    public boolean disconnectDatabase() {
        try {
            if (connection != null) {
                if (!connection.isClosed()) {
                    connection.close();
                    return true;
                }
                return true;
            }
        } catch (SQLException ex) {
            exception = ex;
            return false;
        }
        return false;
    }

    public Vector getSQLTypes() {
        Vector dataTypes = new Vector();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet resultSet = dbmd.getTypeInfo();

            while (resultSet.next()) {
                dataTypes.add(resultSet.getString("TYPE_NAME") + " (" + resultSet.getInt("PRECISION") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return dataTypes;
    }

    public Vector getTablesList() {
        Vector tables = new Vector();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet resultSet = dbmd.getTables(this.getDatabaseName(), this.getUserName().toUpperCase(), "%", types);
            if (resultSet != null) {
                while (resultSet.next()) {
                    tables.add(resultSet.getString(3));
                }
                resultSet.close();
            } else {
                tables = null;
            }
            return tables;
        } catch (SQLException ex) {
            exception = ex;
            return null;
        }
    }

    public Vector getColumsList(String tableName) {
        Vector columnData = new Vector();
        String strSQL = "Select * from " + tableName.toUpperCase().replaceAll(" ", "") + " where 'ONE' = 'TWO'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(strSQL);
            if (resultSet != null) {
                ResultSetMetaData rsMetaData = resultSet.getMetaData();
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    columnData.add(rsMetaData.getColumnName(i) + "," + rsMetaData.getColumnTypeName(i) + "," + rsMetaData.getPrecision(i));
                }
                rsMetaData = null;
                resultSet.close();
            } else {
                columnData = null;
            }
            statement = null;
            return columnData;
        } catch (SQLException ex) {
            exception = ex;
            return null;
        }
    }

    public boolean tableExists(String tableName) {
        boolean tableExists = false;
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet resultSet = dbmd.getTables(this.getDatabaseName(), this.getUserName().toUpperCase(), tableName, types);
            if (resultSet != null) {
                if (resultSet.next()) {
                    tableExists = true;
                    resultSet.close();
                }
            } else {
                tableExists = false;
            }
            return tableExists;
        } catch (SQLException ex) {
            exception = ex;
            return false;
        }
    }

    public boolean createTable(String query) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            connection.close();
            return true;
        } catch (SQLException ex) {
            exception = ex;
            return false;
        }
    }

    /**
     * Creates and executes SELECT statement on the given database table.
     * @param tableName the name of the database table
     * @param fieldList the name of the columns of the database table
     * @param whereClause a condition to filter the records.
     * @param orderBy the name of the column to be sorted upon
     * @return java.sql.ResultSet the result of the query
     * @roseuid 41F8C9340242
     */
    public ResultSet query(String tableName, String fieldList, String whereClause, String orderBy) {
        String strSQL = "SELECT ";
        strSQL += fieldList + " ";
        strSQL += "FROM " + tableName + " ";
        if (whereClause != null) {
            strSQL += "WHERE " + whereClause + " ";
        }
        if (orderBy != null) {
            strSQL += "ORDER BY " + orderBy;
        }
        System.out.println(strSQL);
        Statement tmpStatement = null;
        java.sql.ResultSet resultSet = null;
        try {
            tmpStatement = connection.createStatement();
            resultSet = tmpStatement.executeQuery(strSQL);
            return resultSet;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex1) {
            }
            return null;
        }
    }

    public ResultSetObject queryNew(String tableName, String fieldList, String whereClause, String orderBy) {
        String strSQL = "SELECT ";
        strSQL += fieldList + " ";
        strSQL += "FROM " + tableName + " ";
        if (whereClause != null) {
            strSQL += "WHERE " + whereClause + " ";
        }
        if (orderBy != null) {
            strSQL += "ORDER BY " + orderBy;
        }
        System.out.println(strSQL);
        Statement tmpStatement = null;
        java.sql.ResultSet resultSet = null;
        try {
            tmpStatement = connection.createStatement();
            resultSet = tmpStatement.executeQuery(strSQL);
            ResultSetObject rsObject = new ResultSetObject();
            rsObject.setRs(resultSet);
            rsObject.setStmt(tmpStatement);
            return rsObject;
        } catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            exception = ex;
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex1) {
            }
            return null;
        }
    }

    /**
     * Executes the SELECT statement.
     * @param strSql the SELECT statement to be executed
     * @return java.sql.ResultSet the result of the query
     * @roseuid 41F8C9FF003E
     */
    public ResultSet query(String strSql) {
        try {
            Statement tmpStatement = connection.createStatement();
            System.out.println(strSql);
            ResultSet resultSet = tmpStatement.executeQuery(strSql);
            return resultSet;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            return null;
        }
    }

    /**
     * Executes the SELECT statement.
     * @param strSql the SELECT statement to be executed
     * @return java.sql.ResultSet the result of the query
     * @roseuid 41F8C9FF003E
     */
    public ResultSet query(String strSql, String nothing) throws java.sql.SQLException {
        Statement tmpStatement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = tmpStatement.executeQuery(strSql);
        return resultSet;
    }

    public ResultSetObject query(String strSql, int another) throws java.sql.SQLException {
        Statement tmpStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        System.out.println(strSql);
        ResultSet resultSet = tmpStatement.executeQuery(strSql);
        ResultSetObject rsObject = new ResultSetObject();
        rsObject.setRs(resultSet);
        rsObject.setStmt(tmpStatement);
        return rsObject;
    }

//  public boolean query(String strSql, int nothing) {
//    try {
//      Statement tmpStatement = connection.createStatement();
//      tmpStatement.execute(strSql);
//      tmpStatement.close();
//      return true;
//    } catch (java.sql.SQLException ex) {
//      exception = ex.getMessage() + "\n" + strSql;
//      return false;
//    }
//  }
    /**
     * Creates and executes UPDATE statement on the database tables.
     * @param tableName the name of the table to be updated
     * @param recordValues a key=value pair of the columns to be updated in the form of java.uti.Properties.
     * @param whereClause filter criteria of the query.
     * @return <code>true</code> if the UPDATE statement is executed successfully. <code>false</code> otherwise.
     * @roseuid 41F8CA3503D8
     */
    public boolean updateSql(String tableName, java.util.Properties recordValues, String whereClause) throws Exception {
        String newValues = recordValues.entrySet().toString();
        newValues = newValues.substring(1, newValues.length() - 1);
        String strSQL = "UPDATE " + tableName + " ";
        strSQL += "SET " + newValues + " ";
        if (whereClause != null) {
            strSQL += "WHERE " + whereClause;
        }
        System.out.println("Update SQL = " + strSQL);
        try {
            Statement tmpStatement = connection.createStatement();
            int iRecordsAffected = tmpStatement.executeUpdate(strSQL);
            tmpStatement.close();
            tmpStatement = null;
            return true;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            throw new SQLException(ex);
        }
    }

    /**
     * Executes UPDATE statement on the given database table.
     * @param strSQL the UPDATE statement to be executed.
     * @return <code>true</code> if the UPDATE statement is executed successfully. <code>false</code> otherwise.
     * @roseuid 41F8CAFF0186
     */
    public boolean updateSql(String strSQL) {
        try {
            Statement tmpStatement = connection.createStatement();
            tmpStatement.executeUpdate(strSQL);
            System.out.println("UPDATE SQL = " + strSQL);
            return true;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            return false;
        }
    }

    /**
     * Creates and executes INSERT statement on the given database table.
     * @param tableName the name of the database table
     * @param recordValues a key=value pair of the columns to be updated in the form of java.uti.Properties.
     * @return <code>true</code> if the INSERT statement is executed successfully. <code>false</code> otherwise.
     * @roseuid 41F8CB1B00CB
     */
    public boolean insertSql(String tableName, java.util.Properties recordValues) throws Exception {
        String fieldsList = recordValues.keySet().toString();
        fieldsList = fieldsList.substring(1, fieldsList.length() - 1);
        String fieldValues = recordValues.values().toString();
        fieldValues = fieldValues.substring(1, fieldValues.length() - 1);
        String strSQL = "INSERT INTO " + tableName + " ";
        if (fieldsList != null) {
            strSQL += "(" + fieldsList + ") ";
        }
        strSQL += "VALUES(" + fieldValues + ")";
        System.out.println("Insert SQL = " + strSQL);
        Statement tmpStatement = null;
        tmpStatement = connection.createStatement();
        int iRecordsAffected = tmpStatement.executeUpdate(strSQL);
        tmpStatement.close();
        tmpStatement = null;
        if (iRecordsAffected > 0) {
            return true;
        } else {
            return false;
        }
    }

        /**
     * Creates and executes INSERT statement on the given database table.
     * @param tableName the name of the database table
     * @param recordValues a key=value pair of the columns to be updated in the form of java.uti.Properties.
     * @return <code>true</code> if the INSERT statement is executed successfully. <code>false</code> otherwise.
     * @roseuid 41F8CB1B00CB
     */
    public int insertSqlandReturnKey(String tableName, java.util.Properties recordValues) {
        String fieldsList = recordValues.keySet().toString();
        fieldsList = fieldsList.substring(1, fieldsList.length() - 1);
        String fieldValues = recordValues.values().toString();
        fieldValues = fieldValues.substring(1, fieldValues.length() - 1);
        String strSQL = "INSERT INTO " + tableName + " ";
        if (fieldsList != null) {
            strSQL += "(" + fieldsList + ") ";
        }
        strSQL += "VALUES(" + fieldValues + ")";
        System.out.println("Insert SQL = " + strSQL);
        PreparedStatement tmpStatement = null;
        try {
            tmpStatement = connection.prepareStatement(strSQL);
            int generatedKey = tmpStatement.executeUpdate(strSQL);
            ResultSet keys = tmpStatement.getGeneratedKeys();
            if (keys.next()) {
                generatedKey = keys.getInt(1);
            }
            keys.close();
            tmpStatement.close();
            tmpStatement = null;
            return generatedKey;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            tmpStatement = null;
            return -1;
        }
    }


    /**
     * Executes INSERT statement on the database table.
     * @param strSql the INSERT statement to be executed
     * @return <code>true</code> if the INSERT statement is executed successfully. <code>false</code> otherwise.
     * @roseuid 41F8CB74007D
     */
    public boolean insertSql(String strSQL) {
        try {
            Statement tmpStatement = connection.createStatement();
            System.out.println("Insert SQL = " + strSQL);
            tmpStatement.executeUpdate(strSQL);
            tmpStatement.close();
            return true;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            return false;
        }
    }

    /**
     * Creates and execute DELETE statement on the given Database table.
     * @param tableName the name of the database table
     * @param whereClause filter criteria for the DELETE statement
     * @return <code>true</code> if the statement is executed successfully. <code>false</code> otherwise.
     * @roseuid 41F8CB8F009C
     */
    public boolean deleteSql(String tableName, String whereClause) {
        String strSQL = "DELETE FROM " + tableName + " ";
        if (whereClause != null) {
            strSQL += "WHERE " + whereClause;
        }

        System.out.println(strSQL);
        try {
            Statement tmpStatement = connection.createStatement();
            int iRecordsAffected = tmpStatement.executeUpdate(strSQL);
            tmpStatement.close();
            tmpStatement = null;
            if (iRecordsAffected > 0) {
                return true;
            } else {
                exception = null;
                return false;
            }
        } catch (java.sql.SQLException ex) {
            exception = ex;
            return false;
        }
    }

    public boolean deleteSql(String strSQL) {
        try {
            Statement tmpStatement = connection.createStatement();
            int iRecordsAffected = tmpStatement.executeUpdate(strSQL);
            tmpStatement.close();
            tmpStatement = null;
            return true;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            return false;
        }
    }

    /**
     * Commit the transaction
     * @return <code>true</code> if no error occured. <code>false</code> otherwise.
     * @roseuid 41F8CBCA01D4
     */
    public boolean commitTransaction() {
        try {
            connection.commit();
            return true;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            return false;
        }
    }

    /**
     * Rollback the current transaction
     * @return <code>true</code> if no error occured.<code>false</code> otherwise.
     * @roseuid 41F8CC44036B
     */
    public boolean rollbackTransaction() {
        try {
            connection.rollback();
            return true;
        } catch (java.sql.SQLException ex) {
            exception = ex;
            return false;
        }
    }

    /**
     * @return java.sql.Connection the existing connection to the database
     * @throws NullConnectionException
     * @roseuid 41F8CFF6008C
     */
    public Connection getConnection() throws NullConnectionException {
        if (connection == null) {
            throw new NullConnectionException();
        }
        return connection;
    }

    /**
     * @roseuid 4201B90703A9
     */
    public void cleanUp() {
    }

    public String getDbUrl() {
        dbUrl = prepareDatabaseURL(databaseType, host, port, databaseName);
        return dbUrl;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String prepareDatabaseURL(int databaseType, String host, int port, String databaseName) {
        String returnVal = new String();
        if (databaseType == Constants.DATABASE_ORACLE) {
            returnVal = "jdbc:oracle:thin:@" + host + ":" + port + ":" + databaseName;
        } else if (databaseType == Constants.DATABASE_MYSQL) {
            returnVal = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
        } else if (databaseType == Constants.DATABASE_SQLSERVER) {
            returnVal = "jdbc:jtds:sqlserver://" + host + ":" + port + "/" + databaseName + ";user=" + userName + ";password=" + password + ";useCursors=true";
        }
        return returnVal;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public Exception getExceptionMessage() {
        return exception;
    }

    public int getDatabaseType() {
        return databaseType;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setDatabaseType(int databaseType) {
        this.databaseType = databaseType;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean testConnection() throws ConnectionCreateException {
        if (connectDatabase(host, port, databaseName, userName, password, databaseType, false)) {
            disconnectDatabase();
            return true;
        }
        return false;
    }

    public boolean testConnection(String host, int port, String databaseName, String userName, String password, int databaseType) throws ConnectionCreateException {
        if (connectDatabase(host, port, databaseName, userName, password, databaseType, false)) {
            disconnectDatabase();
            return true;
        }
        return false;
    }
}
