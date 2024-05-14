package com.yj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Category;
import com.yj.entity.Link;
import com.yj.entity.vo.PageVo;
import com.yj.mapper.CategoryMapper;
import com.yj.mapper.LinkMapper;
import com.yj.service.CategoryService;
import com.yj.utils.BeanCopyUtils;
import com.yj.entity.vo.CategoryVo;
import com.yj.utils.ResponseResult;
import com.yj.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVo> getCategoryList() {
        Set<Long> categoryIds = categoryMapper.getCategoryIds();
        List<Category> categoryList = categoryMapper.getCategoryList(categoryIds);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList,CategoryVo.class);
        return categoryVos;
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult queryCategory(Integer pageNum, Integer pageSize, String name, String description, String metaKeywords, String metaDescription, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Category> Wrapper = new LambdaQueryWrapper<>();
        if (name != null && description != null && status != null) {
            queryWrapper.like(Category::getName, name);
            queryWrapper.like(Category::getDescription, description);
            queryWrapper.like(Category::getStatus, status);
            Page<Category> page = new Page();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            page(page, queryWrapper);
        }
        Page<Category> page = new Page();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, Wrapper);
        int total = CategoryMapper.countCategory();
        PageVo pageVo = new PageVo(page.getRecords(), total);
        return ResponseResult.successResult(pageVo);
    }
}
