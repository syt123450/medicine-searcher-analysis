package com.searcher.model.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/5.
 */

@Data
public class SankeyArgs {
    private String title;
    private ArrayList<ArrayList<String>> argsData;
}
