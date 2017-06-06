package com.searcher.model.argsGenerator;

import com.searcher.model.entity.LineArgs;
import com.searcher.model.entity.WebRequestBean;
import com.searcher.utils.MySQLConnection;
import com.searcher.utils.SQLStatments;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 * Generate Line Chart Arguments for drawing Line Chart
 */
@Data
public class LineArgsGenerator extends ArgsGenerator{
    /* Customized parameters for Line Chart Generator */
    private String subTitle;    // Subtitle of the Line Chart
    private String hAxis;       // Horizontal Axis Name

    public LineArgsGenerator(WebRequestBean webRequestBean){
        super(webRequestBean, ChartType.LINE);

        // Initialize parameters
        this.subTitle = "";
        this.hAxis = "";
    }

    /**
     * Analyze inputs and prepare to Generate
     */
    public void analyzeParameters(){
        String queryFrame = SQLStatments.SUM_SALE_TRANSACTION_LINE_FRAME;
        if (getCommodityLevel().equals("medicine")){
            setTitle("Sale Trend of " + getMedicineParam());
        }
        else if (getCommodityLevel().equals("brand")){
            setTitle("Sale Trend of " + getBrandParam());
        }
        else if (getCommodityLevel().equals("factory")){
            setTitle("Sale Trend of " + getFactoryParam());
        }
        else {
            // getCommodityLevel() ==null
            setTitle("Sale Trend of All Factories");
        }

        if (getTimeLevel().equals("quarter")){
            setTitle(getTitle() + " in Quarter " + getQuarterParam());
            setHAxis("Month");
            queryFrame = queryFrame.replace(SQLStatments.DELIMITER_1, SQLStatments.LINE_ARGS_QUARTER);
        }
        else if (getTimeLevel().equals(("year"))){
            setTitle(getTitle() + " in Year " + getYearParam());
            setHAxis("Quarter");
            queryFrame = queryFrame.replace(SQLStatments.DELIMITER_1, SQLStatments.LINE_ARGS_YEAR);
        }
        else {
            // getTimeLevel() ==null
            setHAxis("Year");
            queryFrame = queryFrame.replace(SQLStatments.DELIMITER_1, SQLStatments.LINE_ARGS_YEARS);
        }

        // Choose proper tables
        queryFrame = queryFrame.replace(SQLStatments.DELIMITER_ST, SQLStatments.ST_SALE_TRANSACTION);
        queryFrame = queryFrame.replace(SQLStatments.DELIMITER_PL, SQLStatments.PL_MEDICINE);

        String[] queriesAry = { queryFrame };
        this.setQueries(queriesAry);
    }

    /**
     * Process based on the analysis
     * @return  null or proper LineArgs object
     */
    public LineArgs generateLineArgs(){
        if (getTimeLevel().equals("month")){
            return null;
        }
        this.analyzeParameters();

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
        ResultSet resultSet = mySQLConnection.calcSaleSumByParam(getQueries()[0],getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());
        if (resultSet !=null){
            try {
                ArrayList<String> lineName = new ArrayList<String>();
                ArrayList<String> tempList = new ArrayList<String>();
                int timeComparison = 0;     // temp time value used to compare with the time value from SQL result to decide if a new list needed
                int count = 0;              // count of SQL result iteration
                int listSize = -1;           // monitor size of each arrayList
                while(resultSet.next()){
                    if (resultSet.getInt(timeColLbl) !=timeComparison){
                        if (count !=0){
                            lineArgs.addItemList(tempList);
                            // Check if all lists have the same size
                            if (listSize <0){
                                listSize = tempList.size();
                            }
                            else if (tempList.size() !=listSize){
                                return null;
                            }
                        }
                        tempList = new ArrayList<String>();
                        timeComparison = resultSet.getInt(timeColLbl);
                        tempList.add(Integer.toString(timeComparison));
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
            } finally {
                mySQLConnection.close();
            }
        }

        mySQLConnection.close();

        return lineArgs;
    }
}
