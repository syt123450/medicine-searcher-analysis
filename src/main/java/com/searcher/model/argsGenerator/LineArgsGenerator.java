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
    private String factoryParam;
    private String brandParam;
    private String medicineParam;
    private int yearParam;
    private int quarterParam;
    private int monthParam;

    public LineArgsGenerator(String query, String title, String subTitle, String hAxis){
        this.title = title;
        this.subTitle = subTitle;
        this.hAxis = hAxis;

        this.query = query;
        this.factoryParam ="";
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =-1;
        this.quarterParam =-1;
        this.monthParam =-1;
    }

    public LineArgsGenerator(String query, String title, String subTitle, String hAxis, String factoryParam, int yearParam){
        this.title = title;
        this.subTitle = subTitle;
        this.hAxis = hAxis;

        this.query = query;
        this.factoryParam =factoryParam;
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =yearParam;
        this.quarterParam =-1;
        this.monthParam =-1;
    }

    public LineArgs generateLineArgs(){
        LineArgs lineArgs = new LineArgs(this.getTitle(), this.getSubTitle(), this.getHAxis());

        // Decide column label
        String timeColLbl = "year";
        if (getQuarterParam()>0){
            timeColLbl = "month";
        }
        else if (getYearParam()>0){
            timeColLbl = "quarter";
        }
        else {
            timeColLbl = "year";
        }



        MySQLConnection mySQLConnection = new MySQLConnection();
        ResultSet resultSet = mySQLConnection.calcSaleSumByParam(getQuery(),getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());
        if (resultSet !=null){
            try {
                ArrayList<String> lineName = new ArrayList<String>();
                ArrayList<String> tempList = new ArrayList<String>();
                int compareValue = 0;
                int count = 0;
                while(resultSet.next()){
                    if (resultSet.getInt(timeColLbl) !=compareValue){
                        if (count !=0){
                            lineArgs.addItemList(tempList);
                        }
                        tempList = new ArrayList<String>();
                        compareValue = resultSet.getInt(timeColLbl);
                        tempList.add(Integer.toString(compareValue));
                        count++;

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
