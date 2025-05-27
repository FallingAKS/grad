package com.grad.interaction.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.interaction.model.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ActivityMapperTest {

  @Autowired
  private ActivityMapper activityMapper;

  @Test
  void testInsert() {
    // 创建测试数据
    Activity activity = new Activity();
    activity.setTitle("测试活动");
    activity.setDescription("这是一个测试活动");
    activity.setLocation("测试地点");
    activity.setStartTime(LocalDateTime.now().plusDays(1));
    activity.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    activity.setType(1);
    activity.setStatus(0);
    activity.setCreatorId(1L);
    activity.setCreatorName("测试用户");
    activity.setMaxParticipants(50);
    activity.setCurrentParticipants(0);
    activity.setCreateTime(LocalDateTime.now());
    activity.setUpdateTime(LocalDateTime.now());

    // 执行插入操作
    int rows = activityMapper.insert(activity);

    // 验证结果
    assertEquals(1, rows);
    assertNotNull(activity.getId());
  }

  @Test
  void testSelectById() {
    // 创建测试数据
    Activity activity = new Activity();
    activity.setTitle("测试活动");
    activity.setDescription("这是一个测试活动");
    activity.setLocation("测试地点");
    activity.setStartTime(LocalDateTime.now().plusDays(1));
    activity.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    activity.setType(1);
    activity.setStatus(0);
    activity.setCreatorId(1L);
    activity.setCreatorName("测试用户");
    activity.setMaxParticipants(50);
    activity.setCurrentParticipants(0);
    activity.setCreateTime(LocalDateTime.now());
    activity.setUpdateTime(LocalDateTime.now());

    // 先插入测试数据
    activityMapper.insert(activity);
    Long activityId = activity.getId();

    // 执行查询
    Activity result = activityMapper.selectById(activityId);

    // 验证结果
    assertNotNull(result);
    assertEquals("测试活动", result.getTitle());
    assertEquals("这是一个测试活动", result.getDescription());
    assertEquals("测试地点", result.getLocation());
  }

  @Test
  void testUpdateById() {
    // 创建测试数据
    Activity activity = new Activity();
    activity.setTitle("原始标题");
    activity.setDescription("原始描述");
    activity.setLocation("原始地点");
    activity.setStartTime(LocalDateTime.now().plusDays(1));
    activity.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    activity.setType(1);
    activity.setStatus(0);
    activity.setCreatorId(1L);
    activity.setCreatorName("测试用户");
    activity.setMaxParticipants(50);
    activity.setCurrentParticipants(0);
    activity.setCreateTime(LocalDateTime.now());
    activity.setUpdateTime(LocalDateTime.now());

    // 先插入测试数据
    activityMapper.insert(activity);
    Long activityId = activity.getId();

    // 修改数据
    activity.setTitle("更新后的标题");
    activity.setDescription("更新后的描述");
    activity.setLocation("更新后的地点");
    activity.setUpdateTime(LocalDateTime.now());

    // 执行更新
    int rows = activityMapper.updateById(activity);

    // 验证更新结果
    assertEquals(1, rows);

    // 查询更新后的数据
    Activity updated = activityMapper.selectById(activityId);
    assertNotNull(updated);
    assertEquals("更新后的标题", updated.getTitle());
    assertEquals("更新后的描述", updated.getDescription());
    assertEquals("更新后的地点", updated.getLocation());
  }

  @Test
  void testSelectByType() {
    // 创建测试数据 - 类型1
    Activity activity1 = new Activity();
    activity1.setTitle("类型1的活动");
    activity1.setDescription("测试内容1");
    activity1.setType(1);
    activity1.setStatus(0);
    activity1.setCreatorId(1L);
    activity1.setStartTime(LocalDateTime.now().plusDays(1));
    activity1.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    activity1.setCreateTime(LocalDateTime.now());
    activityMapper.insert(activity1);

    // 创建测试数据 - 类型2
    Activity activity2 = new Activity();
    activity2.setTitle("类型2的活动");
    activity2.setDescription("测试内容2");
    activity2.setType(2);
    activity2.setStatus(0);
    activity2.setCreatorId(1L);
    activity2.setStartTime(LocalDateTime.now().plusDays(1));
    activity2.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    activity2.setCreateTime(LocalDateTime.now());
    activityMapper.insert(activity2);

    // 按类型查询
    Page<Activity> page = new Page<>(1, 10);
    LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Activity::getType, 1);

    Page<Activity> result = activityMapper.selectPage(page, queryWrapper);

    // 验证结果
    assertNotNull(result);
    assertTrue(result.getRecords().size() > 0);
    assertEquals(1, result.getRecords().get(0).getType());
  }

  @Test
  void testSelectByStatus() {
    // 创建测试数据 - 状态0
    Activity activity1 = new Activity();
    activity1.setTitle("状态0的活动");
    activity1.setDescription("测试内容1");
    activity1.setType(1);
    activity1.setStatus(0);
    activity1.setCreatorId(1L);
    activity1.setStartTime(LocalDateTime.now().plusDays(1));
    activity1.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    activity1.setCreateTime(LocalDateTime.now());
    activityMapper.insert(activity1);

    // 创建测试数据 - 状态1
    Activity activity2 = new Activity();
    activity2.setTitle("状态1的活动");
    activity2.setDescription("测试内容2");
    activity2.setType(1);
    activity2.setStatus(1);
    activity2.setCreatorId(1L);
    activity2.setStartTime(LocalDateTime.now().plusDays(1));
    activity2.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    activity2.setCreateTime(LocalDateTime.now());
    activityMapper.insert(activity2);

    // 按状态查询
    LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Activity::getStatus, 0);

    List<Activity> activities = activityMapper.selectList(queryWrapper);

    // 验证结果
    assertNotNull(activities);
    assertTrue(activities.size() > 0);
    assertEquals(0, activities.get(0).getStatus());
  }

  @Test
  void testDeleteById() {
    // 创建测试数据
    Activity activity = new Activity();
    activity.setTitle("待删除的活动");
    activity.setDescription("这个活动将被删除");
    activity.setType(1);
    activity.setStatus(0);
    activity.setCreatorId(1L);
    activity.setStartTime(LocalDateTime.now().plusDays(1));
    activity.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
    activity.setCreateTime(LocalDateTime.now());

    // 插入数据
    activityMapper.insert(activity);
    Long activityId = activity.getId();

    // 执行删除
    int rows = activityMapper.deleteById(activityId);

    // 验证删除结果
    assertEquals(1, rows);

    // 查询确认已删除
    Activity deleted = activityMapper.selectById(activityId);
    assertNull(deleted);
  }
}