package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.User;
import com.yj.utils.ResponseResult;

public interface UserService extends IService<User> {
    User getById(Long id);

    ResponseResult select(Long id);

    ResponseResult userInfo();

    ResponseResult register(User user);

    ResponseResult updateUserInfo(User user);

    ResponseResult queryUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);
}
