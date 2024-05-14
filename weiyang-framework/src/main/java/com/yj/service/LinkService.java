package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Link;
import com.yj.utils.ResponseResult;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();

    ResponseResult queryLink(Integer pageNum, Integer pageSize, String name, String description, String address, String status, String logo);
}
