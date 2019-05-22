package com.lxw.mytool;

import com.lxw.mytool.config.MyDataSource;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MES_CX_DEPLETE_UNIT {
    /**
     * 修改标准消耗里面物料的单位为物料主数据里面物料的单位
     */
    @Test
    public void test1(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getTestDataSource());
//        查询标准消耗里面所有的物料编码
        List<Map<String, Object>> matNrs = jdbcTemplate.queryForList("SELECT DISTINCT(MAT_NO) FROM MES_CX_DEPLETE");
//        更新标准消耗里面物料的单位为物料主数据里面物料的单位
        String sql = "UPDATE MES_CX_DEPLETE SET UNIT = (SELECT UNIT FROM MES_MD_MAT WHERE MAT_NR = ?) WHERE MAT_NO = ?";
        List<Object[]> params = new ArrayList<>();
        for (Map<String, Object> mat : matNrs) {
            Object matNr =mat.get("MAT_NO");
            Object[] param = {matNr,matNr};
            params.add(param);
        }
        jdbcTemplate.batchUpdate(sql,params);
    }

}
