package com.lxw.mytool.config;


import com.lxw.mytool.util.ColumnInfo;

import java.util.ArrayList;
import java.util.List;

public class GConfig {
    public static String runPath = "C:\\generatortest\\run.txt";
    //生成entity时忽略的字段
    public static List<String> missField = new ArrayList<>();
    static{
        missField.add("sid");
        missField.add("createdBy");
        missField.add("createdDt");
        missField.add("updatedBy");
        missField.add("updatedDt");
        missField.add("version");
    }
    //数据库类型
    public static final String DBType="oracle";
    //全局主键配置(可选配置),如果指定则用指定的,如果没有指定则用获取到的第一个
    public static final ColumnInfo pkColumn = new ColumnInfo("SID","sid","Integer","DECIMAL");
//    mysql数据库配置部分
//    public static final String DRIVER = "com.mysql.jdbc.Driver";

//    public static final String URL = "jdbc:mysql://localhost:3306/tlpes?useUnicode=true&characterEncoding=utf8&useSSL=true";
//    public static final String USERNAME = "root";
//    public static final String PASSWORD = "root";

//  oracle数据库配置
   public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

//    public static final String URL = "jdbc:oracle:thin:@//10.88.254.127:1521/SGJTTLDB";
//    public static final String USERNAME = "JTTLMES";
//    public static final String PASSWORD = "JTTLMES";

    public static final String URL = "jdbc:oracle:thin:@//10.188.26.31:1521/tldbt";
    public static final String USERNAME = "pestest";
    public static final String PASSWORD = "pestest";

//    public static final String URL = "jdbc:oracle:thin:@//10.188.26.57:1521/dddldbt";
//    public static final String USERNAME = "auth";
//    public static final String PASSWORD = "auth";

    /*public static final String URL = "jdbc:oracle:thin:@//localhost:1521/mldn";
    public static final String USERNAME = "scott";
    public static final String PASSWORD = "tiger";*/

    //    日期或者时间戳转为String
    public static final boolean date2String = false;

//  格式,每行生成的列数
    public static Integer columnCount = 5;

    //    生成文件包名
    public static final String entityPackageName = "com.sgai.lb.entity";
    public static final String mapperPackageName = "com.sgai.lb.mapper";
    //实体类生成路径
//    String entityGeneratePath = System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\generator\\entity\\";
    public static final String entityGeneratePath = "C:\\Users\\15689\\Desktop\\gtest\\entity\\";
    public static final String mapperGeneratePath = "C:\\Users\\15689\\Desktop\\gtest\\mapper\\";
    public static final String xmlGeneratePath = "C:\\Users\\15689\\Desktop\\gtest\\xml\\";
    //Mapper类生成路径
//    String mapperGeneratePath = System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\generator\\mapper\\";
    //xml生成路径
//    String xmlGeneratePath = System.getProperty("user.dir")+"\\src\\main\\resources\\mybatis\\mapper\\";
    //    是否生成swagger注解
    public static final boolean swagger = false;
    public static final boolean toString = true;
    //    entity需要导入的其他包.swagger的包已经内置,在swagger开关开启时生成
    /*******************************************生成dao的策略******************************************************************/
    //    生成文件包名
    public static final String daoPackageName = "com.example.generator.mapper";
    //    生成文件的路径
    public static final String daoPath = System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\generator\\mapper";
    /*******************************************生成service的策略******************************************************************/
//    生成文件包名
    public static final String servicePackageName = "com.example.generator.service";
    //    生成文件的路径(当前工程下的)
    public static final String servicePath = System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\generator\\service";
    /*******************************************生成controller的策略******************************************************************/
//    生成controller文件包名
    public static final String controllerPackageName = "com.example.generator.controller";
    //    生成文件的路径(当前工程下的)
    public static final String controllerPath = System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\generator\\controller";


    public static List<String> getMissField() {
        return missField;
    }

    public static void setMissField(List<String> missField) {
        GConfig.missField = missField;
    }
}
