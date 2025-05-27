package com.grad.universityforum.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.universityforum.model.Post;
import com.grad.universityforum.model.Result;
import com.grad.universityforum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/forum/posts")
public class PostController {

  @Autowired
  private PostService postService;

  @GetMapping
  public Result<Page<Post>> getPostList(
      @RequestParam(required = false) Long categoryId,
      @RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<Post> posts = postService.getPostList(categoryId, pageNum, pageSize);
    return Result.success(posts);
  }

  @GetMapping("/{id}")
  public Result<Post> getPostById(@PathVariable Long id) {
    Post post = postService.getPostById(id);
    if (post == null) {
      return Result.error("帖子不存在");
    }
    return Result.success(post);
  }

  @PostMapping
  public Result<Long> createPost(@RequestBody Post post) {
    if (post.getTitle() == null || post.getTitle().trim().isEmpty()) {
      return Result.error("标题不能为空");
    }
    if (post.getContent() == null || post.getContent().trim().isEmpty()) {
      return Result.error("内容不能为空");
    }
    Long postId = postService.createPost(post);
    return Result.success(postId);
  }

  @PutMapping("/{id}")
  public Result<Boolean> updatePost(@PathVariable Long id, @RequestBody Post post) {
    post.setId(id);
    boolean success = postService.updatePost(post);
    if (!success) {
      return Result.error("帖子不存在");
    }
    return Result.success(true);
  }

  @DeleteMapping("/{id}")
  public Result<Boolean> deletePost(@PathVariable Long id) {
    boolean success = postService.deletePost(id);
    if (!success) {
      return Result.error("帖子不存在");
    }
    return Result.success(true);
  }

  @PostMapping("/{id}/like")
  public Result<Post> likePost(@PathVariable Long id) {
    Post post = postService.likePost(id);
    if (post == null) {
      return Result.error("帖子不存在");
    }
    return Result.success(post);
  }
}