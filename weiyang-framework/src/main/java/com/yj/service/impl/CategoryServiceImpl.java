package com.yj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yj.entity.Category;
import com.yj.entity.Category;
import com.yj.entity.vo.CategoryVo;
import com.yj.entity.vo.PageVo;
import com.yj.exception.SystemException;
import com.yj.mapper.CategoryMapper;
import com.yj.mapper.CategoryMapper;
import com.yj.service.CategoryService;
import com.yj.utils.AppHttpCodeEnum;
import com.yj.utils.BeanCopyUtils;
import com.yj.entity.vo.CategoryVo;
import com.yj.utils.ResponseResult;
import com.yj.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ResponseResult addCategory(Category category) {
        if(!StringUtils.hasText(category.getName())){
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_NOT_NULL);
        }
        save(category);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getId, id);
        if(categoryMapper.delete(wrapper)>0)
        {
            return ResponseResult.successResult();
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public ResponseResult selectById(Long id) {
        Category category =  categoryMapper.selectById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.successResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getId,category.getId());
        categoryMapper.update(category,wrapper);
        return ResponseResult.successResult();
    }
}
