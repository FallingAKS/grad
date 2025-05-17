package com.grad.datamonitoring.controller;

import com.grad.datamonitoring.model.MonitoringData;
import com.grad.datamonitoring.model.Platform;
import com.grad.datamonitoring.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monitoring/data")
public class MonitoringController {

  private final MonitoringService monitoringService;

  @Autowired
  public MonitoringController(MonitoringService monitoringService) {
    this.monitoringService = monitoringService;
  }

  @GetMapping
  public MonitoringData getMonitoringData() {
    return monitoringService.getMonitoringData();
  }

  @PostMapping("/collect")
  public String collectData(@RequestBody Platform platform) {
    monitoringService.collectData(platform);
    return "数据采集已启动";
  }
}