package com.grad.interaction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grad.interaction.model.Activity;
import com.grad.interaction.service.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ActivityService activityService;

  @Autowired
  private ObjectMapper objectMapper;

  private Activity testActivity;

  @BeforeEach
  void setUp() {
    testActivity = new Activity();
    testActivity.setId(1L);
    testActivity.setTitle("测试活动");
    testActivity.setDescription("这是一个测试活动");
    testActivity.setLocation("测试地点");
    testActivity.setStartTime(LocalDateTime.now().plusDays(1));
    testActivity.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    testActivity.setType(1); // 假设1表示线下活动
    testActivity.setStatus(0); // 假设0表示未开始
    testActivity.setCreatorId(1L);
    testActivity.setCreatorName("测试用户");
    testActivity.setMaxParticipants(50);
    testActivity.setCurrentParticipants(0);
    testActivity.setCreateTime(LocalDateTime.now());
    testActivity.setUpdateTime(LocalDateTime.now());
  }

  @Test
  void testGetAllActivities() throws Exception {
    // 准备测试数据
    List<Activity> activities = Arrays.asList(testActivity);

    // 设置模拟行为
    when(activityService.getAllActivities()).thenReturn(activities);

    // 执行测试
    mockMvc.perform(get("/api/interaction/activities"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("测试活动"))
        .andExpect(jsonPath("$[0].description").value("这是一个测试活动"));

    // 验证服务调用
    verify(activityService).getAllActivities();
  }

  @Test
  void testGetActivityById_Success() throws Exception {
    // 设置模拟行为
    when(activityService.getActivityById(1L)).thenReturn(testActivity);

    // 执行测试
    mockMvc.perform(get("/api/interaction/activities/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("测试活动"))
        .andExpect(jsonPath("$.description").value("这是一个测试活动"));

    // 验证服务调用
    verify(activityService).getActivityById(1L);
  }

  @Test
  void testGetActivityById_NotFound() throws Exception {
    // 设置模拟行为
    when(activityService.getActivityById(999L)).thenReturn(null);

    // 执行测试
    mockMvc.perform(get("/api/interaction/activities/999"))
        .andExpect(status().isNotFound());

    // 验证服务调用
    verify(activityService).getActivityById(999L);
  }

  @Test
  void testCreateActivity() throws Exception {
    // 设置模拟行为
    when(activityService.createActivity(any(Activity.class))).thenReturn(testActivity);

    // 执行测试
    mockMvc.perform(post("/api/interaction/activities")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(testActivity)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("测试活动"));

    // 验证服务调用
    verify(activityService).createActivity(any(Activity.class));
  }

  @Test
  void testUpdateActivity_Success() throws Exception {
    // 设置模拟行为
    when(activityService.updateActivity(any(Activity.class))).thenReturn(testActivity);

    // 执行测试
    mockMvc.perform(put("/api/interaction/activities/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(testActivity)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.title").value("测试活动"));

    // 验证服务调用
    verify(activityService).updateActivity(any(Activity.class));
  }

  @Test
  void testUpdateActivity_NotFound() throws Exception {
    // 设置模拟行为
    when(activityService.updateActivity(any(Activity.class))).thenReturn(null);

    // 执行测试
    mockMvc.perform(put("/api/interaction/activities/999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(testActivity)))
        .andExpect(status().isNotFound());

    // 验证服务调用
    verify(activityService).updateActivity(any(Activity.class));
  }

  @Test
  void testDeleteActivity() throws Exception {
    // 设置模拟行为
    doNothing().when(activityService).deleteActivity(anyLong());

    // 执行测试
    mockMvc.perform(delete("/api/interaction/activities/1"))
        .andExpect(status().isOk());

    // 验证服务调用
    verify(activityService).deleteActivity(1L);
  }

  @Test
  void testToggleActivityStatus_Success() throws Exception {
    // 设置模拟行为
    when(activityService.toggleActivityStatus(1L)).thenReturn(testActivity);

    // 执行测试
    mockMvc.perform(put("/api/interaction/activities/1/toggle"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));

    // 验证服务调用
    verify(activityService).toggleActivityStatus(1L);
  }

  @Test
  void testToggleActivityStatus_NotFound() throws Exception {
    // 设置模拟行为
    when(activityService.toggleActivityStatus(999L)).thenReturn(null);

    // 执行测试
    mockMvc.perform(put("/api/interaction/activities/999/toggle"))
        .andExpect(status().isNotFound());

    // 验证服务调用
    verify(activityService).toggleActivityStatus(999L);
  }
}