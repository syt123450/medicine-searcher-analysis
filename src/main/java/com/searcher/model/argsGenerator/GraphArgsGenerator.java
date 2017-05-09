package com.searcher.model.argsGenerator;

import com.searcher.utils.MySQLConnection;
import lombok.Data;

import java.sql.ResultSet;

/**
 * Created by zchholmes on 2017/5/8.
 */
@Data
public class GraphArgsGenerator {
    private String commodityLevel;
    private String timeLevel;
    private String factoryPara;
    private String brandPara;
    private String medicinePara;
    private int yearPara;
    private int quarterPara;
    private int monthPara;

    public GraphArgsGenerator(
            String commodityLevel,
            String timeLevel,
            String factoryPara,
            String brandPara,
            String medicinePara,
            int yearPara,
            int quarterPara,
            int monthPara
    ){
        this.commodityLevel = commodityLevel;
        this.timeLevel = timeLevel;
        this.factoryPara = factoryPara;
        this.brandPara = brandPara;
        this.medicinePara = medicinePara;
        this.yearPara = yearPara;
        this.quarterPara = quarterPara;
        this.monthPara = monthPara;
    }

    /**
     * Based on provided parameters from constructor,
     * use proper SQL to catch data from MYSQL
     */
    public void processData(){
        // Case 1: Both level is empty
        if (this.getCommodityLevel().isEmpty() && this.getTimeLevel().isEmpty()){
            MySQLConnection mySQLConnection = new MySQLConnection();
            ResultSet resultSet = mySQLConnection.calcSaleSumByFactoryYear();
            mySQLConnection.close();

            if (resultSet !=null){
                PieArgsGenerator pieArgsGenerator = new PieArgsGenerator(resultSet, "Shares SaleTransaction of All Factories");
            }
        }
    }


}
