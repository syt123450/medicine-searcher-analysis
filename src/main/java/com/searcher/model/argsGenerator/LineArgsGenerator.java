package com.searcher.model.argsGenerator;

import com.searcher.model.entity.LineArgs;
import com.searcher.utils.MySQLConnection;

import javax.sound.sampled.Line;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 */
public class LineArgsGenerator {
    private String query;
    private String title;
    private String subTitle;
    private String hAxis;

    public LineArgsGenerator(String query, String title, String subTitle, String hAxis){
        this.query = query;
        this.title = title;
        this.subTitle = subTitle;
        this.hAxis = hAxis;
    }

    public LineArgs generateLineArgs(){
        LineArgs lineArgs = new LineArgs();

//        // Add the default 1st ArrayList
//        ArrayList<String> fixList = new ArrayList<String>();
//        fixList.add("Language");
//        fixList.add("Speakers (in millions)");
//        pieArgs.addItemList(fixList);
//
//        // Add data from SQL call
//        MySQLConnection mySQLConnection = new MySQLConnection();
//        ResultSet resultSet = mySQLConnection.calcSaleSumByFactoryYear(getQuery());
//        if (resultSet !=null){
//            try {
//                while (resultSet.next()){
//                    ArrayList<String> tempList = new ArrayList<String>();
//
//                    tempList.add(resultSet.getString("factoryName"));
//                    tempList.add(Double.toString(resultSet.getDouble("SUM(s.totalPrice)")));
//
//                    pieArgs.addItemList(tempList);
//                }
//            } catch (Exception what){
//                what.printStackTrace();
//            }
//        }
//        mySQLConnection.close();

        return lineArgs;
    }
}
