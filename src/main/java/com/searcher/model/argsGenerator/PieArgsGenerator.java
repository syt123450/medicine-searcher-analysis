package com.searcher.model.argsGenerator;

import com.searcher.model.entity.PieArgs;
import com.searcher.model.entity.WebRequestBean;
import com.searcher.utils.MySQLConnection;
import com.searcher.utils.SQLStatments;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 * Generate Pie Chart Arguments for drawing Pie Chart
 */
@Data
public class PieArgsGenerator extends ArgsGenerator{

    public PieArgsGenerator(WebRequestBean webRequestBean){
        super(webRequestBean, ChartType.PIE);
    }

    /**
     * Special Constructor for "Yesterday Sale Information"
     * @param query
     * @param title
     */
    public PieArgsGenerator(String query, String title){
        super();

        // Initialize parameter
        String[] queriesAry = { query };
        this.setQueries(queriesAry);

        this.setChartType(ChartType.PIE);
    }

    public void determineCustomization(){
        // No customized settings for PIE Chart
    }


    /**
     * Process based on the analysis
     * @return  null or proper PieArgs object
     */
    public PieArgs generatePieArgs(){
        if (getCommodityLevel().equals("medicine")){
            return null;
        }
        this.analyzeParameters();

        PieArgs pieArgs = new PieArgs(this.getTitle());
        String columnLabel = determineProductLable();       // Label used to catch different data

        // Add the default 1st ArrayList
        ArrayList<String> fixList = new ArrayList<String>();
        fixList.add("Language");
        fixList.add("Speakers (in millions)");
        pieArgs.addItemList(fixList);

        // Add data from SQL call
        MySQLConnection mySQLConnection = new MySQLConnection();
        ResultSet resultSet = mySQLConnection.calcSaleSum(getQueries()[0],
                SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel()),SQLStatments.TIME_LEVEL.get(getTimeLevel()),
                getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());

        int listSize = -1;
        if (resultSet !=null){
            try {
                while (resultSet.next()){
                    // Create temp arrayList to hold detailed data <[name], [totalSum]>
                    // Each tempList contains a name of the target [name] and the [totalSum]
                    ArrayList<String> tempList = new ArrayList<String>();

                    // Catch data from SQL result
                    tempList.add(resultSet.getString(columnLabel));
                    tempList.add(String.valueOf((long)resultSet.getDouble("totalSum")));

                    /* Verify each arrayList is constructed with the same size */
                    // Case: the first ArrayList, catch the size
                    if (listSize <0){
                        listSize = tempList.size();
                    }
                    // Case: the size of current one is different from the previous one
                    else if (tempList.size() !=listSize){
                        return null;
                    }

                    // Add caught arrayList in the general list
                    pieArgs.addItemList(tempList);
                }
            } catch (Exception what){
                what.printStackTrace();
            } finally {
                mySQLConnection.close();
            }
        }
        mySQLConnection.close();

        return pieArgs;
    }

    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** *****   DEPRECATED & Legacy   ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */
    /* ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** */

    /**
     * DEPRECATED
     * Analyze inputs and prepare to Generate
     */
//    public void analyzeParameters_orig(){
//        String queryFrame = SQLStatments.SUM_SALE_TRANSACTION_PIE_FRAME;
//        if (getCommodityLevel().equals("brand")){
//            setTitle("Shares of " + getBrandParam());
//            queryFrame = queryFrame.replace(SQLStatments.DELIMITER_1, SQLStatments.PIE_ARGS_BRAND);
//        }
//        else if (getCommodityLevel().equals("factory")){
//            setTitle("Shares of " + getFactoryParam());
//            queryFrame = queryFrame.replace(SQLStatments.DELIMITER_1, SQLStatments.PIE_ARGS_FACTORY);
//        }
//        else {
//            // getCommodityLevel() ==null
//            setTitle("Shares of All Factories");
//            queryFrame = queryFrame.replace(SQLStatments.DELIMITER_1, SQLStatments.PIE_ARGS_FACTORIES);
//        }
//
//        if (getTimeLevel().equals("month")){
//            setTitle(getTitle() + " in Month" + getMonthParam());
//        }
//        else if (getTimeLevel().equals("quarter")){
//            setTitle(getTitle() + " in Quarter " + getQuarterParam());
//        }
//        else if (getTimeLevel().equals(("year"))){
//            setTitle(getTitle() + " in Year " + getYearParam());
//        }
//        else {
//            // getTimeLevel() ==null
//        }
//
//        // Choose proper tables
//        queryFrame = queryFrame.replace(SQLStatments.DELIMITER_ST, SQLStatments.ST_TRANSACTION);
//        queryFrame = queryFrame.replace(SQLStatments.DELIMITER_PL, SQLStatments.PL_MEDICINE);
//        queryFrame = queryFrame.replace(SQLStatments.DELIMITER_COND, SQLStatments.COND_MEDICINE);
//        queryFrame = queryFrame.replace(SQLStatments.DELIMITER_PREPD, SQLStatments.PREPD_MEDICINE);
//        queryFrame = queryFrame.replace(SQLStatments.DELIMITER_PREPT, SQLStatments.PREPT_MONTH);
//
//        String[] queriesAry = { queryFrame };
//        this.setQueries(queriesAry);
//    }

    /**
     * DEPRECATED
     * Process based on the analysis
     * @return  null or proper PieArgs object
     */
//    public PieArgs generatePieArgs_old(){
//        if (getCommodityLevel().equals("medicine")){
//            return null;
//        }
//        this.analyzeParameters();
//
//        PieArgs pieArgs = new PieArgs(this.getTitle());
//
//        // Add the default 1st ArrayList
//        ArrayList<String> fixList = new ArrayList<String>();
//        fixList.add("Language");
//        fixList.add("Speakers (in millions)");
//        pieArgs.addItemList(fixList);
//
//        // Decide column label
//        String columnLabel = "factoryName";
//        if (!getBrandParam().isEmpty()){
//            columnLabel = "medicineName";
//        }
//        else if (!getFactoryParam().isEmpty()){
//            columnLabel = "brandName";
//        }
//        else {
//            columnLabel = "factoryName";
//        }
//
//
//        // Add data from SQL call
//        MySQLConnection mySQLConnection = new MySQLConnection();
//        ResultSet resultSet = mySQLConnection.calcSaleSum(getQueries()[0],
//                SQLStatments.PRODUCT_LEVEL_DD.get(getCommodityLevel()),SQLStatments.TIME_LEVEL.get(getTimeLevel()),
//                getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());
////        ResultSet resultSet = mySQLConnection.calcSaleSumByParam(getQueries()[0], getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());
//
//        int listSize = -1;
//        if (resultSet !=null){
//            try {
//                while (resultSet.next()){
//                    ArrayList<String> tempList = new ArrayList<String>();
//
//                    tempList.add(resultSet.getString(columnLabel));
//                    tempList.add(Double.toString(resultSet.getDouble("totalSum")));
//
//                    if (listSize <0){
//                        listSize = tempList.size();
//                    }
//                    else if (tempList.size() !=listSize){
//                        return null;
//                    }
//
//                    pieArgs.addItemList(tempList);
//
//                }
//            } catch (Exception what){
//                what.printStackTrace();
//            } finally {
//                mySQLConnection.close();
//            }
//        }
//        mySQLConnection.close();
//
//        return pieArgs;
//    }


}
