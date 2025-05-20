package com.grad.datamonitoring.crawler.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grad.datamonitoring.model.DataItem;

/**
 * B站(bilibili)用户信息爬虫实现 - 直接从网页爬取
 */
@Component
public class BilibiliUserCrawler {

  private static final Logger logger = LoggerFactory.getLogger(BilibiliUserCrawler.class);
  private static final String SOURCE_NAME = "Bilibili";

  // 用户代理列表，模拟不同浏览器
  private static final String[] USER_AGENTS = {
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36",
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Safari/605.1.15",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:93.0) Gecko/20100101 Firefox/93.0",
      "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 Edg/94.0.992.47"
  };

  // 代理IP池
  private static final String[] PROXY_IPS = {
      "127.0.0.1:8090",
      "127.0.0.1:8091",
      "127.0.0.1:8092"
  };

  private static final int TIMEOUT = 10000; // 10秒超时
  private static final int MIN_DELAY = 1000; // 最小延迟1秒
  private static final int MAX_DELAY = 5000; // 最大延迟5秒
  private static final int MAX_RETRY = 3; // 最大重试次数

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final Random random = new Random();
  private String uid = "481701670"; // 默认使用示例UID

  public List<DataItem> crawl() {
    return crawlUserData(uid);
  }

  public List<DataItem> crawlUserData(String uid) {
    List<DataItem> results = new ArrayList<>();

    try {
      logger.info("开始爬取B站用户信息, UID: {}", uid);
      String userUrl = "https://space.bilibili.com/" + uid;

      // 获取用户主页
      Document userDoc = getDocumentWithRetry(userUrl);
      if (userDoc == null) {
        logger.error("获取用户主页失败");
        return results;
      }

      Map<String, Object> userData = new HashMap<>();
      userData.put("uid", uid);

      // 获取用户名
      Element nameElement = userDoc.selectFirst(".h-name");
      if (nameElement != null) {
        userData.put("name", nameElement.text());
      }

      // 获取关注数、粉丝数、获赞数
      Elements statElements = userDoc.select(".n-statistics .n-data");
      if (statElements.size() >= 3) {
        userData.put("following", statElements.get(0).text()); // 关注数
        userData.put("follower", statElements.get(1).text()); // 粉丝数
        userData.put("likes", statElements.get(2).text()); // 获赞数
      }

      // 获取播放数
      Element playElement = userDoc.selectFirst(".n-play");
      if (playElement != null) {
        userData.put("archive_view", playElement.text());
      }

      // 获取第一个视频链接
      Element firstVideo = userDoc.selectFirst(".small-item");
      if (firstVideo != null) {
        String videoUrl = "https:" + firstVideo.selectFirst("a").attr("href");
        String bvid = videoUrl.substring(videoUrl.lastIndexOf("/") + 1);

        // 获取视频详情页
        randomDelay();
        Document videoDoc = getDocumentWithRetry(videoUrl);
        if (videoDoc != null) {
          Map<String, Object> videoInfo = new HashMap<>();
          videoInfo.put("bvid", bvid);

          // 获取视频标题
          Element titleElement = videoDoc.selectFirst(".video-title");
          if (titleElement != null) {
            videoInfo.put("title", titleElement.text());
          }

          // 获取视频数据
          Elements videoStats = videoDoc.select(".video-data .video-data-list span");
          for (Element stat : videoStats) {
            String text = stat.text();
            if (text.contains("点赞")) {
              videoInfo.put("like", text.replaceAll("[^0-9]", ""));
            } else if (text.contains("投币")) {
              videoInfo.put("coin", text.replaceAll("[^0-9]", ""));
            } else if (text.contains("收藏")) {
              videoInfo.put("favorite", text.replaceAll("[^0-9]", ""));
            } else if (text.contains("转发")) {
              videoInfo.put("share", text.replaceAll("[^0-9]", ""));
            }
          }

          userData.put("latestVideo", videoInfo);
        }
      }

      // 创建数据项
      String content = objectMapper.writeValueAsString(userData);
      String title = "Bilibili用户数据: " + userData.getOrDefault("name", "未知用户");

      DataItem dataItem = createDataItem(title, content, SOURCE_NAME, userUrl, "B站数据");
      results.add(dataItem);

      logger.info("爬取完成，获取到用户 {} 的数据", userData.getOrDefault("name", uid));

    } catch (Exception e) {
      logger.error("爬取B站用户信息过程中出错", e);
    }

    return results;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getUid() {
    return this.uid;
  }

  /**
   * 获取HTML文档（带重试和代理）
   */
  private Document getDocumentWithRetry(String url) {
    int retryCount = 0;
    while (retryCount < MAX_RETRY) {
      try {
        // 随机选择用户代理
        String userAgent = USER_AGENTS[random.nextInt(USER_AGENTS.length)];

        // 随机选择代理IP（如果启用）
        boolean useProxy = random.nextBoolean(); // 随机决定是否使用代理

        // 创建连接
        if (useProxy) {
          String proxyIp = PROXY_IPS[random.nextInt(PROXY_IPS.length)];
          String[] parts = proxyIp.split(":");
          String proxyHost = parts[0];
          int proxyPort = Integer.parseInt(parts[1]);

          logger.info("使用代理: {}:{}", proxyHost, proxyPort);

          return Jsoup.connect(url)
              .userAgent(userAgent)
              .timeout(TIMEOUT)
              .proxy(proxyHost, proxyPort)
              .get();
        } else {
          return Jsoup.connect(url)
              .userAgent(userAgent)
              .timeout(TIMEOUT)
              .get();
        }
      } catch (IOException e) {
        retryCount++;
        logger.error("获取页面失败，第{}次重试: {}", retryCount, url, e);

        if (retryCount < MAX_RETRY) {
          // 延迟后重试
          randomDelay();
        }
      }
    }

    logger.error("获取页面失败，已达到最大重试次数: {}", url);
    return null;
  }

  /**
   * 在爬取多个链接之间添加随机延迟
   */
  private void randomDelay() {
    try {
      int delayTime = random.nextInt(MAX_DELAY - MIN_DELAY) + MIN_DELAY;
      logger.debug("爬取间隔等待{}毫秒", delayTime);
      TimeUnit.MILLISECONDS.sleep(delayTime);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * 创建数据项
   */
  private DataItem createDataItem(String title, String content, String source, String url, String category) {
    DataItem item = new DataItem();
    item.setTitle(title);
    item.setContent(content);
    item.setSource(source);
    item.setUrl(url);
    item.setType(category);
    return item;
  }
}