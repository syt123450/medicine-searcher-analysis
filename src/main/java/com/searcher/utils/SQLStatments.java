package com.searcher.utils;

/**
 * Created by zchholmes on 2017/5/8.
 */
public class SQLStatments {
    public static final String SumSaleTransaction
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
                    "AND c.year >= 2012" +
                    "AND s.medicineKey = m.medicineKey " +
                    "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.factoryName, c.year;";

    public static final String SumSaleTransactionNoYear
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName " +
            "FROM SaleTransaction s, Medicine m " +
            "WHERE s.medicineKey <> -1 " +
                    "AND s.medicineKey = m.medicineKey " +
            "GROUP BY m.factoryName;";

    public static final String SumSaleTransactionNoFactory
            =
            "SELECT SUM(s.totalPrice) as totalSum, c.year " +
            "FROM SaleTransaction s, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
                    "AND c.year >= 2012" +
                    "AND s.calendarKey = c.calendarKey " +
            "GROUP BY c.year;";

    public static final String SumSaleTransactionAll_0
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND c.year >=2012 " +
            "AND s.medicineKey = m.medicineKey " +
            "GROUP BY m.brandName, m.factoryName " +
            "ORDER BY m.factoryName, totalSum DESC " +
                    ";";

    public static final String SumSaleTransactionAll_1
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND c.year >=2012 " +
            "AND s.medicineKey = m.medicineKey " +
            "GROUP BY m.medicineName, m.brandName " +
            "ORDER BY m.brandName, totalSum DESC ;" +
                    ";";

}
