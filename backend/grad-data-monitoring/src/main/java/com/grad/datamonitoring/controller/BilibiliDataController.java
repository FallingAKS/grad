package com.grad.datamonitoring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grad.datamonitoring.model.BilibiliData;
import com.grad.datamonitoring.service.BilibiliDataService;

@RestController
@RequestMapping("/api/data-monitoring/bilibili")
public class BilibiliDataController {

  private final BilibiliDataService bilibiliDataService;

  public BilibiliDataController(BilibiliDataService bilibiliDataService) {
    this.bilibiliDataService = bilibiliDataService;
  }

  @GetMapping("/user/{uid}")
  public ResponseEntity<?> getUserData(@PathVariable String uid) {
    try {
      BilibiliData data = bilibiliDataService.getUserData(uid);
      if (data == null) {
        return ResponseEntity.badRequest().body(buildResponse(400, "获取数据失败", null));
      }
      return ResponseEntity.ok(buildResponse(200, "获取成功", data));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(buildResponse(500, "服务器错误: " + e.getMessage(), null));
    }
  }

  private Map<String, Object> buildResponse(int code, String message, Object data) {
    Map<String, Object> response = new HashMap<>();
    response.put("code", code);
    response.put("message", message);
    if (data != null) {
      response.put("data", data);
    }
    return response;
  }
}