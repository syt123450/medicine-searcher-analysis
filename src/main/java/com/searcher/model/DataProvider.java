package com.searcher.model;

import com.searcher.model.entity.*;

import java.util.ArrayList;

/**
 * Created by ss on 2017/5/5.
 */


public class DataProvider {

    public GraphCollection getGraphAnalyse(WebRequestBean webRequestBean){
        GraphCollection graphCollection = new GraphCollection();

        graphCollection.setPieArgs(mockPieArgsGenerator());
        graphCollection.setComboArgs(mockComboArgsGenerator());

        return graphCollection;
    }

    public PieArgs mockPieArgsGenerator(){
        // Mock PieArgs
        PieArgs pieArgs = new PieArgs();
        pieArgs.setTitle("Indian Language Use");

        ArrayList<String> tempList_1 = new ArrayList<String>();
        ArrayList<String> tempList_2 = new ArrayList<String>();
        ArrayList<String> tempList_3 = new ArrayList<String>();
        ArrayList<String> tempList_4 = new ArrayList<String>();
        ArrayList<String> tempList_5 = new ArrayList<String>();
        ArrayList<String> tempList_6 = new ArrayList<String>();
        ArrayList<String> tempList_7 = new ArrayList<String>();
        ArrayList<String> tempList_8 = new ArrayList<String>();

        tempList_1.add("Language");
        tempList_1.add("Speakers (in millions)");

        tempList_2.add("Assamese");
        tempList_2.add("13");
        tempList_2.add("Bengali");
        tempList_2.add("83");
        tempList_2.add("Bodo");
        tempList_2.add("1.4");

        tempList_3.add("Dogri");
        tempList_3.add("2.3");
        tempList_3.add("Gujarati");
        tempList_3.add("46");
        tempList_3.add("Hindi");
        tempList_3.add("300");

        tempList_4.add("Kannada");
        tempList_4.add("38");
        tempList_4.add("Kashmiri");
        tempList_4.add("5.5");
        tempList_4.add("Konkani");
        tempList_4.add("5");

        tempList_5.add("Maithili");
        tempList_5.add("20");
        tempList_5.add("Malayalam");
        tempList_5.add("33");
        tempList_5.add("Manipuri");
        tempList_5.add("1.5");

        tempList_6.add("Marathi");
        tempList_6.add("72");
        tempList_6.add("Nepali");
        tempList_6.add("2.9");
        tempList_6.add("Oriya");
        tempList_6.add("33");

        tempList_7.add("Punjabi");
        tempList_7.add("29");
        tempList_7.add("Sanskrit");
        tempList_7.add("0.01");
        tempList_7.add("Santhali");
        tempList_7.add("6.5");

        tempList_8.add("Sindhi");
        tempList_8.add("2.5");
        tempList_8.add("Tamil");
        tempList_8.add("61");
        tempList_8.add("Telugu");
        tempList_8.add("74");
        tempList_8.add("Urdu");
        tempList_8.add("52");

        pieArgs.addItemList(tempList_1);
        pieArgs.addItemList(tempList_2);
        pieArgs.addItemList(tempList_3);
        pieArgs.addItemList(tempList_4);
        pieArgs.addItemList(tempList_5);
        pieArgs.addItemList(tempList_6);
        pieArgs.addItemList(tempList_7);
        pieArgs.addItemList(tempList_8);

        return pieArgs;
    }

    public ComboArgs mockComboArgsGenerator(){
        ComboArgs comboArgs = new ComboArgs();

        return comboArgs;
    }

    public SankeyArgs mockSankeyArgsGenerator(){
        SankeyArgs sankeyArgs = new SankeyArgs();

        return sankeyArgs;
    }

}
