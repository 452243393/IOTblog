package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Link;
import com.yj.entity.Link;
import com.yj.entity.vo.LinkVo;
import com.yj.utils.ResponseResult;

import java.util.List;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLink();

    ResponseResult queryLink(Integer pageNum, Integer pageSize, String name, String description, String address, String status, String logo);

    ResponseResult addLink(Link link);

    ResponseResult deleteLink(Long id);

    ResponseResult selectById(Long id);

    ResponseResult updateLink(Link link);

    List<LinkVo> listAllLink();
}
