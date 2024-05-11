package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.User;
import com.yj.utils.ResponseResult;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logOut();
}
