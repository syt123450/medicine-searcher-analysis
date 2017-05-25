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
/**
 * Created by ss on 2017/5/3.
 */
public class MysqlUtils {
    private static final String URL      = "jdbc:mysql://localhost:3306/226analysis?serverTimezone=GMT";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";

    public static void persistSaleTransaction(ArrayList<SaleTransactionBean> saleTransactionBeans) {
        Calendar c  = Calendar.getInstance();
        int year    = c.get(Calendar.YEAR);
        int month   = c.get(Calendar.MONTH) + 1;   // 0 to 11
        int date    = c.get(Calendar.DAY_OF_MONTH);
        int quarter = c.get(Calendar.MONTH)/3 + 1;
        String fullDate = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(date);

        // Insert calender for TODAY
        insertIntoCalenderTbl(fullDate, date, month, quarter, year);

        // Get calendarKey
        int cKey = getCalendarKeyOfDate( fullDate );
        for ( int idx = 0; idx < saleTransactionBeans.size(); idx++ ) {
            SaleTransactionBean saleTransaction = saleTransactionBeans.get(idx);
            insertIntoSaleTransactionTbl(saleTransaction, cKey);
        }
    }

    public static void persistSearchTransaction(ArrayList<SearchTransactionBean> searchTransactionBeans) {
        Calendar c  = Calendar.getInstance();
        int year    = c.get(Calendar.YEAR);
        int month   = c.get(Calendar.MONTH) + 1;   // 0 to 11
        int date    = c.get(Calendar.DAY_OF_MONTH);
        int quarter = c.get(Calendar.MONTH)/3 + 1;
        String fullDate = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(date);

        // Insert calender for TODAY
        insertIntoCalenderTbl(fullDate, date, month, quarter, year);

        // Get calendarKey
        int cKey = getCalendarKeyOfDate(fullDate);

        for ( int idx = 0; idx < searchTransactionBeans.size(); idx++ ) {
            SearchTransactionBean searchTransaction = searchTransactionBeans.get(idx);
            insertIntoSearchTransactionTbl(searchTransaction, cKey);
        }
    }

    private static void insertIntoSaleTransactionTbl(SaleTransactionBean saleTransaction, int calendarKey ) {
        long time         = saleTransaction.getTime();
        int medicineKey   = saleTransaction.getMedicineId();
        int storeKey      = saleTransaction.getStoreId();
        int customerKey   = saleTransaction.getCustomerId();
        double totalPrice = saleTransaction.getTotalPrice();

        String insertSQL = "INSERT INTO saleTransaction " +
                     " (calendarKey, medicineKey, storeKey, customerKey, totalPrice)" +
                     " VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, calendarKey);
            prepareStmt.setInt(2, medicineKey);
            prepareStmt.setInt(3, storeKey);
            prepareStmt.setInt(4, customerKey);
            prepareStmt.setDouble(5, totalPrice);

            prepareStmt.execute();

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void insertIntoSearchTransactionTbl(SearchTransactionBean searchTransaction, int calendarKey ) {
        int medicineId  = searchTransaction.getMedicineId();
        int storeId     = searchTransaction.getStoreId();
        int customerId  = searchTransaction.getCustomerId();

        int medicineKey = getMedicineKeyFromId(medicineId);
        int storeKey    = getStoreKeyFromId(storeId);
        int customerKey = getCustomerKeyFromId(customerId);

        String insertSQL = "INSERT INTO searchTransaction " +
                           " (medicineId, medicineKey, storeId, storeKey, customerId, customerKey, calendarKey)" +
                           " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, medicineId);
            prepareStmt.setInt(2, medicineKey);
            prepareStmt.setInt(3, storeId);
            prepareStmt.setInt(4, storeKey);
            prepareStmt.setInt(5, customerId);
            prepareStmt.setInt(6, customerKey);
            prepareStmt.setInt(7, calendarKey);

            prepareStmt.execute();

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void insertIntoCalenderTbl(String fullDate, int date, int month, int quarter, int year) {
        String insertSQL = "INSERT INTO calendar" +
                           "(fullDate, date, month, quarter, year)" +
                           " VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setString(1, fullDate);
            prepareStmt.setInt(2, date);
            prepareStmt.setInt(3, month);
            prepareStmt.setInt(4, quarter);
            prepareStmt.setInt(5, year);

            prepareStmt.execute();

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static int getCalendarKeyOfDate( String date ) {
        String selectSQL = "SELECT calendarKey, fullDate FROM calendar WHERE fullDate = ?";
        int key = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, date);

            ResultSet rs = preparedStatement.executeQuery(selectSQL);

            // iterate through the java resultset
            if (rs.next()){
                key = rs.getInt("calendarKey");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return key;
    }

    private static int getMedicineKeyFromId( int mId ) {
        String selectSQL = "SELECT medicineKey, medicineId FROM medicine WHERE medicineId = ?";
        int mKey = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setInt(1, mId);

            ResultSet rs = preparedStatement.executeQuery(selectSQL);

            // iterate through the java resultset
            if(rs.next()){
                mKey = rs.getInt("medicineKey");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return mKey;
    }

    private static int getStoreKeyFromId( int sId ) {
        String selectSQL = "SELECT storeKey, storeId FROM store WHERE storeId = ?";
        int sKey = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setInt(1, sId);

            ResultSet rs = preparedStatement.executeQuery(selectSQL);

            // iterate through the java resultset
            if (rs.next()) {
                sKey = rs.getInt("storeKey");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return sKey;
    }

    private static int getCustomerKeyFromId( int cId ) {
        String selectSQL = "SELECT customerKey, customerId FROM customer WHERE customerId = ?";
        int cKey = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setInt(1, cId);

            ResultSet rs = preparedStatement.executeQuery(selectSQL);

            // iterate through the java resultset
            if (rs.next()) {
                cKey = rs.getInt("customerKey");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return cKey;
    }

    //Test code, will DELETE
    //public static void main(String[] args) {
        //int k = getCalendarKeyOfDate( 1509005131L*1000 );
        //System.out.println(k);
        //SimpleCalendar s = new SimpleCalendar();

        // Insert calender for TODAY
        //insertIntoCalenderTbl(s);

        // Get calendarKey
        //int cKey = getCalendarKeyOfDate( s.getFullDate() );
        //System.out.println(cKey);

        //System.out.println(getMedicineKeyFromId(3));
        //System.out.println(getStoreKeyFromId(2));
        //System.out.println(getCustomerKeyFromId(5));

        //ArrayList<SaleTransactionBean> s = new ArrayList<>();
        //s.add( new SaleTransactionBean( 1000, 1000, 2000, 123456) );
        //s.add( new SaleTransactionBean( 2000, 2000, 3000, 234567) );
        //persistSaleTransaction(s);

        //ArrayList<SearchTransactionBean> s = new ArrayList<>();
        //s.add(new SearchTransactionBean(3, 4, 5));
        //s.add(new SearchTransactionBean(6, 7, 8));
        //persistSearchTransaction(s);
    //}
}
