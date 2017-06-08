package com.searcher.model.virtualizationModule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.searcher.model.cacheHelper.RedisHelper;
import com.searcher.model.entity.SaleTransactionBean;
import com.searcher.model.entity.SearchTransactionBean;
import com.searcher.utils.MysqlUtils;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by ss on 2017/5/5.
 */

@Service
public class ETLDumper implements Runnable {

    static private String SALE_ETL_ADDRESS = "http://localhost:80/API/saleTransaction.php";
    static private String SEARCH_ETL_ADDRESS = "http://localhost:80/API/searchTransaction.php";
    static private int DUMP_INTERVAL = 86400000;

    private Gson gson = new GsonBuilder().create();
    private Logger logger = Logger.getLogger(ETLDumper.class);
    @Autowired
    RedisHelper redisHelper;

    @Override
    public void run() {

        while (true) {
            try {

                logger.info("ETL dumping yesterday data to MySQL.");

                String responseContent1 = Request.Get(SALE_ETL_ADDRESS).execute().returnContent().asString();
                System.out.println(responseContent1);
                ArrayList<SaleTransactionBean> saleTransactionBeans = gson.fromJson(responseContent1,
                        new TypeToken<ArrayList<SaleTransactionBean>>(){}.getType());
                MysqlUtils.persistSaleTransaction(saleTransactionBeans);

                String responseContent2 = Request.Get(SEARCH_ETL_ADDRESS).execute().returnContent().asString();
                System.out.println(responseContent2);
                ArrayList<SearchTransactionBean> searchTransactionBeans = gson.fromJson(responseContent1,
                        new TypeToken<ArrayList<SearchTransactionBean>>(){}.getType());
                MysqlUtils.persistSearchTransaction(searchTransactionBeans);

                logger.info("ETL dumping data to Redis.");

                redisHelper.dumpYesterdayData();

                Thread.sleep(DUMP_INTERVAL);

            } catch (Exception e) {

            }
        }
    }

//    public static void main( String[] args ) {
//        ETLDumper e = new ETLDumper();
//        e.run();
//    }
}