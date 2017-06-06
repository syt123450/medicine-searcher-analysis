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
public class ArgsGenerator {
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
            tempTitle += "in Years 2012-2017";
        }

        this.setTitle(tempTitle);

    }

    public void determineSQLStatements(){
        String queryFrame_0 = "";
        String queryFrame_1 = "";

        String delRep_1 = "";
        String delRep_2 = "";
        String delRep_3 = "";

        // Based on chart type to decide SQL frames
        switch (this.getChartType()) {
            case LINE:
                queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_LINE_FRAME;
                break;

            case PIE:
                queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_PIE_FRAME;
                break;

            case COMBO:
                queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_COMBO_0;
                queryFrame_1 = SQLStatments.SUM_SALE_TRANSACTION_COMBO_1;
                break;

            case SANKEY:
                queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_SANKEY_0;
                queryFrame_1 = SQLStatments.SUM_SALE_TRANSACTION_SANKEY_1;
                break;

            default:
                break;
        }


        // Replace delimeters in the frame
        queryFrame_0 = queryFrame_0
                .replace(SQLStatments.DELIMITER_1, delRep_1)
                .replace(SQLStatments.DELIMITER_2, delRep_2)
                .replace(SQLStatments.DELIMITER_3, delRep_3);
        queryFrame_1 = queryFrame_1
                .replace(SQLStatments.DELIMITER_1, delRep_1)
                .replace(SQLStatments.DELIMITER_2, delRep_2)
                .replace(SQLStatments.DELIMITER_3, delRep_3);

        String[] queriesAry = {queryFrame_0, queryFrame_1};
        this.setQueries(queriesAry);

    }

    /**
     * Analyze collected requested information and prepare to generate data
     */
    public void analyzeParameters(){
        determineTitle();

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
