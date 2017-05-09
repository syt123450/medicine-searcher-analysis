package com.searcher.model.argsGenerator;

import lombok.Data;

/**
 * Created by zchholmes on 2017/5/8.
 */
@Data
public class ComboArgsGenerator {
    private String query;
    private String title;
    private String vAxis;
    private String hAxis;

    public ComboArgsGenerator(String query, String title, String vAxis, String hAxis){
        this.query = query;
        this.title = title;
        this.vAxis = vAxis;
        this.hAxis = hAxis;
    }


}
