package com.grad.interaction.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 活动实体类
 */
@Data
@TableName("activity")
public class Activity {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 活动标题
   */
  private String title;

  /**
   * 活动描述
   */
  private String description;

  /**
   * 活动地点
   */
  private String location;

  /**
   * 活动开始时间
   */
  private LocalDateTime startTime;

  /**
   * 活动结束时间
   */
  private LocalDateTime endTime;

  /**
   * 活动类型 1-讲座 2-研讨会 3-实践活动 4-文体活动 5-其他
   */
  private Integer type;

  /**
   * 活动封面图URL
   */
  private String coverUrl;

  /**
   * 组织者ID
   */
  private Long organizerId;

  /**
   * 组织者名称
   */
  private String organizerName;

  /**
   * 活动状态 0-筹备中 1-报名中 2-进行中 3-已结束 4-已取消
   */
  private Integer status;

  /**
   * 报名截止时间
   */
  private LocalDateTime registrationDeadline;

  /**
   * 最大参与人数，0表示不限制
   */
  private Integer maxParticipants;

  /**
   * 当前参与人数
   */
  private Integer currentParticipants;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;
}