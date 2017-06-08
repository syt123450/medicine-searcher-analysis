package com.searcher.model.argsGenerator;

import com.searcher.model.entity.ChartArgs;
import com.searcher.model.entity.WebRequestBean;
import com.searcher.utils.SQLStatments;
import lombok.Data;

/**
 * Created by zchholmes on 2017/6/1.
 * Parent Class of Chart Argument Generator
 */
@Data
public abstract class ArgsGenerator {
    /* All necessary information based on WebRequestBean */
    private ChartType chartType;        // Type of the drawing chart

    private String commodityLevel;      // Product Dimension Level
    private String factoryParam;        // User Inputted Factory Name
    private String brandParam;          // User Inputted Brand Name
    private String medicineParam;       // User Inputted Medicine Name
    private String timeLevel;           // Time Dimension Level
    private int yearParam;              // User Inputted Year
    private int quarterParam;           // User Inputted Quarter
    private int monthParam;             // User Inputted Month

    private String title;               // Title of the Chart
    private String[] queries;           // Queries used to gather Data from Database

    public enum ChartType {
        DEFAULT,
        LINE,
        PIE,
        COMBO,
        SANKEY
    }

    /**
     * Default Constructor
     */
    public ArgsGenerator(){
        this.chartType = ChartType.DEFAULT;

        this.commodityLevel ="";
        this.factoryParam ="";
        this.brandParam ="";
        this.medicineParam ="";
        this.timeLevel ="";
        this.yearParam =-1;
        this.quarterParam =-1;
        this.monthParam =-1;

        this.title = "";
        this.queries = new String[0];
    }

    /**
     * Constructor used by providing WebReqeustBean
     * @param webRequestBean
     */
    public ArgsGenerator(WebRequestBean webRequestBean, ChartType chartType){
        this.chartType = chartType;

        this.commodityLevel =webRequestBean.getCommodityLevel();
        this.factoryParam =webRequestBean.getFactory();
        this.brandParam =webRequestBean.getBrand();
        this.medicineParam =webRequestBean.getMedicine();
        this.timeLevel =webRequestBean.getTimeLevel();
        this.yearParam =webRequestBean.getYear();
        this.quarterParam =webRequestBean.getQuarter();
        this.monthParam =webRequestBean.getMonth();

        this.title = "";
        this.queries = new String[0];
    }

    /**
     * Allow to determine any customization variables about the chart
     */
    public abstract void determineCustomization();

    public String determineTimeHAxisName(){
        String ret = "";
        if (getTimeLevel().equals("quarter")){
            ret = "Month";
        }
        else if (getTimeLevel().equals(("year"))){
            ret = "Quarter";
        }
        else {
            // getTimeLevel() ==null
            ret = "Year";
        }
        return ret;
    }

    public String determineTimeLable(){
        String ret = "";

        return ret;
    }

    public String determineTimeLable_DD(){
        String ret = "";

        return ret;
    }

    public String determineProductLable(){
        String ret = "factoryName";
        if (getCommodityLevel().equals("medicine")){
            ret = "medicineName";
        }
        else if (getCommodityLevel().equals("brand")){
            ret = "brandName";
        }

        return ret;
    }

    public String determineProductLable_DD(){
        String ret = "factoryName";

        return ret;
    }

    /**
     * Based on collected data to determine the Title of the Chart
     */
    public void determineTitle(){
        String tempTitle = "";      // Hold temporary title name

        // Based on chart type to decide basic title
        switch (this.getChartType()) {
            case LINE:
                tempTitle = "Sale Trend of ";
                break;

            case PIE:
                tempTitle = "Shares of ";
                break;

            case COMBO:
                tempTitle = "Total and Average Sales of ";
                break;

            case SANKEY:
                tempTitle = "Medicine Distribution of ";
                break;

            case DEFAULT:
                tempTitle = "Default Title of ";


            default:
                tempTitle = "Transactions of ";
                break;
        }

        // Based on commodity level, decide either medicine, brand or factory
        if (getCommodityLevel().equals("medicine")
                && !(getChartType().equals(ChartType.PIE)) && !(getChartType().equals(ChartType.COMBO)) && !(getChartType().equals(ChartType.SANKEY))){
            tempTitle += getMedicineParam();
        }
        else if (getCommodityLevel().equals("brand") && !(getChartType().equals(ChartType.SANKEY))){
            tempTitle += getBrandParam();
        }
        else if (getCommodityLevel().equals("factory")){
            tempTitle += getFactoryParam();
        }
        else {
            // getCommodityLevel() ==null
            tempTitle += "All Factories";
        }

        // Based on commodity level, decide either month, quarter or year
        if (getTimeLevel().equals("month")
                && !(getChartType().equals(ChartType.LINE)) && !(getChartType().equals(ChartType.COMBO))){
            tempTitle += (" in Month" + getMonthParam());
        }
        else if (getTimeLevel().equals("quarter")){
            tempTitle += (" in Quarter " + getQuarterParam());
        }
        else if (getTimeLevel().equals(("year"))){
            tempTitle += (" in Year " + getYearParam());
        }
        else {
            // getTimeLevel() ==null
            tempTitle += " in Years 2012-2017";
        }

        this.setTitle(tempTitle);

    }

    /**
     * Based on collected data to determine the SQL statements for the Chart
     */
    public void determineSQLStatements(){
        // Temp SQL frames
        String queryFrame_0 = "";
        String queryFrame_1 = "";

        // Temp replaced delimiters
        String delRep_1 = "";
        String delRep_2 = "";
        String delRep_3 = "";
        String delRep_st = "ST_";       // hold KEY related to the Transaction Table of the HashMap
        String delRep_pl = "PL_";       // hold KEY related to the Product Level Table of the HashMap
        String delRep_prepd = "PREPD_"; // hold KEY related to condition statement for Prepared Statement on Product
        String delRep_prept = "PREPT_"; // hold KEY related to condition statement for Prepared Statement on Time
        String delRep_cond = "COND_";   // hold KEY related to condition statement on join

        // Based on chart type to decide SQL frames and decide some delimiter values
        switch (this.getChartType()) {
            case LINE:
                /* Use PRODUCT_LEVEL && TIME_LEVEL_DD */
                queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_LINE_FRAME;
                if (getTimeLevel().equals("quarter")){
                    delRep_1 = SQLStatments.LINE_ARGS_QUARTER;
                }
                else if (getTimeLevel().equals("year")){
                    delRep_1 = SQLStatments.LINE_ARGS_YEAR;
                }
                else {
                    // getTimeLevel() ==null || getTimeLevel().equals("year")
                    delRep_1 = SQLStatments.LINE_ARGS_YEARS;
                }
                // Determine other Table and Condition delimiters
                delRep_st += ( SQLStatments.PRODUCT_LEVEL.get(getCommodityLevel()) +"_" );
                delRep_st += ( SQLStatments.TIME_LEVEL_DD.get(getTimeLevel()) +"_TRANSACTION" );
                delRep_pl += SQLStatments.PRODUCT_LEVEL.get(getCommodityLevel());
                delRep_prepd += SQLStatments.PRODUCT_LEVEL.get(getCommodityLevel());
                delRep_prept += SQLStatments.TIME_LEVEL_DD.get(getTimeLevel());
                delRep_cond += SQLStatments.PRODUCT_LEVEL.get(getCommodityLevel());

                break;

            case PIE:
                /* Use PRODUCT_LEVEL_DD && TIME_LEVEL */
                queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_PIE_FRAME;
                if (getCommodityLevel().equals("brand")){
                    delRep_1 =  SQLStatments.PIE_ARGS_BRAND;
                }
                else if (getCommodityLevel().equals("factory")){
                    delRep_1 = SQLStatments.PIE_ARGS_FACTORY;
                }
                else {
                    // getCommodityLevel() ==null
                    delRep_1 = SQLStatments.PIE_ARGS_FACTORIES;
                }
                // Determine other Table and Condition delimiters
                delRep_st += ( SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel()) +"_" );
                delRep_st += ( SQLStatments.TIME_LEVEL.get(getTimeLevel()) +"_TRANSACTION" );
                delRep_pl += SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel());
                delRep_prepd += SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel());
                delRep_prept += SQLStatments.TIME_LEVEL.get(getTimeLevel());
                delRep_cond += SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel());

                break;

            case COMBO:
                /* Use PRODUCT_LEVEL_DD && TIME_LEVEL_DD */
                queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_COMBO_0;
                queryFrame_1 = SQLStatments.SUM_SALE_TRANSACTION_COMBO_1;
                if (getCommodityLevel().equals("brand")){
                    delRep_1 = SQLStatments.COMBO_ARGS_BRAND;
                }
                else if (getCommodityLevel().equals("factory")){
                    delRep_1 = SQLStatments.COMBO_ARGS_FACTORY;
                }
                else {
                    // getCommodityLevel() ==null
                    delRep_1 = SQLStatments.COMBO_ARGS_FACTORIES;
                }

                if (getTimeLevel().equals("quarter")){
                    delRep_2 = SQLStatments.COMBO_ARGS_QUARTER;
                    delRep_3 = SQLStatments.COMBO_ARGS_T_QUARTER;
                }
                else if (getTimeLevel().equals(("year"))){
                    delRep_2 = SQLStatments.COMBO_ARGS_YEAR;
                    delRep_3 = SQLStatments.COMBO_ARGS_T_YEAR;
                }
                else {
                    // getTimeLevel() ==null
                    delRep_2 = SQLStatments.COMBO_ARGS_YEARS;
                    delRep_3 = SQLStatments.COMBO_ARGS_T_YEARS;
                }
                // Determine other Table and Condition delimiters
                delRep_st += ( SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel()) +"_" );
                delRep_st += ( SQLStatments.TIME_LEVEL_DD.get(getTimeLevel()) +"_TRANSACTION" );
                delRep_pl += SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel());
                delRep_prepd += SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel());
                delRep_prept += SQLStatments.TIME_LEVEL_DD.get(getTimeLevel());
                delRep_cond += SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel());

                break;

            case SANKEY:
                /* Use PRODUCT_LEVEL_DD && TIME_LEVEL */
                queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_SANKEY_0;
                queryFrame_1 = SQLStatments.SUM_SALE_TRANSACTION_SANKEY_1;

                delRep_st += "BRAND_";
                delRep_st += ( SQLStatments.TIME_LEVEL.get(getTimeLevel()) +"_TRANSACTION" );
                delRep_pl += "BRAND";
                delRep_prepd += "BRAND";
                delRep_prept += SQLStatments.TIME_LEVEL.get(getTimeLevel());
                delRep_cond += "BRAND";

                break;

            default:
                break;
        }

        // Replace delimeters in the frame
        queryFrame_0 = queryFrame_0
                .replace(SQLStatments.DELIMITER_1, delRep_1)
                .replace(SQLStatments.DELIMITER_2, delRep_2)
                .replace(SQLStatments.DELIMITER_3, delRep_3)
                .replace(SQLStatments.DELIMITER_ST, SQLStatments.DELIMITER_MAP.get(delRep_st))
                .replace(SQLStatments.DELIMITER_PL, SQLStatments.DELIMITER_MAP.get(delRep_pl))
                .replace(SQLStatments.DELIMITER_PREPD, SQLStatments.DELIMITER_MAP.get(delRep_prepd))
                .replace(SQLStatments.DELIMITER_PREPT, SQLStatments.DELIMITER_MAP.get(delRep_prept))
                .replace(SQLStatments.DELIMITER_COND, SQLStatments.DELIMITER_MAP.get(delRep_cond))
        ;
        // Update for Sankey only
        if (this.getChartType().equals(ChartType.SANKEY)){
            delRep_st = delRep_st.replace("BRAND", "MEDICINE");
            delRep_pl = delRep_pl.replace("BRAND", "MEDICINE");
            delRep_prepd = delRep_prepd.replace("BRAND", "MEDICINE");
            delRep_cond = delRep_cond.replace("BRAND", "MEDICINE");
        }
        queryFrame_1 = queryFrame_1
                .replace(SQLStatments.DELIMITER_1, delRep_1)
                .replace(SQLStatments.DELIMITER_2, delRep_2)
                .replace(SQLStatments.DELIMITER_3, delRep_3)
                .replace(SQLStatments.DELIMITER_ST, SQLStatments.DELIMITER_MAP.get(delRep_st))
                .replace(SQLStatments.DELIMITER_PL, SQLStatments.DELIMITER_MAP.get(delRep_pl))
                .replace(SQLStatments.DELIMITER_PREPD, SQLStatments.DELIMITER_MAP.get(delRep_prepd))
                .replace(SQLStatments.DELIMITER_PREPT, SQLStatments.DELIMITER_MAP.get(delRep_prept))
                .replace(SQLStatments.DELIMITER_COND, SQLStatments.DELIMITER_MAP.get(delRep_cond))
        ;

        String[] queriesAry = {queryFrame_0, queryFrame_1};
        this.setQueries(queriesAry);

    }

    /**
     * Analyze collected requested information and prepare to generate data
     */
    public void analyzeParameters(){
        determineTitle();
        determineSQLStatements();
        determineCustomization();
    }

    /**
     * Generate Data to draw the chart
     * @return an empty ChartArgs Object
     */
    public ChartArgs generateChartArgs(){
        ChartArgs chartArgs = new ChartArgs();

        return chartArgs;
    }
}
