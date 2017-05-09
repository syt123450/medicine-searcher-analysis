package com.searcher.model.argsGenerator;

import com.searcher.model.entity.LineArgs;
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
    private LineArgsGenerator lineArgsGenerator;

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
            // Generate pieArgs
            pieArgsGenerator = new PieArgsGenerator(SQLStatments.SumSaleTransaction,
                    "Shares SaleTransaction of All Factories");
            // Generate sankeyArgs
            String[] queries = {SQLStatments.SumSaleTransactionAll_0, SQLStatments.SumSaleTransactionAll_1};
            sankeyArgsGenerator = new SankeyArgsGenerator(queries,
                    "Test Title");
            // Generate lineArgs
            lineArgsGenerator = new LineArgsGenerator(SQLStatments.SumSaleTransactionAllFactoryTime,
                    "Sales of All Factories",
                    "",
                    "Year");
        }
    }

    public PieArgs generatePieArgs(){
        if (getPieArgsGenerator() !=null){
            return getPieArgsGenerator().generatePieArgs();
        }
        else {
            return null;
        }
    }
    public SankeyArgs generateSankeyArgs(){
        if (getSankeyArgsGenerator() !=null){
            return getSankeyArgsGenerator().generateSankeyArgs();
        }
        else {
            return null;
        }
    }

    public LineArgs generateLineArgs(){
        if (getLineArgsGenerator() !=null){
            return getLineArgsGenerator().generateLineArgs();
        }
        else {
            return null;
        }
    }

    public static void main(String[] args){
        GraphArgsGenerator graphArgsGenerator = new GraphArgsGenerator("", "","","","",0,0,0);
        graphArgsGenerator.processData();

        PieArgs pieArgs = graphArgsGenerator.generatePieArgs();
        SankeyArgs sankeyArgs = graphArgsGenerator.generateSankeyArgs();
        LineArgs lineArgs = graphArgsGenerator.generateLineArgs();
        System.out.println("");
    }

}
