package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.ArticleTag;

import java.util.List;

public interface ArticleTagService extends IService<ArticleTag> {
    List<Long> getTagId(Long id);
}
