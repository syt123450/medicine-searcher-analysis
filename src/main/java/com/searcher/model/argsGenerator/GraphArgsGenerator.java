package com.searcher.model.argsGenerator;

import com.searcher.model.entity.*;
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
    private ComboArgsGenerator comboArgsGenerator;

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

    public GraphArgsGenerator(WebRequestBean webRequestBean){
        this.commodityLevel = webRequestBean.getCommodityLevel();
        this.timeLevel = webRequestBean.getTimeLevel();
        this.factoryPara = webRequestBean.getFactory();
        this.brandPara = webRequestBean.getBrand();
        this.medicinePara = webRequestBean.getMedicine();

        this.yearPara = webRequestBean.getYear();
        this.quarterPara = webRequestBean.getQuarter();
        this.monthPara = webRequestBean.getMonth();
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
                    "Distributions of Top Medicines for All Factories");

            // Generate lineArgs
            lineArgsGenerator = new LineArgsGenerator(SQLStatments.SumSaleTransactionAllFactoryTime,
                    "Sales of All Factories",
                    "",
                    "Year");

            // Generate comboArgs
            String[] queries_c = {SQLStatments.SumSaleTransactionAllFactoryTimeSum, SQLStatments.AvgSaleTransactionAllFactoryTime};
            comboArgsGenerator = new ComboArgsGenerator(queries_c,
                    "Total Sales and Average Sales of All Factories",
                    "Amount",
                    "Year");
        }
        // Case 2: 1 specific factory with 1 specific year
        else if (this.getCommodityLevel().equals("factory") && this.getTimeLevel().equals("year")){
            if ( !this.getFactoryPara().isEmpty() && this.getYearPara() !=0){
                // Generate pieArgs
                pieArgsGenerator = new PieArgsGenerator(SQLStatments.SumSaleTransaction1F1Y_Pie,
                        "Sale Shares of Each Brand of " + getFactoryPara(), this.getFactoryPara(), this.getYearPara());

                // Generate lineArgs
                lineArgsGenerator = new LineArgsGenerator(SQLStatments.SumSaleTransaction1F1Y_Line,
                        "Sale Trend of Each Quarter in " + getYearPara(),
                        "",
                        "Quarter", this.getFactoryPara(), this.getYearPara());

                // Generate comboArgs
                String[] queries_c = {SQLStatments.SumSaleTransaction1F1Y_Combo_0, SQLStatments.SumSaleTransaction1F1Y_Combo_1};
                comboArgsGenerator = new ComboArgsGenerator(queries_c,
                        "Total Sales and Average Sales of " + this.getFactoryPara(),
                        "Amount",
                        "Quarter", this.getFactoryPara(), this.getYearPara());

                // Generate sankeyArgs
                String[] queries = {SQLStatments.SumSaleTransaction1F1Y_Sankey_0, SQLStatments.SumSaleTransaction1F1Y_Sankey_1};
                sankeyArgsGenerator = new SankeyArgsGenerator(queries,
                        "Distributions of Top Medicines for " + getFactoryPara(), this.getFactoryPara(), this.getYearPara());
            }
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

    public ComboArgs generateComboArgs(){
        if (getComboArgsGenerator() !=null){
            return getComboArgsGenerator().generateComboArgs();
        }
        else {
            return null;
        }
    }

    public static void main(String[] args){
//        GraphArgsGenerator graphArgsGenerator = new GraphArgsGenerator("", "","","","",0,0,0);
//        GraphArgsGenerator graphArgsGenerator = new GraphArgsGenerator("factory", "year","Greenstone LLC","","",2014,0,0);
//        graphArgsGenerator.processData();

//        PieArgs pieArgs = graphArgsGenerator.generatePieArgs();
//        SankeyArgs sankeyArgs = graphArgsGenerator.generateSankeyArgs();
//        LineArgs lineArgs = graphArgsGenerator.generateLineArgs();
//        ComboArgs comboArgs = graphArgsGenerator.generateComboArgs();

        WebRequestBean webRequestBean = new WebRequestBean();



        webRequestBean.setCommodityLevel("");
        webRequestBean.setFactory("");
        webRequestBean.setBrand("");
        webRequestBean.setMedicine("");
        webRequestBean.setTimeLevel("");
        webRequestBean.setYear(0);
        webRequestBean.setQuarter(0);
        webRequestBean.setMonth(0);


//        webRequestBean.setCommodityLevel("factory");
//        webRequestBean.setFactory("Greenstone LLC");
//        webRequestBean.setTimeLevel("year");
//        webRequestBean.setYear(2014);



        PieArgsGenerator pieArgsGenerator = new PieArgsGenerator(webRequestBean);
        LineArgsGenerator lineArgsGenerator = new LineArgsGenerator(webRequestBean);
        ComboArgsGenerator comboArgsGenerator = new ComboArgsGenerator(webRequestBean);

        PieArgs pieArgs = pieArgsGenerator.generatePieArgs();
        LineArgs lineArgs = lineArgsGenerator.generateLineArgs();
        ComboArgs comboArgs = comboArgsGenerator.generateComboArgs();

        System.out.println("");
    }

}
