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

    //public static void main(String[] args) {
    //updateMedicineTbl();
    //updateCalendarTbl();
    //updateSaleTransactionTbl() ;
    //}

    public static void updateMedicineTbl() {
        String sql = "SELECT * FROM medicine";
        ResultSet medicineRS = null;
        ResultSet medicineBasicRS = null;
        ResultSet brandBasicRS = null;
        ResultSet factoryRS = null;
        Statement queryMedicineBasic;
        Statement queryBrandBasic;
        Statement queryFactoryBasic;
        Statement updateMName;
        Statement updateBName;
        Statement updateFName;
        String mName = "";
        String bName = "";
        String fName = "";
        String updateSql = "";

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);    //connect to mysql
            Statement stmt = conn.createStatement();

            medicineRS = stmt.executeQuery(sql);

            // iterate through the java resultset
            while (medicineRS.next()) {
                // get medicine name
                int medicineId = medicineRS.getInt("medicineId");
                queryMedicineBasic = conn.createStatement();
                medicineBasicRS = queryMedicineBasic.executeQuery("SELECT * FROM medicineBasic");
                try {
                    // iterate through the java resultset
                    while (medicineBasicRS.next()) {
                        if (medicineId == medicineBasicRS.getInt("medicineId")) {
                            mName = medicineBasicRS.getString("medicineName");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                updateSql = String.format("update medicine set " + "medicineName='%s' where medicineId=%d", mName, medicineId);
                updateMName = conn.createStatement();
                updateMName.executeUpdate(updateSql);

                // get brand name
                int brandId = medicineRS.getInt("brandId");
                queryBrandBasic = conn.createStatement();
                brandBasicRS = queryBrandBasic.executeQuery("SELECT * FROM brandBasic");
                try {
                    // iterate through the java resultset
                    while (brandBasicRS.next()) {
                        if (brandId == brandBasicRS.getInt("brandId")) {
                            bName = brandBasicRS.getString("brandName");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                updateSql = String.format("update medicine set " + "brandName='%s' where brandId=%d", bName, brandId);
                updateBName = conn.createStatement();
                updateBName.executeUpdate(updateSql);

                // get factory name
                int factoryId = medicineRS.getInt("factoryId");
                queryFactoryBasic = conn.createStatement();
                factoryRS = queryFactoryBasic.executeQuery("SELECT * FROM factory");
                try {
                    // iterate through the java resultset
                    while (factoryRS.next()) {
                        if (factoryId == factoryRS.getInt("factoryId")) {
                            fName = factoryRS.getString("factoryName");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                updateSql = String.format("update medicine set " + "factoryName='%s' where factoryId=%d", fName, factoryId);
                updateFName = conn.createStatement();
                updateFName.executeUpdate(updateSql);
                //System.out.println("m:" + mName + " " + "b:" + bName + " " + "f:" + fName );
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public static void updateSaleTransactionTbl() {
        ResultSet cRS = null;
        ResultSet medicineBasicRS = null;
        Statement queryMedicineBasic;
        int mKey = 0;

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);    //connect to mysql
            Statement stmt = conn.createStatement();
            cRS = stmt.executeQuery("SELECT * FROM saleTransaction");

            while(cRS.next()) {
                int medicineId = cRS.getInt("medicineId");

                queryMedicineBasic = conn.createStatement();
                medicineBasicRS = queryMedicineBasic.executeQuery("SELECT * FROM medicine");
                try {
                    // iterate through the java resultset
                    while (medicineBasicRS.next()) {
                        if (medicineId == medicineBasicRS.getInt("medicineId")) {
                            mKey = medicineBasicRS.getInt("medicineKey");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String updateSql = String.format("update saleTransaction set " + "medicineKey=%d where medicineId=%d", mKey, medicineId);
                Statement updateMName = conn.createStatement();
                updateMName.executeUpdate(updateSql);
            }
        } catch ( Exception e ){

        }
    }
}
