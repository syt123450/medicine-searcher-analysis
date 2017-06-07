package com.searcher.utils;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ss on 2017/6/5.
 */

@Service
public class CacheDataUtils {

    private static final String URL      = "jdbc:mysql://localhost:3306/226analysis?serverTimezone=GMT";
    private static final String USERNAME = "ultimate";
    private static final String PASSWORD = "sesame";

    public static final String YESTERDAY_PIE
            =
            " SELECT SUM(s.totalPrice) as totalSum, m.brandName " +
                    " FROM SaleTransaction s, Medicine m, Calendar c " +
                    " WHERE c.fullDate = subdate(current_date, 1) " +
                    " AND s.medicineKey = m.medicineKey " +
                    " AND s.calendarKey = c.calendarKey " +
                    " GROUP BY m.brandName " +
                    " ORDER BY totalSum DESC " +
                    " ;";


    public static ArrayList<ArrayList<String>> getYesterdayPie() {

        ArrayList<ArrayList<String>> pieDataList = new ArrayList<>();

        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(YESTERDAY_PIE);
            while (rs.next()) {
                ArrayList<String> brandData = new ArrayList<>();
                brandData.add(rs.getString("brandName"));
                brandData.add(String.valueOf(rs.getDouble("totalSum")));
                pieDataList.add(brandData);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pieDataList;
    }

}
