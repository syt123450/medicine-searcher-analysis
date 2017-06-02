package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 * Chart Argument used to draw a Combo Chart
 */
@Data
public class ComboArgs extends ChartArgs{

    /* Customized parameters for Line Chart */
    private String vAxis;       // Verticle Axis Name
    private String hAxis;       // Horizontal Axis Name

    /**
     * Default Constructor
     */
    public ComboArgs(){
        super();
        this.vAxis = "";
        this.hAxis = "";
    }

    /**
     * Constructor used by providing title, vAxis and hAxis as parameters
     * @param title
     * @param vAxis
     * @param hAxis
     */
    public ComboArgs(String title, String vAxis, String hAxis){
        super();
        this.setTitle(title);
        this.vAxis = vAxis;
        this.hAxis = hAxis;
    }
}
