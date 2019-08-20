package com.lxw.mytool;
import com.lxw.mytool.config.GlobalConfig;
import com.lxw.mytool.config.MyDataSource;
import com.lxw.mytool.util.ExcelUtil;
import com.lxw.mytool.util.IOUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtilTest {
//    File file = new File("C:\\Users\\lixiewen\\Desktop\\标准消耗数据导入\\标准消耗待处理数据.xlsx");
    File file = new File("D:\\360MoveData\\Users\\lixiewen\\Desktop\\标准消耗数据导入\\新元数据\\钢坯标准成本.xlsx");
    int sheetIndex = 0;
    JdbcTemplate testJdbcTemplate = new JdbcTemplate(MyDataSource.getTestDataSource());

    @Test
    public void cailiaoStor() throws Exception {
        File file = new File("D:\\1工作\\材料库数据\\材料库数据.xlsx");
        List<List<String>> rows = ExcelUtil.excel2List(file,1,false,true);
        List<String> errinfo = new ArrayList<>();
        for (int r=0;r<rows.size();r++) {
            List<String> cells = rows.get(r);
            for (int c=0;c<cells.size();c++) {
                if(r>0&&c>=2){
                    String cell = cells.get(c);
                    String sql = "select * from VIEW_MD_ORG_STRUCTURE_INFO where COST_CENTER_CODE = ?";
                    List<Map<String, Object>> maps = testJdbcTemplate.queryForList(sql, cell);
                    if(maps.size()<1){
                        errinfo.add("根据"+cell+"没有找到作业部信息");
                    }else{
                        Map<String, Object> map = maps.get(0);
                        String plant_code = map.get("PLANT_CODE").toString();
                        String plant_desc = map.get("PLANT_DESC").toString();
                    }
                }

            }
        }

    }
    @Test
    public void updateCailiaoStorPlant() throws Exception {
        JdbcTemplate moniJdbcTemplate = new JdbcTemplate(MyDataSource.getMoniDataSource());
        String querySql = "SELECT * FROM MES_CL_STOR";
        List<String> err = new ArrayList<>();
        List<Map<String, Object>> maps = moniJdbcTemplate.queryForList(querySql);
        if(maps.size()!=208){
            System.out.println("查到了:"+maps.size()+"条数据");
            return;
        }
        for (Map<String, Object> map : maps) {
            Object sid = map.get("SID");
            Object cost_center_code = map.get("COST_CENTER_CODE");
            String findP = "select * from VIEW_MD_ORG_STRUCTURE_INFO where COST_CENTER_CODE = ?";
            List<Map<String, Object>> maps1 = moniJdbcTemplate.queryForList(findP, cost_center_code);
            if(maps1.size()<1){
                err.add("没有找到该成本中心对应的作业部信息:"+cost_center_code.toString());
            }else{
                Map<String, Object> stringObjectMap = maps1.get(0);
                Object plant_code = stringObjectMap.get("PLANT_CODE");
                Object plant_desc = stringObjectMap.get("PLANT_DESC");
                String sql = "UPDATE MES_CL_STOR SET PLANT_CODE = ?,PLANT_DESC = ? WHERE SID = ?";
                int update = moniJdbcTemplate.update(sql, plant_code, plant_desc,sid);

            }

        }

    }

    /**
     * excel转List<List<String>>以行为单位输出
     * @throws Exception
     */
    @Test
    public void excel2ListTest() throws Exception {
        List<List<String>> rows = ExcelUtil.excel2List(file, sheetIndex,true,true);
        int i=0;
        for (List<String> row : rows) {
            if(i<5){
                StringBuilder sb = new StringBuilder();
                for (String cell : row) {
                    sb.append(cell+"\t");
                }
                System.out.println(sb.toString());
            }
            i++;
        }
        System.out.println("一共读取到了:"+rows.size()+"条数据");
    }

    /**
     * 标准消耗管理数据整理平面坐标转网格)
     * @throws Exception
     */
    @Test
    public void contextLoads() throws Exception {
        String fileName = file.getName();
//        判断是否是Excel
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new RuntimeException("上传文件格式不正确");
        }
//        判断Excel的版本号
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = new FileInputStream(file);
//        根据版本号获取工作簿
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
//        获取指定的sheet页
        Sheet sheet = wb.getSheetAt(sheetIndex);

        List<List<Object>> drs = new ArrayList<>();
        List<String> matCode = new ArrayList<>();
        List<String> matDesc = new ArrayList<>();
        List<MatInfo> matInfos = new ArrayList<>();
        for (int r = 0; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<Object> rcs = new ArrayList<>();
            String rc = null;
            StringBuilder sb = new StringBuilder();
            String ph = "";

            for (int c = 0; c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    rc = cell.getStringCellValue();
                    if(r==0){
                        matDesc.add(rc);
                    }else if(r==1){
                        matCode.add(rc);
                    }else{
                        if(c>0&&c<78){
                            MatInfo matInfo = new MatInfo();
                            matInfo.setMatCode(matCode.get(c));
                            matInfo.setMatDesc(matDesc.get(c));
                            matInfo.setPh(ph);
                            matInfo.setBv(rc);
                            matInfos.add(matInfo);
                        }else{
                            ph=rc;
                        }

                    }
                }
            }
        }
        PrintWriter pw = IOUtil.getPrintWriter(GlobalConfig.loggerPath + "test20190530.txt",true);
        for (MatInfo matInfo : matInfos) {
            pw.println(matInfo);
//            System.out.println(matInfo);
        }
        pw.flush();
        pw.close();
//        for(int i=0;i<matDesc.size();i++){
//            System.out.println(matDesc.get(i)+"\t"+matCode.get(i));
//        }
    }
    @Test
    public void updateUnit(){
        String sql = "select distinct(MAT_NO) from MES_CX_DEPLETE";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getTestDataSource());
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        String queryUnit = "select UNIT from MES_MD_MAT where MAT_NR = ?";
        List<String> err = new ArrayList<>();
        String update = "update mes_cx_deplete set UNIT = ? where mat_no=?";
        for (Map<String, Object> map : maps) {
            String mat_no = map.get("MAT_NO").toString();
            try {
                Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(queryUnit, mat_no);
                jdbcTemplate.update(update,stringObjectMap.get("UNIT").toString(),mat_no);

            }catch (Exception e){
                err.add(mat_no);
            }
        }
        for (String s : err) {
            System.out.println(s);
        }

    }
    @Test
    public void test1() throws Exception{
        File f = new File("D:\\360MoveData\\Users\\lixiewen\\Desktop\\标准消耗数据导入\\新元数据\\钢坯标准成本.xlsx");
        Object plant_code = "";
        Object plant_desc = "";
        Object scope_code = "";
        Object scope_desc = "";
        List<List<String>> rows = ExcelUtil.excel2List(f, 0, true, true);
        StringBuilder result = new StringBuilder();
        for (int ri=0;ri<rows.size();ri++){
                List<String> cells = rows.get(ri);
                StringBuilder sb = new StringBuilder();
                for(int ci=0;ci<cells.size();ci++){
                    String cell = cells.get(ci);
                    if(ri>0){
//                        如果成本中心编码不为空就给作业部,作业区赋值
                        if(ci==3){
                            if(cell!=null&&cell.trim().length()>0){
                                String sql = "SELECT PLANT_CODE,PLANT_DESC,SCOPE_CODE,SCOPE_DESC from VIEW_MD_ORG_STRUCTURE_INFO where COST_CENTER_CODE = ?";
                                System.out.println(cell);
                                List<Map<String, Object>> maps = testJdbcTemplate.queryForList(sql,cell);
                                if(maps.size()>0){
                                    Map<String, Object> info = maps.get(0);
                                    plant_code = info.get("PLANT_CODE");
                                    plant_desc = info.get("PLANT_DESC");
                                    scope_code = info.get("SCOPE_CODE");
                                    scope_desc = info.get("SCOPE_DESC");
                                }
                            }
                        }
                        if(ci ==15){
                            if(plant_code!=null &&plant_code.toString().trim().length()>0){
                                cell = plant_code.toString();
                            }
                            plant_code = "";
                        }
                        if(ci ==16){
                            if(plant_desc!=null &&plant_desc.toString().trim().length()>0){
                                cell = plant_desc.toString();
                            }
                            plant_desc = "";
                        }
                        if(ci ==17){
                            if(scope_code!=null &&scope_code.toString().trim().length()>0){
                                cell = scope_code.toString();
                            }
                            plant_desc = "";
                        }
                        if(ci ==18){
                            if(scope_desc!=null && scope_desc.toString().trim().length()>0){
                                cell = scope_desc.toString();
                            }
                            scope_code = "";
                        }

                    }
                    sb.append(cell+"\t");
                }
            result.append(sb.toString()+"\n");
            }
        System.out.println(result.toString());
//        IOUtil.print2RunTxt(result.toString());
    }
}
class MatInfo{
    private String matCode;
    private String matDesc;
    private String ph;
    private String bv;

    public String getMatCode() {
        return matCode;
    }

    public void setMatCode(String matCode) {
        this.matCode = matCode;
    }

    public String getMatDesc() {
        return matDesc;
    }

    public void setMatDesc(String matDesc) {
        this.matDesc = matDesc;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    @Override
    public String toString() {
        return matCode+"\t"+matDesc+"\t"+ph+"\t"+bv;
    }
}
