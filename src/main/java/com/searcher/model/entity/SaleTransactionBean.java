package com.searcher.model.entity;

import lombok.Data;

/**
 * Created by ss on 2017/5/5.
 */

@Data
public class SaleTransactionBean {
    private int saleTransactionId;
    private int quantity;
    private int time;
    private int medicineId;
    private int storeId;
    private int customerId;
    private int factoryId;
    private double totalPrice;

    public int getSaleTransactionId()  {
        return saleTransactionId;
    }

    public int getQuantity()  {
        return quantity;
    }

    public int getTime()  {
        return time;
    }

    public int getMedicineId()  {
        return medicineId;
    }

    public int getStoreId()  {
        return storeId;
    }

    public int getCustomerId()  {
        return customerId;
    }

    public int getFactoryId()  {
        return factoryId;
    }

    public double getTotalPrice()  {
        return totalPrice;
    }
}
