package com.searcher.model;


import com.searcher.model.argsGenerator.PieArgsGenerator;
import com.searcher.model.entity.PieArgs;
import com.searcher.utils.SQLStatments;

/**
 * Created by ss on 2017/5/9.
 */

public class YesterdayInfoGetter {

    private static final String TITLE = "Yesterday Sale Information";

    public PieArgs get() {

        PieArgsGenerator pieArgsGenerator = new PieArgsGenerator(SQLStatments.YESTERDAY_PIE, TITLE);
        PieArgs pieArgs = pieArgsGenerator.generatePieArgs();

        return pieArgs;
    }
}