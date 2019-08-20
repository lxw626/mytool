package com.lxw.mytool;

import com.lxw.mytool.config.MyDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class JointTableQueryTEST {
    @Test
    public void test1() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getLocalhostMysqlDataSource());
        String sql = "select * from emp e,dept d where e.deptno = d.deptno";
        List<Map<String,Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }
    @Test
    public void test2() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getLocalhostMysqlDataSource());
        String sql1 = "select * from emp";
        List maps1 = jdbcTemplate.queryForList(sql1);
        String sql2 = "select * from dept";
        List maps2 = jdbcTemplate.queryForList(sql2);
        Map columns1 = new HashMap<>();
        columns1.put("ename","myename");
        Map columns2 = new HashMap<>();
        columns2.put("dname","mydname");
        System.out.println(maps1);
        System.out.println(maps2);
        String s = " <div class=\"portlet\">\n" +
                "    <div class=\"top-l\">\n" +
                "        <div class=\"top-r\">\n" +
                "            <div class=\"top-m\">\n" +
                "                <span class=\"title\">标题</span>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"middle-l\">\n" +
                "        <div class=\"middle-r\">\n" +
                "            <div class=\"middle-m p-content\">\n" +
                "                <div id=\"panel1\" style=\"width:300px;height:150px;\">内容区域（包括正文和按钮）</div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"bottom-l\">\n" +
                "        <div class=\"bottom-r\">\n" +
                "            <div class=\"bottom-m\"></div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
        List<Map<String, Object>> maps = jointQuery2(maps1,maps2,columns1,columns2,"deptno", "deptno");
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }
    public static List<Map> jointQuery2(List<Map> masterData,List<Map> childData,Map masterColumnInfo,Map childColumnInfo,String masterKey,String childKey){
        List<Map> result = new ArrayList();
        for (Map masterRow : masterData) {
            Object masterValue = masterRow.get(masterKey);
            for (Map childRow : childData) {
                Object childValue = childRow.get(childKey);
                if(masterValue != null && masterValue.equals(childValue)){
                    Map map = new HashMap();
                    Set<String> masterColumns = masterColumnInfo.keySet();
                    for(String masterColumn:masterColumns){
                        String masterColumnAlias = masterColumnInfo.get(masterColumn).toString();
                        map.put(masterColumnAlias, masterRow.get(masterColumn));
                    }
                    Set<String> childColumns = childColumnInfo.keySet();
                    for(String childColumn:childColumns){
                        String childColumnAlias = childColumnInfo.get(childColumn).toString();
                        map.put(childColumnAlias, childRow.get(childColumn));
                    }
                    result.add(map);
                }
            }
        }
        return result;
    }
    public static List<Map<String,Object>> hhh(List<Map<String,Object>> data1,List<Map<String,Object>> data2,String key1,String key2){
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map<String, Object> map1 : data1) {
            Object value1 = map1.get(key1);
            for (Map<String, Object> map2 : data2) {
                Object value2 = map2.get(key2);
                if(value1.equals(value2)){
                    Map<String, Object> map = new HashMap<>();
                    result.add(mapCopy(mapCopy(map,map1),map2));
                }
            }
        }
        return result;
    }
    /**
     * 复制map对象
     * @explain 将paramsMap中的键值对全部拷贝到resultMap中；
     * paramsMap中的内容不会影响到resultMap（深拷贝）
     * @param paramsMap
     *     被拷贝对象
     * @param resultMap
     *     拷贝后的对象
     */
    public static Map<String,Object> mapCopy(Map<String,Object> paramsMap, Map<String,Object> resultMap) {
        if (resultMap == null) {
            resultMap = new HashMap();
        }
        if (paramsMap == null){
            return resultMap;
        }
        Set<String> keys = paramsMap.keySet();
        for (String key : keys) {
            resultMap.put(key,paramsMap.get(key));
        }
        return resultMap;
    }
}
