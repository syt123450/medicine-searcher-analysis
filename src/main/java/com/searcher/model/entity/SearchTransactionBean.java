package com.searcher.model.entity;

import lombok.Data;

/**
 * Created by ss on 2017/5/5.
 */

@Data
public class SearchTransactionBean {
    private int medicineId;
    private int storeId;
    private int customerId;

    //public SearchTransactionBean(int m, int s, int c) {
    //    medicineId = m;
    //    storeId = s;
    //    customerId = c;
    //}
}
