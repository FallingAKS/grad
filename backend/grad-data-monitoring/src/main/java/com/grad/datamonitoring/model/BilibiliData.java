package com.grad.datamonitoring.model;

import java.util.Map;

import lombok.Data;

@Data
public class BilibiliData {
  private String uid;
  private String name;
  private String following; // 关注数
  private String follower; // 粉丝数
  private String likes; // 获赞数
  private String archiveView; // 播放数
  private Map<String, Object> latestVideo; // 最新视频信息
}