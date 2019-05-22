package com.lxw.mytool.constant;

/**
 * @autor lixiewen
 * @date 2019/2/22-15:40
 */
public interface XmlMethod {
    String find = "\t<select id=\"find\" parameterType=\"java.util.Map\" resultMap=\"BaseResultMap\">\n" +
            "\t\tselect\n" +
            "\t\t<include refid=\"Base_Column_List\" />\n" +
            "\t\tfrom <#tableName>\n" +
            "\t\t<where>\n" +
            "<#ifs>" +
            "\t\t</where>\n" +
            "\t\tORDER BY\n" +
            "\t\t<if test=\"order != null\">\n" +
            "\t\t\t\\$\\{order\\}\n" +
            "\t\t</if>\n" +
            "\t\t<if test=\"order == null\">\n" +
            "\t\t\tSID DESC\n" +
            "\t\t</if>\n" +
            "\t</select>\n";
    String selectByPrimaryKey = "\t<select id=\"selectByPrimaryKey\" parameterType=\"<#pkFieldClassFullName>\" resultMap=\"BaseResultMap\">\n" +
            "\t\tselect \n" +
            "\t\t<include refid=\"Base_Column_List\" />\n" +
            "\t\tfrom <#tableName>\n" +
            "\t\twhere <#pkColumnName> = #{<#pkFieldName>,jdbcType=<#pkJdbcType>}\n" +
            "\t</select>\n";
    String countByExample = "    <select id=\"countByExample\" parameterType=\"<#entityClassFullName>Example\" resultType=\"java.lang.Long\">\n" +
            "        select count(*) from <#tableName>\n" +
            "        <if test=\"_parameter != null\">\n" +
            "            <include refid=\"Example_Where_Clause\" />\n" +
            "        </if>\n" +
            "    </select>\n";
    String insert = "\t<insert id=\"insert\" parameterType=\"<#entityClassFullName>\">\n" +
            "\t\t<selectKey resultType=\"java.lang.Long\" keyProperty=\"sid\" order=\"BEFORE\" >\n" +
            "\t\t\tSELECT SEQ_<#tableName>.NEXTVAL FROM DUAL\n" +
            "\t\t</selectKey>\n"+
            "\t\tinsert into <#tableName> (\n" +
            "<#baseColumnList>"+
            "\t\t)values (\n" +
            "<#insertValues>"+
            "\t\t)\n" +
            "\t</insert>\n";
    String updateByPrimaryKey = "    <update id=\"update\" parameterType=\"<#entityClassFullName>\">\n" +
            "        update <#tableName> set\n" +
            "<#updateByEntitySets>"+
            "        where SID = #{sid,jdbcType=DECIMAL}\n" +
            "    </update>\n";
    String updateByPrimaryKeySelective = "    <update id=\"update\" parameterType=\"<#entityClassFullName>\">\n" +
            "        update <#tableName>\n" +
            "        <set>\n" +
            "<#updateByEntitySets>"+
            "        </set>\n" +
            "        where <#primaryKeyColumn> = #{<#primaryKeyField>,jdbcType=<#primaryKeyJdbctype>}\n" +
            "    </update>\n";
    String insertSelective = "\t<insert id=\"insertSelective\" parameterType=\"<#entityClassFullName>\">\n" +
            "\t\tinsert into <#tableName>\n" +
            "\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "<#insertSelectiveColumns>"+
            "\t\t</trim>\n" +
            "\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n" +
            "<#insertSelectiveValues>"+
            "\t\t</trim>\n" +
            "\t</insert>\n";
    String updateByExample = "    <update id=\"updateByExample\" parameterType=\"map\">\n" +
            "        update <#tableName> set\n" +
            "<#updateByExampleSets>"+
            "        <if test=\"_parameter != null\">\n" +
            "            <include refid=\"Update_By_Example_Where_Clause\" />\n" +
            "        </if>\n" +
            "    </update>\n";
    String updateByExampleSelective = "\t<update id=\"updateByExampleSelective\" parameterType=\"map\">\n" +
            "\t\tupdate <#tableName>\n" +
            "\t\t<set>\n" +
            "<#updateByExampleSelectiveSets>"+
            "\t\t</set>\n" +
            "\t\t<if test=\"_parameter != null\">\n" +
            "\t\t\t<include refid=\"Update_By_Example_Where_Clause\" />\n" +
            "\t\t</if>\n" +
            "\t</update>\n";
    String deleteByPrimaryKey = "\t<delete id=\"delete\" parameterType=\"java.lang.Long\">\n" +
            "\t\tdelete from <#tableName>\n" +
            "\t\twhere SID = #{sid,jdbcType=DECIMAL}\n" +
            "\t</delete>\n";
    String deleteByExample = "\t<delete id=\"deleteByExample\" parameterType=\"<#entityClassFullName>Example\">\n" +
            "\t\tdelete from <#tableName>\n" +
            "\t\t<if test=\"_parameter != null\">\n" +
            "\t\t\t<include refid=\"Example_Where_Clause\" />\n" +
            "\t\t</if>\n" +
            "\t</delete>\n";
}
