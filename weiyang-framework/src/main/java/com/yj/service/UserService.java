package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.User;
import com.yj.utils.ResponseResult;

public interface UserService extends IService<User> {
    User getById(Long createBy);

    ResponseResult userInfo();

    ResponseResult register(User user);

    ResponseResult updateUserInfo(User user);
}
