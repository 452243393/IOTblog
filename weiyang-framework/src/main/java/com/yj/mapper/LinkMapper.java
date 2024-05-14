package com.yj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.entity.Link;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LinkMapper extends BaseMapper<Link> {
    List<Link> getAllLink(@Param("linkStatusNormal") String linkStatusNormal);

    @Select("Select count(*) from sys_link")
    static int countLink() {
        return 0;
    }
}
