package com.grad.interaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grad.interaction.model.ActivityParticipant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 活动参与者Mapper接口
 */
@Mapper
public interface ActivityParticipantMapper extends BaseMapper<ActivityParticipant> {
}