package com.searcher.model.argsGenerator;

import com.searcher.model.entity.LineArgs;
import com.searcher.utils.MySQLConnection;
import lombok.Data;

import javax.sound.sampled.Line;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 */
@Data
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
        LineArgs lineArgs = new LineArgs(this.getTitle(), this.getSubTitle(), this.getHAxis());

        MySQLConnection mySQLConnection = new MySQLConnection();
        ResultSet resultSet = mySQLConnection.calcSaleSumByFactoryYear(getQuery());
        if (resultSet !=null){
            try {
                ArrayList<String> lineName = new ArrayList<String>();
                ArrayList<String> tempList = new ArrayList<String>();
                int year = 0;
                int count = 0;
                while(resultSet.next()){
                    if (resultSet.getInt("year") !=year){
                        tempList = new ArrayList<String>();
                        year = resultSet.getInt("year");
                        tempList.add(Integer.toString(year));
                        count++;
                        if (count !=1){
                            lineArgs.addItemList(tempList);
                        }
                    }
                    if (count <=1){
                        lineName.add(resultSet.getString("factoryName"));
                    }
                    tempList.add(Double.toString(resultSet.getDouble("totalSum")));
                }
                lineArgs.addItemList(tempList);
                lineArgs.setLineName(lineName);

            } catch (Exception what){
                what.printStackTrace();
            }
        }

        mySQLConnection.close();

        return lineArgs;
    }
}
