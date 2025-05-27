package com.grad.universityforum.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.universityforum.model.Post;

public interface PostService {
  /**
   * 创建帖子
   *
   * @param post 帖子信息
   * @return 帖子ID
   */
  Long createPost(Post post);

  /**
   * 更新帖子
   *
   * @param post 帖子信息
   * @return 是否成功
   */
  boolean updatePost(Post post);

  /**
   * 删除帖子
   *
   * @param id 帖子ID
   * @return 是否成功
   */
  boolean deletePost(Long id);

  /**
   * 获取帖子详情
   *
   * @param id 帖子ID
   * @return 帖子信息
   */
  Post getPostById(Long id);

  /**
   * 获取帖子列表
   *
   * @param categoryId 分类ID
   * @param pageNum    页码
   * @param pageSize   每页大小
   * @return 帖子列表
   */
  Page<Post> getPostList(Long categoryId, Integer pageNum, Integer pageSize);

  /**
   * 增加帖子浏览量
   *
   * @param id 帖子ID
   * @return 是否成功
   */
  boolean incrementViewCount(Long id);

  /**
   * 点赞帖子
   *
   * @param id 帖子ID
   * @return 帖子信息
   */
  Post likePost(Long id);
}