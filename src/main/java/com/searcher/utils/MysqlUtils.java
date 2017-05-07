package com.searcher.utils;

import com.searcher.model.entity.SaleTransactionBean;
import com.searcher.model.entity.SearchTransactionBean;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ss on 2017/5/3.
 */
public class MysqlUtils {
    private static final String URL      = "jdbc:mysql://localhost:3306/226operation?serverTimezone=GMT";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";
    private static final String saleTransactionTbl = "saleTransaction";

    public static void persistSaleTransaction(ArrayList<SaleTransactionBean> saleTransactionBeans) {
        // Check table existence
        createSaleTransactionTable();

        // Insert calender for TODAY and get CalendarID


        for ( int idx = 0; idx < saleTransactionBeans.size(); idx++ ) {
            SaleTransactionBean saleTransaction = saleTransactionBeans.get(idx);
            insertIntoSaleTransactionTbl(saleTransaction);
        }
    }

    public static void persistSearchTransaction(ArrayList<SearchTransactionBean> searchTransactionBeans) {

    }

    private static void createSaleTransactionTable(){
        String sql = "CREATE TABLE IF NOT EXISTS saleTransaction ( " +
                     "saleTransactionKey    INT     NOT NULL," +
                     "quantity              INT     NOT NULL," +
                     "calendarKey           INT     NOT NULL," +
                     "medicineKey           INT     NOT NULL," +
                     "storeKey              INT     NOT NULL," +
                     "customerKey           INT     NOT NULL," +
                     "factoryKey            INT     NOT NULL," +
                     "totalPrice            DOUBLE  NOT NULL," +
                     "PRIMARY KEY (saleTransactionKey) );";

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt  = conn.createStatement();
            stmt.execute( sql );

            stmt.close();
            conn.close();
        }catch ( Exception e ) {
            e.printStackTrace();
        }
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

        String sql = String.format( "INSERT INTO saleTransaction " +
                                    "(saleTransactionKey, quantity, calendarKey, medicineKey, storeKey, customerKey, factoryKey, totalPrice)" +
                                    " VALUES " +
                                    "(%d, %d, %d, %d, %d, %d, %d, %f)",
                                    saleTransKey, quantity, calendarKey, medicineKey, storeKey, customerKey, factoryKey, totalPrice );

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
}
