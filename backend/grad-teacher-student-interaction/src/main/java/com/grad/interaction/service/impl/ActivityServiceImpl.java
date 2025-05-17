package com.grad.interaction.service.impl;

import com.grad.interaction.model.Activity;
import com.grad.interaction.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ActivityServiceImpl implements ActivityService {

  // 模拟数据库，实际应使用数据库
  private final ConcurrentHashMap<Long, Activity> activityMap = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Activity getActivityById(Long id) {
    return activityMap.get(id);
  }

  @Override
  public List<Activity> getAllActivities() {
    return new ArrayList<>(activityMap.values());
  }

  @Override
  public Activity createActivity(Activity activity) {
    activity.setId(idGenerator.getAndIncrement());
    activity.setCreateTime(new Date());
    activity.setIsActive(true);
    activityMap.put(activity.getId(), activity);
    return activity;
  }

  @Override
  public Activity updateActivity(Activity activity) {
    Activity existingActivity = activityMap.get(activity.getId());
    if (existingActivity != null) {
      activity.setCreateTime(existingActivity.getCreateTime());
      activity.setIsActive(existingActivity.getIsActive());
      activityMap.put(activity.getId(), activity);
      return activity;
    }
    return null;
  }

  @Override
  public void deleteActivity(Long id) {
    activityMap.remove(id);
  }

  @Override
  public Activity toggleActivityStatus(Long id) {
    Activity activity = activityMap.get(id);
    if (activity != null) {
      activity.setIsActive(!activity.getIsActive());
      return activity;
    }
    return null;
  }
}