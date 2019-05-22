package com.lxw.mytool.util;

import java.util.Map;
import java.util.Set;

public class StringUtil {
    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    /**
     * y_yyy==>yYyy
     * 下划线命名分格转为驼峰命名风格
     * @param s
     * @return
     */
    public static String _x2X(String s){
    	if(s.contains("_")){
//        找出_x
    		String _x = "_" + s.charAt(s.indexOf("_") + 1);
//        找出X
    		String X = String.valueOf(s.charAt(s.indexOf("_") + 1)).toUpperCase();
//        转为驼峰命名法
    		return _x2X(s.replaceAll(_x, X));
    	}
    	return s;
    }

    /**
     * 非空判断
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s){
        if(s != null && s.trim().length()>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 空判断
     * @param s
     * @return
     */
    public static boolean isEmpty(String s){
        if(s != null && s.trim().length()>0){
            return false;
        }else{
            return true;
        }
    }
    public static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;

        }

        return false;

    }

    /**
     * 将字符串里面的key替换成value
     * @param str
     * @param map
     * @return
     */
    public static String myReplanceAll(String str,Map<String,String> map){
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = map.get(key);
            if(value!=null){
                str = str.replaceAll(key,value);
            }else{
                str = str.replaceAll(key,"");
            }
        }
        return str;
    }
}
