package com.searcher.model.entity;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 * Chart Argument used to draw a Line Chart
 */
@Data
public class LineArgs extends ChartArgs{

    /* Customized parameters for Line Chart */
    private String subTitle;            // Subtitle of the Line Chart
    private String hAxis;               // Horizontal Axis Name
    private ArrayList<String> lineName; // List of all line names (in order)

    /**
     * Default constructor
     */
    public LineArgs(){
        super();
        subTitle = "";
        hAxis = "";
        lineName = new ArrayList<String>();
    }

    /**
     * Constructor used by providing title, subTitle and hAxis as parameters
     * @param title
     * @param subTitle
     * @param hAxis
     */
    public LineArgs(String title, String subTitle, String hAxis){
        super();
        this.setTitle(title);
        this.setSubTitle(subTitle);
        this.setHAxis(hAxis);
        this.setLineName(new ArrayList<String>());
    }

    /**
     * Add a new line name to lineName list
     * @param lineName
     */
    public void addLineNameItem(String lineName){
        ArrayList<String> tempList = this.getLineName();
        tempList.add(lineName);
        this.setLineName(tempList);
    }
}