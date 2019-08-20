package com.lxw.mytool;

import com.lxw.mytool.config.MyDataSource;
import com.lxw.mytool.util.DBUtil;
import com.lxw.mytool.util.IOUtil;
import com.lxw.mytool.util.ThreeDESUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.util.*;

public class MTest {
    /**
     * String对象的replace也是替换所有,
     * 若要替换第一个需要用replaceFirst
     */
    @Test
    public void stringReplaceTest(){
        String str = "hello and hello";
        if(str.contains("and")){
            System.out.println("and");
        }
        if(str.contains("AND")){
            System.out.println("AND");
        }
        System.out.println(str.replaceFirst("hello",""));

    }

    /**
     * 根据map生成插入sql
     */
    @Test
    public void getInsertSqlTest(){
        Map<String,Object> data = new HashMap<>();
        data.put("name","张三");
        data.put("age",20);
        String tableName = "person";
        String insertSql = DBUtil.getInsertSql(data, tableName);
        System.out.println(insertSql);

    }

    /**
     * List与数组互转
     * 特别注意List转数组时的类型
     */
    @Test
    public void test4(){
       List<String> nameList = new ArrayList<>();
        nameList.add("张三");
        nameList.add("李四");
        nameList.add("王五");
        Object[] objects = nameList.toArray();
//        运行时会报错ClassCastException
//        String[] eobjects = (String[]) nameList.toArray();
//        建议使用
        String[] nameArray = nameList.toArray(new String [nameList.size()]);
        List<String> names = Arrays.asList(nameArray);

    }

    /**
     * 可变参数的传入与接受
     * 第一中传入多个参数
     * 第二种传入一个数组
     * 接受:将可变参数作为数组遍历即可
     *
     */
    @Test
    public void variableParamTest(){
//        不传入参数
        this.myPrintln();
//        传入多个参数
        this.myPrintln("张三","李四");
//        传入一个数组
        String [] params = new String[]{"java","python"};
        this.myPrintln(params);

    }

    /**
     * 可变参数的接受
     * @param args
     */
    public void myPrintln(String ...args){
        for(String str:args){
            System.out.println(str);
        }
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

    /**
     * 单点测试系统查询密码
     * @throws Exception
     */
    @Test
    public void  findPassword() throws Exception {
//        String loginId = "tladmin";
        String loginId = "wenp0323";
        String sql = "SELECT LOGIN_ID,PASSWORD FROM SADP_AC_USER WHERE LOGIN_ID = ? ";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getScDanDianDataSource());
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(MyDataSource.getTestDDDataSource());
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql,loginId);
        for (Map<String, Object> map : maps) {
            String password = map.get("PASSWORD").toString();
            password = ThreeDESUtil.unEncryptPassword(password);
            System.out.println(map.get("LOGIN_ID").toString()+"\t"+password);
        }
    }

    @Test
    public void testxx() throws Exception {
        File file = new File("D:\\360MoveData\\Users\\lixiewen\\Desktop\\test22.html");
        List<String> strings = IOUtil.readFileByLine(file);
        String[] split = ss.split("\n");
        int i = 0;
        PrintWriter pw = IOUtil.getPrintWriter("D:\\360MoveData\\Users\\lixiewen\\Desktop\\test22x.html");
        for (String string : strings) {
            if(string.trim().contains("<img")){
                String substring = "<img src=\"/bl/findImage.action?";
                String substring2 = "\" alt=\"\" />";
                pw.println(substring+split[i]+substring2);
                i++;
            }else{
                pw.println(string);
            }
        }
        pw.flush();
        pw.close();
    }
    String ss = "newFileName=20190610154214_129.png&fileType=image&fileName=image1.png\n" +
            "newFileName=20190610154215_314.png&fileType=image&fileName=image2.png\n" +
            "newFileName=20190610154215_528.png&fileType=image&fileName=image3.png\n" +
            "newFileName=20190610154216_847.png&fileType=image&fileName=image4.png\n" +
            "newFileName=20190610154217_686.png&fileType=image&fileName=image5.png\n" +
            "newFileName=20190610154217_286.png&fileType=image&fileName=image6.png\n" +
            "newFileName=20190610154217_570.png&fileType=image&fileName=image7.png\n" +
            "newFileName=20190610154217_511.png&fileType=image&fileName=image8.png\n" +
            "newFileName=20190610154217_438.png&fileType=image&fileName=image9.png\n" +
            "newFileName=20190610154214_378.png&fileType=image&fileName=image10.png\n" +
            "newFileName=20190610154214_305.png&fileType=image&fileName=image11.png\n" +
            "newFileName=20190610154214_457.png&fileType=image&fileName=image12.png\n" +
            "newFileName=20190610154214_531.png&fileType=image&fileName=image13.png\n" +
            "newFileName=20190610154214_276.png&fileType=image&fileName=image14.png\n" +
            "newFileName=20190610154214_884.png&fileType=image&fileName=image15.png\n" +
            "newFileName=20190610154214_327.png&fileType=image&fileName=image16.png\n" +
            "newFileName=20190610154215_704.png&fileType=image&fileName=image17.png\n" +
            "newFileName=20190610154215_609.png&fileType=image&fileName=image18.png\n" +
            "newFileName=20190610154215_485.png&fileType=image&fileName=image19.png\n" +
            "newFileName=20190610154215_899.png&fileType=image&fileName=image20.png\n" +
            "newFileName=20190610154215_232.png&fileType=image&fileName=image21.png\n" +
            "newFileName=20190610154215_743.png&fileType=image&fileName=image22.png\n" +
            "newFileName=20190610154215_834.png&fileType=image&fileName=image23.png\n" +
            "newFileName=20190610154215_407.png&fileType=image&fileName=image24.png\n" +
            "newFileName=20190610154215_984.png&fileType=image&fileName=image25.png\n" +
            "newFileName=20190610154215_831.png&fileType=image&fileName=image26.png\n" +
            "newFileName=20190610154215_875.png&fileType=image&fileName=image27.png\n" +
            "newFileName=20190610154215_457.png&fileType=image&fileName=image28.png\n" +
            "newFileName=20190610154215_863.png&fileType=image&fileName=image29.png\n" +
            "newFileName=20190610154216_653.png&fileType=image&fileName=image30.png\n" +
            "newFileName=20190610154216_408.png&fileType=image&fileName=image31.png\n" +
            "newFileName=20190610154216_831.png&fileType=image&fileName=image32.png\n" +
            "newFileName=20190610154216_417.png&fileType=image&fileName=image33.png\n" +
            "newFileName=20190610154216_194.png&fileType=image&fileName=image34.png\n" +
            "newFileName=20190610154216_648.png&fileType=image&fileName=image35.png\n" +
            "newFileName=20190610154216_697.png&fileType=image&fileName=image36.png\n" +
            "newFileName=20190610154216_437.png&fileType=image&fileName=image37.png\n" +
            "newFileName=20190610154216_15.png&fileType=image&fileName=image38.png\n" +
            "newFileName=20190610154216_51.png&fileType=image&fileName=image39.png\n" +
            "newFileName=20190610154216_493.png&fileType=image&fileName=image40.png\n" +
            "newFileName=20190610154216_755.png&fileType=image&fileName=image41.png\n" +
            "newFileName=20190610154216_599.png&fileType=image&fileName=image42.png\n" +
            "newFileName=20190610154216_211.png&fileType=image&fileName=image43.png\n" +
            "newFileName=20190610154216_580.png&fileType=image&fileName=image44.png\n";


}
