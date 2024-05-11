package com.yj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.entity.ArticleTag;
import com.yj.entity.Tag;
import com.yj.entity.dto.TagListDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    int count(int articleStatusNormal);


}
