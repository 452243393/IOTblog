package com.yj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> geArticleId(@Param("offset") int offset, @Param("pageSize") Integer pageSize, @Param("articleId") Long articleId, @Param("rootId") int rootId);

    int getTotal(@Param("articleId") Long articleId,@Param("rootId") int rootId);

    List<Comment> getRootId(@Param("id") Long id);

    int addComment(Comment comment);
}
