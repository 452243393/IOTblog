package com.yj.service.impl;

import com.yj.entity.LoginUser;
import com.yj.entity.User;
import com.yj.mapper.MenuMapper;
import com.yj.mapper.UserMapper;
import com.yj.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户的信息
        User user = userMapper.getUser(username);
        //判断是否查到用户 如果没查到 抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //查到用户，作为方法的返回值，返回用户信息
        if(user.getType().equals(SystemConstants.ADMIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        return new LoginUser(user,null);
    }
}
