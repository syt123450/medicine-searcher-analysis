package com.searcher.model.argsGenerator;

import com.searcher.model.entity.ComboArgs;
import com.searcher.model.entity.WebRequestBean;
import com.searcher.utils.MySQLConnection;
import com.searcher.utils.SQLStatments;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 * Generate Combo Chart Arguments for drawing Combo Chart
 */
@Data
public class ComboArgsGenerator extends ArgsGenerator{
    private String vAxis;
    private String hAxis;

    public ComboArgsGenerator(WebRequestBean webRequestBean){
        super(webRequestBean, ChartType.COMBO);

        // Initialize parameters
        this.hAxis = "";
        this.vAxis = "";
    }

    public void determineCustomization(){

    }

    /**
     * Analyze inputs and prepare to Generate
     */
    public void analyzeParameters(){
        setVAxis("Amount");
        String queryFrame_0 = SQLStatments.SUM_SALE_TRANSACTION_COMBO_0;
        String queryFrame_1 = SQLStatments.SUM_SALE_TRANSACTION_COMBO_1;
        if (getCommodityLevel().equals("brand")){
            setTitle("Total and Average Sales of " + getBrandParam());
            queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_1, SQLStatments.COMBO_ARGS_BRAND);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_1, SQLStatments.COMBO_ARGS_BRAND);
        }
        else if (getCommodityLevel().equals("factory")){
            setTitle("Total and Average Sales of " + getFactoryParam());
            queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_1, SQLStatments.COMBO_ARGS_FACTORY);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_1, SQLStatments.COMBO_ARGS_FACTORY);
        }
        else {
            // getCommodityLevel() ==null
            setTitle("Total and Average Sales of All Factories");
            queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_1, SQLStatments.COMBO_ARGS_FACTORIES);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_1, SQLStatments.COMBO_ARGS_FACTORIES);
        }

        if (getTimeLevel().equals("quarter")){
            setTitle(getTitle() + " in Quarter " + getQuarterParam());
            setHAxis("Month");
            queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_2, SQLStatments.COMBO_ARGS_QUARTER);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_2, SQLStatments.COMBO_ARGS_QUARTER);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_3, SQLStatments.COMBO_ARGS_T_QUARTER);
        }
        else if (getTimeLevel().equals(("year"))){
            setTitle(getTitle() + " in Year " + getYearParam());
            setHAxis("Quarter");
            queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_2, SQLStatments.COMBO_ARGS_YEAR);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_2, SQLStatments.COMBO_ARGS_YEAR);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_3, SQLStatments.COMBO_ARGS_T_YEAR);
        }
        else {
            // getTimeLevel() ==null
            setHAxis("Year");
            queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_2, SQLStatments.COMBO_ARGS_YEARS);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_2, SQLStatments.COMBO_ARGS_YEARS);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_3, SQLStatments.COMBO_ARGS_T_YEARS);
        }

        // Choose proper Table
        queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_ST, SQLStatments.ST_TRANSACTION);
        queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_ST, SQLStatments.ST_TRANSACTION);
        queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_PL, SQLStatments.PL_MEDICINE);
        queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_PL, SQLStatments.PL_MEDICINE);
        queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_COND, SQLStatments.COND_MEDICINE);
        queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_COND, SQLStatments.COND_MEDICINE);
        queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_PREPD, SQLStatments.PREPD_MEDICINE);
        queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_PREPD, SQLStatments.PREPD_MEDICINE);
        queryFrame_0 = queryFrame_0.replace(SQLStatments.DELIMITER_PREPT, SQLStatments.PREPT_MONTH);
        queryFrame_1 = queryFrame_1.replace(SQLStatments.DELIMITER_PREPT, SQLStatments.PREPT_MONTH);

        String[] queriesAry = {queryFrame_0, queryFrame_1};
        this.setQueries(queriesAry);
    }

    /**
     * Process based on the analysis
     * Note:
     *  Assume all SQL are ordered Properly
     * @return  null or proper ComboArgs object
     */
    public ComboArgs generateComboArgs(){
        if(getCommodityLevel().equals("medicine") ||getTimeLevel().equals("month")){
            return null;
        }
        this.analyzeParameters();


        ComboArgs comboArgs = new ComboArgs(this.getTitle(), this.getVAxis(), this.getHAxis());
        MySQLConnection mySQLConnection = new MySQLConnection();

        try {
            ArrayList<String> tempList = new ArrayList<String>();

            /* Try to catch name list first */
            ResultSet resultSet_0 = mySQLConnection.calcSaleSumByParam(getQueries()[0],getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());

            // Decide levels
            String commodityLvlLbl = "factoryName";
            String timeLvlLbl = "year";
            // Default add first as time level
            if (getQuarterParam() >0){
                tempList.add("Month");
                timeLvlLbl = "month";
            }
            else if (getYearParam() >0){
                tempList.add("Quarter");
                timeLvlLbl = "quarter";
            }
            else {
                tempList.add("Year");
                timeLvlLbl = "year";
            }

            if (!getBrandParam().isEmpty()){
                commodityLvlLbl = "medicineName";
            }
            else if (!getFactoryParam().isEmpty()){
                commodityLvlLbl = "brandName";
            }
            else {
                commodityLvlLbl = "factoryName";
            }

            /* Generate name list */
            while(resultSet_0.next()) {
                if (tempList.contains(resultSet_0.getString(commodityLvlLbl))) {
                    break;
                } else {
                    tempList.add(resultSet_0.getString(commodityLvlLbl));
                }
            }
            // Add last as "Average"
            tempList.add("Average");
            comboArgs.addItemList(tempList);

            /* Add data */
            // reset sql_0 curser to the first
            resultSet_0.beforeFirst();
            ResultSet resultSet_1 = mySQLConnection.calcSaleSumByParam(getQueries()[1],getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());

            int tempTime =-1;
            int count =0;
            int listSize =-1;
            tempList = new ArrayList<String>();
            while(resultSet_0.next()){
                if (tempTime != resultSet_0.getInt(timeLvlLbl)){
                    if (count !=0){
                        resultSet_1.next();
                        tempList.add( Double.toString(resultSet_1.getDouble("avgSum")) );
                        comboArgs.addItemList(tempList);
                        if (listSize <0){
                            listSize = tempList.size();
                        }
                        else if (tempList.size() !=listSize){
                            return null;
                        }
                    }
                    tempTime = resultSet_0.getInt(timeLvlLbl);
                    tempList = new ArrayList<String>();
                    tempList.add(Integer.toString(tempTime));
                    count++;
                }
                tempList.add( Double.toString(resultSet_0.getDouble("totalSum")) );
            }
            resultSet_1.next();
            tempList.add( Double.toString(resultSet_1.getDouble("avgSum")) );
            comboArgs.addItemList(tempList);
            if (listSize <0){
                listSize = tempList.size();
            }
            else if (tempList.size() !=listSize){
                return null;
            }

            mySQLConnection.close();
        } catch (Exception what){
            what.printStackTrace();
        } finally {
            mySQLConnection.close();
        }


        return comboArgs;
    }

}
