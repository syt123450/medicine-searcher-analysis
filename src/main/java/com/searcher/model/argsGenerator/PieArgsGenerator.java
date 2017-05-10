package com.searcher.model.argsGenerator;

import com.searcher.model.entity.PieArgs;
import com.searcher.utils.MySQLConnection;
import com.searcher.utils.SQLStatments;
import lombok.Data;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by zchholmes on 2017/5/8.
 */
@Data
public class PieArgsGenerator {
    private String query;
    private String title;
    private String factoryParam;
    private String brandParam;
    private String medicineParam;
    private int yearParam;
    private int quarterParam;
    private int monthParam;

    public PieArgsGenerator(String query, String title){
        this.title = title;

        this.query = query;
        this.factoryParam ="";
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =-1;
        this.quarterParam =-1;
        this.monthParam =-1;
    }
    public PieArgsGenerator(String query, String title, String factoryParam, int yearParam){
        this.title = title;

        this.query = query;
        this.factoryParam =factoryParam;
        this.brandParam ="";
        this.medicineParam ="";
        this.yearParam =yearParam;
        this.quarterParam =-1;
        this.monthParam =-1;
    }

    public PieArgs generatePieArgs(){
        PieArgs pieArgs = new PieArgs(this.getTitle());

        // Add the default 1st ArrayList
        ArrayList<String> fixList = new ArrayList<String>();
        fixList.add("Language");
        fixList.add("Speakers (in millions)");
        pieArgs.addItemList(fixList);

        // Decide column label
        String columnLabel = "factoryName";
        if (!getBrandParam().isEmpty()){
            columnLabel = "medicineName";
        }
        else if (!getFactoryParam().isEmpty()){
            columnLabel = "brandName";
        }
        else {
            columnLabel = "factoryName";
        }


        // Add data from SQL call
        MySQLConnection mySQLConnection = new MySQLConnection();
        ResultSet resultSet = mySQLConnection.calcSaleSumByParam(getQuery(), getFactoryParam(),getBrandParam(),getMedicineParam(),getYearParam(),getQuarterParam(),getMonthParam());

        if (resultSet !=null){
            try {
                while (resultSet.next()){
                    ArrayList<String> tempList = new ArrayList<String>();

                    tempList.add(resultSet.getString(columnLabel));
                    tempList.add(Double.toString(resultSet.getDouble("totalSum")));

                    pieArgs.addItemList(tempList);
                }
            } catch (Exception what){
                what.printStackTrace();
            }
        }
        mySQLConnection.close();

        return pieArgs;
    }
}
