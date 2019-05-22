package com.lxw.mytool;

import com.alibaba.druid.pool.DruidDataSource;
import com.lxw.mytool.util.DBUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MytoolApplicationTests {
	@Autowired
	JdbcTemplate jdbcTemplate;
	static Logger logger = LoggerFactory.getLogger(MytoolApplicationTests.class);




	@Test
	public void contextLoads() {
		String sql = "select count(0) FROM MES_MD_STOR where stor_code in (select stor_code from MES_MD_STOR where sid > 3)";
		Integer integer = jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println(integer);
	}
	@Test
	public void getText(){}
	@Test
	public void findTest(){
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUsername("pestest");
		sourceDS.setPassword("pestest");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.26.31:1521/tldbt");
		String sourceTN = "MES_MD_STOR";
		List<Map<String, Object>> maps = DBUtil.find(sourceDS, sourceTN);
		System.out.println(maps);

	}
	@Test
	public void dataSynFromOracleToMysql(){
		long start = System.currentTimeMillis();
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUsername("pestest");
		sourceDS.setPassword("pestest");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.26.31:1521/tldbt");
		String sourceTN = "MES_MD_PERSON";
		DruidDataSource destDS = new DruidDataSource();
		destDS.setDriverClassName("com.mysql.jdbc.Driver");
		destDS.setUsername("root");
		destDS.setPassword("root");
		destDS.setUrl("jdbc:mysql://localhost:3306/tlpes");
		String destTN = "MES_MD_PERSON";
		DBUtil.dataSyn(sourceDS,sourceTN,destDS,destTN);
		long end = System.currentTimeMillis();
		System.out.println("耗时:"+(end-start)/1000);
	}
	@Test
	public  void sysnTableTest(){
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUsername("pestest");
		sourceDS.setPassword("pestest");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.26.31:1521/tldbt");
		String sourceTN = "MES_MD_PERSON";
		DruidDataSource destDS = new DruidDataSource();
		destDS.setDriverClassName("com.mysql.jdbc.Driver");
		destDS.setUsername("root");
		destDS.setPassword("root");
		destDS.setUrl("jdbc:mysql://localhost:3306/tlpes");
		DBUtil.sysnTable(sourceDS,sourceTN,destDS);
	}
	@Test
	public void test5(){
		long start = System.currentTimeMillis();
		List<String> errTab = new ArrayList<>();
		DruidDataSource sourceDS = new DruidDataSource();
		sourceDS.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		sourceDS.setUsername("pestest");
		sourceDS.setPassword("pestest");
		sourceDS.setUrl("jdbc:oracle:thin:@//10.188.26.31:1521/tldbt");
		DruidDataSource destDS = new DruidDataSource();
		destDS.setDriverClassName("com.mysql.jdbc.Driver");
		destDS.setUsername("root");
		destDS.setPassword("root");
		destDS.setUrl("jdbc:mysql://localhost:3306/tlpes");
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
	@Test
	public void tt(){
		String sql = "select rowidtochar(rowid) as row_id,rownum as row_num,p.* from mes_md_stor_in p";
		List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
		System.out.println(maps);
		for (Map<String, Object> map : maps) {
			Set<String> keys = map.keySet();
			for (String key : keys) {
				Object o = map.get(key);
				System.out.println(key);
				System.out.println(o);
				System.out.println("========================");
			}
		}
	}


}
