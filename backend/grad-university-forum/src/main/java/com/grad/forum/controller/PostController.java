package com.grad.forum.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.forum.model.Post;
import com.grad.forum.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帖子控制器
 */
@RestController
@RequestMapping("/api/forum/posts")
public class PostController {

  private static final Logger logger = LoggerFactory.getLogger(PostController.class);

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  /**
   * 创建帖子
   */
  @PostMapping
  public ResponseEntity<?> createPost(@RequestBody Post post) {
    try {
      Long postId = postService.createPost(post);
      return ResponseEntity.ok(buildResponse(200, "发布成功", Map.of("id", postId)));
    } catch (Exception e) {
      logger.error("创建帖子失败", e);
      return ResponseEntity.internalServerError().body(buildResponse(500, "发布失败: " + e.getMessage(), null));
    }
  }

  /**
   * 更新帖子
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post post) {
    try {
      post.setId(id);
      boolean success = postService.updatePost(post);
      if (success) {
        return ResponseEntity.ok(buildResponse(200, "更新成功", null));
      } else {
        return ResponseEntity.badRequest().body(buildResponse(400, "更新失败，帖子不存在", null));
      }
    } catch (Exception e) {
      logger.error("更新帖子失败", e);
      return ResponseEntity.internalServerError().body(buildResponse(500, "更新失败: " + e.getMessage(), null));
    }
  }

  /**
   * 删除帖子
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePost(@PathVariable Long id) {
    try {
      boolean success = postService.deletePost(id);
      if (success) {
        return ResponseEntity.ok(buildResponse(200, "删除成功", null));
      } else {
        return ResponseEntity.badRequest().body(buildResponse(400, "删除失败，帖子不存在", null));
      }
    } catch (Exception e) {
      logger.error("删除帖子失败", e);
      return ResponseEntity.internalServerError().body(buildResponse(500, "删除失败: " + e.getMessage(), null));
    }
  }

  /**
   * 获取帖子详情
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getPostDetail(@PathVariable Long id) {
    try {
      Post post = postService.getPostById(id);
      if (post != null) {
        // 增加浏览量
        postService.incrementViewCount(id);
        return ResponseEntity.ok(buildResponse(200, "获取成功", post));
      } else {
        return ResponseEntity.badRequest().body(buildResponse(400, "帖子不存在", null));
      }
    } catch (Exception e) {
      logger.error("获取帖子详情失败", e);
      return ResponseEntity.internalServerError().body(buildResponse(500, "获取失败: " + e.getMessage(), null));
    }
  }

  /**
   * 获取帖子列表
   */
  @GetMapping
  public ResponseEntity<?> getPostList(
      @RequestParam(required = false) Long categoryId,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    try {
      Page<Post> postPage = postService.getPostList(categoryId, page, size);

      Map<String, Object> result = new HashMap<>();
      result.put("records", postPage.getRecords());
      result.put("total", postPage.getTotal());
      result.put("pages", postPage.getPages());
      result.put("current", postPage.getCurrent());
      result.put("size", postPage.getSize());

      return ResponseEntity.ok(buildResponse(200, "获取成功", result));
    } catch (Exception e) {
      logger.error("获取帖子列表失败", e);
      return ResponseEntity.internalServerError().body(buildResponse(500, "获取失败: " + e.getMessage(), null));
    }
  }

  /**
   * 获取热门帖子
   */
  @GetMapping("/hot")
  public ResponseEntity<?> getHotPosts(@RequestParam(defaultValue = "5") Integer limit) {
    try {
      List<Post> hotPosts = postService.getHotPosts(limit);
      return ResponseEntity.ok(buildResponse(200, "获取成功", hotPosts));
    } catch (Exception e) {
      logger.error("获取热门帖子失败", e);
      return ResponseEntity.internalServerError().body(buildResponse(500, "获取失败: " + e.getMessage(), null));
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