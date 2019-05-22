package com.lxw.mytool.constant;

/**
 * @autor lixiewen
 * @date 2019/2/22-16:18
 */
public interface XmlModel {
    String if1 = "\t\t\t<if test = \"<#fieldName> != null\">\n"+
            "\t\t\t\tand <#columnName> = #{<#fieldName>,jdbcType=<#jdbcType>}\n"+
            "\t\t\t</if>\n";
    String if2 = "\t\t\t<if test = \"<#fieldName> != null and <#fieldName>!=''\">\n"+
            "\t\t\t\tand <#columnName> = #{<#fieldName>,jdbcType=<#jdbcType>}\n"+
            "\t\t\t</if>\n";
    String insertValue = "\t#{<#fieldName>,jdbcType=<#jdbcType>}";
    String insertSelectiveColumn = "\t\t\t<if test=\"<#fieldName> != null\">\n" +
            "\t\t\t\t<#columnName>,\n" +
            "\t\t\t</if>\n";
    String insertSelectiveValue = "\t\t\t<if test=\"<#fieldName> != null\">\n" +
            "\t\t\t\t#{<#fieldName>,jdbcType=<#jdbcType>},\n" +
            "\t\t\t</if>\n";
    String updateByEntitySetMember = "\t\t<#columnName> = #{<#fieldName>,jdbcType=<#jdbcType>}";
    String updateByExampleSetMember = "\t\t<#columnName> = #{record.<#fieldName>,jdbcType=<#jdbcType>}";
    String updateByEntitySelectiveSetMember = "\t\t\t<if test=\"<#fieldName> != null\">\n" +
            "\t\t\t\t<#columnName> = #{<#fieldName>,jdbcType=<#jdbcType>},\n" +
            "\t\t\t</if>\n";
    String updateByExampleSelectiveSetMember = "\t\t\t<if test=\"record.<#fieldName> != null\">\n" +
            "\t\t\t\t<#columnName> = #{record.<#fieldName>,jdbcType=<#jdbcType>},\n" +
            "\t\t\t</if>\n";
    String xmlModel = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
            "<mapper namespace=\"<#mapperClassFullName>\">\n" +
            "    <resultMap id=\"BaseResultMap\" type=\"<#entityClassFullName>\">\n" +
            "<#results>"+
            "    </resultMap>\n" +
            "    <sql id=\"Base_Column_List\">\n" +
            "<#baseColumnList>"+
            "    </sql>\n" +
            "<#find>"+
            "<#insert>"+
            "<#updateByPrimaryKeySelective>"+
            "<#deleteByPrimaryKey>"+
            "</mapper>";
    //
    String andFieldIsNullModel = "        public Criteria and<fieldNameToUpperCaseFirstOne>IsNull() {\n" +
            "            addCriterion(\"<columnName> is null\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldIsNotNullModel = "        public Criteria and<fieldNameToUpperCaseFirstOne>IsNotNull() {\n" +
            "            addCriterion(\"<columnName> is not null\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldEqualToModel = "        public Criteria and<fieldNameToUpperCaseFirstOne>EqualTo(<fieldType> value) {\n"+
            "            addCriterion(\"<columnName> =\", value, \"<fieldName>\");\n"+
            "            return (Criteria) this;\n"+
            "        }\n"+
            "\n";
    String andFieldNotEqualToModel ="        public Criteria and<fieldNameToUpperCaseFirstOne>NotEqualTo(<fieldType> value) {\n" +
            "            addCriterion(\"<columnName> <>\", value, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldGreaterThan = "        public Criteria and<fieldNameToUpperCaseFirstOne>GreaterThan(<fieldType> value) {\n" +
            "            addCriterion(\"<columnName> >\", value, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldGreaterThanOrEqualTo = "        public Criteria and<fieldNameToUpperCaseFirstOne>GreaterThanOrEqualTo(<fieldType> value) {\n" +
            "            addCriterion(\"<columnName> >=\", value, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldLessThanModel = "        public Criteria and<fieldNameToUpperCaseFirstOne>LessThan(<fieldType> value) {\n" +
            "            addCriterion(\"<columnName> <\", value, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldLessThanModelOrEqualTo = "        public Criteria and<fieldNameToUpperCaseFirstOne>LessThanOrEqualTo(<fieldType> value) {\n" +
            "            addCriterion(\"<columnName> <=\", value, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldIn = "        public Criteria and<fieldNameToUpperCaseFirstOne>In(List<<fieldType>> values) {\n" +
            "            addCriterion(\"<columnName> in\", values, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldNotIn = "        public Criteria and<fieldNameToUpperCaseFirstOne>NotIn(List<<fieldType>> values) {\n" +
            "            addCriterion(\"<columnName> not in\", values, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldBetween = "        public Criteria and<fieldNameToUpperCaseFirstOne>Between(<fieldType> value1, <fieldType> value2) {\n" +
            "            addCriterion(\"<columnName> between\", value1, value2, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldNotBetween = "        public Criteria and<fieldNameToUpperCaseFirstOne>NotBetween(<fieldType> value1, <fieldType> value2) {\n" +
            "            addCriterion(\"<columnName> not between\", value1, value2, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldLike = "        public Criteria and<fieldNameToUpperCaseFirstOne>Like(<fieldType> value) {\n" +
            "            addCriterion(\"<columnName> like\", value, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    String andFieldNotLike = "        public Criteria and<fieldNameToUpperCaseFirstOne>NotLike(<fieldType> value) {\n" +
            "            addCriterion(\"<columnName> not like\", value, \"<fieldName>\");\n" +
            "            return (Criteria) this;\n" +
            "        }\n"+
            "\n";
    //xml
    String resultModel = "\t\t<result column=\"<#columnName>\" jdbcType=\"<#jdbcType>\" property=\"<#property>\" />\n";
    String insertSelective = "      <if test=\"<#fieldName> != null\">\n" +
            "        <#columnName>,\n" +
            "      </if>\n";
}
