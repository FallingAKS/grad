package com.grad.datamonitoring.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grad.datamonitoring.crawler.impl.BilibiliUserCrawler;
import com.grad.datamonitoring.model.BilibiliData;
import com.grad.datamonitoring.model.DataItem;
import com.grad.datamonitoring.service.BilibiliDataService;

@Service
public class BilibiliDataServiceImpl implements BilibiliDataService {

  private final BilibiliUserCrawler bilibiliUserCrawler;
  private final ObjectMapper objectMapper;

  public BilibiliDataServiceImpl(BilibiliUserCrawler bilibiliUserCrawler, ObjectMapper objectMapper) {
    this.bilibiliUserCrawler = bilibiliUserCrawler;
    this.objectMapper = objectMapper;
  }

  @Override
  public BilibiliData getUserData(String uid) {
    try {
      // 设置要爬取的UID
      bilibiliUserCrawler.setUid(uid);

      // 爬取数据
      List<DataItem> dataItems = bilibiliUserCrawler.crawl();

      if (dataItems.isEmpty()) {
        return null;
      }

      // 将爬取的数据转换为BilibiliData对象
      return objectMapper.readValue(dataItems.get(0).getContent(), BilibiliData.class);
    } catch (Exception e) {
      throw new RuntimeException("获取B站用户数据失败", e);
    }
  }
}