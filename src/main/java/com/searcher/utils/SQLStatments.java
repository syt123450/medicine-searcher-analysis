package com.searcher.utils;

/**
 * Created by zchholmes on 2017/5/8.
 */
public class SQLStatments {

    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** *****   Special   ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

    // Pie for yesterday
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


    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** *****        No Factory & No Year         ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

    // (NFL,NTL) Pie
    public static final String SumSaleTransaction
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND c.year >= 2012 " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.factoryName;";

    // (NFL,NTL) Sankey part0
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

    // (NFL,NTL) Sankey part1
    public static final String SumSaleTransactionAll_1
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND c.year >=2012 " +
            "AND s.medicineKey = m.medicineKey " +
            "GROUP BY m.medicineName, m.brandName, m.factoryName " +
            "ORDER BY m.factoryName, totalSum DESC ;" +
                    ";";

    // (NFL,NTL) Line
    public static final String SumSaleTransactionAllFactoryTime
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND c.year >= 2012 " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.factoryName, c.year " +
            "ORDER BY c.year, m.factoryName ASC " +
            ";";

    // (NFL,NTL) Combo Main Columns
    public static final String SumSaleTransactionAllFactoryTimeSum
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND c.year >=2013 " +
            "AND s.medicineKey = m.medicineKey " +
            "GROUP BY c.year, m.factoryName " +
            "ORDER BY c.year, m.factoryName, totalSum DESC " +
            ";";

    // (NFL,NTL) Combo AVG Line
    public static final String AvgSaleTransactionAllFactoryTime
            =
            "SELECT AVG(t.totalSum) as avgSum, t.year " +
            "FROM ( " +
                "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
                "FROM SaleTransaction s, Medicine m, Calendar c " +
                "WHERE s.medicineKey <> -1 " +
                "AND c.year >=2013 " +
                "AND s.medicineKey = m.medicineKey " +
                "GROUP BY c.year, m.factoryName " +
                "ORDER BY c.year, m.factoryName, totalSum DESC " +
            ") as t " +
            "GROUP BY t.year " +
            ";";



    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** *****       One Factory & One Year        ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

    // (1F,1Y) Pie
    public static final String SumSaleTransaction1F1Y_Pie
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName = ? " +
            "AND c.year = ? " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.brandName, m.factoryName " +
            ";";

    // (1F,1Y) Line
    public static final String SumSaleTransaction1F1Y_Line
            =
            "SELECT SUM(s.totalPrice) as totalSum, c.quarter, c.year, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName = ? " +
            "AND c.year = ? " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY c.quarter, c.year " +
            "ORDER BY c.quarter, c.year ASC " +
            ";";

    // (1F,1Y) Combo main columns
    public static final String SumSaleTransaction1F1Y_Combo_0
            = "SELECT SUM(s.totalPrice) as totalSum, m.brandName, c.quarter " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName = ? " +
            "AND c.year = ? " +
            "AND s.medicineKey = m.medicineKey " +
            "GROUP BY m.brandName, c.quarter " +
            "ORDER BY c.quarter, m.brandName, totalSum DESC " +
            ";";

    // (1F,1Y) Combo AVG Line
    public static final String SumSaleTransaction1F1Y_Combo_1
            = "SELECT AVG(t.totalSum) as avgSum, t.quarter " +
            "FROM ( " +
            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, c.quarter " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName = ? " +
            "AND c.year = ? " +
            "AND s.medicineKey = m.medicineKey " +
            "GROUP BY m.brandName, c.quarter " +
            "ORDER BY c.quarter, m.brandName, totalSum DESC " +
            ") as t " +
            "GROUP BY t.quarter " +
            ";";

    public static final String SumSaleTransaction1F1Y_Sankey_0
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName = ? " +
            "AND c.year = ? " +
            "AND s.medicineKey = m.medicineKey " +
            "GROUP BY m.brandName, m.factoryName " +
            "ORDER BY m.factoryName, totalSum DESC " +
            ";";

    public static final String SumSaleTransaction1F1Y_Sankey_1
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName = ? " +
            "AND c.year = ? " +
            "AND s.medicineKey = m.medicineKey " +
            "GROUP BY m.medicineName, m.brandName, m.factoryName " +
            "ORDER BY m.factoryName, totalSum DESC " +
            ";";
}
