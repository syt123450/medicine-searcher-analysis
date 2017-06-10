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
    //private static final String URL      = "jdbc:mysql://localhost:3306/finalProj?serverTimezone=GMT&useSSL=false";
    //private static final String USERNAME = "root";
    //private static final String PASSWORD = "123456";
    private static final String URL      = "jdbc:mysql://localhost:3306/226analysis?serverTimezone=GMT&useSSL=false";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";

    public static void persistSaleTransaction(ArrayList<SaleTransactionBean> saleTransactionBeans) {
        Calendar c  = Calendar.getInstance();
        // Connect to MySQL
        Connection conn = connectToMysql();

        Hashtable<Integer, Double> bHashTbl = new Hashtable<Integer, Double>();
        Hashtable<Integer, Double> mHashTbl = new Hashtable<Integer, Double>();
        Hashtable<Integer, Double> fHashTbl = new Hashtable<Integer, Double>();

        ResultSet medicineTbl  = retrieveMedicineTbl( conn );
        ResultSet medicineMTbl = retrieveMedicineTransactionTbl( conn, "medicineMonthTransaction" );
        ResultSet medicineQTbl = retrieveMedicineTransactionTbl( conn, "medicineQuarterTransaction" );
        ResultSet medicineYTbl = retrieveMedicineTransactionTbl( conn, "medicineYearTransaction" );
        ResultSet brandMTbl    = retrieveBrandTransactionTbl( conn, "brandMonthTransaction" );
        ResultSet brandQTbl    = retrieveBrandTransactionTbl( conn, "brandQuarterTransaction" );
        ResultSet brandYTbl    = retrieveBrandTransactionTbl( conn, "brandYearTransaction" );
        ResultSet factoryMTbl  = retrieveFactoryTransactionTbl( conn, "factoryMonthTransaction" );
        ResultSet factoryQTbl  = retrieveFactoryTransactionTbl( conn, "factoryQuarterTransaction" );
        ResultSet factoryYTbl  = retrieveFactoryTransactionTbl( conn, "factoryYearTransaction" );

        int year    = c.get(Calendar.YEAR);
        int month   = c.get(Calendar.MONTH) + 1;   // 0 to 11
        int date    = c.get(Calendar.DAY_OF_MONTH);
        int quarter = c.get(Calendar.MONTH)/3 + 1;
        String fullDate = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(date);

        // Insert today into calendar table
        updateCalendarTbl( conn, fullDate, date, month, quarter, year);

        // Get calendarKey
        int cKey = getCalendarKeyOfDate( conn, fullDate );

        for ( int idx = 0; idx < saleTransactionBeans.size(); idx++ ) {
            SaleTransactionBean saleTransaction = saleTransactionBeans.get(idx);

            // Insert every record in operational db into analytical db, saleTransaction tbl
            insertIntoSaleTransactionTbl( conn, saleTransaction, cKey );

            int medicineId = saleTransaction.getMedicineId();
            int brandId    = getBrandIdFromMedicineId( medicineTbl, medicineId );
            int factoryId  = getFactoryIdFromMedicineId( medicineTbl, medicineId );
            double price   = saleTransaction.getTotalPrice();

            // Update hash tables
            addToBHashTbl( bHashTbl, brandId, price );
            addToMHashTbl( mHashTbl, medicineId, price );
            addToFHashTbl( fHashTbl, factoryId, price );
        }

        cKey  = getCalendarKeyFromMonthAndYear( conn, year, month );
        // Update MonthTransaction table
        updateBrandTransactionTbl( conn, bHashTbl,"brandMonthTransaction", cKey, brandMTbl ) ;
        updateFactoryTransactionTbl( conn, fHashTbl, "factoryMonthTransaction", cKey, factoryMTbl );
        updateMedicineTransactionTbl( conn, mHashTbl, "medicineMonthTransaction", cKey, medicineMTbl );

        cKey = getCalendarKeyFromQuarterAndYear( conn, year, quarter );
        // Update QuarterTransaction table
        updateBrandTransactionTbl( conn, bHashTbl, "brandQuarterTransaction", cKey, brandQTbl ) ;
        updateFactoryTransactionTbl( conn, fHashTbl, "factoryQuarterTransaction", cKey, factoryQTbl );
        updateMedicineTransactionTbl( conn, mHashTbl,"medicineQuarterTransaction", cKey, medicineQTbl );

        cKey = getCalendarKeyFromYear( conn, year );
        // Update YearTransaction table
        updateBrandTransactionTbl( conn, bHashTbl, "brandYearTransaction", cKey, brandYTbl ) ;
        updateFactoryTransactionTbl( conn, fHashTbl, "factoryYearTransaction", cKey, factoryYTbl );
        updateMedicineTransactionTbl( conn, mHashTbl, "medicineYearTransaction", cKey, medicineYTbl );

        // Disconnect from MySQL
        disconnectFromMysql( conn );
    }

    public static void persistSearchTransaction(ArrayList<SearchTransactionBean> searchTransactionBeans) {
        // DO NOTHING. RESERVED.
    }

    private static Connection connectToMysql() {
        Connection conn = null;

        try {
            conn   = DriverManager.getConnection( URL, USERNAME, PASSWORD );    //connect to mysql
        }catch( Exception e ){
            e.printStackTrace();
        }

        return conn;
    }

    private static void disconnectFromMysql( Connection conn ) {
        try {
            conn.close();
        }catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private static void insertIntoSaleTransactionTbl( Connection conn, SaleTransactionBean saleTransaction, int calendarKey ) {
        long time         = saleTransaction.getTime();
        int medicineKey   = saleTransaction.getMedicineId();
        int storeKey      = saleTransaction.getStoreId();
        int customerKey   = saleTransaction.getCustomerId();
        double totalPrice = saleTransaction.getTotalPrice();

        String insertSQL  = "INSERT INTO saleTransaction " +
                            " (calendarKey, medicineKey, storeKey, customerKey, totalPrice)" +
                            " VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, calendarKey);
            prepareStmt.setInt(2, medicineKey);
            prepareStmt.setInt(3, storeKey);
            prepareStmt.setInt(4, customerKey);
            prepareStmt.setDouble(5, totalPrice);

            prepareStmt.execute();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void updateCalendarTbl( Connection conn, String fullDate, int date, int month, int quarter, int year ) {
        insertIntoCalenderTbl( conn, fullDate, date, month, quarter, year );
        insertYearMonthQuarter( conn, year, month, date );
    }

    private static void insertIntoCalenderTbl( Connection conn, String fullDate, int date, int month, int quarter, int year ) {
        String insertSQL = "INSERT INTO calendar (fullDate, date, month, quarter, year) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setString(1, fullDate);
            prepareStmt.setInt(2, date);
            prepareStmt.setInt(3, month);
            prepareStmt.setInt(4, quarter);
            prepareStmt.setInt(5, year);

            prepareStmt.execute();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void insertYearMonthQuarter( Connection conn, int year, int month, int date ) {
         if ( month == 1 && date == 1 ) {
            // Insert new Year
            insertYearIntoCalenderTbl( conn, year);

            // Insert month of that year
            for ( int m = 1; m <= 12; m++ ) {
                insertMonthIntoCalenderTbl( conn, year, m );
            }

            // Insert quarter of that year
            for ( int q = 1; q <=4; q++) {
                insertQuarterIntoCalenderTbl( conn, year, q );
            }
        }
    }

    private static void insertYearIntoCalenderTbl( Connection conn, int year ) {
        String insertSQL = "INSERT INTO calendar (year) VALUES (?)";

        try {
            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, year);

            prepareStmt.execute();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void insertMonthIntoCalenderTbl( Connection conn, int year, int month ) {
        String insertSQL = "INSERT INTO calendar (year, month) VALUES (?, ?)";

        try {
            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, year);
            prepareStmt.setInt(2, month);

            prepareStmt.execute();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void insertQuarterIntoCalenderTbl( Connection conn, int year, int quarter) {
        String insertSQL = "INSERT INTO calendar (year, month) VALUES (?, ?)";

        try {
            PreparedStatement prepareStmt = conn.prepareStatement(insertSQL);
            prepareStmt.setInt(1, year);
            prepareStmt.setInt(2, quarter);

            prepareStmt.execute();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static int getCalendarKeyOfDate( Connection conn, String date ) {
        String selectSQL = "SELECT calendarKey, fullDate FROM calendar WHERE fullDate= ? " ;
        int key = -1;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
            preparedStatement.setString(1, date);

            ResultSet rs = preparedStatement.executeQuery();

            // iterate through the java resultset
            if (rs.next()){
                key = rs.getInt("calendarKey");
            }
        }catch( Exception e ){
            e.printStackTrace();
        }

        return key;
    }

    private static int getCalendarKeyFromMonthAndYear( Connection conn, int year, int month ) {
        String selectSQL = "SELECT calendarKey FROM calendar WHERE fullDate = ? " +
                           "AND date = ? "           +
                           "AND month = ? "          +
                           "AND quarter = ? "        +
                           "AND year = ?";

        int cKey = -1;

        try {
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
        }catch( Exception e ){
            e.printStackTrace();
        }

        return cKey;
    }

    private static int getCalendarKeyFromYear( Connection conn, int year ) {
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
        }catch( Exception e ){
            e.printStackTrace();
        }

        return cKey;
    }

    private static int getCalendarKeyFromQuarterAndYear( Connection conn, int year, int quarter ) {
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
        }catch( Exception e ){
            e.printStackTrace();
        }

        return cKey;
    }

    private static int getBrandIdFromMedicineId( ResultSet retSet, int medicineId ) {
        int brandId = -1;

        if ( retSet != null ) {
            try {
                while (retSet.next()) {
                    if ( retSet.getInt("medicineId") == medicineId ) {
                        brandId = retSet.getInt("brandId");
                        break;
                    }
                }
            }catch( Exception e ) {
                e.printStackTrace();
            }
        }

        return brandId;
    }

    private static int getFactoryIdFromMedicineId( ResultSet retSet, int medicineId ) {
        int factoryId = -1;

        if ( retSet != null ) {
            try {
                while (retSet.next()) {
                    if ( retSet.getInt("medicineId") == medicineId ) {
                        factoryId = retSet.getInt("factoryId");
                        break;
                    }
                }
            }catch( Exception e ) {
                e.printStackTrace();
            }
        }

        return factoryId;
    }

    private static double getTotalPriceFromBrandTransaction( ResultSet rs, int brandKey, int calendarKey ) {
        double p = 0.0;

        if ( rs != null ) {
            try {
                while (rs.next()) {
                    if (brandKey == rs.getInt("brandKey") &&
                            calendarKey == rs.getInt("calendarKey") ) {
                        p = rs.getDouble("totalPrice");
                    }
                }
            }catch( Exception e ) {
                e.printStackTrace();
            }
        }

        return p;
    }

    private static double getTotalPriceFromFactoryTransaction( ResultSet rs, int factoryKey, int calendarKey ) {
        double p = 0.0;

        if ( rs != null ) {
            try {
                while (rs.next()) {
                    if ( factoryKey == rs.getInt("factoryKey") &&
                            calendarKey == rs.getInt("calendarKey") ) {
                        p = rs.getDouble("totalPrice");
                    }
                }
            }catch( Exception e ) {
                e.printStackTrace();
            }
        }

        return p;
    }

    private static double getTotalPriceFromMedicineTransaction( ResultSet rs, int medicineKey, int calendarKey ) {
        double p = 0.0;

        if ( rs != null ) {
            try {
                while (rs.next()) {
                    if ( medicineKey == rs.getInt("medicineKey") &&
                            calendarKey == rs.getInt("calendarKey") ) {
                        p = rs.getDouble("totalPrice");
                    }
                }
            }catch( Exception e ) {
                e.printStackTrace();
            }
        }

        return p;
    }

    private static ResultSet retrieveMedicineTbl( Connection conn ) {
        String selectSQL = "SELECT medicineKey, medicineId, medicineName, price, brandId, brandName, factoryId, factoryName FROM medicine";
        ResultSet rs = null;

        try {
            PreparedStatement prepareStmtSelect = conn.prepareStatement(selectSQL);
            rs = prepareStmtSelect.executeQuery( selectSQL );
        }catch ( Exception e ) {
            e.printStackTrace();
        }

        return rs;
    }

    private static ResultSet retrieveMedicineTransactionTbl( Connection conn, String tblName ) {
        String selectSQL = "SELECT calendarKey, medicineKey, totalPrice FROM " + tblName;
        ResultSet rs = null;

        try {
            PreparedStatement prepareStmtSelect = conn.prepareStatement(selectSQL);
            rs = prepareStmtSelect.executeQuery( selectSQL );
        }catch ( Exception e ) {
            e.printStackTrace();
        }

        return rs;
    }

    private static ResultSet retrieveBrandTransactionTbl( Connection conn, String tblName ) {
        String selectSQL = "SELECT calendarKey, brandKey, totalPrice FROM " + tblName;
        ResultSet rs = null;

        try {
            PreparedStatement prepareStmtSelect = conn.prepareStatement(selectSQL);
            rs = prepareStmtSelect.executeQuery( selectSQL );
        }catch ( Exception e ) {
            e.printStackTrace();
        }

        return rs;
    }

    private static ResultSet retrieveFactoryTransactionTbl( Connection conn, String tblName ) {
        String selectSQL = "SELECT calendarKey, factoryKey, totalPrice FROM " + tblName;
        ResultSet rs = null;

        try {
            PreparedStatement prepareStmtSelect = conn.prepareStatement(selectSQL);
            rs = prepareStmtSelect.executeQuery( selectSQL );
        }catch ( Exception e ) {
            e.printStackTrace();
        }

        return rs;
    }

    private static void addToMHashTbl( Hashtable<Integer, Double> mHashTbl, int medicineKey, double totalPrice ){
        if (mHashTbl.containsKey(medicineKey)) {
            mHashTbl.put(medicineKey, mHashTbl.get(medicineKey) + totalPrice);
        } else {
            mHashTbl.put(medicineKey, totalPrice);
        }
    }

    private static void addToBHashTbl( Hashtable<Integer, Double> bHashTbl, int brandKey, double totalPrice ){
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

    private static void addToFHashTbl( Hashtable<Integer, Double> fHashTbl, int factoryKey, double totalPrice ){
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

    private static void updateBrandTransactionTbl( Connection conn, Hashtable<Integer, Double> bHashTbl, String tblName, int calendarKey, ResultSet rs) {
        String insertSQL  = "INSERT INTO " + tblName + " (calendarKey, brandKey, totalPrice) VALUES (?, ?, ?)";
        String updateSQL  = "UPDATE " + tblName + " SET totalPrice = ? WHERE calendarKey = ? AND brandKey = ?";
        int brandKey = -1;
        double totalPrice = 0.0;

        try {
            PreparedStatement prepareStmtInsert = conn.prepareStatement(updateSQL);
            PreparedStatement prepareStmtUpdate = conn.prepareStatement(insertSQL);

            Set<Integer> brandSet       = bHashTbl.keySet();
            Iterator<Integer> iterator  = brandSet.iterator();

            while ( iterator.hasNext() ) {
                brandKey = iterator.next();
                totalPrice = getTotalPriceFromBrandTransaction( rs, brandKey, calendarKey );

                if ( totalPrice == 0.0 ) {
                    // insert new record
                    prepareStmtInsert.setInt(1, calendarKey );
                    prepareStmtInsert.setInt(2, brandKey );
                    prepareStmtInsert.setDouble( 3, bHashTbl.get(brandKey) );
                    prepareStmtInsert.addBatch();
                }
                else {
                    // update existing record
                    prepareStmtUpdate.setDouble(1, totalPrice + bHashTbl.get(brandKey) );
                    prepareStmtUpdate.setInt(2, calendarKey );
                    prepareStmtUpdate.setInt(3, brandKey );
                    prepareStmtUpdate.addBatch();
                }
            }

            prepareStmtInsert.executeBatch();
            prepareStmtUpdate.executeBatch();

        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void updateFactoryTransactionTbl( Connection conn, Hashtable<Integer, Double> fHashTbl, String tblName, int calendarKey, ResultSet rs ) {
        String insertSQL = "INSERT INTO " + tblName + " (calendarKey, factoryKey, totalPrice) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE " + tblName + " SET totalPrice = ? WHERE calendarKey = ? AND factoryKey = ?";
        double totalPrice = 0.0;
        int factoryKey    = -1;

        try {
            PreparedStatement prepareStmtInsert = conn.prepareStatement(updateSQL);
            PreparedStatement prepareStmtUpdate = conn.prepareStatement(insertSQL);

            Set<Integer> brandSet       = fHashTbl.keySet();
            Iterator<Integer> iterator  = brandSet.iterator();

            while ( iterator.hasNext() ) {
                factoryKey = iterator.next();
                totalPrice = getTotalPriceFromFactoryTransaction( rs, calendarKey, factoryKey );

                if ( totalPrice == 0.0 ) {
                    // insert new record
                    prepareStmtInsert.setInt(1, calendarKey );
                    prepareStmtInsert.setInt(2, factoryKey );
                    prepareStmtInsert.setDouble( 3, fHashTbl.get(factoryKey) );
                    prepareStmtInsert.addBatch();
                }
                else {
                    // update existing record
                    prepareStmtUpdate.setDouble(1, totalPrice + fHashTbl.get(factoryKey) );
                    prepareStmtUpdate.setInt(2, calendarKey );
                    prepareStmtUpdate.setInt(3, factoryKey );
                    prepareStmtUpdate.addBatch();
                }
            }

            prepareStmtInsert.executeBatch();
            prepareStmtUpdate.executeBatch();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    private static void updateMedicineTransactionTbl( Connection conn, Hashtable<Integer, Double> mHashTbl, String tblName, int calendarKey, ResultSet rs ) {
        String insertSQL = "INSERT INTO " + tblName + " (calendarKey, medicineKey, totalPrice) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE " + tblName + " SET totalPrice = ? WHERE calendarKey = ? AND medicineKey = ?";
        double totalPrice = 0.0;
        int medicineKey = -1;


        try {
            PreparedStatement prepareStmtInsert = conn.prepareStatement(updateSQL);
            PreparedStatement prepareStmtUpdate = conn.prepareStatement(insertSQL);

            Set<Integer> brandSet       = mHashTbl.keySet();
            Iterator<Integer> iterator  = brandSet.iterator();

            while ( iterator.hasNext() ) {
                medicineKey = iterator.next();
                totalPrice = getTotalPriceFromMedicineTransaction( rs, calendarKey, medicineKey );

                if ( totalPrice == 0.0 ) {
                    // insert new record
                    prepareStmtInsert.setInt(1, calendarKey );
                    prepareStmtInsert.setInt(2, medicineKey );
                    prepareStmtInsert.setDouble( 3, mHashTbl.get(medicineKey) );
                    prepareStmtInsert.addBatch();
                }
                else {
                    // update existing record
                    prepareStmtUpdate.setDouble(1, totalPrice + mHashTbl.get(medicineKey) );
                    prepareStmtUpdate.setInt(2, calendarKey );
                    prepareStmtUpdate.setInt(3, medicineKey );
                    prepareStmtUpdate.addBatch();
                }
            }

            prepareStmtInsert.executeBatch();
            prepareStmtUpdate.executeBatch();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }

    //Test code, will DELETE
    //public static void main(String[] args) {
    //    ArrayList<SaleTransactionBean> s = new ArrayList<>();
    //    s.add( new SaleTransactionBean( -1, 100, 1000, 2000, 123456) );
    //    s.add( new SaleTransactionBean( -1, 120, 1001, 2000, 234567) );
    //    s.add( new SaleTransactionBean( -1, 200, 2000, 3000, 234567) );
    //    persistSaleTransaction(s);
    //}
}
