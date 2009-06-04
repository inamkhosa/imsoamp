package org.ict.util;

import java.sql.*;

public class ResultSetObject {

    private ResultSet rs = null;
    private Statement stmt = null;

    public ResultSetObject() {
    }

    public ResultSet getRs() {
        return rs;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public void close(){
        try{
            this.stmt.close();
        }catch(Exception ex){
        }
        try{
            this.rs.close();
        }catch(Exception ex){
        }
    }

    public boolean next() throws Exception{
        return this.rs.next();
    }
}
