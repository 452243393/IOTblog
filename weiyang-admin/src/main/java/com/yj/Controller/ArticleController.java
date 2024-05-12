package com.yj.Controller;

import com.yj.entity.dto.AddArticleDto;
import com.yj.service.ArticleService;
import com.yj.utils.ResponseResult;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @PostMapping
//    public ResponseResult add(@RequestBody AddArticleDto article){
//        return articleService.add(article);
//    }

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize,String title,String summary){
        return articleService.listArticle(pageNum,pageSize,title,summary);
    }
    @GetMapping("/{id}")
    public ResponseResult select(@PathVariable("id") Long id){
        return articleService.select(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto article){
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }
}
