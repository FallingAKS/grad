package com.grad.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grad.forum.model.Reply;
import org.apache.ibatis.annotations.Mapper;

/**
 * 回复Mapper接口
 */
@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {
}