package com.lxw.mytool.constant;

/**
 * @autor lixiewen
 * @date 2019/1/31-15:58
 */
public interface JavaMapperModel {
    String mapperModel = "package com.example.demo.mapper;\n" +
            "\n"+
            "<importPackageNames>"+
            "\n"+
            "import com.example.demo.entity.Emp;\n" +
            "import com.example.demo.entity.EmpExample;\n" +
            "import java.util.List;\n" +
            "import org.apache.ibatis.annotations.Param;\n" +
            "\n" +
            "public interface EmpMapper {\n" +
            "<countByExample>" +
            "<selectByPrimaryKey>" +
            "<selectByExample>" +
            "<insertSelective>" +
            "<insert>" +
            "<updateByPrimaryKeySelective>" +
            "<updateByExampleSelective>" +
            "<updateByPrimaryKey>" +
            "<updateByExample>" +
            "<deleteByPrimaryKey>" +
            "<deleteByExample>" +
            "}";
}
