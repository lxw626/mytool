package com.lxw.mytool.constant;

import com.lxw.mytool.util.DateUtil;

import java.util.Date;

/**
 * @autor lixiewen
 * @date 2019/1/19-15:58
 */
public interface GntityModel {
    // 注释模型
    String fieldNote = "    // <fieldNote>\n";
    //String类型成员变量申明模型
    String fieldModelString = "    private String <fieldName>;\n";
    //Date类型成员变量申明模型
    String fieldModelDate = "    private Date <fieldName>;\n";
    //BigDecimal类型成员变量申明模型
    String fieldModelBigDecimal = "    private BigDecimal <fieldName>;\n";
    //Integer类型成员变量申明模型
    String fieldModelInteger = "    private Integer <fieldName>;\n";
    //Integer类型成员变量申明模型
    String fieldModelDouble = "    private Double <fieldName>;\n";
    //生成get方法的模型
    String geterModel =  "    public <fieldType> get<fieldNameToUpperCaseFirstOne>() {\n" +
            "        return <fieldName>;\n" +
            "    }\n" +
            "\n";
    //生成set方法的模型
    String seterModel = "    public void set<fieldNameToUpperCaseFirstOne>(<fieldType> <fieldName>) {\n" +
            "        this.<fieldName> = <fieldName>;\n" +
            "    }\n" +
            "\n";
    //生成实体类的模型
    String entityModel = "package <entityPackageName>;\n" +
            "\nimport com.sgai.core.base.entity.AbstractEntity;\n"+
            "<importPackageNames>" +
            "\n" +
            "/**\n" +
            " * \n" +
            " * <p>Description: 实体类</p>\n" +
            " * <p>Copyright: Copyright (c) 2018</p>\n" +
            " * <p>Company: sgai</p>\n" +
            " * @author： lixiewen\n" +
            " * @version "+ DateUtil.Date2ShortString(new Date())+"\n" +
            " */"+
            "\n" +
            "public class <entityClassName>  extends  AbstractEntity {\n" +
            "\n" +
            "<fieldsAndNotes>"+
            "\n"+
            "<setersAndGeters>"+
            "\n"+
            "}";


}
