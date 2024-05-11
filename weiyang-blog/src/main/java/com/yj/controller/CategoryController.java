package com.yj.controller;

import com.yj.service.CategoryService;
import com.yj.utils.ResponseResult;
import com.yj.entity.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("getCategoryList")
    public ResponseResult getCategoryList(){
        List<CategoryVo> categoryVos = categoryService.getCategoryList();
        return ResponseResult.successResult(categoryVos);
    }

}
