package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.User;
import com.yj.utils.ResponseResult;

public interface LoginService extends IService<User> {
    ResponseResult login(User user);

    ResponseResult logOut();
}
