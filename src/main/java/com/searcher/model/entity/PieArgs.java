package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 * Chart Argument used to draw a Pie Chart
 */

@Data
public class PieArgs extends ChartArgs{

    /**
     * Constructor used by providing title
     * @param title
     */
    public PieArgs(String title){
        super();
        this.setTitle(title);
    }
}
