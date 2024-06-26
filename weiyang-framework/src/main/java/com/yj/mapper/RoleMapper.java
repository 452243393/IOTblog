package com.yj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<String> selectRoleKeyByUserId(@Param("userId") Long userId);

    @Select("Select count(*) from sys_role")
    int countRole();
}
