package com.searcher.model.argsGenerator;

import com.searcher.model.entity.SankeyArgs;
import com.searcher.model.entity.WebRequestBean;
import com.searcher.utils.MySQLConnection;
import com.searcher.utils.SQLStatments;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 * Generate Sankey Chart Arguments for drawing Sankey Chart
 */
@Data
public class SankeyArgsGenerator extends ArgsGenerator{
    private String[] queries;

    public SankeyArgsGenerator(WebRequestBean webRequestBean){
        super(webRequestBean);

        // Initialize parameter
        this.queries =new String[0];
    }

    /**
     * Analyze inputs and prepare to Generate
     */
    public void analyzeParameters(){
        String queryFrame_0 = SQLStatments.SumSaleTransactionSankey_0;
        String queryFrame_1 = SQLStatments.SumSaleTransactionSankey_1;
        if (getCommodityLevel().equals("factory")){
            setTitle("Medicine Distribution of " + getFactoryParam());

        }
        else {
            // getCommodityLevel() ==null
            setTitle("Medicine Distribution of All Factories");
        }

        if (getTimeLevel().equals("quarter")){
            setTitle(getTitle() + " in Quarter " + getQuarterParam());
        }
        else if (getTimeLevel().equals(("year"))){
            setTitle(getTitle() + " in Year " + getYearParam());
        }
        else {
            // getTimeLevel() ==null
        }

        // Choose proper table
        queryFrame_0 = queryFrame_0.replace(SQLStatments.delimeter_t, SQLStatments.TableSaleTransaction);
        queryFrame_1 = queryFrame_1.replace(SQLStatments.delimeter_t, SQLStatments.TableSaleTransaction);

        String[] queries_s = {queryFrame_0, queryFrame_1};
        this.setQueries(queries_s);
    }

    /**
     * Process based on the analysis
     * @return  null or proper SankeyArgs object
     */
    public SankeyArgs generateSankeyArgs(){
        if ( getCommodityLevel().equals("brand")||getCommodityLevel().equals("medicine") ){
            return null;
        }
        this.analyzeParameters();

        SankeyArgs sankeyArgs = new SankeyArgs(this.getTitle());

        MySQLConnection mySQLConnection = new MySQLConnection();

        // Add data from SQL call
        try {
            String tempFactoryName = "";
            String tempBrandName = "";
            int count =0;
            int listSize =-1;

            ResultSet resultSet_0 = mySQLConnection.calcSaleSumByParam(getQueries()[0],getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());

            ArrayList<String> dulpNameCheckList = new ArrayList<String>();
            String dulpNameCheck = "";

            while(resultSet_0.next()){
                if (!tempFactoryName.equals(resultSet_0.getString("factoryName"))){
                    tempFactoryName = resultSet_0.getString("factoryName");
                    count =0;
                }
                else {
                    count ++;
                }

                if (count >=3){
                    // Ignore other brands out of TOP 5 of a factory
                }
                else {
                    ArrayList<String> tempList = new ArrayList<String>();
                    tempList.add(resultSet_0.getString("factoryName"));
                    dulpNameCheck = resultSet_0.getString("brandName");
                    tempList.add(dulpNameCheck);
                    if( !dulpNameCheckList.contains(dulpNameCheck) ){
                        dulpNameCheckList.add(dulpNameCheck);
                    }
                    tempList.add( Double.toString(resultSet_0.getDouble("totalSum")) );
                    sankeyArgs.addItemList(tempList);
                    if (listSize <0){
                        listSize = tempList.size();
                    }
                    else if (tempList.size() !=listSize){
                        return null;
                    }
                }
            }

            count =0;
            ResultSet resultSet_1 = mySQLConnection.calcSaleSumByParam(getQueries()[1],getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());
            while(resultSet_1.next()){
                if (!tempBrandName.equals(resultSet_1.getString("brandName"))){
                    count =0;
                }
                else {
                    count++;
                }
                if (count >=3){
                    // Ignore other medicine out of TOP 3 of a brand
                }
                else {
                    dulpNameCheck = resultSet_1.getString("brandName");
                    if (dulpNameCheckList.contains(dulpNameCheck)){
                        ArrayList<String> tempList = new ArrayList<String>();
                        tempList.add(dulpNameCheck);
                        tempList.add(resultSet_1.getString("medicineName"));
                        tempList.add( Double.toString(resultSet_1.getDouble("totalSum")) );
                        sankeyArgs.addItemList(tempList);
                        if (listSize <0){
                            listSize = tempList.size();
                        }
                        else if (tempList.size() !=listSize){
                            return null;
                        }
                    }
                    else {
                        count--;
                    }
                }
            }
            mySQLConnection.close();
        } catch (Exception what){
            what.printStackTrace();
        } finally {
            mySQLConnection.close();
        }

        return sankeyArgs;
    }

}
