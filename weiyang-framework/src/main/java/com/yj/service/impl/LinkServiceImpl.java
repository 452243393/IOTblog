package com.yj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Link;
import com.yj.entity.Role;
import com.yj.entity.vo.PageVo;
import com.yj.mapper.LinkMapper;
import com.yj.service.LinkService;
import com.yj.utils.BeanCopyUtils;
import com.yj.utils.ResponseResult;
import com.yj.utils.SystemConstants;
import com.yj.entity.vo.LinkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper,Link> implements LinkService {
    @Autowired
    private LinkMapper linkMapper;
    @Override
    public ResponseResult getAllLink() {
        //查询所有通过审核的
        List<Link> link = linkMapper.getAllLink(SystemConstants.LINK_STATUS_NORMAL);
        List<LinkVo> linkVo = BeanCopyUtils.copyBeanList(link,LinkVo.class);
        return ResponseResult.successResult(linkVo);
    }

    @Override
    public ResponseResult queryLink(Integer pageNum, Integer pageSize, String name, String description, String address, String status, String logo) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Link> Wrapper = new LambdaQueryWrapper<>();
        if (name != null && description != null && status != null) {
            queryWrapper.like(Link::getName, name);
            queryWrapper.like(Link::getDescription, description);
            queryWrapper.like(Link::getStatus, status);
            Page<Link> page = new Page();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            page(page, queryWrapper);
        }
        Page<Link> page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, Wrapper);
        int total = LinkMapper.countLink();
        PageVo pageVo = new PageVo(page.getRecords(), total);
        return ResponseResult.successResult(pageVo);
    }
}
