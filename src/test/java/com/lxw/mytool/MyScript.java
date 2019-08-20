package com.lxw.mytool;

import com.lxw.mytool.config.MyDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class MyScript {
    /**
     * 更新成本中心物料映射表中的物料描述为物料主数据中的
     */
    @Test
    public void testx(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getScDataSource());
        String sql = "select DISTINCT c.RFA_MATERIAL_CODE  MATNR,m.MAT_DESC MATDESC from MES_CONF_RFA_COST c,MES_MD_MAT m where c.RFA_MATERIAL_CODE = m.MAT_NR and  c.RFA_MATERIAL_NAME !=m.MAT_DESC";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        String sql2 ="update MES_CONF_RFA_COST set RFA_MATERIAL_NAME = ? where RFA_MATERIAL_CODE = ?";
        for (Map<String, Object> map : maps) {
            jdbcTemplate.update(sql2,map.get("MATDESC"),map.get("MATNR"));
        }
    }

    /**
     * mes_mat_stock_record表更新物料描述为物料主数据中的
     */
    @Test
    public void testxx(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getScDataSource());
        String sql = "SELECT distinct r.mat_nr MATNR,m.mat_desc MATDESC FROM mes_mat_stock_record r,MES_MD_MAT m where r.mat_nr = m.mat_nr and r.mat_desc != m.mat_desc";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        String sql2 ="update mes_mat_stock_record set mat_desc = ? where mat_nr = ?";
        for (Map<String, Object> map : maps) {
            jdbcTemplate.update(sql2,map.get("MATDESC"),map.get("MATNR"));
        }
    }

}
