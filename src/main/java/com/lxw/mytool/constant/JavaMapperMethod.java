package com.lxw.mytool.constant;

/**
 * @autor lixiewen
 * @date 2019/2/22-14:00
 */
public interface JavaMapperMethod {
    String countByExample = "    long countByExample(<entityClassName>Example example);\n\n";
    String deleteByExample = "    int deleteByExample(<entityClassName>Example example);\n\n";
    String deleteByPrimaryKey = "    int deleteByPrimaryKey(<pkFieldType> <pkFieldName>);\n\n";
    String insert = "    int insert(<entityClassName> record);\n\n";
    String insertSelective = "    int insertSelective(<entityClassName> record);\n\n";
    String selectByExample = "    List<<entityClassName>> selectByExample(<entityClassName>Example example);\n\n";
    String selectByPrimaryKey = "    <entityClassName> selectByPrimaryKey(<pkFieldType> <pkFieldName>);\n\n";
    String updateByExampleSelective = "    int updateByExampleSelective(@Param(\"record\") <entityClassName> record, @Param(\"example\") <entityClassName>Example example);\n\n";
    String updateByExample = "    int updateByExample(@Param(\"record\") <entityClassName> record, @Param(\"example\") <entityClassName>Example example);\n\n";
    String updateByPrimaryKeySelective = "    int updateByPrimaryKeySelective(<entityClassName> record);\n\n";
    String updateByPrimaryKey = "    int updateByPrimaryKey(<entityClassName> record);\n\n";
}
