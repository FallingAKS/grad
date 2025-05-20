package com.grad.interaction.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 活动参与者实体类
 */
@Data
@TableName("activity_participant")
public class ActivityParticipant {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 活动ID
   */
  private Long activityId;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 用户姓名
   */
  private String userName;

  /**
   * 用户类型 1-教师 2-学生
   */
  private Integer userType;

  /**
   * 报名时间
   */
  private LocalDateTime registrationTime;

  /**
   * 签到时间
   */
  private LocalDateTime checkInTime;

  /**
   * 签到状态 0-未签到 1-已签到
   */
  private Integer checkInStatus;

  /**
   * 备注
   */
  private String remark;
}