package com.grad.universityforum.service.impl;

import com.grad.universityforum.model.Post;
import com.grad.universityforum.service.PostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PostServiceImpl implements PostService {

  // 模拟数据库，实际应使用数据库
  private final ConcurrentHashMap<Long, Post> postMap = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Post getPostById(Long id) {
    return postMap.get(id);
  }

  @Override
  public List<Post> getAllPosts() {
    return new ArrayList<>(postMap.values());
  }

  @Override
  public Post createPost(Post post) {
    post.setId(idGenerator.getAndIncrement());
    post.setCreateTime(new Date());
    post.setLikeCount(0);
    post.setCommentCount(0);
    postMap.put(post.getId(), post);
    return post;
  }

  @Override
  public Post updatePost(Post post) {
    Post existingPost = postMap.get(post.getId());
    if (existingPost != null) {
      post.setCreateTime(existingPost.getCreateTime());
      post.setLikeCount(existingPost.getLikeCount());
      post.setCommentCount(existingPost.getCommentCount());
      postMap.put(post.getId(), post);
      return post;
    }
    return null;
  }

  @Override
  public void deletePost(Long id) {
    postMap.remove(id);
  }

  @Override
  public Post likePost(Long id) {
    Post post = postMap.get(id);
    if (post != null) {
      post.setLikeCount(post.getLikeCount() + 1);
      return post;
    }
    return null;
  }
}