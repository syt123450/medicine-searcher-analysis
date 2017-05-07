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
    private static final String URL      = "jdbc:mysql://localhost:3306/226operation?serverTimezone=GMT";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";

    public static void persistSaleTransaction(ArrayList<SaleTransactionBean> saleTransactionBeans) {
        SimpleCalendar s = new SimpleCalendar();

        // Insert calender for TODAY
        insertIntoCalenderTbl(s);

        // Get calendarKey
        int cKey = getCalendarKeyOfDate( s.getFullDate() );

        for ( int idx = 0; idx < saleTransactionBeans.size(); idx++ ) {
            SaleTransactionBean saleTransaction = saleTransactionBeans.get(idx);
            insertIntoSaleTransactionTbl(saleTransaction);
        }
    }

    public static void persistSearchTransaction(ArrayList<SearchTransactionBean> searchTransactionBeans) {

    }

    private static void insertIntoSaleTransactionTbl(SaleTransactionBean saleTransaction) {
        int saleTransKey  = saleTransaction.getSaleTransactionId();
        int quantity      = saleTransaction.getQuantity();
        int calendarKey   = saleTransaction.getTime();// ??
        int medicineKey   = saleTransaction.getMedicineId();
        int storeKey      = saleTransaction.getStoreId();
        int customerKey   = saleTransaction.getCustomerId();
        int factoryKey    = saleTransaction.getFactoryId();
        double totalPrice = saleTransaction.getTotalPrice();

        //!!! TO-DO later: change format
        String sql = String.format( "INSERT INTO saleTransaction " +
                                    "(saleTransactionKey, quantity, calendarKey, medicineKey, storeKey, customerKey, factoryKey, totalPrice)" +
                                    " VALUES " +
                                    "(%d, %d, %d, %d, %d, %d, %d, %f)",
                                    saleTransKey, quantity, calendarKey, medicineKey, storeKey, customerKey, factoryKey, totalPrice );

        runMysqlWithUpdate( sql );
    }

    private static void insertIntoCalenderTbl( SimpleCalendar c ) {
        //!!! TO-DO later: change format
        String sql = String.format( "INSERT INTO calendar" +
                                    "(fullDate, date, month, quarter, year)" +
                                    " VALUES " +
                                    "('%s', %d, %d, %d, %d)",
                                    c.getFullDate(), c.getDay(), c.getMonth(), c.getQuarter(), c.getYear() );

        runMysqlWithUpdate( sql );
    }

    private static int getCalendarKeyOfDate( String date ) {
        String sql = "SELECT calendarKey, fullDate FROM calendar";
        ResultSet rs = null;
        int key = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            Statement stmt    = conn.createStatement();

            rs = stmt.executeQuery(sql);

            // iterate through the java resultset
            while (rs.next())
            {
                String fullDate = rs.getString("fullDate" );
                if ( fullDate.equals(date) ) {
                    key = rs.getInt("calendarKey");
                    break;
                }
            }

            stmt.close();
            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return key;
    }

    private static void runMysqlWithUpdate( String sql ) {
        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            Statement stmt    = conn.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    //Test code, will DELETE
    public static void main(String[] args) {
        //int k = getCalendarKeyOfDate( 1509005131L*1000 );
        //System.out.println(k);
        //SimpleCalendar s = new SimpleCalendar();

        // Insert calender for TODAY
        //insertIntoCalenderTbl(s);

        // Get calendarKey
        //int cKey = getCalendarKeyOfDate( s.getFullDate() );
        //System.out.println(cKey);
    }
}

class SimpleCalendar {
    private Calendar c = null;

    SimpleCalendar(){
        c = Calendar.getInstance();
    }

    public int getYear() {
        int year = 0;

        if ( c != null )
            year = c.get(Calendar.YEAR);

        return year;
    }

    public int getMonth() {
        int month = 0;

        if ( c != null )
            month = c.get(Calendar.MONTH) + 1;   // 0 to 11

        return month;
    }

    public int getDay() {
        int day = 0;

        if ( c != null )
            day = c.get(Calendar.DAY_OF_MONTH);

        return day;
    }

    public int getQuarter() {
        int quarter = 0;

        if ( c != null )
            quarter = c.get(Calendar.MONTH)/3 + 1;

        return quarter;
    }

    public String getFullDate() {
        String fullDate = null;

        if ( c!= null )
            fullDate = getYear() + "-" + getMonth() + "-" + getDay();

        return fullDate;
    }
}
