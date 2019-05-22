package com.lxw.mytool.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @autor lixiewen
 * @date 2019/4/20-22:19
 */
@Mapper
@Repository
public interface HelloDao {
    @Select("select * from person where sid < #{sid}")
    List<Map<String, Object>> helloMyBatis(Integer sid);
}
