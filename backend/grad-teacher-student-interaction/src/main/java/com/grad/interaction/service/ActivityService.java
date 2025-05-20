package com.grad.interaction.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.interaction.model.Activity;
import com.grad.interaction.model.ActivityParticipant;

import java.util.List;

/**
 * 活动服务接口
 */
public interface ActivityService {

  /**
   * 创建活动
   * 
   * @param activity 活动信息
   * @return 活动ID
   */
  Long createActivity(Activity activity);

  /**
   * 更新活动
   * 
   * @param activity 活动信息
   * @return 是否成功
   */
  boolean updateActivity(Activity activity);

  /**
   * 删除活动
   * 
   * @param id 活动ID
   * @return 是否成功
   */
  boolean deleteActivity(Long id);

  /**
   * 获取活动详情
   * 
   * @param id 活动ID
   * @return 活动详情
   */
  Activity getActivityById(Long id);

  /**
   * 分页获取活动列表
   * 
   * @param type   活动类型
   * @param status 活动状态
   * @param page   页码
   * @param size   每页大小
   * @return 活动分页列表
   */
  Page<Activity> getActivityList(Integer type, Integer status, int page, int size);

  /**
   * 参与活动
   * 
   * @param participant 参与者信息
   * @return 是否成功
   */
  boolean joinActivity(ActivityParticipant participant);

  /**
   * 取消参与
   * 
   * @param activityId 活动ID
   * @param userId     用户ID
   * @return 是否成功
   */
  boolean cancelJoin(Long activityId, Long userId);

  /**
   * 获取活动参与者列表
   * 
   * @param activityId 活动ID
   * @return 参与者列表
   */
  List<ActivityParticipant> getParticipants(Long activityId);

  /**
   * 签到
   * 
   * @param activityId 活动ID
   * @param userId     用户ID
   * @return 是否成功
   */
  boolean checkIn(Long activityId, Long userId);

  /**
   * 获取用户参与的活动列表
   * 
   * @param userId 用户ID
   * @param page   页码
   * @param size   每页大小
   * @return 活动分页列表
   */
  Page<Activity> getUserActivities(Long userId, int page, int size);
}