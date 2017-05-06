package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 */

@Data
public class SankeyArgs {
    private String title;
    private ArrayList<ArrayList<String>> argsData;

    public SankeyArgs(){
        this.argsData = new ArrayList<ArrayList<String>>();
        this.title = "";
    }

    public SankeyArgs(String title) {
        this.argsData = new ArrayList<ArrayList<String>>();
        this.title = title;
    }

    public void addItemList(ArrayList<String> itemList){
        ArrayList<ArrayList<String>> tempList = this.getArgsData();
        tempList.add(itemList);
        this.setArgsData(tempList);
    }
}
