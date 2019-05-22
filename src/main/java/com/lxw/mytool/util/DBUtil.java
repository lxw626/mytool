package com.lxw.mytool.util;


import com.lxw.mytool.config.GConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class DBUtil {
    static Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static final String SQL = "SELECT * FROM ";// 数据库操作

    /**
     * 获取oracle数据库下的所有表名
     */
    public static List<String> getTableNamesForOracle() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名,Oracle数据库的前两个参数为用户名但是必须大写！必须大写！必须大写！
            rs = db.getTables(GConfig.USERNAME.toUpperCase(), GConfig.USERNAME.toUpperCase(), null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            System.err.println("获取数据库表名失败");
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("关闭ResultSet失败");
            }
        }
        Collections.sort(tableNames);
        return tableNames;
    }
    /**
     * 获取mysql数据库下的所有表名
     */
    public static List<String> getTableNamesForMysql() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            logger .error("获取表名失败", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                logger.error("关闭结果集失败", e);
            }
        }
        return tableNames;
    }

    /**
     * 获取给定表的字段信息
     *
     * @param tableName
     * @return
     */
    public static List<ColumnInfo> getColumnInfos(String tableName) {
        if ("oracle".equals(GConfig.DBType)) {
            return getColumnInfosForOracle(tableName);
        } else if("mysql".equals(GConfig.DBType)){
            return getColumnInfosForMysql(tableName);
        }else{
            return null;
        }
    }


    /**
     * 获取oracle给定表的表信息
     *
     * @param tableName
     * @return
     */
    private static List<ColumnInfo> getColumnInfosForOracle(String tableName) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            String sql = "select utc.column_name,utc.data_type,ucc.comments,data_precision,data_scale" +
                    " from user_tab_columns utc,user_col_comments ucc " +
                    "where utc.table_name = ucc.table_name " +
                    "and utc.column_name = ucc.column_name " +
                    "and utc.table_name = '" + tableName + "' order by column_id";
            rs = pStemt.executeQuery(sql);
            while (rs.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setColumnName(rs.getString("COLUMN_NAME"));
                columnInfo.setColumnType(rs.getString("DATA_TYPE"));
                columnInfo.setColumnComment(rs.getString("COMMENTS"));
                String scale = rs.getString("DATA_SCALE");
                String precision = rs.getString("DATA_PRECISION");
                if(precision!=null){
                    columnInfo.setPrecision(Integer.valueOf(precision));
                }
                if(scale!=null){
                    columnInfo.setScale(Integer.valueOf(scale));
                }
                columnInfos.add(columnInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    System.err.println("获取表信息时关闭连接异常");
                }
            }
        }
        return columnInfos;
    }

    /**
     * 获取mysql给定表的表信息
     * @param tableName
     * @return
     */
    private static List<ColumnInfo> getColumnInfosForMysql(String tableName) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        return columnInfos;
    }

    /**
     * 返回指定表中的所有主键
     * @param tableName
     * @return
     */
    public static List<ColumnInfo> getPKs(String tableName) {
        List<ColumnInfo> pks = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = getConnection();
            String sql ="select utc.column_name,utc.data_type,ucc.comments,data_precision,data_scale " +
                    "from user_tab_columns utc,user_col_comments ucc " +
                    "where utc.table_name = ucc.table_name " +
                    "and utc.column_name = ucc.column_name " +
                    "and utc.table_name = '"+tableName+"'  " +
                    "and utc.column_name in(" +
                    "select column_name from user_cons_columns " +
                    "where constraint_name = (" +
                    "select constraint_name from user_constraints " +
                    "where table_name = '"+tableName+"'  and constraint_type ='P')) " +
                    "order by column_id";
            try {
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ColumnInfo columnInfo = new ColumnInfo();
                    columnInfo.setColumnName(rs.getString("COLUMN_NAME"));
                    columnInfo.setColumnType(rs.getString("DATA_TYPE"));
                    columnInfo.setColumnComment(rs.getString("COMMENTS"));
                    String scale = rs.getString("DATA_SCALE");
                    String precision = rs.getString("DATA_PRECISION");
                    if(precision!=null){
                        columnInfo.setPrecision(Integer.valueOf(precision));
                    }
                    if(scale!=null){
                        columnInfo.setScale(Integer.valueOf(scale));
                    }
                    pks.add(columnInfo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if (rs != null) {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("获取主键时关闭连接异常");
            }
        }
        return pks;
    }

    /**
     *
     * @param
     * @return
     */
    public static ColumnInfo getPKInfo(String pk,List<ColumnInfo> columnInfos) {
        for(ColumnInfo columnInfo:columnInfos){
            if(columnInfo.getColumnName().equals(pk)){
                return columnInfo;
            }
        }
        return null;
    }


    static {
        try {
            Class.forName(GConfig.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("加载数据库驱动异常");
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    private static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(GConfig.URL, GConfig.USERNAME, GConfig.PASSWORD);
        } catch (SQLException e) {
            logger.error("获取数据库连接失败",e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    private static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("关闭数据库失败");
            }
        }
    }
    /**
     * 生成带有占位符的SQL语句,
     * 用途举例:数据同步
     * @param data
     * @param tableName
     * @return
     */
    public static String getInsertSql(Map<String, Object> data, String tableName){
        Set<String> columns = data.keySet();
        String insertSql = getInsertSql(columns, tableName);
        return insertSql;
    }

    /**
     * 根据传入的集合生成带占位符的插入sql语句
     * 用途举例:excel导入,数据同步生成插入sql语句
     * @param columns
     * @param tableName
     * @return
     */
    public static String getInsertSql(Collection<String> columns, String tableName){
        StringBuilder sb = new StringBuilder("insert into "+tableName+" (");
        StringBuilder placeholder = new StringBuilder("values (");
        for (String column : columns) {
            if(column!=null&&column.trim().length()>0){
                sb.append(column.trim()+",");
                placeholder.append("?,");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(") ");
        placeholder.deleteCharAt(placeholder.length()-1);
        placeholder.append(")");
        sb.append(placeholder);
        return sb.toString();
    }
    /**
     * 将传入的数据插入到指定的表中
     * @param ds
     * @param tableName
     * @param data
     */
    public static int[] insert(DataSource ds, String tableName, List<Map<String, Object>> data){
        String insertSql = getInsertSql(data.get(0), tableName);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List<Object[]> params = new ArrayList<>();
        for (Map<String, Object> datum : data) {
            Set<String> keys = datum.keySet();
            List<Object> param = new ArrayList<>();
            for (String key : keys) {
                param.add(datum.get(key));
            }
            params.add(param.toArray());
        }
        int[] ints = jdbcTemplate.batchUpdate(insertSql, params);
        return ints;
    }
    /**
     * 数据同步
     * @param sourceDS
     * @param sourceTN
     * @param destDS
     * @param destTN
     */
    public static int[] dataSyn(DataSource sourceDS,String sourceTN,DataSource destDS,String destTN){
        List<Map<String, Object>> maps = find(sourceDS, sourceTN);
        //清空目标表
        emptyTable(destDS,destTN);
        //如果源表里没有数据,返回即可
        if(maps.size()<1){
            return null;
        }
        int[] insert = insert(destDS, destTN, maps);
        return insert;
    }

    /**
     * 根据传入的数据源和表名查询所有数据
     * @param ds
     * @param tableName
     * @return
     */
    public static List<Map<String, Object>> find(DataSource ds,String tableName){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        String findSql = "select * from "+tableName;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(findSql);
        return list;
    }

    /**
     * 清空表
     * @param ds
     * @param tableName
     * @return
     */
    public static void emptyTable(DataSource ds,String tableName){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        String emptySql = "TRUNCATE TABLE "+tableName;
        jdbcTemplate.execute(emptySql);
    }
    public static void sysnTable(DataSource sourceDS,String sourceTN,DataSource destDS){
        String dropTableSql = "drop table "+sourceTN;
        String createTableSql = getCreateTableSql(sourceDS, sourceTN);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(destDS);
//        try {
//            jdbcTemplate.execute(dropTableSql);
//        }catch (Exception e){
//
//        }
        jdbcTemplate.execute(createTableSql);
    }
    public static void sysnTable(DataSource sourceDS,List<String> tableNames,DataSource destDS){
        List<String> sqlList = new ArrayList<>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(destDS);
        for (String tableName : tableNames) {
            String createTableSql = getCreateTableSql(sourceDS, tableName);
            String dropTableSql = "DROP TABLE IF EXISTS `"+tableName+"`";
//            createTableSqls.add(dropTableSql);
//            sqlList.add(createTableSql);
            try {
                jdbcTemplate.execute(createTableSql);
            }catch (Exception e){
                System.err.println("生成"+tableName+"表时出错啦");
                e.printStackTrace();
            }
        }
//        String[] sqlArray = new String [sqlList.size()];
//        sqlArray = sqlList.toArray(sqlArray);
//        jdbcTemplate.batchUpdate(sqlArray);
    }
    public static String getCreateTableSql(DataSource ds,String tableName){
        String sql ="SELECT UTC.COLUMN_NAME,UTC.DATA_TYPE,UTC.DATA_LENGTH,UTC.DATA_PRECISION,UTC.DATA_SCALE,UTC.NULLABLE,UCC.COMMENTS " +
                "FROM USER_TAB_COLUMNS UTC, USER_COL_COMMENTS UCC " +
                "WHERE UTC.TABLE_NAME = UCC.TABLE_NAME AND UTC.COLUMN_NAME = UCC.COLUMN_NAME AND UTC.TABLE_NAME = '"+tableName+"' " +
                "ORDER BY COLUMN_ID";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        List<ColumnInfo> pKs = DBUtil.getPKs(tableName);
        StringBuilder sb = new StringBuilder("CREATE TABLE "+tableName+"(");
        for (Map<String, Object> map : maps) {
            String columnName = map.get("COLUMN_NAME").toString();
//            防止列名为关键字
            columnName = "`"+columnName+"`";
            String dataType = map.get("DATA_TYPE").toString();
            String dataLength = map.get("DATA_LENGTH").toString();
//			数字类型的总位数,可能为空
            String nullable = map.get("NULLABLE").toString();
            Object dataPrecision = map.get("DATA_PRECISION");
//			小数位数,可能为空
            Object dataScale = map.get("DATA_SCALE");
//			注释可能为空
            Object comments = map.get("COMMENTS");

            StringBuilder columnDefinition = new StringBuilder(columnName+" ");
            if(dataType.equals("DATE")||dataType.equals("BLOB")){
                columnDefinition.append(dataType+" ");
            }else{
//				不为日期不为数字就当字符串处理TODO
                if(dataPrecision!=null){
                    if(dataScale!=null && dataScale.toString().equals("0")){
                        columnDefinition.append("int("+dataLength+") ");
                    }else{
                        columnDefinition.append("double("+dataPrecision+","+dataScale+") ");
                    }
                }else if(dataType.equals("NUMBER")){
                    columnDefinition.append("int("+dataLength+") ");
                } else{
                    columnDefinition.append("VARCHAR("+dataLength+") ");
                }
            }
            if(nullable.equals("N")){
                columnDefinition.append("NOT NULL ");
            }
            if(comments !=null){
                columnDefinition.append("COMMENT '"+comments+"'");
            }
            columnDefinition.append(",");
            sb.append(columnDefinition);
        }
        if(pKs.size()>0){
            sb.append("PRIMARY KEY (");
            for (ColumnInfo pK : pKs) {
                sb.append(pK.getColumnName()+",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
        }else{
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();
    }


}
