package com.searcher.model.entity;

import lombok.Data;

/**
 * Created by zchholmes on 2017/5/5.
 */

@Data
public class GraphCollection {

    private boolean drawPie;
    private boolean drawLine;
    private boolean drawCombo;
    private boolean drawSankey;

    private PieArgs pieArgs;
    private LineArgs lineArgs;
    private ComboArgs comboArgs;
    private SankeyArgs sankeyArgs;
}
