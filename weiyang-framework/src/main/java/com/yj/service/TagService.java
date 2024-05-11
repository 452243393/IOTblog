package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Tag;
import com.yj.entity.dto.TagListDto;
import com.yj.entity.vo.TagVo;
import com.yj.utils.ResponseResult;
import com.yj.entity.vo.PageVo;

import java.util.List;

public interface TagService extends IService<Tag> {
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult selectById(Long id);

    ResponseResult updateTag(Tag tag);

    List<TagVo> listAllTag();
}
