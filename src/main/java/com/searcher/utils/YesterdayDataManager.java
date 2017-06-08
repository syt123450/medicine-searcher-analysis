package com.searcher.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by ss on 2017/6/7.
 */

public class YesterdayDataManager {

    private static final String URL      = "jdbc:mysql://localhost:3306/226analysis?serverTimezone=GMT";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";

    public void generate() {
        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(createSQLString());
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {

        String deleteSQL = "delete from saleTransaction where calendarKey = (select calendarKey from calendar where fulldate = subdate(current_date, 1));";
        String keyQuerySQL = "select max(saleTransactionKey) from saleTransaction;";
        String resetAutoIncrementSQL = "alter table saleTransaction auto_increment %d;";
        int saleTransactionKey = 0;

        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(deleteSQL);
            ResultSet resultSet = stmt.executeQuery(keyQuerySQL);
            while(resultSet.next()) {
                saleTransactionKey = resultSet.getInt(1);
            }
            String resetStatement = String.format(resetAutoIncrementSQL, saleTransactionKey + 1);
            stmt.execute(resetStatement);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createSQLString() {

        String baseString = "insert into saleTransaction (calendarKey, medicineKey, storeKey, customerKey, totalPrice) values ";
        String transactionItem = "(%d, %d, %d, %d, %.2f), ";
        int calendarKey = 0;
        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select calendarKey from calendar where fulldate = subdate(current_date, 1);");
            while (rs.next()) {
                calendarKey = rs.getInt(1);
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 29; i++) {
            baseString += String.format(transactionItem, calendarKey,
                    30 * i + 1,
                    (int)(Math.random() * 50),
                    (int)(Math.random() * 2000),
                    Math.random() * 1000);
        }

        return baseString.substring(0, baseString.length() - 2) + ";";
    }

    public static void main(String[] args) {
        YesterdayDataManager yesterdayDataManager = new YesterdayDataManager();
        yesterdayDataManager.generate();
//        yesterdayDataManager.delete();
    }
}
