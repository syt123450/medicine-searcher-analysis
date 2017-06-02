package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 */

@Data
public class SankeyArgs extends ChartArgs{

    /**
     * Constructor used by providing title
     * @param title
     */
    public SankeyArgs(String title) {
        super();
        this.setTitle(title);
    }
}
