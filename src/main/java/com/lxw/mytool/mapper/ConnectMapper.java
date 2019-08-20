package com.lxw.mytool.mapper;

import com.lxw.mytool.entity.Connect;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ConnectMapper {
    int deleteByPrimaryKey(Integer sid);

    int insert(Connect record);

    Connect selectByPrimaryKey(Integer sid);

    List<Connect> selectAll(Map<String,String> param);

    int updateByPrimaryKey(Connect record);
}