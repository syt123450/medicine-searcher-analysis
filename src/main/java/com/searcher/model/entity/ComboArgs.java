package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 */
@Data
public class ComboArgs {

    private String title;
    private String vAxis;
    private String hAxis;
    private ArrayList<ArrayList<String>> argsData;

    public ComboArgs(){
        this.argsData = new ArrayList<ArrayList<String>>();
        this.title = "";
        this.vAxis = "";
        this.hAxis = "";
    }

    public ComboArgs(String title, String vAxis, String hAxis){
        this.argsData = new ArrayList<ArrayList<String>>();
        this.title = title;
        this.vAxis = vAxis;
        this.hAxis = hAxis;
    }

    public void addItemList(ArrayList<String> itemList){
        ArrayList<ArrayList<String>> tempList = this.getArgsData();
        tempList.add(itemList);
        this.setArgsData(tempList);
    }

}
