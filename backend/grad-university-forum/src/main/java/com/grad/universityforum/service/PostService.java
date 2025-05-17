package com.grad.universityforum.service;

import com.grad.universityforum.model.Post;

import java.util.List;

public interface PostService {
  Post getPostById(Long id);

  List<Post> getAllPosts();

  Post createPost(Post post);

  Post updatePost(Post post);

  void deletePost(Long id);

  Post likePost(Long id);
}