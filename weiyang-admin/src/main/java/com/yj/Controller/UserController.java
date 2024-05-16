package com.yj.Controller;


import com.yj.entity.Tag;
import com.yj.entity.User;
import com.yj.mapper.UserMapper;
import com.yj.service.UserService;
import com.yj.utils.ResponseResult;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseResult addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseResult selectById(@PathVariable("id") Long id){
        return userService.selectById(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

}
