package com.lxw.mytool;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtilTest {
    File file = new File("C:\\Users\\lixiewen\\Desktop\\标准消耗数据导入\\标准消耗待处理数据.xlsx");
//    File file = new File("C:\\Users\\lixiewen\\Desktop\\测试数据.xls");
    int sheetIndex = 0;
    JdbcTemplate testJdbcTemplate = new JdbcTemplate(MyDataSource.getTestDataSource());

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
                        if(c>0&&c<70){
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
        for (MatInfo matInfo : matInfos) {
            System.out.println(matInfo);
        }
//        for(int i=0;i<matDesc.size();i++){
//            System.out.println(matDesc.get(i)+"\t"+matCode.get(i));
//        }
    }
    @Test
    public void test1() throws Exception{
        File f = new File("C:\\Users\\lixiewen\\Desktop\\标准消耗数据导入\\标准消耗待处理数据.xlsx");
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
        IOUtil.print2RunTxt(result.toString());
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
