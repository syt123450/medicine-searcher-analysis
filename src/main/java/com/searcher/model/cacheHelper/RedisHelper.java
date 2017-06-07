package com.searcher.model.cacheHelper;

import com.searcher.utils.CacheDataUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by ss on 2017/6/5.
 */

@Component
public class RedisHelper {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void dumpYesterdayData() throws Exception {
        ArrayList<ArrayList<String>> yesterdayDataList = CacheDataUtils.getYesterdayPie();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
        objectOutputStream.writeObject(yesterdayDataList);
        byte[] serializedData = arrayOutputStream.toByteArray();
        objectOutputStream.close();
        arrayOutputStream.close();

        stringRedisTemplate.opsForValue().set("yesterdayData", new String(serializedData, "iso-8859-1"));
    }

    public ArrayList<ArrayList<String>> getYesterdayData() throws Exception {

        String dataInString = stringRedisTemplate.opsForValue().get("yesterdayData");
        byte[] dataInByte = dataInString.getBytes("iso-8859-1");
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(dataInByte);
        ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
        ArrayList<ArrayList<String>> yesterdayDataList = (ArrayList<ArrayList<String>>) inputStream.readObject();

        return yesterdayDataList;
    }
}
