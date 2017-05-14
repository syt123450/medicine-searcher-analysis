package com.searcher.model.argsGenerator;

import com.searcher.model.entity.LineArgs;
import com.searcher.model.entity.WebRequestBean;
import com.searcher.utils.MySQLConnection;
import com.searcher.utils.SQLStatments;
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

    private String commodityLevel;
    private String factoryParam;
    private String brandParam;
    private String medicineParam;
    private String timeLevel;
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

    public LineArgsGenerator(WebRequestBean webRequestBean){
        this.commodityLevel =webRequestBean.getCommodityLevel();
        this.factoryParam =webRequestBean.getFactory();
        this.brandParam =webRequestBean.getBrand();
        this.medicineParam =webRequestBean.getMedicine();
        this.timeLevel =webRequestBean.getTimeLevel();
        this.yearParam =webRequestBean.getYear();
        this.quarterParam =webRequestBean.getQuarter();
        this.monthParam =webRequestBean.getMonth();

        this.query = "";
        this.title = "";
        this.subTitle = "";
        this.hAxis = "";
    }

    public void analyzeParameters(){
        String queryFrame = SQLStatments.SumSaleTransactionPieFrame;
        if (getCommodityLevel().equals("medicine")){

        }
        else if (getCommodityLevel().equals("brand")){
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

        if (getTimeLevel().equals("quarter")){
            setTitle(getTitle() + " in " + getQuarterParam());
        }
        else if (getTimeLevel().equals(("year"))){
            setTitle(getTitle() + " in " + getYearParam());
        }
        else {
            // getTimeLevel() ==null
        }

        this.setQuery(queryFrame);
    }

    public LineArgs generateLineArgs(){
        if (getTimeLevel().equals("month")){
            return null;
        }


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
