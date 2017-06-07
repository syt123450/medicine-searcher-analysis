package com.searcher.model.virtualizationModule;

import org.apache.http.client.fluent.Request;

/**
 * Created by ss on 2017/5/5.
 */
public class RealtimeDataGetter {

    static private String REAL_TIME_URL = "http://localhost:80/API/virtualization.php";

    public String get() {

        String responseContent = "";

        try {
            responseContent = Request.Get(REAL_TIME_URL).execute().returnContent().asString();
//            System.out.println(responseContent);
        } catch (Exception e) {

        }

        return responseContent;
    }
}