package com.grad.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grad.forum.model.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * 帖子Mapper接口
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
}