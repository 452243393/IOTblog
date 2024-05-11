package com.yj.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Article;
import com.yj.entity.ArticleTag;
import com.yj.entity.Category;
import com.yj.entity.dto.AddArticleDto;
import com.yj.mapper.ArticleMapper;
import com.yj.mapper.CategoryMapper;
import com.yj.service.ArticleService;
import com.yj.service.ArticleTagService;
import com.yj.service.TagService;
import com.yj.utils.BeanCopyUtils;
import com.yj.utils.PageUtils;
import com.yj.utils.RedisCache;
import com.yj.utils.ResponseResult;
import com.yj.entity.vo.ArticleDetailVo;
import com.yj.entity.vo.ArticleListVo;
import com.yj.entity.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;
    @Override
    public List<Article> listAll() {
        return articleMapper.list();
    }

    @Override
    public List<Article> hotArticle() {
        return articleMapper.hotArticle();
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
//        //根据id查询文章
//        Article article = articleMapper.getById(id);
//        //从redis中获取viewCount
//        Integer viewCount = redisCache.getCacheMapValue("article:viewCount",id.toString());
//        article.setViewCount(viewCount.longValue());
//        //转换成VO
//        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article,ArticleDetailVo.class);
//        System.out.println(articleDetailVo);
//        //获取vo中的categoryId
//        Long categoryId = articleDetailVo.getCategoryId();
//        Category category = categoryMapper.getById(categoryId);
//        System.out.println(category);
//        if(category != null){
//            articleDetailVo.setCategoryName(category.getName());
//        }
//        System.out.println(articleDetailVo);
//        return ResponseResult.successResult(articleDetailVo);
        //根据id查询文章
        System.out.println(id);
        Article article = articleMapper.getById(id);
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryMapper.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.successResult(articleDetailVo);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Integer categoryId) {
        List<Article> articleList = null;
        //判断是否存在categoryId，如果有就要查询在查询时和传入的相同, 且状态为正常发布的, 对isTop进行降序,并分页查询
        int offset = PageUtils.calcOffset(pageNum,pageSize);
        if(categoryId!=null&&categoryId>0){
            System.out.println(pageNum+"---"+pageSize+"---"+categoryId);
            articleList = articleMapper.getArticle(offset,pageSize,categoryId);
            }else{
            articleList = articleMapper.getAllArticle(offset,pageSize);
        }
        //查询记录数
        int total = articleMapper.count(categoryId);
        //根据article表中的category_id查询category表中的name
        for (Article article : articleList) {
            String categoryName = categoryMapper.getCategoryName(article.getCategoryId());
            article.setCategoryName(categoryName);
        }
        //封装
        List<ArticleListVo> articleListVoList = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVoList, total);
        return ResponseResult.successResult(pageVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //跟新数据库中 redis对应的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.successResult();
    }

    @Override
    public void updateViewMap(List<Article> articleList) {
        articleMapper.updateViewMap();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加博客
        Article article1 = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article1);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article1.getId(),tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult listArticle(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Article> Wrapper = new LambdaQueryWrapper<>();
        if(title!=null&&summary!=null) {
            queryWrapper.like(Article::getTitle, title);
            queryWrapper.like(Article::getSummary, summary);
            Page<Article> page = new Page();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            page(page,queryWrapper);
        }
        Page<Article> page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,Wrapper);
        int total = articleMapper.countArticle();
        PageVo pageVo = new PageVo(page.getRecords(),total);
        return ResponseResult.successResult(pageVo);
    }

    @Override
    public ResponseResult select(Long id) {
        Article article = baseMapper.selectById(id);
        article.setTags(articleTagService.getTagId(id));//标签id查询标签
        AddArticleDto articleDto = BeanCopyUtils.copyBean(article,AddArticleDto.class);
        return ResponseResult.successResult(articleDto);
    }

    @Override
    public ResponseResult updateArticle(AddArticleDto articleDto) {
        int count = articleMapper.deleteArticleById(articleDto.getId());
        System.out.println(count);
        Article article1 = BeanCopyUtils.copyBean(articleDto, Article.class);
        article1.setId(article1.getId()+1);
        save(article1);

        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article1.getId(),tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        articleMapper.deleteById(id);
        return ResponseResult.successResult();
    }

}
