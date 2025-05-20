package com.grad.forum.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 论坛帖子实体类
 */
@Data
@TableName("forum_post")
public class Post {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 帖子标题
   */
  private String title;

  /**
   * 帖子内容
   */
  private String content;

  /**
   * 作者ID
   */
  private Long authorId;

  /**
   * 作者名称
   */
  private String authorName;

  /**
   * 板块ID
   */
  private Long categoryId;

  /**
   * 板块名称
   */
  private String categoryName;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;

  /**
   * 浏览量
   */
  private Integer viewCount;

  /**
   * 回复数
   */
  private Integer replyCount;

  /**
   * 点赞数
   */
  private Integer likeCount;

  /**
   * 状态 0-正常 1-置顶 2-精华 3-禁用
   */
  private Integer status;
}