package com.grad.datamonitoring.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 爬虫数据实体类
 */
@Data
public class DataItem {

  /**
   * 数据标题
   */
  private String title;

  /**
   * 数据内容
   */
  private String content;

  /**
   * 数据来源
   */
  private String source;

  /**
   * 原始URL
   */
  private String url;

  /**
   * 爬取时间
   */
  private LocalDateTime crawlTime;

  /**
   * 分类
   */
  private String category;

  /**
   * 状态 0-未处理 1-已处理
   */
  private Integer status;

  /**
   * 数据类型
   */
  private String type;
}