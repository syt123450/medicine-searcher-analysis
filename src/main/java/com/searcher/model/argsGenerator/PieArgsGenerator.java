package com.searcher.model.argsGenerator;

import com.searcher.model.entity.PieArgs;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 */
@Data
public class PieArgsGenerator {
    private ResultSet resultSet =null;
    private String title;

    public PieArgsGenerator(ResultSet resultSet, String title){
        this.resultSet = resultSet;
    }

    public PieArgs generatePieArgs(){
        PieArgs pieArgs = new PieArgs(this.getTitle());

        // Add the default 1st ArrayList
        ArrayList<String> tempList = new ArrayList<String>();
        tempList.add("Language");
        tempList.add("Speakers (in millions)");
        pieArgs.addItemList(tempList);



        return pieArgs;
    }
}
