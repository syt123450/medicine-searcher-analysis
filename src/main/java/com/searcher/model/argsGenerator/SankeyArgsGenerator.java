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

    public SankeyArgsGenerator(String[] queries, String title){
        this.queries = queries;
        this.title = title;
    }

    public SankeyArgs generateSankeyArgs(){
        SankeyArgs sankeyArgs = new SankeyArgs(this.getTitle());

        // Add data from SQL call
        try {
            String tempFactoryName = "";
            String tempBrandName = "";
            int count =0;
            MySQLConnection mySQLConnection = new MySQLConnection();
            ResultSet resultSet_0 = mySQLConnection.calcSaleSumByFactoryYear(getQueries()[0]);
            while(resultSet_0.next()){
                if (!tempFactoryName.equals(resultSet_0.getString("factoryName"))){
                    count =0;
                }
                else {
                    count ++;
                }

                if (count >5){
                    // Ignore other brands out of TOP 5 of a factory
                }
                else {
                    ArrayList<String> tempList = new ArrayList<String>();
                    tempList.add(resultSet_0.getString("factoryName"));
                    tempList.add(resultSet_0.getString("brandName"));
                    tempList.add( Double.toString(resultSet_0.getDouble("totalSum")) );
                    sankeyArgs.addItemList(tempList);
                }
            }

            count =0;
            ResultSet resultSet_1 = mySQLConnection.calcSaleSumByFactoryYear(getQueries()[1]);
            while(resultSet_1.next()){
                if (!tempBrandName.equals(resultSet_1.getString("brandName"))){
                    count =0;
                }
                else {
                    count++;
                }
                if (count >5){
                    // Ignore other medicine out of TOP 5 of a brand
                }
                else {
                    ArrayList<String> tempList = new ArrayList<String>();
                    tempList.add(resultSet_1.getString("brandName"));
                    tempList.add(resultSet_1.getString("medicineName"));
                    tempList.add( Double.toString(resultSet_1.getDouble("totalSum")) );
                    sankeyArgs.addItemList(tempList);
                }
            }
            mySQLConnection.close();
        } catch (Exception what){
            what.printStackTrace();
        }

        return sankeyArgs;
    }

}
