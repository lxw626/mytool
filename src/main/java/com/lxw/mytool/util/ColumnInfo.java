package com.lxw.mytool.util;


import com.lxw.mytool.config.GautoConfig;

public class ColumnInfo {
    //列名
    private String columnName;
    //列类型
    private String columnType;
    //列注释
    private String columnComment;
    //如果为数字类型时数字的总位数
    private Integer precision;
    //如果为数字类型时数字的小数位数
    private Integer scale;

    //字段名
    private String fieldName;
    //字段类型
    private String fieldType;
    //jdbcType类型
    private String jdbcType;


    public ColumnInfo() {
    }

    public ColumnInfo(String columnName, String fieldName, String fieldType, String jdbcType) {
        this.columnName = columnName;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.jdbcType = jdbcType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
        this.fieldType = GautoConfig.getFieldType(columnType,precision,scale);
        this.jdbcType = GautoConfig.getJdbcType(columnType);

    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getFieldName() {
        return GautoConfig.getFieldName(columnName);
    }

    public String getFieldType() {
        return fieldType;
    }

    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "columnName='" + columnName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", precision=" + precision +
                ", scale=" + scale +
                '}';
    }
}
