package com.yj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Link;
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
}
