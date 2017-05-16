package com.searcher.utils;
import com.searcher.model.entity.SaleTransactionBean;
import com.searcher.model.entity.SearchTransactionBean;

import javax.xml.transform.Result;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.sql.*;

/*
/**
 * Created by carl on 5/8/17.
 */
public class GenData {
    private static final String URL = "jdbc:mysql://localhost:3306/226analysis?serverTimezone=GMT";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";

//    public static void main(String[] args) {
//        updateMedicineTbl();
//        updateCalendarTbl();
//        updateSaleTransactionTbl() ;
//    }

//    public static void updateMedicineTbl() {
//        ResultSet medicineRS = null;
//        ResultSet medicineBasicRS = null;
//        ResultSet brandBasicRS = null;
//        ResultSet factoryRS = null;
//        Statement queryMedicineBasic;
//        Statement queryBrandBasic;
//        Statement queryFactoryBasic;
//        Statement updateMName;
//        Statement updateBName;
//        Statement updateFName;
//        String mName = "";
//        String bName = "";
//        String fName = "";
//        String updateSql = "";
//
//        try {
//            Connection conn                     = DriverManager.getConnection(URL, USERNAME, PASSWORD);    //connect to mysql
//
//            String mSelectSQL                   = "SELECT * FROM medicine";
//            PreparedStatement preparedStatement = conn.prepareStatement(mSelectSQL);
//            medicineRS                          = preparedStatement.executeQuery(mSelectSQL);
//
//            while (medicineRS.next()) {
//                int medicineId = medicineRS.getInt("medicineId");
//
//                try {
//                    String mBasicSelectSQL       = "SELECT * FROM medicineBasic WHERE medicineId = ?";
//                    PreparedStatement mBasicStmt = conn.prepareStatement(mBasicSelectSQL);
//                    mBasicStmt.setInt(1, medicineId);
//                    medicineBasicRS              = mBasicStmt.executeQuery(mBasicSelectSQL);
//
//                    if (medicineBasicRS.next()) {
//                        mName = medicineBasicRS.getString("medicineName");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                // update medicine Name
//                String updateMSQL             = "UPDATE medicine SET medicineName=? WHERE medicineId=?";
//                PreparedStatement mUpdateStmt = conn.prepareStatement( updateMSQL);
//                mUpdateStmt.setString(1, mName);
//                mUpdateStmt.setInt(2, medicineId);
//                mUpdateStmt.executeUpdate(updateMSQL);
//
//                int brandId = medicineRS.getInt("brandId");
//                String bSelectSQL       = "SELECT * FROM brandBasic WHERE brandId=?";
//                PreparedStatement bPrepareStmt = conn.prepareStatement(bSelectSQL);
//                bPrepareStmt.setInt(1, brandId);
//                brandBasicRS = bPrepareStmt.executeQuery(bSelectSQL);
//
//                try {
//                    if(brandBasicRS.next()) {
//                        bName = brandBasicRS.getString("brandName");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                //update brand name
//                String updateBSQL =  "UPDATE medicine SET brandName=? WHERE brandId=?";
//                PreparedStatement bUpdateStmt = conn.prepareStatement(updateBSQL);
//                bUpdateStmt.setString(1, bName);
//                bUpdateStmt.setInt(2, brandId);
//                bUpdateStmt.executeUpdate(updateBSQL);
//
//                int factoryId = medicineRS.getInt("factoryId");
//                String fSelectSQL = "SELECT * FROM factory WHERE factoryId = ?";
//                PreparedStatement fSelectStmt = conn.prepareStatement(fSelectSQL);
//                fSelectStmt.setInt(1, factoryId);
//                factoryRS = fSelectStmt.executeQuery(fSelectSQL);
//
//                try {
//                    if(factoryRS.next()) {
//                        fName = factoryRS.getString("factoryName");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                //update factoryName
//                String fUpdateSQL = "UPDATE medicine SET factoryName=? WHERE factoryId=?";
//                PreparedStatement fUpdateStmt = conn.prepareStatement(fUpdateSQL);
//                fUpdateStmt.setString(1, fName);
//                fUpdateStmt.setInt(2, factoryId);
//                fUpdateStmt.executeUpdate(updateSql);
//                //System.out.println("m:" + mName + " " + "b:" + bName + " " + "f:" + fName );
//            }
//
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void updateCalendarTbl() {
        Statement updateFullDateStmt;
        Statement updateQuarterStmt;

        ResultSet cRS = null;
        String sql = "SELECT * FROM calendar";
        String fullDateSql = "";
        String quarterSql = "";

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);    //connect to mysql
            Statement stmt = conn.createStatement();
            cRS = stmt.executeQuery(sql);

            while(cRS.next()) {
                int year        = cRS.getInt("year");
                int month       = cRS.getInt("month");
                int date        = cRS.getInt("date");
                String fullDate = Integer.toString(year)+"/"+Integer.toString(month)+"/"+Integer.toString(date);

                fullDateSql = String.format("update calendar set fullDate='%s' where year=%d and month=%d and date=%d", fullDate, year, month, date );
                updateFullDateStmt = conn.createStatement();
                updateFullDateStmt.executeUpdate(fullDateSql);

                quarterSql = String.format("update calendar set quarter=%d where month=%d", (month-1)/3 + 1, month);
                updateQuarterStmt = conn.createStatement();
                updateQuarterStmt.executeUpdate(quarterSql);
            }
        } catch ( Exception e ){

        }
    }

//    public static void updateSaleTransactionTbl() {
//        String selectSql = "SELECT * FROM saleTransaction";
//        String updateSql = "UPDATE saleTransaction SET medicineId=?, storeId=?, customerId=? WHERE saleTransactionKey=?";
//
//        try {
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);    //connect to mysql
//            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
//            ResultSet rs = selectStmt.executeQuery(selectSql);
//
//            while(rs.next()) {
//                int medicineId         = rs.getInt("medicineKey");
//                int storeId            = rs.getInt("storeKey");
//                int customerId         = rs.getInt("customerKey");
//                int saleTransactionKey = rs.getInt("saleTransactionKey");
//
//                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
//                updateStmt.setInt(1, medicineId);
//                updateStmt.setInt(2, storeId);
//                updateStmt.setInt(3, customerId);
//                updateStmt.setInt(4, saleTransactionKey);
//                updateStmt.executeUpdate();
//            }
//        } catch ( Exception e ){
//
//        }
//    }
}
