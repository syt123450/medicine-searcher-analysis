package com.searcher.utils;

/**
 * Created by zchholmes on 2017/5/8.
 */
public class SQLStatments {
    public static final String delimeter_1 = "#$1$#";
    public static final String delimeter_2 = "#$2$#";
    public static final String delimeter_3 = "#$3$#";


    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** *****           General Cases             ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

    public static final String SumSaleTransactionPieFrame
            =
            "SELECT SUM(s.totalPrice) as totalSum, #$1$# " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName like ? " +
            "AND m.brandName like ? " +
            "AND m.medicineName like ? " +
            "AND c.year >= ? " +
            "AND c.year <= ? " +
            "AND c.quarter >= ? " +
            "AND c.quarter <= ? " +
            "AND c.month >= ? " +
            "AND c.month <= ? " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY #$1$# " +
            "ORDER BY totalSum DESC " +
            ";";

    public static final String PieArgsFactories = " m.factoryName ";
    public static final String PieArgsFactory = " m.brandName, m.factoryName ";
    public static final String PieArgsBrand = " m.medicineName, m.brandName, m.factoryName ";


    public static final String SumSaleTransactionLineFrame
            =
            "SELECT SUM(s.totalPrice) as totalSum, #$1$# " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName like ? " +
            "AND m.brandName like ? " +
            "AND m.medicineName like ? " +
            "AND c.year >= ? " +
            "AND c.year <= ? " +
            "AND c.quarter >= ? " +
            "AND c.quarter <= ? " +
            "AND c.month >= ? " +
            "AND c.month <= ? " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY #$1$# " +
            "ORDER BY #$1$# ASC " +
            ";";

    public static final String LineArgsYears = " c.year, m.factoryName ";
    public static final String LineArgsYear = " c.year, c.quarter, m.factoryName ";
    public static final String LineArgsQuarter = " c.year, c.quarter, c.month, m.factoryName ";


    public static final String SumSaleTransactionCombo_0
            =
            "SELECT SUM(s.totalPrice) as totalSum, #$1$#, #$2$# " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName like ? " +
            "AND m.brandName like ? " +
            "AND m.medicineName like ? " +
            "AND c.year >= ? " +
            "AND c.year <= ? " +
            "AND c.quarter >= ? " +
            "AND c.quarter <= ? " +
            "AND c.month >= ? " +
            "AND c.month <= ? " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY #$1$#, #$2$# " +
            "ORDER BY #$2$#, #$1$#, totalSum DESC " +
            ";";
    public static final String ComboArgsFactories = " m.factoryName ";
    public static final String ComboArgsFactory = " m.factoryName, m.brandName ";
    public static final String ComboArgsBrand = " m.factoryName, m.brandName, m.medicineName ";

    public static final String ComboArgsYears = " c.year ";
    public static final String ComboArgsYear = " c.quarter, c.year ";
    public static final String ComboArgsQuarter = " c.month, c.quarter, c.year ";


    public static final String SumSaleTransactionCombo_1
            =
            "SELECT AVG(t.totalSum) as avgSum, #$3$# " +
            "FROM ( " +
            "SELECT SUM(s.totalPrice) as totalSum, #$1$#, #$2$# " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName like ? " +
            "AND m.brandName like ? " +
            "AND m.medicineName like ? " +
            "AND c.year >= ? " +
            "AND c.year <= ? " +
            "AND c.quarter >= ? " +
            "AND c.quarter <= ? " +
            "AND c.month >= ? " +
            "AND c.month <= ? " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY #$1$#, #$2$# " +
            "ORDER BY #$2$#, #$1$#, totalSum DESC " +
            ") as t " +
            "GROUP BY #$3$# " +
            ";";
    public static final String ComboArgsTYears = "t.year";
    public static final String ComboArgsTYear = "t.quarter, t.year";
    public static final String ComboArgsTQuarter = "t.month, t.quarter, t.year";



    public static final String SumSaleTransactionSankey_0
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName like ? " +
            "AND m.brandName like ? " +
            "AND m.medicineName like ? " +
            "AND c.year >= ? " +
            "AND c.year <= ? " +
            "AND c.quarter >= ? " +
            "AND c.quarter <= ? " +
            "AND c.month >= ? " +
            "AND c.month <= ? " +
            "AND s.medicineKey = m.medicineKey\n" +
            "AND s.calendarKey = c.calendarKey\n" +
            "GROUP BY m.brandName, m.factoryName\n" +
            "ORDER BY m.factoryName, totalSum DESC " +
            ";";

    public static final String SumSaleTransactionSankey_1
            = "SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName " +
            "FROM SaleTransaction s, Medicine m, Calendar c " +
            "WHERE s.medicineKey <> -1 " +
            "AND m.factoryName like ? " +
            "AND m.brandName like ? " +
            "AND m.medicineName like ? " +
            "AND c.year >= ? " +
            "AND c.year <= ? " +
            "AND c.quarter >= ? " +
            "AND c.quarter <= ? " +
            "AND c.month >= ? " +
            "AND c.month <= ? " +
            "AND s.medicineKey = m.medicineKey " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.medicineName, m.brandName, m.factoryName " +
            "ORDER BY m.factoryName, totalSum DESC " +
            ";";



    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****   Legacy    ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */


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
