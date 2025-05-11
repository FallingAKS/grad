package com.grad.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
  private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * 格式化日期
   */
  public static String formatDate(LocalDate date) {
    return date != null ? date.format(DATE_FORMATTER) : null;
  }

  /**
   * 格式化时间
   */
  public static String formatTime(LocalTime time) {
    return time != null ? time.format(TIME_FORMATTER) : null;
  }

  /**
   * 格式化日期时间
   */
  public static String formatDateTime(LocalDateTime dateTime) {
    return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : null;
  }

  /**
   * 解析日期
   */
  public static LocalDate parseDate(String dateStr) {
    return dateStr != null ? LocalDate.parse(dateStr, DATE_FORMATTER) : null;
  }

  /**
   * 解析时间
   */
  public static LocalTime parseTime(String timeStr) {
    return timeStr != null ? LocalTime.parse(timeStr, TIME_FORMATTER) : null;
  }

  /**
   * 解析日期时间
   */
  public static LocalDateTime parseDateTime(String dateTimeStr) {
    return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER) : null;
  }
}