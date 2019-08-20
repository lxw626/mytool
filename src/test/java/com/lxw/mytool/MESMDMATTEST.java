package com.lxw.mytool;

import com.lxw.mytool.config.GlobalConfig;
import com.lxw.mytool.config.MyDataSource;
import com.lxw.mytool.util.ExcelUtil;
import com.lxw.mytool.util.IOUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class MESMDMATTEST {
    @Test
    public void test() throws Exception {
        String path = "D:\\360MoveData\\Users\\lixiewen\\Desktop\\物料主数据核对\\";
//        String fileName = "物料主信息20190531（固废）(1).xls";
//        String fileName = "物料主信息20190531（废资材备件）(1).xls";
//        String fileName = "物料主信息20190531（燃料）(1).xls";
        String fileName = "物料主信息20190531（原料）(1).xls";
        File file = new File(path + fileName);
        List<List<String>> rows = ExcelUtil.excel2List(file, 0);
        String sql = "select * from mes_md_mat where mat_nr = ?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getScDataSource());
        PrintWriter matDescErr = IOUtil.getPrintWriter(GlobalConfig.loggerPath+fileName+"matDescErr.txt", true);
        PrintWriter matDescNo = IOUtil.getPrintWriter(GlobalConfig.loggerPath+fileName+"matDescNo.txt", true);
        matDescErr.println("物料编码\texcel里面的物料编码\t生产库里面的物料描述");
        for (int r=0;r<rows.size();r++){
            List<String> cells = rows.get(r);
            if(r>0){
                String matnr = cells.get(1);
                String matDesc = cells.get(2);
                List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, matnr.trim());
                if(maps.size()<1){
                    matDescNo.println(matnr+"\t生产库里没有找到物料");
                }else{
                    Map<String, Object> stringObjectMap = maps.get(0);
                    String mat_desc = stringObjectMap.get("MAT_DESC").toString();
                    if(!matDesc.trim().equals(mat_desc.trim())){
                        matDescErr.println(matnr+"\t"+matDesc+"\t"+mat_desc);
                    }
                }
            }
        }
        matDescErr.flush();
        matDescNo.flush();
        matDescErr.close();
        matDescNo.close();

    }
    @Test
    public void test2() throws Exception {
        String path = "D:\\360MoveData\\Users\\lixiewen\\Desktop\\物料主数据核对\\";
//        String fileName = "物料主信息20190531（固废）(1).xls";
//        String fileName = "物料主信息20190531（固废）(1).xls";
//        String fileName = "物料主信息20190531（燃料）(1).xls";
        String fileName = "物料主信息20190531（原料）(1).xls";
        File file = new File(path + fileName);
        List<List<String>> rows = ExcelUtil.excel2List(file, 0);
        for (int r=0;r<rows.size();r++){
            List<String> cells = rows.get(r);
            String matnr = cells.get(1);
            String matDesc = cells.get(2);
            System.out.println(matnr);
            System.out.println(matDesc);
        }

    }
}
