package com.searcher.utils;

import com.searcher.model.entity.WebRequestBean;

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
    private static final String URL      = "jdbc:mysql://localhost:3306/226analysis?serverTimezone=GMT&useSSL=false";
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

    public ResultSet calcSaleSumByParam(String query, String factoryName, String brandName, String medicineName, int year, int quarter, int month){
        try {
            preStmt = conn.prepareStatement(query);

            updateName(1, factoryName);
            updateName(2, brandName);
            updateName(3, medicineName);

            updateYear(4, year);
            updateQuarter(6, quarter);
            updateMonth(8, month);

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

    public ResultSet calcSaleSum(String query, String productLevel, String timeLevel, String factoryName, String brandName, String medicineName, int year, int quarter, int month){
        try {
            preStmt = conn.prepareStatement(query);
            int idx = 1;    // index of the prepared statement

            updateName(idx, factoryName);
            idx++;
            if (productLevel.equals("MEDICINE")){
                updateName(idx, brandName);
                idx++;
                updateName(idx, medicineName);
                idx++;
            }
            else if (productLevel.equals("BRAND")){
                updateName(idx, brandName);
                idx++;
            }

            updateYear(idx, year);
            idx+=2;
            if (timeLevel.equals("MONTH")){
                updateQuarter(idx, quarter);
                idx+=2;
                updateMonth(idx, month);
                idx+=2;
            }
            else if (timeLevel.equals("QUARTER")){
                updateQuarter(idx, quarter);
                idx+=2;
            }

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

    public void updateName(int idx, String name){
        try {
            if (!name.isEmpty()){
                preStmt.setString(idx, name);
            }
            else {
                preStmt.setString(idx, "%%");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateYear(int idx, int year){
        try {
            if (year >0){
                preStmt.setInt(idx, year);
                preStmt.setInt((idx+1), year);
            }
            else {
                preStmt.setInt(idx, 2012);
                preStmt.setInt((idx+1), 2017);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateQuarter(int idx, int quarter){
        try {
            if (quarter >0){
                preStmt.setInt(idx, quarter);
                preStmt.setInt((idx+1), quarter);
            }
            else {
                preStmt.setInt(idx, 1);
                preStmt.setInt((idx+1), 4);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateMonth(int idx, int month){
        try {
            if (month >0){
                preStmt.setInt(idx, month);
                preStmt.setInt((idx+1), month);
            }
            else {
                preStmt.setInt(idx, 1);
                preStmt.setInt((idx+1), 12);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
