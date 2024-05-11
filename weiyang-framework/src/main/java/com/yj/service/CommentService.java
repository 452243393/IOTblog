package com.yj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yj.entity.Comment;
import com.yj.utils.ResponseResult;

public interface CommentService extends IService<Comment> {
    ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
