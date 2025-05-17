package com.grad.datamonitoring.model;

import java.util.List;

public class MonitoringData {
  private List<String> labels;
  private List<Integer> followerData;

  public MonitoringData() {
  }

  public MonitoringData(List<String> labels, List<Integer> followerData) {
    this.labels = labels;
    this.followerData = followerData;
  }

  public List<String> getLabels() {
    return labels;
  }

  public void setLabels(List<String> labels) {
    this.labels = labels;
  }

  public List<Integer> getFollowerData() {
    return followerData;
  }

  public void setFollowerData(List<Integer> followerData) {
    this.followerData = followerData;
  }
}