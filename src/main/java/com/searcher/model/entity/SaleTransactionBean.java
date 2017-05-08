package com.searcher.model.entity;

import lombok.Data;

/**
 * Created by ss on 2017/5/5.
 */

@Data
public class SaleTransactionBean {
    //private int _id;            //Mongodb Obj ID
    private int quantity;
    private long time;
    private int medicineId;
    //private int medicineKey;
    private int storeId;
    //private int storeKey;
    private int customerId;
    //private int customerKey;
    private double totalPrice;

    //public int getSaleTransactionId()  {
    //    return _id;
    //}
    //public SaleTransactionBean( int q, long t, int mId, int sId, int cId, double p ) {
    //    quantity   = q;
    //    time       = t;
    //    medicineId = mId;
    //    storeId    = sId;
    //    customerId = cId;
    //    totalPrice = p;
    //}

    public int getQuantity()  {
        return quantity;
    }

    public long getTime()  {
        return time;
    }

    public int getMedicineId()  {
        return medicineId;
    }

    //public int getMedicineKey() { return medicineKey; }

    public int getStoreId()  {
        return storeId;
    }

    //public int getStoreKey() { return storeKey; }

    public int getCustomerId()  {
        return customerId;
    }

    //public int getCustomerKey() { return customerKey; }

    public double getTotalPrice()  {
        return totalPrice;
    }
}
