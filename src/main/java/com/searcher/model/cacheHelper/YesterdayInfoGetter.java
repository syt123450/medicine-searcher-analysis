package com.searcher.model.cacheHelper;


import com.searcher.model.cacheHelper.RedisHelper;
import com.searcher.model.entity.PieArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ss on 2017/5/9.
 */

@Service
public class YesterdayInfoGetter {

    private static final String TITLE = "Yesterday Sale Information";
    @Autowired
    private RedisHelper redisHelper;

    public PieArgs get() throws Exception {

//        PieArgsGenerator pieArgsGenerator = new PieArgsGenerator(SQLStatments.YESTERDAY_PIE, TITLE);
//        PieArgs pieArgs = pieArgsGenerator.generatePieArgs();

        PieArgs pieArgs = new PieArgs(TITLE);
        pieArgs.setArgsData(redisHelper.getYesterdayData());

        return pieArgs;
    }
}