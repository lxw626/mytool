package com.lxw.mytool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxw.mytool.config.GlobalConfig;
import com.lxw.mytool.entity.Stor;
import com.lxw.mytool.mapper.StorMapper;
import com.lxw.mytool.util.DBUtil;
import com.lxw.mytool.util.ExcelUtil;
import com.lxw.mytool.util.IOUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MytoolApplicationTests {
	@Autowired
	JdbcTemplate jdbcTemplate;
	static Logger logger = LoggerFactory.getLogger(MytoolApplicationTests.class);

	@Test
	public void getTableNames(){
		List<String> tableNamesForOracle = DBUtil.getTableNamesForOracle();
		PrintWriter pw = IOUtil.getPrintWriter(GlobalConfig.loggerPath + "tableNames.txt");
		for (String s : tableNamesForOracle) {
			pw.println(s);
		}
		pw.flush();
		pw.close();
	}



	/**
	 * 数据同步
	 */
	@Test
	public void dataSynFromOracleToMysql() throws Exception {
		long start = System.currentTimeMillis();
//		源库
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.26.73:1521/dddldbt");
		sourceDS.setUsername("auth");
		sourceDS.setPassword("auth");
//		目标库
		DruidDataSource destDS = new DruidDataSource();
		destDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		destDS.setUrl("jdbc:oracle:thin:@//localhost:1521/ORCL");
		destDS.setUsername("scott");
		destDS.setPassword("tiger");
		//获取本地库所有表名
		DruidPooledConnection connection2 = destDS.getConnection();
		List<String> tableNames2 = DBUtil.getTableNamesForOracle(connection2,"SCOTT");

		JdbcTemplate jdbcTemplate1 = new JdbcTemplate(sourceDS);
		JdbcTemplate jdbcTemplate2 = new JdbcTemplate(destDS);
		PrintWriter printWriter = IOUtil.getPrintWriter(GlobalConfig.loggerPath + "测试获取数据库的所有表.txt");

		for (String tableName2 : tableNames2) {
			String sql = "SELECT * FROM ( " +
					" SELECT tmp_page.*, rownum AS row_id " +
					" FROM ( " +
					"select * from " + tableName2+
					" ) tmp_page " +
					"WHERE rownum <= 100 " +
					" )" +
					" WHERE row_id > 0 ";
			try {
				List<Map<String, Object>> maps = jdbcTemplate1.queryForList(sql);
				for (Map<String, Object> map : maps) {
					map.remove("ROW_ID");
				}
				if(maps.isEmpty()){
					printWriter.println(tableName2+"在生产库没有数据");
					System.out.println(tableName2+"在生产库没有数据");
				}else{
					List<Map<String, Object>> maps1 = jdbcTemplate2.queryForList("SELECT * FROM " + tableName2);
					if(maps1.size()==0){
						DBUtil.insert(destDS,tableName2,maps);
						printWriter.println("插入到本地数据库:"+tableName2+":"+maps.size()+"条");
					}
				}
			}catch (Exception e){

			}
		}
		printWriter.flush();
		printWriter.close();
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)/1000);
	}
	@Test
	public void dataSynFromOracleToMysqlxx() throws Exception {
		String tableName = "PES_REFERENCE_TABLE";
//		源库
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.18.215:1521/tldbp");
		sourceDS.setUsername("tlpesm");
		sourceDS.setPassword("tlpesm");
//		目标库
		DruidDataSource destDS = new DruidDataSource();
		destDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		destDS.setUrl("jdbc:oracle:thin:@//localhost:1521/ORCL");
		destDS.setUsername("scott");
		destDS.setPassword("tiger");
		JdbcTemplate jdbcTemplate1 = new JdbcTemplate(sourceDS);
		String sql = "select * from "+tableName;
		List<Map<String, Object>> maps = jdbcTemplate1.queryForList(sql);
		DBUtil.insert(destDS,tableName,maps);

	}

	/**
	 * 表同步
	 */
	@Test
	public  void sysnTableTest(){
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUsername("auth");
		sourceDS.setPassword("auth");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.26.57:1521/dddldbt");
		String sourceTN = "SADP_AC_RESOURCE";
		DruidDataSource destDS = new DruidDataSource();
		destDS.setDriverClassName("com.mysql.jdbc.Driver");
		destDS.setUsername("root");
		destDS.setPassword("root");
		destDS.setUrl("jdbc:mysql://localhost:3306/auth");
		DBUtil.sysnTable(sourceDS,sourceTN,destDS);
	}
	@Test
	public void test5(){
		long start = System.currentTimeMillis();
		List<String> errTab = new ArrayList<>();
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUsername("auth");
		sourceDS.setPassword("auth");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.26.57:1521/dddldbt");
		DruidDataSource destDS = new DruidDataSource();
		destDS.setDriverClassName("com.mysql.jdbc.Driver");
		destDS.setUsername("root");
		destDS.setPassword("root");
		destDS.setUrl("jdbc:mysql://localhost:3306/auth");
		List<String>  tableNames = DBUtil.getTableNamesForOracle();
		DBUtil.sysnTable(sourceDS,tableNames,destDS);
		System.out.println("耗时:"+(System.currentTimeMillis()-start)/1000);
		System.out.println(tableNames.size());
		System.out.println(errTab);
	}
	@Test
	public void getCreateTableSqlTest(){
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUsername("pestest");
		sourceDS.setPassword("pestest");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.26.31:1521/tldbt");
		String sourceTN = "MES_ENERGY_SHARE_INFO";
		DBUtil.getCreateTableSql(sourceDS,sourceTN);
	}
	@Test
	public void testxx(){
		DruidDataSource destDS = new DruidDataSource();
		destDS.setDriverClassName("com.mysql.jdbc.Driver");
		destDS.setUsername("root");
		destDS.setPassword("root");
		destDS.setUrl("jdbc:mysql://localhost:3306/tlpes");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(destDS);
		String sql = "CREATE TABLE MES_ENERGY_SHARE_INFO(`SID` int(22) NOT NULL COMMENT 'PK',`COMPANY_CODE` VARCHAR(4) COMMENT '公司编码',`PLANT_CODE` VARCHAR(8) COMMENT '作业部编码（工厂编码）',`PLANT_DESC` VARCHAR(60) COMMENT '作业部描述',`SCOPE_CODE` VARCHAR(8) COMMENT '作业区编码',`SCOPE_DESC` VARCHAR(60) COMMENT '作业区描述',`COST_CENTER_CODE` VARCHAR(8) COMMENT '成本中心编码',`COST_CENTER_DESC` VARCHAR(60) COMMENT '成本中心描述',`STOR_CODE` VARCHAR(12) COMMENT '库存地编码',`STOR_DESC` VARCHAR(60) COMMENT '库存地描述',`REPROD_DT` DATE COMMENT '报产日期',`CP_MAT_TYPE` VARCHAR(2) COMMENT '物料类型',`CP_L4_STEELGRADE_ID` VARCHAR(32) COMMENT '成品-外部牌号',`CP_STEELGRADE_ID` VARCHAR(32) COMMENT '成品-内部牌号',`CP_MAT_ID` VARCHAR(32) COMMENT '产出-物料号',`PROCESSES_TYPE` VARCHAR(32) COMMENT '工序类型',`ENERGY_ATTR_CODE` VARCHAR(15) COMMENT '能源属性代码(消耗/回收)?',`ENERGY_MED_CODE` VARCHAR(15) COMMENT '能源介质代码',`ENERGY_MED_DESC` VARCHAR(255) COMMENT '能源介质描述',`PRODUCT_CODE` VARCHAR(15) COMMENT '产副品代码',`PRODUCT_CODE_NAME` VARCHAR(15) COMMENT '产副品名称',`QTY` int(22) COMMENT '数量',`UNIT` VARCHAR(15) COMMENT '单位',`CREATED_BY` VARCHAR(32) ,`CREATED_DT` DATE ,`UPDATED_BY` VARCHAR(32) ,`UPDATED_DT` DATE ,`VERSION` int(22) COMMENT '版本',`STEEL_COST_CENTER` VARCHAR(10) COMMENT '炼钢成本中心',`PROCEDURE` VARCHAR(10) COMMENT '工序',`TIMEGRAIN` VARCHAR(1) COMMENT '时间粒度',PRIMARY KEY (SID))";
		int update = jdbcTemplate.update(sql);
	}

	@Autowired
	StorMapper storMapper;
	@Test
	public void testMybatis(){
		Map<String,Object> params = new HashMap<>();
		params.put("storCode","100");
		List<Stor> stors = storMapper.find(params);
		for (Stor stor : stors) {
			System.out.println(stor);
		}
	}
	@Autowired
	PageHelper pageHelper;
	@Test
	public void testMybatis2(){
		Map<String,Object> params = new HashMap<>();
		params.put("storCode","lb");
		PageHelper.startPage(1,3,true);
		List<Stor> stors = storMapper.find(params);
		PageInfo<Stor> storPageInfo = new PageInfo<>(stors);
		long total = storPageInfo.getTotal();
		System.out.println("total:"+total);
		for (Stor stor : stors) {
			System.out.println(stor);
		}
	}
	@Test
	public void testt(){
		String sql = "SELECT * FROM MES_CL_STOR ORDER BY SID ";
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
		HSSFWorkbook cailiao = ExcelUtil.getHSSFWorkbook("材料库管理", maps);
		File file=new File("d:\\poi\\材料库管理.xls");
		ExcelUtil.output2Loacl(cailiao,file);
	}


}
