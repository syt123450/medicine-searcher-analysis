package com.searcher.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
@RequestMapping("/API")
class WebPresenter {

    private Logger logger = Logger.getLogger(WebPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    private String queryAnalysis(@RequestBody String body) throws Exception{

        String response = "";

        return response;
    }

    @RequestMapping("/yesterday")
    private String getYesterdayPercentage() {

        return "";
    }
}