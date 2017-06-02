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
        super(webRequestBean);
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
    }

    /**
     * Analyze inputs and prepare to Generate
     */
    public void analyzeParameters(){
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

        String[] queriesAry = { queryFrame };
        this.setQueries(queriesAry);
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
        ResultSet resultSet = mySQLConnection.calcSaleSumByParam(getQueries()[0], getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());

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
