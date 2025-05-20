package com.grad.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grad.forum.model.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 板块Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}