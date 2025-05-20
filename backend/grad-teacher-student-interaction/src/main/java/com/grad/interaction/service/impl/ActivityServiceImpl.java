package com.grad.interaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.interaction.mapper.ActivityMapper;
import com.grad.interaction.mapper.ActivityParticipantMapper;
import com.grad.interaction.model.Activity;
import com.grad.interaction.model.ActivityParticipant;
import com.grad.interaction.service.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动服务实现
 */
@Service
public class ActivityServiceImpl implements ActivityService {

  private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

  private final ActivityMapper activityMapper;
  private final ActivityParticipantMapper participantMapper;

  public ActivityServiceImpl(ActivityMapper activityMapper, ActivityParticipantMapper participantMapper) {
    this.activityMapper = activityMapper;
    this.participantMapper = participantMapper;
  }

  @Override
  @Transactional
  public Long createActivity(Activity activity) {
    // 设置初始值
    activity.setCreateTime(LocalDateTime.now());
    activity.setUpdateTime(LocalDateTime.now());
    activity.setCurrentParticipants(0);

    if (activity.getStatus() == null) {
      activity.setStatus(0); // 默认为筹备中
    }

    // 插入数据库
    activityMapper.insert(activity);
    logger.info("创建活动成功: {}", activity.getTitle());
    return activity.getId();
  }

  @Override
  @Transactional
  public boolean updateActivity(Activity activity) {
    // 设置更新时间
    activity.setUpdateTime(LocalDateTime.now());

    // 更新数据库
    int result = activityMapper.updateById(activity);
    return result > 0;
  }

  @Override
  @Transactional
  public boolean deleteActivity(Long id) {
    // 删除活动
    int result = activityMapper.deleteById(id);

    if (result > 0) {
      // 删除活动参与者记录
      LambdaQueryWrapper<ActivityParticipant> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(ActivityParticipant::getActivityId, id);
      participantMapper.delete(queryWrapper);
    }

    return result > 0;
  }

  @Override
  public Activity getActivityById(Long id) {
    return activityMapper.selectById(id);
  }

  @Override
  public Page<Activity> getActivityList(Integer type, Integer status, int page, int size) {
    Page<Activity> pageParam = new Page<>(page, size);
    LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();

    // 按类型筛选
    if (type != null) {
      queryWrapper.eq(Activity::getType, type);
    }

    // 按状态筛选
    if (status != null) {
      queryWrapper.eq(Activity::getStatus, status);
    } else {
      // 默认不显示已取消的活动
      queryWrapper.ne(Activity::getStatus, 4);
    }

    // 按开始时间排序
    queryWrapper.orderByAsc(Activity::getStartTime);

    return activityMapper.selectPage(pageParam, queryWrapper);
  }

  @Override
  @Transactional
  public boolean joinActivity(ActivityParticipant participant) {
    // 检查活动是否存在
    Activity activity = activityMapper.selectById(participant.getActivityId());
    if (activity == null) {
      logger.error("活动不存在: {}", participant.getActivityId());
      return false;
    }

    // 检查是否已经报名
    LambdaQueryWrapper<ActivityParticipant> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ActivityParticipant::getActivityId, participant.getActivityId())
        .eq(ActivityParticipant::getUserId, participant.getUserId());
    Long count = participantMapper.selectCount(queryWrapper);
    if (count > 0) {
      logger.error("用户已报名该活动: userId={}, activityId={}", participant.getUserId(), participant.getActivityId());
      return false;
    }

    // 检查活动是否已满
    if (activity.getMaxParticipants() > 0 && activity.getCurrentParticipants() >= activity.getMaxParticipants()) {
      logger.error("活动已满: {}", participant.getActivityId());
      return false;
    }

    // 设置初始值
    participant.setRegistrationTime(LocalDateTime.now());
    participant.setCheckInStatus(0); // 未签到

    // 插入数据库
    int result = participantMapper.insert(participant);

    if (result > 0) {
      // 更新活动参与人数
      activity.setCurrentParticipants(activity.getCurrentParticipants() + 1);
      activityMapper.updateById(activity);
    }

    return result > 0;
  }

  @Override
  @Transactional
  public boolean cancelJoin(Long activityId, Long userId) {
    // 检查活动是否存在
    Activity activity = activityMapper.selectById(activityId);
    if (activity == null) {
      logger.error("活动不存在: {}", activityId);
      return false;
    }

    // 删除参与记录
    LambdaQueryWrapper<ActivityParticipant> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ActivityParticipant::getActivityId, activityId)
        .eq(ActivityParticipant::getUserId, userId);
    int result = participantMapper.delete(queryWrapper);

    if (result > 0) {
      // 更新活动参与人数
      activity.setCurrentParticipants(Math.max(0, activity.getCurrentParticipants() - 1));
      activityMapper.updateById(activity);
    }

    return result > 0;
  }

  @Override
  public List<ActivityParticipant> getParticipants(Long activityId) {
    LambdaQueryWrapper<ActivityParticipant> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ActivityParticipant::getActivityId, activityId)
        .orderByAsc(ActivityParticipant::getRegistrationTime);
    return participantMapper.selectList(queryWrapper);
  }

  @Override
  @Transactional
  public boolean checkIn(Long activityId, Long userId) {
    // 查询参与记录
    LambdaQueryWrapper<ActivityParticipant> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ActivityParticipant::getActivityId, activityId)
        .eq(ActivityParticipant::getUserId, userId);
    ActivityParticipant participant = participantMapper.selectOne(queryWrapper);

    if (participant == null) {
      logger.error("用户未报名该活动: userId={}, activityId={}", userId, activityId);
      return false;
    }

    // 更新签到状态
    participant.setCheckInStatus(1);
    participant.setCheckInTime(LocalDateTime.now());

    int result = participantMapper.updateById(participant);
    return result > 0;
  }

  @Override
  public Page<Activity> getUserActivities(Long userId, int page, int size) {
    Page<Activity> pageParam = new Page<>(page, size);

    // 查询用户参与的活动ID列表
    LambdaQueryWrapper<ActivityParticipant> participantQuery = new LambdaQueryWrapper<>();
    participantQuery.eq(ActivityParticipant::getUserId, userId)
        .select(ActivityParticipant::getActivityId);
    List<ActivityParticipant> participants = participantMapper.selectList(participantQuery);

    if (participants.isEmpty()) {
      return new Page<>();
    }

    // 查询活动列表
    List<Long> activityIds = participants.stream()
        .map(ActivityParticipant::getActivityId)
        .toList();

    LambdaQueryWrapper<Activity> activityQuery = new LambdaQueryWrapper<>();
    activityQuery.in(Activity::getId, activityIds)
        .orderByAsc(Activity::getStartTime);

    return activityMapper.selectPage(pageParam, activityQuery);
  }
}