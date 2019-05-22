package com.lxw.mytool.config;


import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * @autor lixiewen
 * @date 2019/4/18-15:08
 */
public class MyDataSource {
    static DruidDataSource testDataSource;
    static DruidDataSource testDDDataSource;
    static DruidDataSource moniDataSource;
    static DruidDataSource localhostMysqlDataSource;
    static{
        //测试业务数据库
        testDataSource = new DruidDataSource();
        testDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        testDataSource.setUsername("pestest");
        testDataSource.setPassword("pestest");
        testDataSource.setUrl("jdbc:oracle:thin:@//10.188.26.31:1521/tldbt");
        //测试单点数据库
        testDDDataSource = new DruidDataSource();
        testDDDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        testDDDataSource.setUsername("auth");
        testDDDataSource.setPassword("auth");
        testDDDataSource.setUrl("jdbc:oracle:thin:@//10.188.26.57:1521/dddldbt");
        //模拟数据库
        /*moniDataSource = new DruidDataSource();
        moniDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        moniDataSource.setUsername("auth");
        moniDataSource.setPassword("auth");
        moniDataSource.setUrl("jdbc:oracle:thin:@//10.188.26.73:1521/dddldbt");*/
        //本地mysql数据库
        localhostMysqlDataSource = new DruidDataSource();
        localhostMysqlDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        localhostMysqlDataSource.setUsername("root");
        localhostMysqlDataSource.setPassword("root");
        localhostMysqlDataSource.setUrl("jdbc:mysql://localhost:3306/mytool?useUnicode=true&characterEncoding=UTF-8&useSSL=true");
    }
    public static DataSource getTestDataSource() {
        return testDataSource;
    }

    public static DruidDataSource getTestDDDataSource() {
        return testDDDataSource;
    }

    public static DataSource getMoniDataSource() {
        return moniDataSource;
    }
    public static DataSource getLocalhostMysqlDataSource() {
        return localhostMysqlDataSource;
    }

}
