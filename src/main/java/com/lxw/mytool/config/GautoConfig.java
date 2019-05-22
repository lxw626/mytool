package com.lxw.mytool.config;


import com.lxw.mytool.constant.DBColumnType;
import com.lxw.mytool.util.ColumnInfo;
import com.lxw.mytool.util.DBUtil;
import com.lxw.mytool.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor lixiewen
 * @date 2019/1/12-12:15
 */
public class GautoConfig {
    String tableName;
    //entity短名
    String entityClassName;
    //entity包名
    String entityPackageName = GConfig.entityPackageName;
    //mapper接口名
    String mapperClassName;
    //mapper包名
    String mapperPackageName = GConfig.mapperPackageName;
    //全局主键信息
    List<ColumnInfo> pkColumnInfo = new ArrayList<>();
    //  格式,每行生成的列数
    Integer columnCount = GConfig.columnCount;
    //序列
    String sequenceName;
//    列信息
    List<ColumnInfo> columnInfos;
//    成员变量信息
    List<ColumnInfo> fieldInfos = new ArrayList<>();
    //实体类生成路径
    String entityGeneratePath = GConfig.entityGeneratePath;
    //Mapper类生成路径
    String mapperGeneratePath = GConfig.mapperGeneratePath;
    //xml生成路径
    String xmlGeneratePath = GConfig.xmlGeneratePath;


    public GautoConfig(String tableName) throws IllegalAccessException {
        this.tableName = tableName;
        this.entityClassName = getClassName(tableName);
        this.mapperClassName = entityClassName+"Mapper";
        this.columnInfos = DBUtil.getColumnInfos(tableName);
        this.sequenceName = "SEQ_"+tableName;
        Class<GConfig> c = GConfig.class;
        Field[] fields = c.getDeclaredFields();
        for(Field field:fields){
            if(field.getName().equals("pkColumn")){
                Object o = field.get(null);
                pkColumnInfo.add((ColumnInfo)o);
            }
        }
        if(pkColumnInfo==null||pkColumnInfo.size()==0){
            List<ColumnInfo> pKs = DBUtil.getPKs(tableName);
            this.pkColumnInfo = pKs;
        }
    }

    public GautoConfig(String tableName, String entityClassName) throws IllegalAccessException {
        this.tableName = tableName;
        this.entityClassName = entityClassName;
        this.mapperClassName = entityClassName+"Mapper";
        this.columnInfos = DBUtil.getColumnInfos(tableName);
        for(ColumnInfo columnInfo:columnInfos){
            String fieldName = columnInfo.getFieldName();
            if(!GConfig.getMissField().contains(fieldName)){
                fieldInfos.add(columnInfo);
            }
        }
        this.sequenceName = "SEQ_"+tableName;
        Class<GConfig> c = GConfig.class;
        Field[] fields = c.getDeclaredFields();
        for(Field field:fields){
            if(field.getName().equals("pkColumn")){
                Object o = field.get(null);
                pkColumnInfo.add((ColumnInfo)o);
            }
        }
        if(pkColumnInfo==null||pkColumnInfo.size()==0){
            List<ColumnInfo> pKs = DBUtil.getPKs(tableName);
            this.pkColumnInfo = pKs;
        }
    }

    /**
     * entity类全名
     * @return entity类全名
     */
    public String getEntityClassFullName(){
        return entityPackageName+"."+entityClassName;
    }

    /**
     * mapper类全名
     * @return
     */
    public String getMapperClassFullName(){
        return mapperPackageName+"."+ mapperClassName;
    }


    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public String getEntityPackageName() {
        return entityPackageName;
    }

    public void setEntityPackageName(String entityPackageName) {
        this.entityPackageName = entityPackageName;
    }

    public String getMapperClassName() {
        return mapperClassName;
    }

    public void setMapperClassName(String mapperClassName) {
        this.mapperClassName = mapperClassName;
    }

    public String getMapperPackageName() {
        return mapperPackageName;
    }

    public void setMapperPackageName(String mapperPackageName) {
        this.mapperPackageName = mapperPackageName;
    }

    public List<ColumnInfo> getPkColumnInfo() {
        return pkColumnInfo;
    }

    public void setPkColumnInfo(List<ColumnInfo> pkColumnInfo) {
        this.pkColumnInfo = pkColumnInfo;
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }

    public String getEntityGeneratePath() {
        return entityGeneratePath;
    }

    public void setEntityGeneratePath(String entityGeneratePath) {
        this.entityGeneratePath = entityGeneratePath;
    }

    public String getMapperGeneratePath() {
        return mapperGeneratePath;
    }

    public void setMapperGeneratePath(String mapperGeneratePath) {
        this.mapperGeneratePath = mapperGeneratePath;
    }

    public String getXmlGeneratePath() {
        return xmlGeneratePath;
    }

    public void setXmlGeneratePath(String xmlGeneratePath) {
        this.xmlGeneratePath = xmlGeneratePath;
    }

    public List<ColumnInfo> getFieldInfos() {
        return fieldInfos;
    }

    /**
     * 通过表名生成实体类名
     * @param tableName
     * @return
     */
    public static String getClassName(String tableName){
        String className=null;
        className = StringUtil.toUpperCaseFirstOne(StringUtil._x2X(tableName.toLowerCase()));
        return className;
    }
    public static String getFieldName(String columnName){
        String fieldName=null;
        fieldName = StringUtil._x2X(columnName.toLowerCase()
        );
        return fieldName;
    }
    public static String getFieldType(String columnType,Integer precision,Integer scale){
        String fieldType=null;
        if(columnType.startsWith(DBColumnType.CHAR)){
            fieldType = "String";
        }else if(columnType.startsWith(DBColumnType.VARCHAR)){
            fieldType = "String";
        }else if(columnType.startsWith(DBColumnType.VARCHAR2)){
            fieldType = "String";
        }else if(columnType.startsWith(DBColumnType.NVARCHAR2)){
            fieldType = "String";
        }else if(columnType.startsWith(DBColumnType.DATE)){
            fieldType = "Date";
        }else if(columnType.startsWith(DBColumnType.TIMESTAMP)){
            fieldType = "Date";
        }else if(columnType.startsWith(DBColumnType.NUMBER)){
            if(precision!=null&&scale==0){
                fieldType = "Integer";
            }else{
                fieldType = "BigDecimal";
            }
        }
        return fieldType;
    }
    public static String getJdbcType(String columnType){
        String jdbcType=columnType;
        if (columnType.startsWith("VARCHAR2")|| columnType.startsWith("VARCHAR")) {
            jdbcType = "VARCHAR";
        } else if (columnType.startsWith("NUMBER")) {
            jdbcType = "DECIMAL";
        }else if (columnType.startsWith("TIMESTAMP")) {
            jdbcType = "TIMESTAMP";
        }
        return jdbcType;
    }
    public static String getGetMethodName(String columnName){
        String getMethodsName=null;
        getMethodsName = "get"+ StringUtil.toUpperCaseFirstOne(columnName.toLowerCase());
        return getMethodsName;
    }
    public static String getSetMethodName(String columnName){
        String setMethodsName=null;
        setMethodsName = "set"+StringUtil.toUpperCaseFirstOne(columnName.toLowerCase());
        return setMethodsName;
    }

}
