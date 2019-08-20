package com.lxw.mytool;

import com.lxw.mytool.config.MyDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class LocalOracleTest {
    @Test
    public void testx(){
        DataSource localhostOracleDataSource = MyDataSource.getLocalhostOracleDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(localhostOracleDataSource);
        String sql = "select * from SADP_AC_USER";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        System.out.println(maps.size());

    }
}
