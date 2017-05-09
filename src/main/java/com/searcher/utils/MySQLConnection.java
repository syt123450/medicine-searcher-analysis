package com.searcher.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

/**
 * Created by zchholmes on 2017/5/8.
 */
public class MySQLConnection {
    private static final String URL      = "jdbc:mysql://localhost:3306/226analysis?serverTimezone=GMT";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";

    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement preStmt = null;
    private ResultSet retSet = null;

    public MySQLConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager
                    .getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.err.println("SQL ERROR, Exception");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Used to close resultset, statement and connection properly
     */
    public void close() {
        try {
            if (retSet != null) {
                retSet.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {

        }

    }

    /**
     * Determine sale sum with level factory and year
     * witout providing any specific factoryInfo or yearInfo
     * @return  resultSet
     */
    public ResultSet calcSaleSumByFactoryYear(){
        try {
            String query = "SELECT SUM(s.totalPrice), m.factoryName, c.year " +
                    "FROM SaleTransaction s, Medicine m, Calendar c " +
                    "WHERE s.medicineKey <> -1 " +
                    "AND\ts.medicineKey = m.medicineKey " +
                    "AND s.calendarKey = c.calendarKey " +
                    "GROUP BY m.factoryName, c.year;"
                    ;

            preStmt = conn.prepareStatement(query);
            retSet = preStmt.executeQuery();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.err.println("SQL ERROR, Exception");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this.retSet;
    }


}
