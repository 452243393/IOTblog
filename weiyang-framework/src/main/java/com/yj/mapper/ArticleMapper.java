package com.yj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Select("select * from sg_article")
    List<Article> list();
    @Select("select id,title,view_count from sg_article where `status` = '0' order by view_count desc limit 3")
    List<Article> hotArticle();

    List<Article> getArticle(@Param("offset") Integer offset,@Param("pageSize") Integer pageSize,@Param("categoryId") Integer categoryId);

    int count(@Param("categoryId") Integer categoryId);

    List<Article> getAllArticle(@Param("offset") int offset,@Param("pageSize") Integer pageSize);

    Article getById(@Param("id") Long id);

    List<Article> selectList();

    void updateViewMap();

    int countArticle();

    int deleteArticleById(@Param("id") Long id);
}
