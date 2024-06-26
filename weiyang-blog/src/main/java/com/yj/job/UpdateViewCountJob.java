package com.yj.job;

import com.yj.entity.Article;
import com.yj.mapper.ArticleMapper;
import com.yj.service.ArticleService;
import com.yj.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
    @Scheduled(cron = "0/15 * * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String,Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articleList = viewCountMap.entrySet()
                .stream()
                        .map(entry -> new Article(Long.valueOf(entry.getKey()),entry.getValue().longValue()))
                                .collect(Collectors.toList());
        //存储到数据库中
        articleService.updateBatchById(articleList);
    }
}
