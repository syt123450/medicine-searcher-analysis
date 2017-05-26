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
 */
@Data
public class PieArgsGenerator {
    private String query;
    private String title;
    private String commodityLevel;
    private String factoryParam;
    private String brandParam;
    private String medicineParam;
    private String timeLevel;
    private int yearParam;
    private int quarterParam;
    private int monthParam;

    public PieArgsGenerator(String query, String title){
        this.title = title;

        this.query = query;
        this.commodityLevel ="";
        this.factoryParam ="";
        this.brandParam ="";
        this.medicineParam ="";
        this.timeLevel ="";
        this.yearParam =-1;
        this.quarterParam =-1;
        this.monthParam =-1;
    }
    public PieArgsGenerator(String query, String title, String factoryParam, int yearParam){
        this.title = title;

        this.query = query;
        this.factoryParam =factoryParam;
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =yearParam;
        this.quarterParam =-1;
        this.monthParam =-1;
    }

    public PieArgsGenerator(WebRequestBean webRequestBean){
        this.commodityLevel =webRequestBean.getCommodityLevel();
        this.factoryParam =webRequestBean.getFactory();
        this.brandParam =webRequestBean.getBrand();
        this.medicineParam =webRequestBean.getMedicine();
        this.timeLevel =webRequestBean.getTimeLevel();
        this.yearParam =webRequestBean.getYear();
        this.quarterParam =webRequestBean.getQuarter();
        this.monthParam =webRequestBean.getMonth();

        this.title ="";
        this.query ="";
    }

    public void analyzeParameters(){
        if (this.getQuery().isEmpty()){
            String queryFrame = SQLStatments.SumSaleTransactionPieFrame;
            if (getCommodityLevel().equals("brand")){
                setTitle("Shares of " + getBrandParam());
                queryFrame = queryFrame.replace(SQLStatments.delimeter_1, SQLStatments.PieArgsBrand);
            }
            else if (getCommodityLevel().equals("factory")){
                setTitle("Shares of " + getFactoryParam());
                queryFrame = queryFrame.replace(SQLStatments.delimeter_1, SQLStatments.PieArgsFactory);
            }
            else {
                // getCommodityLevel() ==null
                setTitle("Shares of All Factories");
                queryFrame = queryFrame.replace(SQLStatments.delimeter_1, SQLStatments.PieArgsFactories);
            }

            if (getTimeLevel().equals("month")){
                setTitle(getTitle() + " in Month" + getMonthParam());
            }
            else if (getTimeLevel().equals("quarter")){
                setTitle(getTitle() + " in Quarter " + getQuarterParam());
            }
            else if (getTimeLevel().equals(("year"))){
                setTitle(getTitle() + " in Year " + getYearParam());
            }
            else {
                // getTimeLevel() ==null
            }

            // Choose proper table
            queryFrame = queryFrame.replace(SQLStatments.delimeter_t, SQLStatments.TableSaleTransaction);

            this.setQuery(queryFrame);
        }
    }

    public PieArgs generatePieArgs(){
        if (getCommodityLevel().equals("medicine")){
            return null;
        }
        this.analyzeParameters();



        PieArgs pieArgs = new PieArgs(this.getTitle());

        // Add the default 1st ArrayList
        ArrayList<String> fixList = new ArrayList<String>();
        fixList.add("Language");
        fixList.add("Speakers (in millions)");
        pieArgs.addItemList(fixList);

        // Decide column label
        String columnLabel = "factoryName";
        if (!getBrandParam().isEmpty()){
            columnLabel = "medicineName";
        }
        else if (!getFactoryParam().isEmpty()){
            columnLabel = "brandName";
        }
        else {
            columnLabel = "factoryName";
        }


        // Add data from SQL call
        MySQLConnection mySQLConnection = new MySQLConnection();
        ResultSet resultSet = mySQLConnection.calcSaleSumByParam(getQuery(), getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());

        int listSize = -1;
        if (resultSet !=null){
            try {
                while (resultSet.next()){
                    ArrayList<String> tempList = new ArrayList<String>();

                    tempList.add(resultSet.getString(columnLabel));
                    tempList.add(Double.toString(resultSet.getDouble("totalSum")));

                    if (listSize <0){
                        listSize = tempList.size();
                    }
                    else if (tempList.size() !=listSize){
                        return null;
                    }

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
}
