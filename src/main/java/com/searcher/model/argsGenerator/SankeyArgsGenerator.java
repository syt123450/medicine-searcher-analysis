package com.searcher.model.argsGenerator;

import com.searcher.model.entity.SankeyArgs;
import com.searcher.utils.MySQLConnection;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 */
@Data
public class SankeyArgsGenerator {
    private String[] queries;
    private String title;

    private String factoryParam;
    private String brandParam;
    private String medicineParam;
    private int yearParam;
    private int quarterParam;
    private int monthParam;

    public SankeyArgsGenerator(String[] queries, String title){
        this.title = title;

        this.queries = queries;
        this.factoryParam ="";
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =-1;
        this.quarterParam =-1;
        this.monthParam =-1;
    }

    public SankeyArgsGenerator(String[] queries, String title, String factoryParam, int yearParam){
        this.title = title;

        this.queries = queries;
        this.factoryParam =factoryParam;
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =yearParam;
        this.quarterParam =-1;
        this.monthParam =-1;
    }

    public SankeyArgs generateSankeyArgs(){
        SankeyArgs sankeyArgs = new SankeyArgs(this.getTitle());

        // Add data from SQL call
        try {
            String tempFactoryName = "";
            String tempBrandName = "";
            int count =0;
            MySQLConnection mySQLConnection = new MySQLConnection();
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
                    // Ignore other medicine out of TOP 5 of a brand
                }
                else {
                    dulpNameCheck = resultSet_1.getString("brandName");
                    if (dulpNameCheckList.contains(dulpNameCheck)){
                        ArrayList<String> tempList = new ArrayList<String>();
                        tempList.add(dulpNameCheck);
                        tempList.add(resultSet_1.getString("medicineName"));
                        tempList.add( Double.toString(resultSet_1.getDouble("totalSum")) );
                        sankeyArgs.addItemList(tempList);
                    }
                    else {
                        count--;
                    }
                }
            }
            mySQLConnection.close();
        } catch (Exception what){
            what.printStackTrace();
        }

        return sankeyArgs;
    }

}
