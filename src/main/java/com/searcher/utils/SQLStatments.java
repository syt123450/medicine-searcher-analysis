package com.searcher.utils;

/**
 * Created by zchholmes on 2017/5/8.
 */
public class SQLStatments {

    public static final String yesterdayPie
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND c.fullDate = subdate(current_date, 1) " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.factoryName " +
            ";";

    // Pie No Factory Level No Time Level
    public static final String SumSaleTransaction
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND c.year >= 2012 " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.factoryName;";

    // Sankey No Factory Level No Time Level part0
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

    // Sankey No Factory Level No Time Level part1
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

    // Line No Factory Level No Time Level
//    public static final String SumSaleTransactionAllFactoryTime
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
//            "FROM SaleTransaction s, Medicine m, Calendar c " +
//            "WHERE s.medicineKey <> -1 " +
//            "AND c.year >= 2012 " +
//            "AND s.medicineKey = m.medicineKey " +
//            "AND s.calendarKey = c.calendarKey " +
//            "GROUP BY m.factoryName, c.year " +
//            "ORDER BY c.year, m.factoryName ASC " +
//            ";";

    public static final String SumSaleTransactionAllFactoryTime
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
                    "FROM SaleTransaction s, Medicine m, Calendar c " +
                    "WHERE s.medicineKey <> -1 " +
                    "AND c.year >= 2012 " +
                    "AND s.medicineKey = m.medicineKey " +
                    "AND s.calendarKey = c.calendarKey " +
                    "AND m.factoryID <> 5 " +
                    "AND m.factoryID <> 4 " +
                    "AND m.factoryID <> 3 " +
                    "GROUP BY m.factoryName, c.year " +
                    "ORDER BY c.year, m.factoryName ASC " +
                    ";";

}
