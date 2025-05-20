package com.grad.forum.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 论坛回复实体类
 */
@Data
@TableName("forum_reply")
public class Reply {

  @TableId(type = IdType.AUTO)
  private Long id;

  /**
   * 帖子ID
   */
  private Long postId;

  /**
   * 回复内容
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
   * 父回复ID，如果是一级回复则为0
   */
  private Long parentId;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 点赞数
   */
  private Integer likeCount;

  /**
   * 状态 0-正常 1-禁用
   */
  private Integer status;
}