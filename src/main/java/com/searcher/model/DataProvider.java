package com.searcher.model;

import com.searcher.model.entity.*;

import java.util.ArrayList;

/**
 * Created by ss on 2017/5/5.
 */


public class DataProvider {

    public GraphCollection getGraphAnalyse(WebRequestBean webRequestBean){
        GraphCollection graphCollection = new GraphCollection();

        graphCollection.setDrawPie(true);
        graphCollection.setDrawCombo(true);
        graphCollection.setPieArgs(mockPieArgsGenerator());
        graphCollection.setComboArgs(mockComboArgsGenerator());
        graphCollection.setSankeyArgs(mockSankeyArgsGenerator());

        return graphCollection;
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
        ComboArgs comboArgs = new ComboArgs("Monthly Coffee Production by Country", "Cups", "Month");

        ArrayList<String> tempList_1 = new ArrayList<String>();
        ArrayList<String> tempList_2 = new ArrayList<String>();
        ArrayList<String> tempList_3 = new ArrayList<String>();
        ArrayList<String> tempList_4 = new ArrayList<String>();
        ArrayList<String> tempList_5 = new ArrayList<String>();
        ArrayList<String> tempList_6 = new ArrayList<String>();

        tempList_1.add("Month");
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
        SankeyArgs sankeyArgs = new SankeyArgs();

        return sankeyArgs;
    }

}
