package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 */

@Data
public class PieArgs {

    private String title;
    private ArrayList<ArrayList<String>> argsData;

    public PieArgs(){
        argsData = new ArrayList<ArrayList<String>>();
    }

    public void addItemList(ArrayList<String> itemList){
        this.argsData.add(itemList);
    }

}
