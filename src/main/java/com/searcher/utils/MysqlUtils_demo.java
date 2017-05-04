package com.searcher.utils;
//
//import com.LWM2Mclient.model.entity.clientEntity.operation.JobBean;
//import com.LWM2Mclient.model.entity.clientEntity.register.NewRegistrationBean;
//import com.LWM2Mclient.model.entity.clientEntity.register.ResourceAddressBean;
//import com.LWM2Mclient.model.entity.clientEntity.register.UpdateRegistrationBean;
//import com.LWM2Mclient.model.entity.serverEntity.CentralServerInfoBean;
//import com.LWM2Mclient.model.entity.serverEntity.ServerInfoBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ss on 2017/4/2.
 */

public class MysqlUtils_demo {

    static String URL = "jdbc:mysql://localhost:3306/lwm2mClient?serverTimezone=GMT";
    static String USERNAME = "lwm2mClient";
    static String PASSWORD = "123456";

    //read
    static public String readResourceStatus(String resourceName) {

        String resourceStatus = "";

        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = String.format("select resourceStatus from resource where resourceName = '%s'", resourceName);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                resourceStatus = rs.getString(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            resourceStatus = "failed";
        }

        return resourceStatus;
    }

    //update
    static public String updateResourceStatus(String resourceName, String resourceStatus) {

        String updateResult = "";
        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = String.format("update resource " +
                            "set resourceStatus = '%s' " +
                            "where resourceName = '%s' " +
                            "and resourceStatus != '%s'",
                    resourceStatus, resourceName, resourceStatus);
            int rs = stmt.executeUpdate(sql);
            if (rs == 1) {
                updateResult = "success";
            } else {
                updateResult = "failed";
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            updateResult = "failed";
        }

        return updateResult;
    }

    //read
//    static public ArrayList<JobBean> readWorkList() {
//
//        ArrayList<JobBean> workList = new ArrayList<>();
//
//        try {
//
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//
//            String sql = "select jobID, mugColor, mugSize, mugPattern, mugNumber from workList";
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//
//                JobBean jobBean = new JobBean();
//                jobBean.setJobID(rs.getInt(1));
//                jobBean.setMugColor(rs.getString(2));
//                jobBean.setMugSize(rs.getString(3));
//                jobBean.setMugPattern(rs.getString(4));
//                jobBean.setMugNumber(rs.getInt(5));
//
//                workList.add(jobBean);
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return workList;
//    }

    //delete
    static public String deleteJob(int jobID) {

        String deleteResult = "";

        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = String.format("delete from workList where jobID = '%s'", jobID);
            int rs = stmt.executeUpdate(sql);
            if (rs == 1) {
                deleteResult = "success";
            } else {
                deleteResult = "failed";
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            deleteResult = "failed";
        }

        return deleteResult;
    }

    //create
    static public String addJob(ArrayList jobArgs) {

        String addResult = "";

        String mugColor = (String) jobArgs.get(0);
        String mugSize = (String) jobArgs.get(1);
        String mugPattern = (String) jobArgs.get(2);
        int mugNumber = (int) jobArgs.get(3);

        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = String.format("insert into workList " +
                            "(mugColor, mugSize, mugPattern, mugNumber) " +
                            "values " +
                            "('%s', '%s', '%s', %d)",
                    mugColor, mugSize, mugPattern, mugNumber);
            int rs = stmt.executeUpdate(sql);
            if (rs == 1) {
                addResult = "success";
            } else {
                addResult = "failed";
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            addResult = "failed";
        }

        return addResult;
    }

    //read
    static public String getOneServerInfo(String serverName, String infoName) {

        String resourceStatus = "";

        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = String.format("select %s from bootstrapInfo where serverName = '%s'", infoName, serverName);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                resourceStatus = rs.getString(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            resourceStatus = "failed";
        }

        return resourceStatus;
    }

    //read
//    static public NewRegistrationBean getClientInstanceInfo() {
//
//        NewRegistrationBean newRegistrationBean = new NewRegistrationBean();
//
//        try {
//
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//
//            String sql = String.format("select clientName, bindingMode, lifeTime, version, address from clientInfo");
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                newRegistrationBean.setClientName(rs.getString(1));
//                newRegistrationBean.setBindingMode(rs.getString(2));
//                newRegistrationBean.setLifeTime(rs.getInt(3));
//                newRegistrationBean.setVersion(rs.getString(4));
//                newRegistrationBean.setAddress(rs.getString(5));
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return newRegistrationBean;
//    }
//
//    //read
//    static public ArrayList<ResourceAddressBean> getClientResourceInstanceInfo() {
//
//        ArrayList<ResourceAddressBean> resourceAddressList = new ArrayList<>();
//
//        try {
//
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//
//            String sql = String.format("select resourceID, resourceName, operation, address " +
//                    "from resource_address");
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                ResourceAddressBean resourceAddressBean = new ResourceAddressBean();
//                resourceAddressBean.setResourceID(rs.getInt("resourceID"));
//                resourceAddressBean.setResourceName(rs.getString("resourceName"));
//                resourceAddressBean.setOperation(rs.getString("operation"));
//                resourceAddressBean.setAddress(rs.getString("address"));
//                resourceAddressList.add(resourceAddressBean);
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return resourceAddressList;
//    }
//
//    //update
//    static public void updateRegistrationInfo(UpdateRegistrationBean updateRegistrationBean) {
//
//        String bindingMode = updateRegistrationBean.getBindingMode();
//        String version = updateRegistrationBean.getVersion();
//
//        try {
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//
//            if (!bindingMode.equals("")) {
//                String updateBindingModeSql = String.format("update clientInfo set bindingMode = '%s'", bindingMode);
//                stmt.executeUpdate(updateBindingModeSql);
//            }
//            if (!version.equals("")) {
//                String updateVersionSql = String.format("update clientInfo set version = '%s'", version);
//                stmt.executeUpdate(updateVersionSql);
//            }
//
//            stmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //read
    static public HashMap<String, String> getResourceOperationInfo(int resourceID, String operation) {

        HashMap<String, String> infoMap = new HashMap<>();

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = String.format("select resourceName, address " +
                    "from resource_address " +
                    "where resourceID = '%s'" +
                    "and operation = '%s'", resourceID, operation);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                infoMap.put("resourceName", rs.getString("resourceName"));
                infoMap.put("address", rs.getString("address"));
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return infoMap;
    }

    //read
//    static public ArrayList<ServerInfoBean> getBootstrapInfo() {
//
//        ArrayList<ServerInfoBean> serverInfoBeans = new ArrayList<>();
//
//        try {
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//
//            String sql = "select serverName, newRegistrationUrl, updateRegistrationUrl, deRegistrationUrl, reporterUrl " +
//                    "from bootstrapInfo";
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                ServerInfoBean serverInfoBean = new ServerInfoBean();
//                serverInfoBean.setServerName(rs.getString("serverName"));
//                serverInfoBean.setNewRegistrationUrl(rs.getString("newRegistrationUrl"));
//                serverInfoBean.setUpdateRegistrationUrl(rs.getString("updateRegistrationUrl"));
//                serverInfoBean.setDeRegistrationUrl(rs.getString("deRegistrationUrl"));
//                serverInfoBean.setReporterUrl(rs.getString("reporterUrl"));
//                serverInfoBeans.add(serverInfoBean);
//            }
//
//            stmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return serverInfoBeans;
//    }

    static public void deleteAllServer() {

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = "delete from bootstrapInfo";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void deleteServer(String serverName) {

        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = String.format("delete from bootstrapInfo " +
                    "where serverName = '%s'", serverName);
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    static public void persistAllServer(ArrayList<ServerInfoBean> serverInfoBeans) {
//
//        try {
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//
//            for (int i = 0; i < serverInfoBeans.size(); i++) {
//                ServerInfoBean serverInfoBean = serverInfoBeans.get(i);
//                String sql = String.format("insert into bootstrapInfo " +
//                                "(serverName, newRegistrationUrl, updateRegistrationUrl, deRegistrationUrl, reporterUrl) " +
//                                "values " +
//                                "('%s', '%s', '%s', '%s', '%s')",
//                        serverInfoBean.getServerName(), serverInfoBean.getNewRegistrationUrl(),
//                        serverInfoBean.getUpdateRegistrationUrl(), serverInfoBean.getDeRegistrationUrl()
//                        , serverInfoBean.getReporterUrl());
//                stmt.executeUpdate(sql);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    static public void setEnvironment(String environment) {
        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = String.format("update environment set environment = '%s'", environment);
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static public String getEnvironment() {

        String environment = "";

        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = "select * from environment";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                environment = rs.getString(1);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return environment;
    }

//    static public JobBean getJobNow() {
//
//        JobBean jobBean = new JobBean();
//
//        try {
//
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//
//            String sql = "select jobID, mugNumber from workList " +
//                    "where jobID = " +
//                    "(select min(jobID) from workList)";
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                jobBean.setJobID(rs.getInt(1));
//                jobBean.setMugNumber(rs.getInt(2));
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return jobBean;
//    }

    static public void deleteJobItem() {
        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = "update workList, resource  " +
                    "set mugNumber = mugNumber - 1 " +
                    "where jobID =  (select min(jobID) from (select jobID from workList) as tmp) " +
                    "and (select resourceStatus from resource where resourceName = 'executor') = 'ON'";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

            deleteFinishedJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private void deleteFinishedJob() {
        try {

            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = "delete from workList where mugNumber = 0";
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void deleteCentralServer() {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = "delete from centralBootstrapInfo";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    static public void persistCentralServer(CentralServerInfoBean centralServerInfoBean) {
//        try {
//            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement stmt = conn.createStatement();
//
//            String sql = String.format("insert into centralBootstrapInfo " +
//                    "(paymentUrl, createReportUrl, downLoadReportUrl) " +
//                    "values ('%s', '%s', '%s')",
//                    centralServerInfoBean.getPaymentUrl(), centralServerInfoBean.getCreateReportUrl(),
//                    centralServerInfoBean.getDownLoadReportUrl());
//            stmt.executeUpdate(sql);
//            stmt.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    static public String getCentralServerResource(String resourceName) {

        String resource = "";

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = connection.createStatement();

            String sql = String.format("select %s from centralBootstrapInfo", resourceName);

            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                resource = resultSet.getString(1);
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resource;
    }

    static public String getClientName() {

        String clientName = "";

        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = connection.createStatement();

            String sql = "select clientName from clientInfo";

            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                clientName = resultSet.getString(1);
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return clientName;
    }
}