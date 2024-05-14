package com.yj.Controller;


import com.yj.entity.User;
import com.yj.mapper.UserMapper;
import com.yj.service.UserService;
import com.yj.utils.ResponseResult;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("system/user")
public class UserController {
    @Autowired
    private UserService userService;

    //查询用户列表
    @GetMapping("/list")
    public ResponseResult queryUser(Integer pageNum, Integer pageSize,String userName,String phonenumber,String status){
        return userService.queryUser(pageNum,pageSize,userName,phonenumber,status);
    }
    //查询用户详细
    @GetMapping("/{id}")
    public ResponseResult select(@PathVariable("id") Long id){
        return userService.select(id);
    }

}
