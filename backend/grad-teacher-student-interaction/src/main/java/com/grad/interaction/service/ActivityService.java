package com.grad.interaction.service;

import com.grad.interaction.model.Activity;

import java.util.List;

public interface ActivityService {
  Activity getActivityById(Long id);

  List<Activity> getAllActivities();

  Activity createActivity(Activity activity);

  Activity updateActivity(Activity activity);

  void deleteActivity(Long id);

  Activity toggleActivityStatus(Long id);
}