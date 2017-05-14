package com.searcher.model.argsGenerator;

import com.searcher.model.entity.ComboArgs;
import com.searcher.model.entity.WebRequestBean;
import com.searcher.utils.MySQLConnection;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 */
@Data
public class ComboArgsGenerator {
    private String[] queries;
    private String title;
    private String vAxis;
    private String hAxis;

    private String commodityLevel;
    private String factoryParam;
    private String brandParam;
    private String medicineParam;
    private String timeLevel;
    private int yearParam;
    private int quarterParam;
    private int monthParam;

    public ComboArgsGenerator(String[] queries, String title, String vAxis, String hAxis){
        this.title = title;
        this.vAxis = vAxis;
        this.hAxis = hAxis;

        this.queries = queries;
        this.factoryParam ="";
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =-1;
        this.quarterParam =-1;
        this.monthParam =-1;
    }

    public ComboArgsGenerator(String[] queries, String title, String vAxis, String hAxis, String factoryParam, int yearParam){
        this.title = title;
        this.vAxis = vAxis;
        this.hAxis = hAxis;

        this.queries = queries;
        this.factoryParam =factoryParam;
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =yearParam;
        this.quarterParam =-1;
        this.monthParam =-1;
    }

    public ComboArgsGenerator(WebRequestBean webRequestBean){
        this.commodityLevel =webRequestBean.getCommodityLevel();
        this.factoryParam =webRequestBean.getFactory();
        this.brandParam =webRequestBean.getBrand();
        this.medicineParam =webRequestBean.getMedicine();
        this.timeLevel =webRequestBean.getTimeLevel();
        this.yearParam =webRequestBean.getYear();
        this.quarterParam =webRequestBean.getQuarter();
        this.monthParam =webRequestBean.getMonth();

        this.queries = new String[0];
        this.title = "";
        this.hAxis = "";
        this.vAxis = "";
    }

    /**
     * Assume all SQL are ordered Properly
     * @return
     */
    public ComboArgs generateComboArgs(){
        if(getCommodityLevel().equals("medicine") ||getTimeLevel().equals("month")){
            return null;
        }


        ComboArgs comboArgs = new ComboArgs(this.getTitle(), this.getVAxis(), this.getHAxis());

        try {
            ArrayList<String> tempList = new ArrayList<String>();

            /* Try to catch name list first */
            MySQLConnection mySQLConnection = new MySQLConnection();
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

            // Add data
            resultSet_0.beforeFirst();
            ResultSet resultSet_1 = mySQLConnection.calcSaleSumByParam(getQueries()[1],getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());

            int tempTime =-1;
            int count =0;
            tempList = new ArrayList<String>();
            while(resultSet_0.next()){
                if (tempTime != resultSet_0.getInt(timeLvlLbl)){
                    if (count !=0){
                        resultSet_1.next();
                        tempList.add( Double.toString(resultSet_1.getDouble("avgSum")) );
                        comboArgs.addItemList(tempList);
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

            mySQLConnection.close();
        } catch (Exception what){
            what.printStackTrace();
        }


        return comboArgs;
    }

}
