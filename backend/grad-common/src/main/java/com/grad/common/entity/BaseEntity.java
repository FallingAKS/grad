package com.grad.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 基础实体类
 */
@Data
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;

  /**
   * 创建者
   */
  private String createBy;

  /**
   * 更新者
   */
  private String updateBy;

  /**
   * 备注
   */
  private String remark;
}