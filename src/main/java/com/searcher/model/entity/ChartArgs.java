package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/6/1.
 * Parent Class of all Chart Arguments
 */
@Data
public class ChartArgs {
    /* Minimum Required Parameters */
    private String title;   // Title of the chart
    private ArrayList<ArrayList<String>> argsData;  // Main Data List, contains all data used to draw charts

    /**
     * Default constructor
     */
    public ChartArgs(){
        title = "";
        argsData = new ArrayList<ArrayList<String>>();
    }

    /**
     * Add prepared list into the Main Data List.
     * Note:
     *  Different chart could have different detailed ArrayList formats
     * @param itemList
     */
    public void addItemList(ArrayList<String> itemList){
        ArrayList<ArrayList<String>> tempList = this.getArgsData();
        tempList.add(itemList);
        this.setArgsData(tempList);
    }
}
