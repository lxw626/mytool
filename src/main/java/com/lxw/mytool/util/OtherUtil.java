package com.lxw.mytool.util;


import com.lxw.mytool.constant.ClassFullName;

/**
 * @autor lixiewen
 * @date 2019/2/22-19:34
 */
public class OtherUtil {
    public static String getClassFullName(String name){
        if("Integer".equals(name)||"int".equals(name)){
            return ClassFullName.integerType;
        }else if("Long".equals(name)){
            return ClassFullName.longType;
        }else if("BigDecimal".equals(name)){
            return ClassFullName.bigDecimalType;
        }else if("String".equals(name)){
            return ClassFullName.stringType;
        }
        return null;
    }
}
