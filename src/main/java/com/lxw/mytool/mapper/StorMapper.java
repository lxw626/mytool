package com.lxw.mytool.mapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lxw.mytool.entity.Stor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p>Title: pes-befiron-app</p>
 * <p>Description: MAPPER接口</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: sgai</p>
 * @author lixiewen
 * @version 2019年3月7日
 */
@Repository
@Mapper
public interface StorMapper {
    List<Stor> find(Map<String,Object> params);
//    Page<Stor> find(Map<String,Object> params);
    List<Stor> find(Map<String,Object> params,RowBounds rowBounds);
    List<Stor> find(Map<String,Object> params,PageHelper pageHelper);
}
