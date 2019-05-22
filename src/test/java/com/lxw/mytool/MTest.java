package com.lxw.mytool;

import com.alibaba.druid.pool.DruidDataSource;
import com.lxw.mytool.config.MyDataSource;
import com.lxw.mytool.util.DBUtil;
import com.lxw.mytool.util.ThreeDESUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MTest {
    @Test
    public void test1(){
        String sql ="SELECT sid 主键,ename as 用户名,empno FROM MES_MD_PERSON";
        System.out.println(this.getSql(sql));
        sql ="SELECT sid 主键,ename as 用户名,empno FROM MES_MD_PERSON where " +
                " {PLANT_CODE = [作业部]}" +
                " {AND SCOPE_CODE = [作业区]}" +
                " {AND IN_FACTORY_DATE >=  [开始时间]}  " +
                " {AND IN_FACTORY_DATE  <= [结束时间 ]} " +
                " and ename = 'reos' ";
        System.out.println(this.getSql(sql));
        sql ="SELECT sid 主键,ename as 用户名,empno FROM MES_MD_PERSON where " +
                " {AND SCOPE_CODE = [作业区]}" +
                " {AND IN_FACTORY_DATE >=  [开始时间]}  " +
                " {AND IN_FACTORY_DATE  <= [结束时间 ]} " +
                " and ename = 'reos' ";
        System.out.println(this.getSql(sql));
    }
    private String getSql(String sql){
        List<String> templateCondition = this.getTemplateCondition(sql);
        if(templateCondition.size()<1){
            return sql;
        }
        String plantCode = "ltb";
        String scopeCode = "qt";
        String matNr = "blf";
        String matDesc = "布洛芬";
        String startTime = "";
        String endTime = "";
        //第一步:去查sql前后的空格
        sql = sql.trim();
        for(String condition:templateCondition){
            //作业部
            if(condition.contains("作业部")){
                if(this.isNotBlank(plantCode)){
                    sql = sql.replace("作业部",plantCode);
                }else{
                    sql = sql.replace(condition,"");
                }
            }
            //作业区
            if(condition.contains("作业区")){
                if(this.isNotBlank(scopeCode)){
                    sql = sql.replace("作业区",scopeCode);
                }else{
                    sql = sql.replace(condition,"");
                }
            }
            //物料编码
            if(condition.contains("物料编码")){
                if(this.isNotBlank(matNr)){
                    sql = sql.replace("物料编码",matNr);
                }else{
                    sql = sql.replace(condition,"");
                }
            }
            //物料描述
            if(condition.contains("物料描述")){
                if(this.isNotBlank(matNr)){
                    sql = sql.replace("物料描述",matDesc);
                }else{
                    sql = sql.replace(condition,"");
                }
            }
            //开始时间
            if(condition.contains("开始时间")){
                if(this.isNotBlank(startTime)){
                    sql = sql.replace("开始时间",startTime);
                }else{
                    sql = sql.replace(condition,"");
                }
            }
            //结束时间
            if(condition.contains("结束时间")){
                if(this.isNotBlank(startTime)){
                    sql = sql.replace("结束时间",endTime);
                }else{
                    sql = sql.replace(condition,"");
                }
            }
        }
        sql = sql.replaceAll("\\{","");
        sql = sql.replaceAll("}","");
        sql = sql.replaceAll("\\[","");
        sql = sql.replaceAll("]","");
        if(sql.contains("WHERE")||sql.contains("where")){
            String substring;
            if(sql.contains("WHERE")){
                substring = sql.substring(sql.indexOf("WHERE") + 5).trim();
            }else{
                substring = sql.substring(sql.indexOf("where") + 5).trim();
            }
            if(substring.startsWith("AND")){
                sql = sql.replaceAll("AND","");
            }
            if(substring.startsWith("and")){
                sql = sql.replaceAll("and","");
            }
        }

        sql = sql.replaceAll("\\s{2,}"," ");
        return sql;
    }
    private boolean isNotBlank(String str){
        if(str != null && str.trim().length() > 0){
            return true;
        }
        return false;
    }

    private List<String> getTemplateCondition(String sql){
        List<String> conditions = new ArrayList<>();
        if(!(sql.contains("WHERE")||sql.contains("where"))){
            return conditions;
        }
        int startIndex = 0;
        if(sql.contains("WHERE")){
            startIndex = sql.indexOf("WHERE");
        }
        if(sql.contains("where")){
            startIndex = sql.indexOf("where");
        }
        int endIndex = sql.lastIndexOf("}");
        String substring = sql.substring(startIndex+5, endIndex+1);
        String[] split = substring.split("}");
        for (String aSplit : split) {
            conditions.add(aSplit.trim() + "}");
        }
        return conditions;
    }
    @Test
    public void test2(){
        String str = "hello and hello";
        if(str.contains("and")){
            System.out.println("and");
        }
        if(str.contains("AND")){
            System.out.println("AND");
        }
        System.out.println(str.replaceFirst("hello",""));

    }
    @Test
    public void test3(){
        Map<String,Object> data = new HashMap<>();
        data.put("name","张三");
        data.put("age",20);
        String tableName = "person";
        String insertSql = DBUtil.getInsertSql(data, tableName);
        System.out.println(insertSql);

    }
    @Test
    public void test4(){
       /* String [] sss = {"111","222","333"};
        this.ttt(sss);*/
       List<String> hhh = new ArrayList<>();
       hhh.add("aaa");
        hhh.add("bbb");
        hhh.add("ccc");
        String[] a = new String [hhh.size()];
        hhh.toArray(a);
        System.out.println(a.length);

    }
    public void ttt(String ...args){
        for(String str:args){
            System.out.println(str);
        }
    }
    public void  test5(){
        String s = "AAAq/OAAHAAAAEDAAA";
    }
    File file = new File("C:\\Users\\lixiewen\\Desktop\\物料测试数据.xlsx");

    @Test
    public void contextLoads() throws Exception {
        InputStream is = new FileInputStream(file);
        Workbook wb = new HSSFWorkbook(is);
        List<List<Object>> datars = new ArrayList<>();
        Sheet sheet = wb.getSheetAt(0);
        for (int r = 0; r <= 3; r++) {
            List<Object> datacs = new ArrayList<>();
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            Cell cell;
            for (int c = 0; c < row.getLastCellNum(); c++) {
                cell = row.getCell(c);
                if (cell != null) {
                    if (cell.getCellType() == 0) {
                        Double cellValue = cell.getNumericCellValue();
                        datacs.add(cellValue);
                    }
                    if (cell.getCellType() == 1) {
                        String stringCellValue = cell.getStringCellValue();
                        datacs.add(stringCellValue);
                    }
                }
            }
            datars.add(datacs);
        }
        for (List<Object> datar : datars) {
            StringBuilder sb = new StringBuilder();
            for (Object datac : datar) {
                sb.append(datac+"\t");
            }
            System.out.println(sb.toString());
        }


    }
    @Test
    public void  testp() throws Exception {
        String password = "12345611111";
        String newPassword;
//        newPassword =  MD5Util.string2MD5(password);
        byte[] data = password.getBytes("UTF-8");
        byte[] byteEncryptPassword = ThreeDESUtil.des3EncodeCBC(ThreeDESUtil.KEY, ThreeDESUtil.KEY_IV, data);
        newPassword = new sun.misc.BASE64Encoder().encode(byteEncryptPassword);
        System.out.println(newPassword);
        String s = ThreeDESUtil.unEncryptPassword(newPassword);
        System.out.println(s);
//        System.out.println(ThreeDESUtil.unEncryptPassword("wvOd99n0V2o="));
    }
    @Test
    public void  testhhh() throws Exception {
        String sql = "SELECT LOGIN_ID,PASSWORD FROM SADP_AC_USER";
        DruidDataSource testDDDataSource = MyDataSource.getTestDDDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(testDDDataSource);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            String password = map.get("PASSWORD").toString();
            password = ThreeDESUtil.unEncryptPassword(password);
            System.out.println(map.get("LOGIN_ID").toString()+"\t"+password);
        }
    }
    @Test
    public void testt() throws Exception {
        String userName = "tladmin";
        String password = "123456";
        DataSource tlglAuthDataSource = MyDataSource.getTestDDDataSource();
        Connection connection = tlglAuthDataSource.getConnection();
        ResultSet resultSet = null;
        String sql = "SELECT LOGIN_ID,PASSWORD FROM SADP_AC_USER WHERE LOGIN_ID = ?";
        PreparedStatement preState = connection.prepareStatement(sql);
        preState.setString(1, userName);
        resultSet = preState.executeQuery();
        int i = 0;
        String encodePassword = null;
        while (resultSet.next()){
            encodePassword = resultSet.getString("PASSWORD");
            i++;
        }
        if(i==1){
            String dencodePassword = ThreeDESUtil.unEncryptPassword(encodePassword);
            if(password.equals(dencodePassword)){
                System.out.println("解码后的密码为:"+dencodePassword);
            }else{
                System.out.println("账号或密码错误");
            }
        }else if(i<1){
            System.out.println("没有查到用户名");
        }else{
            System.out.println("用户名不唯一请联系管理员");
        }
    }
    @Test
    public void dencodePasswordTest()throws Exception{
        String encodePassword = "TNDBtK4ecJ7hxb1gLrxOag==";
        String dencodePassword = ThreeDESUtil.unEncryptPassword(encodePassword);
        System.out.println(dencodePassword);
    }
}
