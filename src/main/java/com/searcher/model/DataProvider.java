package com.searcher.model;

import com.searcher.model.entity.GraphCollection;
import com.searcher.model.entity.PieArgs;
import com.searcher.model.entity.WebRequestBean;

/**
 * Created by ss on 2017/5/5.
 */


public class DataProvider {

    public GraphCollection getGraphAnalyse(WebRequestBean webRequestBean){
        GraphCollection graphCollection = new GraphCollection();

        graphCollection.setPieArgs(mockPieArgsGenerator());


        return graphCollection;
    }

    public PieArgs mockPieArgsGenerator(){
        // Mock PieArgs
        PieArgs pieArgs = new PieArgs();
        pieArgs.addItem("['Language', 'Speakers (in millions)']");
        pieArgs.addItem("['Assamese', 13]");
        pieArgs.addItem("['Bengali', 83]");
        pieArgs.addItem("['Bodo', 1.4]");
        pieArgs.addItem("['Dogri', 2.3]");
        pieArgs.addItem("['Gujarati', 46]");
        pieArgs.addItem("['Hindi', 300]");
        pieArgs.addItem("['Kannada', 38]");
        pieArgs.addItem("['Kashmiri', 5.5]");
        pieArgs.addItem("['Konkani', 5]");
        pieArgs.addItem("['Maithili', 20]");
        pieArgs.addItem("['Malayalam', 33]");
        pieArgs.addItem("['Manipuri', 1.5]");
        pieArgs.addItem("['Marathi', 72]");
        pieArgs.addItem("['Nepali', 2.9]");
        pieArgs.addItem("['Oriya', 33]");
        pieArgs.addItem("['Punjabi', 29]");

        pieArgs.addItem("['Sanskrit', 0.01]");
        pieArgs.addItem("['Santhali', 6.5]");
        pieArgs.addItem("['Sindhi', 2.5]");
        pieArgs.addItem("['Tamil', 61]");
        pieArgs.addItem("['Telugu', 74]");
        pieArgs.addItem("['Urdu', 52]");

        pieArgs.setTitle("Indian Language Use");

        return pieArgs;
    }

}
