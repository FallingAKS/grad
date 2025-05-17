package com.grad.datamonitoring.service;

import com.grad.datamonitoring.model.MonitoringData;
import com.grad.datamonitoring.model.Platform;

public interface MonitoringService {
  MonitoringData getMonitoringData();

  void collectData(Platform platform);
}