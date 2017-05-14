package com.searcher.model;

import com.searcher.model.argsGenerator.*;
import com.searcher.model.entity.*;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by ss on 2017/5/5.
 */

@Data
public class DataProvider {

    GraphCollection graphCollection;

    public GraphCollection getGraphAnalyse(WebRequestBean webRequestBean){
        graphCollection = new GraphCollection();


//        GraphArgsGenerator graphArgsGenerator = new GraphArgsGenerator(webRequestBean);
//        graphArgsGenerator.processData();

//        PieArgs pieArgs = graphArgsGenerator.generatePieArgs();
//        SankeyArgs sankeyArgs = graphArgsGenerator.generateSankeyArgs();
//        LineArgs lineArgs = graphArgsGenerator.generateLineArgs();
//        ComboArgs comboArgs = graphArgsGenerator.generateComboArgs();

//        this.autoGraphDecisions(webRequestBean);


        PieArgsGenerator pieArgsGenerator = new PieArgsGenerator(webRequestBean);
        LineArgsGenerator lineArgsGenerator = new LineArgsGenerator(webRequestBean);
        ComboArgsGenerator comboArgsGenerator = new ComboArgsGenerator(webRequestBean);
        SankeyArgsGenerator sankeyArgsGenerator = new SankeyArgsGenerator(webRequestBean);

        PieArgs pieArgs = pieArgsGenerator.generatePieArgs();
        LineArgs lineArgs = lineArgsGenerator.generateLineArgs();
        ComboArgs comboArgs = comboArgsGenerator.generateComboArgs();
        SankeyArgs sankeyArgs = sankeyArgsGenerator.generateSankeyArgs();

        if (pieArgs!=null){
            graphCollection.setDrawPie(true);
            graphCollection.setPieArgs(pieArgs);
        }
        else {
            graphCollection.setDrawPie(false);
        }
        if (lineArgs!=null){
            graphCollection.setDrawLine(true);
            graphCollection.setLineArgs(lineArgs);
        }
        else {
            graphCollection.setDrawLine(false);
        }
        if (comboArgs!=null){
            graphCollection.setDrawCombo(true);
            graphCollection.setComboArgs(comboArgs);
        }
        else {
            graphCollection.setDrawCombo(false);
        }
        if (sankeyArgs!=null){
            graphCollection.setDrawSankey(true);
            graphCollection.setSankeyArgs(sankeyArgs);
        }
        else {
            graphCollection.setDrawSankey(false);
        }

        return graphCollection;
    }

    public void autoGraphDecisions(WebRequestBean webRequestBean){
        if (webRequestBean.getCommodityLevel().isEmpty() && webRequestBean.getTimeLevel().isEmpty()){
            getGraphCollection().setDrawLine(true);
            getGraphCollection().setDrawPie(true);
            getGraphCollection().setDrawCombo(true);
            getGraphCollection().setDrawSankey(true);
        }
        else if (!webRequestBean.getCommodityLevel().isEmpty() && !webRequestBean.getTimeLevel().isEmpty()){
            if (webRequestBean.getCommodityLevel().equals("factory") && webRequestBean.getTimeLevel().equals("year")){
                getGraphCollection().setDrawPie(true);
                getGraphCollection().setDrawLine(true);
                getGraphCollection().setDrawCombo(true);
                getGraphCollection().setDrawSankey(true);
            }
        }
        else {
            getGraphCollection().setDrawLine(false);
            getGraphCollection().setDrawPie(false);
            getGraphCollection().setDrawCombo(false);
            getGraphCollection().setDrawSankey(false);
        }
    }

    public PieArgs mockPieArgsGenerator(){
        // Mock PieArgs
        PieArgs pieArgs = new PieArgs("Indian Language Use");

        ArrayList<String> tempList_1 = new ArrayList<String>();
        ArrayList<String> tempList_2 = new ArrayList<String>();
        ArrayList<String> tempList_3 = new ArrayList<String>();
        ArrayList<String> tempList_4 = new ArrayList<String>();
        ArrayList<String> tempList_5 = new ArrayList<String>();
        ArrayList<String> tempList_6 = new ArrayList<String>();
        ArrayList<String> tempList_7 = new ArrayList<String>();
        ArrayList<String> tempList_8 = new ArrayList<String>();
        ArrayList<String> tempList_9 = new ArrayList<String>();
        ArrayList<String> tempList_10 = new ArrayList<String>();
        ArrayList<String> tempList_11 = new ArrayList<String>();
        ArrayList<String> tempList_12 = new ArrayList<String>();
        ArrayList<String> tempList_13 = new ArrayList<String>();
        ArrayList<String> tempList_14 = new ArrayList<String>();
        ArrayList<String> tempList_15 = new ArrayList<String>();
        ArrayList<String> tempList_16 = new ArrayList<String>();
        ArrayList<String> tempList_17 = new ArrayList<String>();
        ArrayList<String> tempList_18 = new ArrayList<String>();
        ArrayList<String> tempList_19 = new ArrayList<String>();
        ArrayList<String> tempList_20 = new ArrayList<String>();
        ArrayList<String> tempList_21 = new ArrayList<String>();
        ArrayList<String> tempList_22 = new ArrayList<String>();
        ArrayList<String> tempList_23 = new ArrayList<String>();


        tempList_1.add("Language");
        tempList_1.add("Speakers (in millions)");

        tempList_2.add("Assamese");
        tempList_2.add("13");
        tempList_3.add("Bengali");
        tempList_3.add("83");
        tempList_4.add("Bodo");
        tempList_4.add("1.4");

        tempList_5.add("Dogri");
        tempList_5.add("2.3");
        tempList_6.add("Gujarati");
        tempList_6.add("46");
        tempList_7.add("Hindi");
        tempList_7.add("300");

        tempList_8.add("Kannada");
        tempList_8.add("38");
        tempList_9.add("Kashmiri");
        tempList_9.add("5.5");
        tempList_10.add("Konkani");
        tempList_10.add("5");

        tempList_11.add("Maithili");
        tempList_11.add("20");
        tempList_12.add("Malayalam");
        tempList_12.add("33");
        tempList_13.add("Manipuri");
        tempList_13.add("1.5");

        tempList_14.add("Marathi");
        tempList_14.add("72");
        tempList_15.add("Nepali");
        tempList_15.add("2.9");
        tempList_16.add("Oriya");
        tempList_16.add("33");

        tempList_17.add("Punjabi");
        tempList_17.add("29");
        tempList_18.add("Sanskrit");
        tempList_18.add("0.01");
        tempList_19.add("Santhali");
        tempList_19.add("6.5");

        tempList_20.add("Sindhi");
        tempList_20.add("2.5");
        tempList_21.add("Tamil");
        tempList_21.add("61");
        tempList_22.add("Telugu");
        tempList_22.add("74");
        tempList_23.add("Urdu");
        tempList_23.add("52");

        pieArgs.addItemList(tempList_1);
        pieArgs.addItemList(tempList_2);
        pieArgs.addItemList(tempList_3);
        pieArgs.addItemList(tempList_4);
        pieArgs.addItemList(tempList_5);
        pieArgs.addItemList(tempList_6);
        pieArgs.addItemList(tempList_7);
        pieArgs.addItemList(tempList_8);

        pieArgs.addItemList(tempList_9);
        pieArgs.addItemList(tempList_10);
        pieArgs.addItemList(tempList_11);
        pieArgs.addItemList(tempList_12);
        pieArgs.addItemList(tempList_13);
        pieArgs.addItemList(tempList_14);
        pieArgs.addItemList(tempList_15);
        pieArgs.addItemList(tempList_16);

        pieArgs.addItemList(tempList_17);
        pieArgs.addItemList(tempList_18);
        pieArgs.addItemList(tempList_19);
        pieArgs.addItemList(tempList_20);
        pieArgs.addItemList(tempList_21);
        pieArgs.addItemList(tempList_22);
        pieArgs.addItemList(tempList_23);

        return pieArgs;
    }

    public ComboArgs mockComboArgsGenerator(){
        // Mock ComboArgs
        ComboArgs comboArgs = new ComboArgs("Monthly Coffee Production by Country", "Cups", "Month");

        ArrayList<String> tempList_1 = new ArrayList<String>();
        ArrayList<String> tempList_2 = new ArrayList<String>();
        ArrayList<String> tempList_3 = new ArrayList<String>();
        ArrayList<String> tempList_4 = new ArrayList<String>();
        ArrayList<String> tempList_5 = new ArrayList<String>();
        ArrayList<String> tempList_6 = new ArrayList<String>();

        tempList_1.add("");
        tempList_1.add("Bolivia");
        tempList_1.add("Ecuador");
        tempList_1.add("Madagascar");
        tempList_1.add("Papua New Guinea");
        tempList_1.add("Rwanda");
        tempList_1.add("Average");

        tempList_2.add("2004/05");
        tempList_2.add("165");
        tempList_2.add("938");
        tempList_2.add("522");
        tempList_2.add("998");
        tempList_2.add("450");
        tempList_2.add("614.6");

        tempList_3.add("2005/06");
        tempList_3.add("135");
        tempList_3.add("1120");
        tempList_3.add("599");
        tempList_3.add("1268");
        tempList_3.add("288");
        tempList_3.add("682");

        tempList_4.add("2006/07");
        tempList_4.add("157");
        tempList_4.add("1167");
        tempList_4.add("587");
        tempList_4.add("807");
        tempList_4.add("397");
        tempList_4.add("623");

        tempList_5.add("2007/08");
        tempList_5.add("139");
        tempList_5.add("1110");
        tempList_5.add("615");
        tempList_5.add("968");
        tempList_5.add("215");
        tempList_5.add("609.4");

        tempList_6.add("2008/09");
        tempList_6.add("136");
        tempList_6.add("691");
        tempList_6.add("629");
        tempList_6.add("1026");
        tempList_6.add("366");
        tempList_6.add("569.6");


        comboArgs.addItemList(tempList_1);
        comboArgs.addItemList(tempList_2);
        comboArgs.addItemList(tempList_3);
        comboArgs.addItemList(tempList_4);
        comboArgs.addItemList(tempList_5);
        comboArgs.addItemList(tempList_6);

        return comboArgs;
    }

    public SankeyArgs mockSankeyArgsGenerator(){
        // Mock SankeyArgs
        SankeyArgs sankeyArgs = new SankeyArgs("某些标题？？？");

        ArrayList<String> tempList_1 = new ArrayList<String>();
        ArrayList<String> tempList_2 = new ArrayList<String>();
        ArrayList<String> tempList_3 = new ArrayList<String>();
        ArrayList<String> tempList_4 = new ArrayList<String>();
        ArrayList<String> tempList_5 = new ArrayList<String>();
        ArrayList<String> tempList_6 = new ArrayList<String>();

        tempList_1.add("A");
        tempList_1.add("X");
        tempList_1.add("5");

        tempList_2.add("A");
        tempList_2.add("Y");
        tempList_2.add("7");

        tempList_3.add("A");
        tempList_3.add("Z");
        tempList_3.add("6");

        tempList_4.add("B");
        tempList_4.add("X");
        tempList_4.add("2");

        tempList_5.add("B");
        tempList_5.add("Y");
        tempList_5.add("9");

        tempList_6.add("B");
        tempList_6.add("Z");
        tempList_6.add("4");

        sankeyArgs.addItemList(tempList_1);
        sankeyArgs.addItemList(tempList_2);
        sankeyArgs.addItemList(tempList_3);
        sankeyArgs.addItemList(tempList_4);
        sankeyArgs.addItemList(tempList_5);
        sankeyArgs.addItemList(tempList_6);

        return sankeyArgs;
    }

    public LineArgs mockLineArgsGenerator(){
        // Mock LineArgs
        LineArgs lineArgs = new LineArgs(
                "Box Office Earnings in First Two Weeks of Opening",
                "in millions of dollars (USD)",
                "Day"
        );

        ArrayList<String> lineName = new ArrayList<String>();
        lineName.add("Guardians of the Galaxy");
        lineName.add("The Avengers");
        lineName.add("Transformers: Age of Extinction");
        lineArgs.setLineName(lineName);

        ArrayList<String> tempList_1 = new ArrayList<String>();
        ArrayList<String> tempList_2 = new ArrayList<String>();
        ArrayList<String> tempList_3 = new ArrayList<String>();
        ArrayList<String> tempList_4 = new ArrayList<String>();
        ArrayList<String> tempList_5 = new ArrayList<String>();
        ArrayList<String> tempList_6 = new ArrayList<String>();
        ArrayList<String> tempList_7 = new ArrayList<String>();
        ArrayList<String> tempList_8 = new ArrayList<String>();
        ArrayList<String> tempList_9 = new ArrayList<String>();
        ArrayList<String> tempList_10 = new ArrayList<String>();
        ArrayList<String> tempList_11 = new ArrayList<String>();
        ArrayList<String> tempList_12 = new ArrayList<String>();
        ArrayList<String> tempList_13 = new ArrayList<String>();
        ArrayList<String> tempList_14 = new ArrayList<String>();

        tempList_1.add("1");
        tempList_1.add("37.8");
        tempList_1.add("80.8");
        tempList_1.add("41.8");

        tempList_2.add("2");
        tempList_2.add("30.9");
        tempList_2.add("69.5");
        tempList_2.add("32.4");

        tempList_3.add("3");
        tempList_3.add("25.4");
        tempList_3.add("57");
        tempList_3.add("25.7");

        tempList_4.add("4");
        tempList_4.add("11.7");
        tempList_4.add("18.8");
        tempList_4.add("10.5");

        tempList_5.add("5");
        tempList_5.add("11.9");
        tempList_5.add("17.6");
        tempList_5.add("10.4");

        tempList_6.add("6");
        tempList_6.add("8.8");
        tempList_6.add("13.6");
        tempList_6.add("7.7");

        tempList_7.add("7");
        tempList_7.add("7.6");
        tempList_7.add("12.3");
        tempList_7.add("9.6");

        tempList_8.add("8");
        tempList_8.add("12.3");
        tempList_8.add("29.2");
        tempList_8.add("10.6");

        tempList_9.add("9");
        tempList_9.add("16.9");
        tempList_9.add("42.9");
        tempList_9.add("14.8");

        tempList_10.add("10");
        tempList_10.add("12.8");
        tempList_10.add("30.9");
        tempList_10.add("11.6");

        tempList_11.add("11");
        tempList_11.add("5.3");
        tempList_11.add("7.9");
        tempList_11.add("4.7");

        tempList_12.add("12");
        tempList_12.add("6.6");
        tempList_12.add("8.4");
        tempList_12.add("5.2");

        tempList_13.add("13");
        tempList_13.add("4.8");
        tempList_13.add("6.3");
        tempList_13.add("3.6");

        tempList_14.add("14");
        tempList_14.add("4.2");
        tempList_14.add("6.2");
        tempList_14.add("3.4");

        lineArgs.addItemList(tempList_1);
        lineArgs.addItemList(tempList_2);
        lineArgs.addItemList(tempList_3);
        lineArgs.addItemList(tempList_4);
        lineArgs.addItemList(tempList_5);

        lineArgs.addItemList(tempList_6);
        lineArgs.addItemList(tempList_7);
        lineArgs.addItemList(tempList_8);
        lineArgs.addItemList(tempList_9);
        lineArgs.addItemList(tempList_10);

        lineArgs.addItemList(tempList_11);
        lineArgs.addItemList(tempList_12);
        lineArgs.addItemList(tempList_13);
        lineArgs.addItemList(tempList_14);

        return lineArgs;
    }

}
