package com.grad.datamonitoring.service.impl;

import com.grad.datamonitoring.crawler.BilibiliCrawler;
import com.grad.datamonitoring.model.MonitoringData;
import com.grad.datamonitoring.model.Platform;
import com.grad.datamonitoring.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MonitoringServiceImpl implements MonitoringService {

  private final BilibiliCrawler bilibiliCrawler;

  @Autowired
  public MonitoringServiceImpl(BilibiliCrawler bilibiliCrawler) {
    this.bilibiliCrawler = bilibiliCrawler;
  }

  @Override
  public MonitoringData getMonitoringData() {
    // 示例数据，实际应从数据库获取
    List<String> labels = Arrays.asList("一月", "二月", "三月", "四月", "五月");
    List<Integer> followerData = Arrays.asList(100, 150, 200, 250, 300);
    return new MonitoringData(labels, followerData);
  }

  @Override
  public void collectData(Platform platform) {
    // 实际项目中应该将爬取的数据存储到数据库
    if ("bilibili".equals(platform.getName())) {
      bilibiliCrawler.crawlData(platform.getUrl(), ".video-item");
    }
  }
}