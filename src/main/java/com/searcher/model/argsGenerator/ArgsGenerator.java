package com.searcher.model.argsGenerator;

import com.searcher.model.entity.ChartArgs;
import com.searcher.model.entity.WebRequestBean;
import lombok.Data;

/**
 * Created by zchholmes on 2017/6/1.
 * Parent Class of Chart Argument Generator
 */
@Data
public class ArgsGenerator {
    /* All necessary information based on WebRequestBean */
    private String commodityLevel;      // Product Dimension Level
    private String factoryParam;        // User Inputted Factory Name
    private String brandParam;          // User Inputted Brand Name
    private String medicineParam;       // User Inputted Medicine Name
    private String timeLevel;           // Time Dimension Level
    private int yearParam;              // User Inputted Year
    private int quarterParam;           // User Inputted Quarter
    private int monthParam;             // User Inputted Month

    private String title;               // Title of the Chart

    /**
     * Default Constructor
     */
    public ArgsGenerator(){
        this.commodityLevel ="";
        this.factoryParam ="";
        this.brandParam ="";
        this.medicineParam ="";
        this.timeLevel ="";
        this.yearParam =-1;
        this.quarterParam =-1;
        this.monthParam =-1;

        this.title = "";
    }

    /**
     * Constructor used by providing WebReqeustBean
     * @param webRequestBean
     */
    public ArgsGenerator(WebRequestBean webRequestBean){
        this.commodityLevel =webRequestBean.getCommodityLevel();
        this.factoryParam =webRequestBean.getFactory();
        this.brandParam =webRequestBean.getBrand();
        this.medicineParam =webRequestBean.getMedicine();
        this.timeLevel =webRequestBean.getTimeLevel();
        this.yearParam =webRequestBean.getYear();
        this.quarterParam =webRequestBean.getQuarter();
        this.monthParam =webRequestBean.getMonth();

        this.title = "";
    }

    /**
     * Analyze collected requested information and prepare to generate data
     */
    public void analyzeParameters(){
        // Do nothing for parament analyzer
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
