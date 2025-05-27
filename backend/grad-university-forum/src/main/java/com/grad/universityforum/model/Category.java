package com.grad.universityforum.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 论坛板块实体类
 */
@Data
@TableName("category")
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
   * 排序
   */
  private Integer sort;

  /**
   * 状态 0-正常 1-禁用
   */
  private Integer status;

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getSort() {
    return sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}