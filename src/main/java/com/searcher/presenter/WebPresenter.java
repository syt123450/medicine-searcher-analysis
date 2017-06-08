package com.searcher.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.searcher.model.DataProvider;
import com.searcher.model.cacheHelper.YesterdayInfoGetter;
import com.searcher.model.entity.GraphCollection;
import com.searcher.model.entity.PieArgs;
import com.searcher.model.entity.WebRequestBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    YesterdayInfoGetter yesterdayInfoGetter;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    private String queryAnalysis(@RequestBody String body) throws Exception {

        WebRequestBean webRequestBean = gson.fromJson(body, WebRequestBean.class);
        DataProvider dataProvider = new DataProvider();
        GraphCollection graphCollection = dataProvider.getGraphAnalyse(webRequestBean);
        String response = gson.toJson(graphCollection);

        return response;
    }

    @RequestMapping("/yesterday")
    private String getYesterdayPercentage() throws Exception {

        PieArgs pieArgs = yesterdayInfoGetter.get();
        System.out.println(pieArgs.getArgsData().size());
        String response = gson.toJson(pieArgs);

        return response;
    }
}