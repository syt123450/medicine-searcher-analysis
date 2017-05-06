package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 */

@Data
public class LineArgs {

    private String title;
    private String xAxisName;
    private ArrayList lineName;
    private ArrayList<ArrayList<String>> LineChartData;

}