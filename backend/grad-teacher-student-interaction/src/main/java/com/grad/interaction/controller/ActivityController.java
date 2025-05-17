package com.grad.interaction.controller;

import com.grad.interaction.model.Activity;
import com.grad.interaction.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interaction/activities")
public class ActivityController {

  private final ActivityService activityService;

  @Autowired
  public ActivityController(ActivityService activityService) {
    this.activityService = activityService;
  }

  @GetMapping
  public List<Activity> getAllActivities() {
    return activityService.getAllActivities();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Activity> getActivityById(@PathVariable Long id) {
    Activity activity = activityService.getActivityById(id);
    if (activity != null) {
      return ResponseEntity.ok(activity);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public Activity createActivity(@RequestBody Activity activity) {
    return activityService.createActivity(activity);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Activity> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
    activity.setId(id);
    Activity updatedActivity = activityService.updateActivity(activity);
    if (updatedActivity != null) {
      return ResponseEntity.ok(updatedActivity);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
    activityService.deleteActivity(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}/toggle")
  public ResponseEntity<Activity> toggleActivityStatus(@PathVariable Long id) {
    Activity activity = activityService.toggleActivityStatus(id);
    if (activity != null) {
      return ResponseEntity.ok(activity);
    }
    return ResponseEntity.notFound().build();
  }
}