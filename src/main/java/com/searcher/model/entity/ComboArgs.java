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


}
