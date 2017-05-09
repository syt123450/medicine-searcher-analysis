package com.searcher.model.argsGenerator;

import com.searcher.model.entity.PieArgs;
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

    public PieArgsGenerator(String query, String title){
        this.query = query;
        this.title = title;
    }

    public PieArgs generatePieArgs(){
        PieArgs pieArgs = new PieArgs(this.getTitle());

        // Add the default 1st ArrayList
        ArrayList<String> fixList = new ArrayList<String>();
        fixList.add("Language");
        fixList.add("Speakers (in millions)");
        pieArgs.addItemList(fixList);

        // Add data from SQL call
        MySQLConnection mySQLConnection = new MySQLConnection();
        ResultSet resultSet = mySQLConnection.calcSaleSumByFactoryYear(getQuery());
        if (resultSet !=null){
            try {
                while (resultSet.next()){
                    ArrayList<String> tempList = new ArrayList<String>();

                    tempList.add(resultSet.getString("factoryName"));
                    tempList.add(Double.toString(resultSet.getDouble("totalSum")));

                    pieArgs.addItemList(tempList);
                }
            } catch (Exception what){
                what.printStackTrace();
            }
        }
        mySQLConnection.close();

        return pieArgs;
    }
}
