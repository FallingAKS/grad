package com.grad.contentproduction.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 */
@RestController
@RequestMapping("/api/content-production/upload")
public class FileUploadController {

  private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

  @Value("${file.upload.path:/upload}")
  private String uploadPath;

  @Value("${file.upload.url-prefix:http://localhost:8080}")
  private String urlPrefix;

  /**
   * 上传图片
   */
  @PostMapping("/image")
  public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
    return uploadFile(file, "images");
  }

  /**
   * 上传视频
   */
  @PostMapping("/video")
  public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file) {
    return uploadFile(file, "videos");
  }

  /**
   * 通用文件上传方法
   */
  private ResponseEntity<?> uploadFile(MultipartFile file, String subDir) {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body(buildResponse(400, "文件为空", null));
    }

    try {
      // 创建上传目录
      String dirPath = uploadPath + File.separator + subDir;
      Path directory = Paths.get(dirPath);
      if (!Files.exists(directory)) {
        Files.createDirectories(directory);
      }

      // 生成文件名
      String originalFilename = file.getOriginalFilename();
      String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
      String newFilename = UUID.randomUUID().toString() + extension;

      // 保存文件
      Path filePath = Paths.get(dirPath + File.separator + newFilename);
      Files.write(filePath, file.getBytes());

      // 构建URL
      String fileUrl = urlPrefix + "/upload/" + subDir + "/" + newFilename;

      logger.info("文件上传成功: {}", fileUrl);
      return ResponseEntity.ok(buildResponse(200, "上传成功", Map.of("url", fileUrl)));
    } catch (IOException e) {
      logger.error("文件上传失败", e);
      return ResponseEntity.internalServerError().body(buildResponse(500, "上传失败: " + e.getMessage(), null));
    }
  }

  /**
   * 构建响应对象
   */
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