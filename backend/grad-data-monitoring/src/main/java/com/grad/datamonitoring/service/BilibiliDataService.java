package com.grad.datamonitoring.service;

import com.grad.datamonitoring.model.BilibiliData;

public interface BilibiliDataService {
  /**
   * 获取B站用户数据
   * 
   * @param uid 用户ID
   * @return B站用户数据
   */
  BilibiliData getUserData(String uid);
}