package com.grad.universityforum.controller;

import com.grad.universityforum.model.Post;
import com.grad.universityforum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum/posts")
public class PostController {

  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public List<Post> getAllPosts() {
    return postService.getAllPosts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable Long id) {
    Post post = postService.getPostById(id);
    if (post != null) {
      return ResponseEntity.ok(post);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public Post createPost(@RequestBody Post post) {
    return postService.createPost(post);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
    post.setId(id);
    Post updatedPost = postService.updatePost(post);
    if (updatedPost != null) {
      return ResponseEntity.ok(updatedPost);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/like")
  public ResponseEntity<Post> likePost(@PathVariable Long id) {
    Post post = postService.likePost(id);
    if (post != null) {
      return ResponseEntity.ok(post);
    }
    return ResponseEntity.notFound().build();
  }
}