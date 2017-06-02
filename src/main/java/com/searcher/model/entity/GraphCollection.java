package com.searcher.model.entity;

import lombok.Data;

/**
 * Created by zchholmes on 2017/5/5.
 * Class to hold the actual necessary data sets of each charts
 */
@Data
public class GraphCollection {

    /* booleans used to determine if we have to display the specified chart  */
    private boolean drawPie;
    private boolean drawLine;
    private boolean drawCombo;
    private boolean drawSankey;

    /* Chart Arguments used to hold the data to draw each chart */
    private PieArgs pieArgs;
    private LineArgs lineArgs;
    private ComboArgs comboArgs;
    private SankeyArgs sankeyArgs;


}
