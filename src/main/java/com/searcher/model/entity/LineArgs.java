package com.searcher.model.entity;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 */

@Data
public class LineArgs {

    private String title;
    private String subTitle;
    private String hAxis;
    private ArrayList<String> lineName;
    private ArrayList<ArrayList<String>> argsData;

    public LineArgs(){
        title = "";
        subTitle = "";
        hAxis = "";
        lineName = new ArrayList<String>();
        argsData = new ArrayList<ArrayList<String>>();
    }

    public LineArgs(String title, String subTitle, String hAxis){
        title = "";
        subTitle = "";
        hAxis = "";
        lineName = new ArrayList<String>();
        argsData = new ArrayList<ArrayList<String>>();
    }

    public void addItemList(ArrayList<String> itemList){
        ArrayList<ArrayList<String>> tempList = this.getArgsData();
        tempList.add(itemList);
        this.setArgsData(tempList);
    }

    public void addLineNameItem(String lineName){
        ArrayList<String> tempList = this.getLineName();
        tempList.add(lineName);
        this.setLineName(tempList);
    }
}