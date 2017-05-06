package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 */

@Data
public class PieArgs {

    private String title;
    private ArrayList<String> argsData;

    public PieArgs(){
        this.argsData = new ArrayList<String>();
    }

    public void addItem(String item){
        this.argsData.add(item);
    }
}
