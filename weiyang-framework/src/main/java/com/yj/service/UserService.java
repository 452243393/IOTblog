package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Tag;
import com.yj.entity.User;
import com.yj.entity.vo.LinkVo;
import com.yj.entity.vo.UserVo;
import com.yj.utils.ResponseResult;

import java.util.List;

public interface UserService extends IService<User> {
    User getById(Long id);

    ResponseResult select(Long id);

    ResponseResult userInfo();

    ResponseResult register(User user);

    ResponseResult updateUserInfo(User user);

    ResponseResult queryUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUser(User user);

    ResponseResult deleteUser(Long id);

    ResponseResult selectById(Long id);

    ResponseResult updateUser(User user);

    List<UserVo> listAllUser();
}
