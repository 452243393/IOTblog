package com.yj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.entity.Category;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Set;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    Set<Long> getCategoryIds();

    List<Category> getCategoryList(@Param("categoryIds") Set<Long> categoryIds);

    String getCategoryName(@Param("categoryId") Long categoryId);

    Category getById(@Param("categoryId")Long categoryId);
}
