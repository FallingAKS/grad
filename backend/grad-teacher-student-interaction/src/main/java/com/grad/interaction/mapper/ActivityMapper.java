package com.grad.interaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grad.interaction.model.Activity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 活动Mapper接口
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
}