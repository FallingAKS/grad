package com.grad.common.entity;

import lombok.Data;

/**
 * 统一返回结果
 */
@Data
public class Result<T> {

  /**
   * 状态码
   */
  private Integer code;

  /**
   * 返回信息
   */
  private String message;

  /**
   * 返回数据
   */
  private T data;

  /**
   * 成功返回结果
   */
  public static <T> Result<T> success() {
    return success(null);
  }

  /**
   * 成功返回结果
   */
  public static <T> Result<T> success(T data) {
    return success(data, "操作成功");
  }

  /**
   * 成功返回结果
   */
  public static <T> Result<T> success(T data, String message) {
    Result<T> result = new Result<>();
    result.setCode(200);
    result.setData(data);
    result.setMessage(message);
    return result;
  }

  /**
   * 失败返回结果
   */
  public static <T> Result<T> error(String message) {
    return error(500, message);
  }

  /**
   * 失败返回结果
   */
  public static <T> Result<T> error(Integer code, String message) {
    Result<T> result = new Result<>();
    result.setCode(code);
    result.setMessage(message);
    return result;
  }
}