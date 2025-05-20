package com.grad.forum.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 论坛板块实体类
 */
@Data
@TableName("forum_category")
public class Category {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 板块名称
   */
  private String name;

  /**
   * 板块描述
   */
  private String description;

  /**
   * 板块图标
   */
  private String icon;

  /**
   * 排序
   */
  private Integer sort;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;

  /**
   * 状态 0-正常 1-禁用
   */
  private Integer status;
}