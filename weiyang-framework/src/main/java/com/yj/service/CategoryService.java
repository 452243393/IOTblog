package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Category;
import com.yj.entity.vo.CategoryVo;


import java.util.List;

public interface CategoryService extends IService<Category> {
    List<CategoryVo> getCategoryList();

    List<CategoryVo> listAllCategory();
}
