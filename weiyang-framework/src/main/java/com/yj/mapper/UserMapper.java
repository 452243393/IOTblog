package com.yj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
//@Repository(value = "blogLoginMapper")
public interface UserMapper extends BaseMapper<User> {
    User getUser(@Param("username") String username);

    User getById(@Param("id") Long id);

    int getByUserName(@Param("username") String userName);

    int getByNickName(@Param("nickname") String nickName);

    int insertUser(User user);

    int updateUserInfo(User user);
}
