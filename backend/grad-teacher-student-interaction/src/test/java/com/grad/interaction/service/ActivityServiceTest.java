package com.grad.interaction.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.interaction.mapper.ActivityMapper;
import com.grad.interaction.mapper.ActivityParticipantMapper;
import com.grad.interaction.model.Activity;
import com.grad.interaction.model.ActivityParticipant;
import com.grad.interaction.service.impl.ActivityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ActivityServiceTest {

  @Mock
  private ActivityMapper activityMapper;

  @Mock
  private ActivityParticipantMapper participantMapper;

  @InjectMocks
  private ActivityServiceImpl activityService;

  private Activity testActivity;
  private ActivityParticipant testParticipant;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    testActivity = new Activity();
    testActivity.setId(1L);
    testActivity.setTitle("测试活动");
    testActivity.setDescription("这是一个测试活动");
    testActivity.setLocation("测试地点");
    testActivity.setStartTime(LocalDateTime.now().plusDays(1));
    testActivity.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    testActivity.setType(1);
    testActivity.setStatus(0);
    testActivity.setCreatorId(1L);
    testActivity.setCreatorName("测试用户");
    testActivity.setMaxParticipants(50);
    testActivity.setCurrentParticipants(0);
    testActivity.setCreateTime(LocalDateTime.now());
    testActivity.setUpdateTime(LocalDateTime.now());

    testParticipant = new ActivityParticipant();
    testParticipant.setId(1L);
    testParticipant.setActivityId(1L);
    testParticipant.setUserId(2L);
    testParticipant.setUserName("参与者");
    testParticipant.setJoinTime(LocalDateTime.now());
    testParticipant.setStatus(0); // 0表示已报名未签到
  }

  @Test
  void testCreateActivity() {
    // 设置模拟行为
    when(activityMapper.insert(any(Activity.class))).thenReturn(1);

    // 执行测试
    Long activityId = activityService.createActivity(testActivity);

    // 验证结果
    assertNotNull(activityId);
    assertEquals(testActivity.getId(), activityId);
    assertNotNull(testActivity.getCreateTime());
    assertNotNull(testActivity.getUpdateTime());
    assertEquals(0, testActivity.getCurrentParticipants());
    assertEquals(0, testActivity.getStatus());

    // 验证方法调用
    verify(activityMapper).insert(testActivity);
  }

  @Test
  void testUpdateActivity() {
    // 设置模拟行为
    when(activityMapper.updateById(any(Activity.class))).thenReturn(1);
    when(activityMapper.selectById(eq(1L))).thenReturn(testActivity);
    when(activityMapper.selectById(eq(2L))).thenReturn(null);

    // 执行测试 - 成功更新
    boolean result = activityService.updateActivity(testActivity);

    // 验证结果
    assertTrue(result);
    assertNotNull(testActivity.getUpdateTime());
    verify(activityMapper).updateById(testActivity);

    // 测试更新不存在的活动
    Activity nonExistentActivity = new Activity();
    nonExistentActivity.setId(2L);
    nonExistentActivity.setTitle("不存在的活动");

    result = activityService.updateActivity(nonExistentActivity);

    // 验证结果
    assertFalse(result);
  }

  @Test
  void testDeleteActivity() {
    // 设置模拟行为
    when(activityMapper.deleteById(eq(1L))).thenReturn(1);
    when(activityMapper.deleteById(eq(2L))).thenReturn(0);

    // 执行测试 - 成功删除
    boolean result = activityService.deleteActivity(1L);

    // 验证结果
    assertTrue(result);
    verify(activityMapper).deleteById(1L);

    // 测试删除不存在的活动
    result = activityService.deleteActivity(2L);

    // 验证结果
    assertFalse(result);
  }

  @Test
  void testGetActivityById() {
    // 设置模拟行为
    when(activityMapper.selectById(eq(1L))).thenReturn(testActivity);
    when(activityMapper.selectById(eq(2L))).thenReturn(null);

    // 执行测试
    Activity result = activityService.getActivityById(1L);

    // 验证结果
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("测试活动", result.getTitle());

    // 测试获取不存在的活动
    result = activityService.getActivityById(2L);

    // 验证结果
    assertNull(result);
  }

  @Test
  void testGetActivityList() {
    // 准备测试数据
    Page<Activity> page = new Page<>(1, 10);
    page.setRecords(Arrays.asList(testActivity));
    page.setTotal(1);

    // 设置模拟行为
    when(activityMapper.selectPage(any(Page.class), any(LambdaQueryWrapper.class))).thenReturn(page);

    // 执行测试 - 带类型和状态
    Page<Activity> resultPage = activityService.getActivityList(1, 0, 1, 10);

    // 验证结果
    assertNotNull(resultPage);
    assertEquals(1, resultPage.getRecords().size());
    assertEquals(1L, resultPage.getRecords().get(0).getId());
    assertEquals("测试活动", resultPage.getRecords().get(0).getTitle());

    // 执行测试 - 不带类型和状态
    resultPage = activityService.getActivityList(null, null, 1, 10);

    // 验证结果
    assertNotNull(resultPage);
    assertEquals(1, resultPage.getRecords().size());
  }

  @Test
  void testJoinActivity() {
    // 设置模拟行为
    when(activityMapper.selectById(eq(1L))).thenReturn(testActivity);
    when(participantMapper.insert(any(ActivityParticipant.class))).thenReturn(1);
    when(activityMapper.updateById(any(Activity.class))).thenReturn(1);

    // 执行测试
    boolean result = activityService.joinActivity(testParticipant);

    // 验证结果
    assertTrue(result);
    assertEquals(1, testActivity.getCurrentParticipants());
    verify(participantMapper).insert(testParticipant);
    verify(activityMapper).updateById(testActivity);
  }

  @Test
  void testCancelJoin() {
    // 设置模拟行为
    when(activityMapper.selectById(eq(1L))).thenReturn(testActivity);
    when(participantMapper.delete(any(LambdaQueryWrapper.class))).thenReturn(1);
    when(activityMapper.updateById(any(Activity.class))).thenReturn(1);

    // 执行测试
    boolean result = activityService.cancelJoin(1L, 2L);

    // 验证结果
    assertTrue(result);
    assertEquals(0, testActivity.getCurrentParticipants()); // 应该减少参与人数
    verify(participantMapper).delete(any(LambdaQueryWrapper.class));
    verify(activityMapper).updateById(testActivity);
  }

  @Test
  void testGetParticipants() {
    // 设置模拟行为
    when(participantMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(Arrays.asList(testParticipant));

    // 执行测试
    List<ActivityParticipant> participants = activityService.getParticipants(1L);

    // 验证结果
    assertNotNull(participants);
    assertEquals(1, participants.size());
    assertEquals(1L, participants.get(0).getActivityId());
    assertEquals(2L, participants.get(0).getUserId());
  }

  @Test
  void testCheckIn() {
    // 设置模拟行为
    when(participantMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testParticipant);
    when(participantMapper.updateById(any(ActivityParticipant.class))).thenReturn(1);

    // 执行测试
    boolean result = activityService.checkIn(1L, 2L);

    // 验证结果
    assertTrue(result);
    assertEquals(1, testParticipant.getStatus()); // 1表示已签到
    verify(participantMapper).updateById(testParticipant);
  }

  @Test
  void testGetUserActivities() {
    // 准备测试数据
    Page<Activity> page = new Page<>(1, 10);
    page.setRecords(Arrays.asList(testActivity));
    page.setTotal(1);

    // 设置模拟行为
    when(activityService.getUserActivities(eq(2L), eq(1), eq(10))).thenReturn(page);

    // 执行测试
    Page<Activity> resultPage = activityService.getUserActivities(2L, 1, 10);

    // 验证结果
    assertNotNull(resultPage);
    assertEquals(1, resultPage.getRecords().size());
    assertEquals(1L, resultPage.getRecords().get(0).getId());
    assertEquals("测试活动", resultPage.getRecords().get(0).getTitle());
  }
}