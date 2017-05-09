package com.searcher.model.argsGenerator;

import com.searcher.model.entity.ComboArgs;
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

    public ComboArgsGenerator(String[] queries, String title, String vAxis, String hAxis){
        this.queries = queries;
        this.title = title;
        this.vAxis = vAxis;
        this.hAxis = hAxis;
    }

    /**
     * Assume all SQL are ordered Properly
     * @return
     */
    public ComboArgs generateComboArgs(){
        ComboArgs comboArgs = new ComboArgs(this.getTitle(), this.getVAxis(), this.getHAxis());

        try {
            ArrayList<String> tempList = new ArrayList<String>();

            /* Try to catch name list first */
            MySQLConnection mySQLConnection = new MySQLConnection();
            ResultSet resultSet_0 = mySQLConnection.calcSaleSumByFactoryYear(getQueries()[0]);
            // Default add first as "Year"
            tempList.add("Year");
            while(resultSet_0.next()){
                if (tempList.contains( resultSet_0.getString("factoryName") )){
                    break;
                }
                else {
                    tempList.add( resultSet_0.getString("factoryName") );
                }
            }
            // Add last as "Average"
            tempList.add("Average");
            comboArgs.addItemList(tempList);

            // Add data
            resultSet_0.beforeFirst();
            ResultSet resultSet_1 = mySQLConnection.calcSaleSumByFactoryYear(getQueries()[1]);

            int tempYear =-1;
            int count =0;
            tempList = new ArrayList<String>();
            while(resultSet_0.next()){
                if (tempYear != resultSet_0.getInt("Year")){
                    if (count !=0){
                        resultSet_1.next();
                        tempList.add( Double.toString(resultSet_1.getDouble("avgSum")) );
                        comboArgs.addItemList(tempList);
                    }
                    tempYear = resultSet_0.getInt("Year");
                    tempList = new ArrayList<String>();
                    tempList.add(Integer.toString(tempYear));
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
