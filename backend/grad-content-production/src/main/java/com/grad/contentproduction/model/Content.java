package com.grad.contentproduction.model;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 内容实体类
 */
@Data
@TableName("content")
public class Content {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 标题
   */
  private String title;

  /**
   * 摘要
   */
  private String summary;

  /**
   * 内容
   */
  private String content;

  /**
   * 封面图URL
   */
  private String coverUrl;

  /**
   * 分类
   */
  private String category;

  /**
   * 标签，JSON格式存储
   */
  private String tags;

  /**
   * 作者ID
   */
  private Long authorId;

  /**
   * 作者名称
   */
  private String authorName;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;

  /**
   * 状态 0-草稿 1-已发布
   */
  private Integer status;

  /**
   * 浏览量
   */
  private Integer viewCount;

  /**
   * 标签列表，非数据库字段
   */
  @TableField(exist = false)
  private List<String> tagList;
}