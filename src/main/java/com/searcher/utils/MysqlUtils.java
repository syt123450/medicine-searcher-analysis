package com.searcher.utils;

import com.searcher.model.entity.SaleTransactionBean;
import com.searcher.model.entity.SearchTransactionBean;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.Hashtable;

/**
 * Created by ss on 2017/5/3.
 */
public class MysqlUtils {
    private static final String URL      = "jdbc:mysql://localhost:3306/226analysis?serverTimezone=GMT&useSSL=false";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";

    public static void persistSaleTransaction(ArrayList<SaleTransactionBean> saleTransactionBeans) {
        Hashtable<Integer, Double> bHashTbl = new Hashtable<Integer, Double>();
        Hashtable<Integer, Double> mHashTbl = new Hashtable<Integer, Double>();
        Hashtable<Integer, Double> fHashTbl = new Hashtable<Integer, Double>();
        Calendar c  = Calendar.getInstance();

        int year    = c.get(Calendar.YEAR);
        int month   = c.get(Calendar.MONTH) + 1;   // 0 to 11
        int date    = c.get(Calendar.DAY_OF_MONTH);
        int quarter = c.get(Calendar.MONTH)/3 + 1;
        String fullDate = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(date);

        updateCalendarTbl(fullDate, date, month, quarter, year);

        // Get calendarKey
        int cKey = getCalendarKeyOfDate( fullDate );

        for ( int idx = 0; idx < saleTransactionBeans.size(); idx++ ) {
            SaleTransactionBean saleTransaction = saleTransactionBeans.get(idx);

            insertIntoSaleTransactionTbl(saleTransaction, cKey);

            // update hash tables
            updateBHashTbl(saleTransaction, bHashTbl);
            updateMHashTbl(saleTransaction, mHashTbl);
            updateFHashTbl(saleTransaction, fHashTbl);
        }

        int calendarKey  = getCalendarKeyFromMonthAndYear(year, month);
        // update brandMonthTransaction table
        updateBrandTransactionTbl( bHashTbl, year, month, "brandMonthTransaction", calendarKey ) ;
        updateFactoryTransactionTbl( fHashTbl, year, month, "factoryMonthTransaction", calendarKey );
        updateMedicineTransactionTbl( mHashTbl, year, month, "medicineMonthTransaction", calendarKey );

        calendarKey = getCalendarKeyFromQuarterAndYear(year, quarter);
        // update brandQuarterTransaction table
        updateBrandTransactionTbl( bHashTbl, year, month, "brandQuarterTransaction", calendarKey ) ;
        updateFactoryTransactionTbl( fHashTbl, year, month, "factoryQuarterTransaction", calendarKey);
        updateMedicineTransactionTbl( mHashTbl, year, month, "medicineQuarterTransaction", calendarKey);

        calendarKey = getCalendarKeyFromYear(year);
        // update brandYearTransaction table
        updateBrandTransactionTbl( bHashTbl, year, month, "brandYearTransaction", calendarKey ) ;
        updateFactoryTransactionTbl( fHashTbl, year, month, "factoryYearTransaction", calendarKey);
        updateMedicineTransactionTbl( mHashTbl, year, month, "medicineYearTransaction", calendarKey);
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

    private static void updateCalendarTbl(String fullDate, int date, int month, int quarter, int year) {
        insertIntoCalenderTbl(fullDate, date, month, quarter, year);
        insertYearMonthQuarter( year, month, date );
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

    private static void insertYearMonthQuarter( int year, int month, int date ) {
         if ( month == 1 && date == 1 ) {
            // Insert new Year
            insertYearIntoCalenderTbl(year);

            // Insert month of that year
            for ( int m = 1; m <= 12; m++ ) {
                insertMonthIntoCalenderTbl(year, m);
            }

            // Insert quarter of that year
            for ( int q = 1; q <=4; q++) {
                insertQuarterIntoCalenderTbl(year, q);
            }
        }
    }

    private static void insertYearIntoCalenderTbl(int year) {
        String insertSQL = "INSERT INTO calendar (year) VALUES (?)";

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, year);

            prepareStmt.execute();

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void insertMonthIntoCalenderTbl(int year, int month) {
        String insertSQL = "INSERT INTO calendar (year, month) VALUES (?, ?)";

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, year);
            prepareStmt.setInt(2, month);

            prepareStmt.execute();

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void insertQuarterIntoCalenderTbl(int year, int quarter) {
        String insertSQL = "INSERT INTO calendar (year, month) VALUES (?, ?)";

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, year);
            prepareStmt.setInt(2, quarter);

            prepareStmt.execute();

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static int getCalendarKeyOfDate( String date ) {
        String selectSQL = "SELECT calendarKey, fullDate FROM calendar WHERE fullDate= ? " ;
        int key = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, date);

            ResultSet rs = preparedStatement.executeQuery();

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

            ResultSet rs = preparedStatement.executeQuery();

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

            ResultSet rs = preparedStatement.executeQuery();

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

            ResultSet rs = preparedStatement.executeQuery();

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



    private static int getBrandIdFromMedicineId( int medicineId ) {
        String selectSQL = "SELECT brandId FROM medicine WHERE medicineId = ?";
        int brandId = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setInt(1, medicineId);

            ResultSet rs = preparedStatement.executeQuery();

            // iterate through the java result set
            if(rs.next()){
                brandId = rs.getInt("brandId");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return brandId;

    }

    private static int getFactoryIdFromMeidcineId( int medicineId ) {
        String selectSQL = "SELECT factoryId FROM medicine WHERE medicineId = ?";
        int factoryId = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setInt(1, medicineId);

            ResultSet rs = preparedStatement.executeQuery();

            // iterate through the java resultset
            if(rs.next()){
                factoryId = rs.getInt("factoryId");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return factoryId;
    }

    private static int getCalendarKeyFromMonthAndYear( int year, int month ) {
        String selectSQL = "SELECT calendarKey FROM calendar " +
                           "WHERE fullDate = ? " +
                           "AND "                +
                           "date = ? "           +
                           "AND "                +
                           "month = ? "          +
                           "AND "                +
                           "quarter = ? "        +
                           "AND "                +
                           "year = ?";

        int cKey = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, "");
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, year);


            ResultSet rs = preparedStatement.executeQuery();

            // iterate through the java resultset
            if(rs.next()){
                cKey = rs.getInt("calendarKey");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return cKey;
    }

    private static int getCalendarKeyFromYear( int year ) {
        String selectSQL = "SELECT calendarKey FROM calendar " +
                           "WHERE fullDate = ? " +
                           "AND "                +
                           "date = ? "           +
                           "AND "                +
                           "month = ? "          +
                           "AND "                +
                           "quarter = ? "        +
                           "AND "                +
                           "year = ?";

        int cKey = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, "");
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, year);


            ResultSet rs = preparedStatement.executeQuery();

            // iterate through the java result set
            if(rs.next()){
                cKey = rs.getInt("calendarKey");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return cKey;
    }

    private static int getCalendarKeyFromQuarterAndYear( int year, int quarter ) {
        String selectSQL = "SELECT calendarKey FROM calendar " +
                           "WHERE fullDate = ? " +
                           "AND "                +
                           "date = ? "           +
                           "AND "                +
                           "month = ? "          +
                           "AND "                +
                           "quarter = ? "        +
                           "AND "                +
                           "year = ?";

        int cKey = -1;

        try {
            Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, "");
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 0);
            preparedStatement.setInt(4, quarter);
            preparedStatement.setInt(5, year);


            ResultSet rs = preparedStatement.executeQuery();

            // Iterate through the java result set
            if(rs.next()){
                cKey = rs.getInt("calendarKey");
            }

            conn.close();
        }catch( Exception e ){
            e.printStackTrace();
        }

        return cKey;
    }
    private static void updateMHashTbl( SaleTransactionBean saleTransaction, Hashtable<Integer, Double> mHashTbl ){
        int medicineKey   = saleTransaction.getMedicineId();
        double totalPrice = saleTransaction.getTotalPrice();

        if (mHashTbl.containsKey(medicineKey)) {
            mHashTbl.put(medicineKey, mHashTbl.get(medicineKey) + totalPrice);
        } else {
            mHashTbl.put(medicineKey, totalPrice);
        }
    }

    private static void updateBHashTbl( SaleTransactionBean saleTransaction, Hashtable<Integer, Double> bHashTbl ){
        int medicineKey   = saleTransaction.getMedicineId();
        double totalPrice = saleTransaction.getTotalPrice();

        int brandKey = getBrandIdFromMedicineId(medicineKey);

        if ( brandKey != -1 ) {
            if (bHashTbl.containsKey(brandKey)) {
                bHashTbl.put(brandKey, bHashTbl.get(brandKey) + totalPrice);
            } else {
                bHashTbl.put(brandKey, totalPrice);
            }
        }else {
            // impossible to be here
            System.out.println("Not find brandKey!!!");
        }
    }

    private static void updateFHashTbl( SaleTransactionBean saleTransaction, Hashtable<Integer, Double> fHashTbl ){
        int medicineKey   = saleTransaction.getMedicineId();
        double totalPrice = saleTransaction.getTotalPrice();

        int factoryKey = getFactoryIdFromMeidcineId(medicineKey);

        if ( factoryKey != -1 ) {
            if (fHashTbl.containsKey(factoryKey)) {
                fHashTbl.put(factoryKey, fHashTbl.get(factoryKey) + totalPrice);
            } else {
                fHashTbl.put(factoryKey, totalPrice);
            }
        }else {
            // impossible to be here
            System.out.println("Not find factoryKey!!!");
        }
    }

    private static void updateBrandTransactionTbl( Hashtable<Integer, Double> bHashTbl, int year, int month, String tblName, int calendarKey ) {
        String insertSQL = "INSERT INTO " + tblName + " (calendarKey, brandKey, totalPrice) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE " + tblName + " SET totalPrice = ? WHERE calendarKey = ? AND brandKey = ?";
        double totalPrice = 0.0;

        Set<Integer> brandSet = bHashTbl.keySet();
        Iterator<Integer> iterator = brandSet.iterator();

        while( iterator.hasNext() ) {
            int brandKey = iterator.next();

            // is this record exists
            totalPrice = getTotalPriceFromBrandTransaction( brandKey, calendarKey, tblName );

            if ( totalPrice == 0.0 ) {
                try {
                    Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

                    PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
                    prepareStmt.setInt(1, calendarKey);
                    prepareStmt.setInt(2, brandKey);
                    prepareStmt.setDouble(3, bHashTbl.get(brandKey) );

                    prepareStmt.execute();

                    conn.close();
                }catch( Exception e ){
                    e.printStackTrace();
                }
            }else {
                try {
                    Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

                    PreparedStatement prepareStmt = conn.prepareStatement(updateSQL);
                    prepareStmt.setDouble(1, totalPrice + bHashTbl.get(brandKey) );
                    prepareStmt.setInt(2, calendarKey);
                    prepareStmt.setInt(3, brandKey);

                    prepareStmt.execute();

                    conn.close();
                }catch( Exception e ){
                    e.printStackTrace();
                }
            }
        }
    }

    private static void updateFactoryTransactionTbl( Hashtable<Integer, Double> fHashTbl, int year, int month, String tblName, int calendarKey ) {
        String insertSQL = "INSERT INTO " + tblName + " (calendarKey, factoryKey, totalPrice) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE " + tblName + " SET totalPrice = ? WHERE calendarKey = ? AND factoryKey = ?";
        double totalPrice = 0.0;

        Set<Integer> factorySet = fHashTbl.keySet();
        Iterator<Integer> iterator = factorySet.iterator();

        while( iterator.hasNext() ) {
            int factoryKey = iterator.next();

            // is this record exists
            totalPrice = getTotalPriceFromFactoryTransaction( factoryKey, calendarKey, tblName );

            if ( totalPrice == 0.0 ) {
                try {
                    Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

                    PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
                    prepareStmt.setInt(1, calendarKey);
                    prepareStmt.setInt(2, factoryKey);
                    prepareStmt.setDouble(3, fHashTbl.get(factoryKey) );

                    prepareStmt.execute();

                    conn.close();
                }catch( Exception e ){
                    e.printStackTrace();
                }
            }else {
                try {
                    Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

                    PreparedStatement prepareStmt = conn.prepareStatement(updateSQL);
                    prepareStmt.setDouble(1, totalPrice + fHashTbl.get(factoryKey) );
                    prepareStmt.setInt(2, calendarKey);
                    prepareStmt.setInt(3, factoryKey);

                    prepareStmt.execute();

                    conn.close();
                }catch( Exception e ){
                    e.printStackTrace();
                }
            }
        }
    }

    private static void updateMedicineTransactionTbl( Hashtable<Integer, Double> mHashTbl, int year, int month, String tblName, int calendarKey ) {
        String insertSQL = "INSERT INTO " + tblName + " (calendarKey, medicineKey, totalPrice) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE " + tblName + " SET totalPrice = ? WHERE calendarKey = ? AND medicineKey = ?";
        double totalPrice = 0.0;

        Set<Integer> medicineSet = mHashTbl.keySet();
        Iterator<Integer> iterator = medicineSet.iterator();

        while( iterator.hasNext() ) {
            int medicineKey = iterator.next();

            // is this record exists
            totalPrice = getTotalPriceFromMedicineTransaction( medicineKey, calendarKey, tblName );

            if ( totalPrice == 0.0 ) {
                try {
                    Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

                    PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
                    prepareStmt.setInt(1, calendarKey);
                    prepareStmt.setInt(2, medicineKey);
                    prepareStmt.setDouble(3, mHashTbl.get(medicineKey) );

                    prepareStmt.execute();

                    conn.close();
                }catch( Exception e ){
                    e.printStackTrace();
                }
            }else {
                try {
                    Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql

                    PreparedStatement prepareStmt = conn.prepareStatement(updateSQL);
                    prepareStmt.setDouble(1, totalPrice + mHashTbl.get(medicineKey) );
                    prepareStmt.setInt(2, calendarKey);
                    prepareStmt.setInt(3, medicineKey);

                    prepareStmt.execute();

                    conn.close();
                }catch( Exception e ){
                    e.printStackTrace();
                }
            }
        }
    }

    private static double getTotalPriceFromBrandTransaction( int brandKey, int calendarKey, String tblName ) {
        String selectSQL = "SELECT totalPrice FROM " + tblName + " WHERE calendarKey = ? AND brandKey = ?";
        double totalPrice = 0.0;

        if ( calendarKey != -1 ) {
            try {
                Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
                PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setInt(1, calendarKey);
                preparedStatement.setInt(2, brandKey);

                ResultSet rs = preparedStatement.executeQuery();

                // iterate through the java resultset
                if(rs.next()){
                    totalPrice = rs.getDouble("totalPrice");
                }

                conn.close();
            }catch( Exception e ){
                e.printStackTrace();
            }
        }else {
            System.out.println("Not find calendarKey");
        }

        return totalPrice;
    }

    private static double getTotalPriceFromFactoryTransaction( int factoryKey, int calendarKey, String tblName ) {
        String selectSQL = "SELECT totalPrice FROM " + tblName + " WHERE calendarKey = ? AND factoryKey = ?";
        double totalPrice = 0.0;

        if ( calendarKey != -1 ) {
            try {
                Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
                PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setInt(1, calendarKey);
                preparedStatement.setInt(2, factoryKey);

                ResultSet rs = preparedStatement.executeQuery();

                // iterate through the java resultset
                if(rs.next()){
                    totalPrice = rs.getDouble("totalPrice");
                }

                conn.close();
            }catch( Exception e ){
                e.printStackTrace();
            }
        }else {
            System.out.println("Not find calendarKey");
        }

        return totalPrice;
    }

    private static double getTotalPriceFromMedicineTransaction( int medicineKey, int calendarKey, String tblName ) {
        String selectSQL = "SELECT totalPrice FROM " + tblName + " WHERE calendarKey = ? AND medicineKey = ?";
        double totalPrice = 0.0;

        if ( calendarKey != -1 ) {
            try {
                Connection conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
                PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
                preparedStatement.setInt(1, calendarKey);
                preparedStatement.setInt(2, medicineKey);

                ResultSet rs = preparedStatement.executeQuery();

                // iterate through the java resultset
                if(rs.next()){
                    totalPrice = rs.getDouble("totalPrice");
                }

                conn.close();
            }catch( Exception e ){
                e.printStackTrace();
            }
        }else {
            System.out.println("Not find calendarKey");
        }

        return totalPrice;
    }

    //Test code, will DELETE
    //public static void main(String[] args) {
        //ArrayList<SaleTransactionBean> s = new ArrayList<>();
        //s.add( new SaleTransactionBean( -1, 100, 1000, 2000, 123456) );
        //s.add( new SaleTransactionBean( -1, 200, 2000, 3000, 234567) );
        //persistSaleTransaction(s);
    //}
}
