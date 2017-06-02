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
        super(webRequestBean);

        // Initialize parameters
        this.hAxis = "";
        this.vAxis = "";
    }

    /**
     * Analyze inputs and prepare to Generate
     */
    public void analyzeParameters(){
        setVAxis("Amount");
        String queryFrame_0 = SQLStatments.SumSaleTransactionCombo_0;
        String queryFrame_1 = SQLStatments.SumSaleTransactionCombo_1;
        if (getCommodityLevel().equals("brand")){
            setTitle("Total and Average Sales of " + getBrandParam());
            queryFrame_0 = queryFrame_0.replace(SQLStatments.delimeter_1, SQLStatments.ComboArgsBrand);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_1, SQLStatments.ComboArgsBrand);
        }
        else if (getCommodityLevel().equals("factory")){
            setTitle("Total and Average Sales of " + getFactoryParam());
            queryFrame_0 = queryFrame_0.replace(SQLStatments.delimeter_1, SQLStatments.ComboArgsFactory);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_1, SQLStatments.ComboArgsFactory);
        }
        else {
            // getCommodityLevel() ==null
            setTitle("Total and Average Sales of All Factories");
            queryFrame_0 = queryFrame_0.replace(SQLStatments.delimeter_1, SQLStatments.ComboArgsFactories);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_1, SQLStatments.ComboArgsFactories);
        }

        if (getTimeLevel().equals("quarter")){
            setTitle(getTitle() + " in Quarter " + getQuarterParam());
            setHAxis("Month");
            queryFrame_0 = queryFrame_0.replace(SQLStatments.delimeter_2, SQLStatments.ComboArgsQuarter);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_2, SQLStatments.ComboArgsQuarter);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_3, SQLStatments.ComboArgsTQuarter);
        }
        else if (getTimeLevel().equals(("year"))){
            setTitle(getTitle() + " in Year " + getYearParam());
            setHAxis("Quarter");
            queryFrame_0 = queryFrame_0.replace(SQLStatments.delimeter_2, SQLStatments.ComboArgsYear);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_2, SQLStatments.ComboArgsYear);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_3, SQLStatments.ComboArgsTYear);
        }
        else {
            // getTimeLevel() ==null
            setHAxis("Year");
            queryFrame_0 = queryFrame_0.replace(SQLStatments.delimeter_2, SQLStatments.ComboArgsYears);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_2, SQLStatments.ComboArgsYears);
            queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_3, SQLStatments.ComboArgsTYears);
        }

        // Choose proper Table
        queryFrame_0 = queryFrame_0.replace(SQLStatments.delimeter_t, SQLStatments.TableSaleTransaction);
        queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_t, SQLStatments.TableSaleTransaction);

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
