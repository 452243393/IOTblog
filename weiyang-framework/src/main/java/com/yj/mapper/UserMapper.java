package com.yj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
//@Repository(value = "blogLoginMapper")
public interface UserMapper extends BaseMapper<User> {
    User getUser(@Param("username") String username);

    User getById(@Param("id") Long id);

    int getByUserName(@Param("username") String userName);

    int getByNickName(@Param("nickname") String nickName);

    int insertUser(User user);

    int updateUserInfo(User user);

    //查询用户编号、用户名称、用户昵称、手机号码、状态、创建时间
    @Select("Select count(*) from sys_user")
    int countUser();
}
