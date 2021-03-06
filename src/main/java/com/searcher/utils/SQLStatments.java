package com.searcher.utils;

import java.util.HashMap;

/**
 * Created by zchholmes on 2017/5/8.
 */
public class SQLStatments {
    /* Delimeters */
    // Limit "SELECT" and "GROUP BY" items and conditions
    public static final String DELIMITER_1 = "#$1$#";
    public static final String DELIMITER_2 = "#$2$#";
    public static final String DELIMITER_3 = "#$3$#";
    // Limit target tables
    public static final String DELIMITER_ST = "#$st$#";     // Sale Transaction related tables
    public static final String DELIMITER_PL = "#$pl$#";     // Product Level related tables
    // Limit search and join conditions
    public static final String DELIMITER_PREPD = "#$prepd$#"; // Search Condition used for prepared statement on Product
    public static final String DELIMITER_PREPT = "#$prept$#"; // Search Condition used for prepared statement on Time
    public static final String DELIMITER_COND = "#$cond$#"; // Join Condition related statement

    /* Constant Checking HashMaps */
    // Normal Time Level related, NO drill down action
    public static final HashMap<String, String> TIME_LEVEL = createTimeLevel();
    public static HashMap<String, String> createTimeLevel(){
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("", "YEAR");
        ret.put("year", "YEAR");
        ret.put("quarter", "QUARTER");
        ret.put("month", "MONTH");
        return ret;
    }
    // Drill Down version Time Level Map
    public static final HashMap<String, String> TIME_LEVEL_DD = createTimeLevelDD();
    public static HashMap<String, String> createTimeLevelDD(){
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("", "YEAR");
        ret.put("year", "QUARTER");
        ret.put("quarter", "MONTH");
        ret.put("month", "");
        return ret;
    }

    // Normal Product Level related, NO drill down action
    public static final HashMap<String, String> PRODUCT_LEVEL = createProductLevel();
    public static HashMap<String, String> createProductLevel(){
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("", "FACTORY");
        ret.put("factory", "FACTORY");
        ret.put("brand", "BRAND");
        ret.put("medicine", "MEDICINE");
        return ret;
    }
    // Drill Down version Product Level Map
    public static final HashMap<String, String> PRODUCT_LEVEL_DD = createProductLevelDD();
    public static HashMap<String, String> createProductLevelDD(){
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("", "FACTORY");
        ret.put("factory", "BRAND");
        ret.put("brand", "MEDICINE");
        ret.put("medicine", "");
        return ret;
    }

    /* DELIMITER Constant values and HashMaps*/
    // For DELIMITER_ST ("#$st$#")
    public static final String ST_TRANSACTION = " SaleTransaction s ";

    public static final String ST_MEDICINE_MONTH_TRANSACTION = " MedicineMonthTransaction s ";
    public static final String ST_MEDICINE_QUARTER_TRANSACTION = " MedicineQuarterTransaction s ";
    public static final String ST_MEDICINE_YEAR_TRANSACTION = " MedicineYearTransaction s ";

    public static final String ST_BRAND_MONTH_TRANSACTION = " BrandMonthTransaction s ";
    public static final String ST_BRAND_QUARTER_TRANSACTION = " BrandQuarterTransaction s ";
    public static final String ST_BRAND_YEAR_TRANSACTION = " BrandYearTransaction s ";

    public static final String ST_FACTORY_MONTH_TRANSACTION = " FactoryMonthTransaction s ";
    public static final String ST_FACTORY_QUARTER_TRANSACTION = " FactoryQuarterTransaction s ";
    public static final String ST_FACTORY_YEAR_TRANSACTION = " FactoryYearTransaction s ";

    // For DELIMITER_PL ("#$pl$#")
    public static final String PL_MEDICINE = " Medicine m ";
    public static final String PL_BRAND = " Brand m ";
    public static final String PL_FACTORY = " Factory m ";

    // For DELIMITER_PREPD ("#$prepd$#")
    public static final String PREPD_MEDICINE = " s.medicineKey <> -1 AND m.factoryName like ? AND m.brandName like ? AND m.medicineName like ? ";
    public static final String PREPD_BRAND = " s.brandKey <> -1 AND m.factoryName like ? AND m.brandName like ? ";
    public static final String PREPD_FACTORY = " s.factoryKey <> -1 AND m.factoryName like ? ";

    // For DELIMITER_PREPT ("#$prept$#")
    public static final String PREPT_MONTH = " c.year >= ? AND c.year <= ? AND c.quarter >= ? AND c.quarter <= ? AND c.month >= ? AND c.month <= ? ";
    public static final String PREPT_QUARTER = " c.year >= ? AND c.year <= ? AND c.quarter >= ? AND c.quarter <= ? ";
    public static final String PREPT_YEAR = " c.year >= ? AND c.year <= ? ";

    // For DELIMITER_COND ("#$cond$#")
    public static final String COND_MEDICINE = " s.medicineKey = m.medicineKey ";
    public static final String COND_BRAND = " s.brandKey = m.brandKey ";
    public static final String COND_FACTORY = " s.factoryKey = m.factoryKey ";


    // DELIMITER HashMap
    public static final HashMap<String, String> DELIMITER_MAP = createDelimiterMap();
    private static  HashMap<String, String> createDelimiterMap(){
        HashMap<String,String> ret = new HashMap<String,String>();

        // For DELIMITER_ST ("#$st$#")
        ret.put("ST_TRANSACTION", " SaleTransaction s ");

        ret.put("ST_MEDICINE_MONTH_TRANSACTION", " MedicineMonthTransaction s ");
        ret.put("ST_MEDICINE_QUARTER_TRANSACTION", " MedicineQuarterTransaction s ");
        ret.put("ST_MEDICINE_YEAR_TRANSACTION", " MedicineYearTransaction s ");

        ret.put("ST_BRAND_MONTH_TRANSACTION", " BrandMonthTransaction s ");
        ret.put("ST_BRAND_QUARTER_TRANSACTION", " BrandQuarterTransaction s ");
        ret.put("ST_BRAND_YEAR_TRANSACTION", " BrandYearTransaction s ");

        ret.put("ST_FACTORY_MONTH_TRANSACTION", " FactoryMonthTransaction s ");
        ret.put("ST_FACTORY_QUARTER_TRANSACTION", " FactoryQuarterTransaction s ");
        ret.put("ST_FACTORY_YEAR_TRANSACTION", " FactoryYearTransaction s ");

        // For DELIMITER_PL ("#$pl$#")
        ret.put("PL_MEDICINE", " Medicine m ");
        ret.put("PL_BRAND", " Brand m ");
        ret.put("PL_FACTORY", " Factory m ");

        // For DELIMITER_PREPD ("#$prepd$#")
        ret.put("PREPD_MEDICINE", " s.medicineKey <> -1 AND m.factoryName like ? AND m.brandName like ? AND m.medicineName like ? ");
        ret.put("PREPD_BRAND", " s.brandKey <> -1 AND m.factoryName like ? AND m.brandName like ? ");
        ret.put("PREPD_FACTORY", " s.factoryKey <> -1 AND m.factoryName like ? ");

        // For DELIMITER_PREPT ("#$prept$#")
        ret.put("PREPT_MONTH", " c.year >= ? AND c.year <= ? AND c.quarter >= ? AND c.quarter <= ? AND c.month >= ? AND c.month <= ? ");
        ret.put("PREPT_QUARTER", " c.year >= ? AND c.year <= ? AND c.quarter >= ? AND c.quarter <= ? ");
        ret.put("PREPT_YEAR", " c.year >= ? AND c.year <= ? ");

        // For DELIMITER_COND ("#$cond$#")
        ret.put("COND_MEDICINE", " s.medicineKey = m.medicineKey ");
        ret.put("COND_BRAND", " s.brandKey = m.brandKey ");
        ret.put("COND_FACTORY", " s.factoryKey = m.factoryKey ");

        return ret;
    }



    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** *****   Special   ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

    // Pie for yesterday
    public static final String YESTERDAY_PIE
            =
            " SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
            " FROM SaleTransaction s, Medicine m, Calendar c " +
            " WHERE s.medicineKey <> -1 " +
            " AND m.factoryName like ? " +
            " AND m.brandName like ? " +
            " AND m.medicineName like ? " +
            " AND c.year >= ? " +
            " AND c.year <= ? " +
            " AND c.quarter >= ? " +
            " AND c.quarter <= ? " +
            " AND c.month >= ? " +
            " AND c.month <= ? " +
            " AND c.fullDate = subdate(current_date, 1) " +
            " AND s.medicineKey = m.medicineKey " +
            " AND s.calendarKey = c.calendarKey " +
            " GROUP BY m.brandName, m.factoryName " +
            " ORDER BY totalSum DESC " +
            " ;";


    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** *****           General Cases             ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

    /* SQL Frames */
    public static final String SUM_SALE_TRANSACTION_PIE_FRAME
            =
            "SELECT SUM(s.totalPrice) as totalSum, #$1$# " +
            "FROM #$st$#, #$pl$#, Calendar c " +
            "WHERE #$prepd$# " +
            "AND #$prept$# " +
            "AND #$cond$# " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY #$1$# " +
            "ORDER BY totalSum DESC " +
            ";";

    public static final String PIE_ARGS_FACTORIES = " m.factoryName ";
    public static final String PIE_ARGS_FACTORY = " m.brandName, m.factoryName ";
    public static final String PIE_ARGS_BRAND = " m.medicineName, m.brandName, m.factoryName ";


    public static final String SUM_SALE_TRANSACTION_LINE_FRAME
            =
            "SELECT SUM(s.totalPrice) as totalSum, #$1$# " +
            "FROM #$st$#, #$pl$#, Calendar c " +
            "WHERE #$prepd$# " +
            "AND #$prept$# " +
            "AND #$cond$# " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY #$1$# " +
            "ORDER BY #$1$# ASC " +
            ";";

    public static final String LINE_ARGS_YEARS = " c.year, m.factoryName ";
    public static final String LINE_ARGS_YEAR = " c.year, c.quarter, m.factoryName ";
    public static final String LINE_ARGS_QUARTER = " c.year, c.quarter, c.month, m.factoryName ";


    public static final String SUM_SALE_TRANSACTION_COMBO_0
            =
            "SELECT SUM(s.totalPrice) as totalSum, #$1$#, #$2$# " +
            "FROM #$st$#, #$pl$#, Calendar c " +
            "WHERE #$prepd$# " +
            "AND #$prept$# " +
            "AND #$cond$# " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY #$1$#, #$2$# " +
            "ORDER BY #$2$#, #$1$#, totalSum DESC " +
            ";";
    public static final String COMBO_ARGS_FACTORIES = " m.factoryName ";
    public static final String COMBO_ARGS_FACTORY = " m.factoryName, m.brandName ";
    public static final String COMBO_ARGS_BRAND = " m.factoryName, m.brandName, m.medicineName ";

    public static final String COMBO_ARGS_YEARS = " c.year ";
    public static final String COMBO_ARGS_YEAR = " c.quarter, c.year ";
    public static final String COMBO_ARGS_QUARTER = " c.month, c.quarter, c.year ";


    public static final String SUM_SALE_TRANSACTION_COMBO_1
            =
            "SELECT AVG(t.totalSum) as avgSum, #$3$# " +
            "FROM ( " +
            "SELECT SUM(s.totalPrice) as totalSum, #$1$#, #$2$# " +
            "FROM #$st$#, #$pl$#, Calendar c " +
            "WHERE #$prepd$# " +
            "AND #$prept$# " +
            "AND #$cond$# " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY #$1$#, #$2$# " +
            "ORDER BY #$2$#, #$1$#, totalSum DESC " +
            ") as t " +
            "GROUP BY #$3$# " +
            ";";
    public static final String COMBO_ARGS_T_YEARS = "t.year";
    public static final String COMBO_ARGS_T_YEAR = "t.quarter, t.year";
    public static final String COMBO_ARGS_T_QUARTER = "t.month, t.quarter, t.year";



    public static final String SUM_SALE_TRANSACTION_SANKEY_0
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
            "FROM #$st$#, #$pl$#, Calendar c " +
            "WHERE #$prepd$# " +
            "AND #$prept$# " +
            "AND #$cond$# " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.brandName, m.factoryName " +
            "ORDER BY m.factoryName, totalSum DESC " +
            ";";

    public static final String SUM_SALE_TRANSACTION_SANKEY_1
            =
            "SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName " +
            "FROM #$st$#, #$pl$#, Calendar c " +
            "WHERE #$prepd$# " +
            "AND #$prept$# " +
            "AND #$cond$# " +
            "AND s.calendarKey = c.calendarKey " +
            "GROUP BY m.medicineName, m.brandName, m.factoryName " +
            "ORDER BY m.factoryName, totalSum DESC " +
            ";";



    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****   Legacy    ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

//    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
//    /* ***** ***** *****        No Factory & No Year         ***** ***** ***** */
//    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
//
//    // (NFL,NTL) Pie
//    public static final String SumSaleTransaction
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND c.year >= 2012 " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "AND s.calendarKey = c.calendarKey " +
//                    "GROUP BY m.factoryName;";
//
//    // (NFL,NTL) Sankey part0
//    public static final String SumSaleTransactionAll_0
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND c.year >=2012 " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "GROUP BY m.brandName, m.factoryName " +
//                    "ORDER BY m.factoryName, totalSum DESC " +
//                    ";";
//
//    // (NFL,NTL) Sankey part1
//    public static final String SumSaleTransactionAll_1
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND c.year >=2012 " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "GROUP BY m.medicineName, m.brandName, m.factoryName " +
//                    "ORDER BY m.factoryName, totalSum DESC ;" +
//                    ";";
//
//    // (NFL,NTL) Line
//    public static final String SumSaleTransactionAllFactoryTime
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND c.year >= 2012 " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "AND s.calendarKey = c.calendarKey " +
//                    "GROUP BY m.factoryName, c.year " +
//                    "ORDER BY c.year, m.factoryName ASC " +
//                    ";";
//
//    // (NFL,NTL) Combo Main Columns
//    public static final String SumSaleTransactionAllFactoryTimeSum
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND c.year >=2013 " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "GROUP BY c.year, m.factoryName " +
//                    "ORDER BY c.year, m.factoryName, totalSum DESC " +
//                    ";";
//
//    // (NFL,NTL) Combo AVG Line
//    public static final String AvgSaleTransactionAllFactoryTime
//            =
//            "SELECT AVG(t.totalSum) as avgSum, t.year " +
//                    "FROM ( " +
//                    "SELECT SUM(s.totalPrice) as totalSum, m.factoryName, c.year " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND c.year >=2013 " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "GROUP BY c.year, m.factoryName " +
//                    "ORDER BY c.year, m.factoryName, totalSum DESC " +
//                    ") as t " +
//                    "GROUP BY t.year " +
//                    ";";
//
//
//
//    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
//    /* ***** ***** *****       One Factory & One Year        ***** ***** ***** */
//    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
//
//    // (1F,1Y) Pie
//    public static final String SumSaleTransaction1F1Y_Pie
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND m.factoryName = ? " +
//                    "AND c.year = ? " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "AND s.calendarKey = c.calendarKey " +
//                    "GROUP BY m.brandName, m.factoryName " +
//                    ";";
//
//    // (1F,1Y) Line
//    public static final String SumSaleTransaction1F1Y_Line
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, c.quarter, c.year, m.factoryName " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND m.factoryName = ? " +
//                    "AND c.year = ? " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "AND s.calendarKey = c.calendarKey " +
//                    "GROUP BY c.quarter, c.year " +
//                    "ORDER BY c.quarter, c.year ASC " +
//                    ";";
//
//    // (1F,1Y) Combo main columns
//    public static final String SumSaleTransaction1F1Y_Combo_0
//            = "SELECT SUM(s.totalPrice) as totalSum, m.brandName, c.quarter " +
//            "FROM SaleTransaction s, Medicine m, Calendar c " +
//            "WHERE s.medicineKey <> -1 " +
//            "AND m.factoryName = ? " +
//            "AND c.year = ? " +
//            "AND s.medicineKey = m.medicineKey " +
//            "GROUP BY m.brandName, c.quarter " +
//            "ORDER BY c.quarter, m.brandName, totalSum DESC " +
//            ";";
//
//    // (1F,1Y) Combo AVG Line
//    public static final String SumSaleTransaction1F1Y_Combo_1
//            = "SELECT AVG(t.totalSum) as avgSum, t.quarter " +
//            "FROM ( " +
//            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, c.quarter " +
//            "FROM SaleTransaction s, Medicine m, Calendar c " +
//            "WHERE s.medicineKey <> -1 " +
//            "AND m.factoryName = ? " +
//            "AND c.year = ? " +
//            "AND s.medicineKey = m.medicineKey " +
//            "GROUP BY m.brandName, c.quarter " +
//            "ORDER BY c.quarter, m.brandName, totalSum DESC " +
//            ") as t " +
//            "GROUP BY t.quarter " +
//            ";";
//
//    public static final String SumSaleTransaction1F1Y_Sankey_0
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.brandName, m.factoryName " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND m.factoryName = ? " +
//                    "AND c.year = ? " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "GROUP BY m.brandName, m.factoryName " +
//                    "ORDER BY m.factoryName, totalSum DESC " +
//                    ";";
//
//    public static final String SumSaleTransaction1F1Y_Sankey_1
//            =
//            "SELECT SUM(s.totalPrice) as totalSum, m.medicineName, m.brandName, m.factoryName " +
//                    "FROM SaleTransaction s, Medicine m, Calendar c " +
//                    "WHERE s.medicineKey <> -1 " +
//                    "AND m.factoryName = ? " +
//                    "AND c.year = ? " +
//                    "AND s.medicineKey = m.medicineKey " +
//                    "GROUP BY m.medicineName, m.brandName, m.factoryName " +
//                    "ORDER BY m.factoryName, totalSum DESC " +
//                    ";";



}
