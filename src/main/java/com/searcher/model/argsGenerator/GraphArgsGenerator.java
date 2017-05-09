package com.searcher.model.argsGenerator;

import com.searcher.model.entity.PieArgs;
import com.searcher.model.entity.SankeyArgs;
import com.searcher.utils.MySQLConnection;
import com.searcher.utils.SQLStatments;
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

    private PieArgsGenerator pieArgsGenerator;
    private SankeyArgsGenerator sankeyArgsGenerator;

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
            pieArgsGenerator = new PieArgsGenerator(SQLStatments.SumSaleTransactionNoYear, "Shares SaleTransaction of All Factories");

            String[] queries = {SQLStatments.SumSaleTransactionAll_0, SQLStatments.SumSaleTransactionAll_1};
            sankeyArgsGenerator = new SankeyArgsGenerator(queries, "Test Title");
        }
    }

    public PieArgs getPieArgs(){
        if (getPieArgsGenerator() !=null){
            return getPieArgsGenerator().generatePieArgs();
        }
        else {
            return null;
        }
    }
    public SankeyArgs getSankeyArgs(){
        if (getSankeyArgsGenerator() !=null){
            return getSankeyArgsGenerator().generateSankeyArgs();
        }
        else {
            return null;
        }
    }

    public static void main(String[] args){
        GraphArgsGenerator graphArgsGenerator = new GraphArgsGenerator("", "","","","",0,0,0);
        graphArgsGenerator.processData();
        PieArgs pieArgs = graphArgsGenerator.getPieArgs();
        SankeyArgs sankeyArgs = graphArgsGenerator.getSankeyArgs();
        System.out.println("");
    }

}
