package com.yj.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Article;
import com.yj.entity.dto.AddArticleDto;
import com.yj.utils.ResponseResult;

import java.util.List;

public interface ArticleService extends IService<Article> {
    List<Article> listAll();

    List<Article> hotArticle();

    ResponseResult getArticleDetail(Long id);

    ResponseResult articleList(Integer pageNum, Integer pageSize, Integer categoryId);

    ResponseResult updateViewCount(Long id);

    void updateViewMap(List<Article> articleList);

    ResponseResult add(AddArticleDto article);

    ResponseResult listArticle(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult select(Long id);

    ResponseResult updateArticle(AddArticleDto articleDto);

    ResponseResult deleteArticle(Long id);
}
