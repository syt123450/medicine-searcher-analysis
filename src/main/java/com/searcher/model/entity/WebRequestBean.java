package com.searcher.model.entity;

import lombok.Data;

/**
 * Created by ss on 2017/5/5.
 */

@Data
public class WebRequestBean {

    private String commodityLevel; //factory, brand, medicine
    private String factory;
    private String brand;
    private String medicine;

    private String timeLevel; //year, quarter, month
    private int year;
    private int quarter;
    private int month;
}
