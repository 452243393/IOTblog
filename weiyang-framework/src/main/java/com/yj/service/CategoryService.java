package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Category;
import com.yj.entity.Category;
import com.yj.entity.vo.CategoryVo;
import com.yj.entity.vo.CategoryVo;
import com.yj.utils.ResponseResult;


import java.util.List;

public interface CategoryService extends IService<Category> {
    List<CategoryVo> getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult queryCategory(Integer pageNum, Integer pageSize, String name, String description, String metaKeywords, String metaDescription, String status);

    ResponseResult addCategory(Category category);

    ResponseResult deleteCategory(Long id);

    ResponseResult selectById(Long id);

    ResponseResult updateCategory(Category category);

}
