package com.lxw.mytool;

import com.lxw.mytool.config.MyDataSource;
import com.lxw.mytool.util.DBUtil;
import com.lxw.mytool.util.ExcelUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBUtilTest {
    Logger logger = LoggerFactory.getLogger(DBUtilTest.class);

    @Test
    public void getTableNamesForMysqlTest(){
        List<String> tableNames = DBUtil.getTableNamesForMysql();
        for (String tableName : tableNames) {
            logger.info(tableName);
        }
        logger.info("一共有:"+tableNames.size()+"张表");
    }
    @Test
    public void test1() throws Exception {
        File file = new File("C:\\Users\\lixiewen\\Desktop\\劳保库存地测试无用数据.xls");
//        String tableName = "MES_MD_MAT";
        String tableName = "MES_MD_STOR";
        List<List<String>> rows = ExcelUtil.excel2List(file, 0,true,true);
        String insertSql = null;
        List<Object[]> params = new ArrayList<>();
        for (int i = 0;i<rows.size();i++) {
            if(i==0){
                List<String> titles = rows.get(0);
                insertSql = DBUtil.getInsertSql(titles, tableName);
            }else{
                List<String> data = rows.get(i);
                System.out.println(data);
                Object[] objects = data.toArray();
                params.add(objects);
            }
        }
        DataSource localhostMysqlDataSource = MyDataSource.getLocalhostMysqlDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(localhostMysqlDataSource);
        int[] ints = jdbcTemplate.batchUpdate(insertSql, params);
        System.out.println(ints);
    }
    @Test
    public void test2(){
        List<String> columns = new ArrayList<>();
        columns.add("name");
        columns.add("age");
        columns.add("gender");
        String person = DBUtil.getInsertSql(columns, "person");
        System.out.println(person);
    }
}
