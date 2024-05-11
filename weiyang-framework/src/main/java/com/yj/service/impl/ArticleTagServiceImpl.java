package com.yj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.ArticleTag;
import com.yj.mapper.ArticleTagMapper;
import com.yj.service.ArticleTagService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Override
    public List<Long> getTagId(Long id) {
        return articleTagMapper.selectByArticleId(id);
    }
}
