package com.grad.forum.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.forum.model.Post;

import java.util.List;

/**
 * 帖子服务接口
 */
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
   * @return 帖子详情
   */
  Post getPostById(Long id);

  /**
   * 分页获取帖子列表
   * 
   * @param categoryId 板块ID
   * @param page       页码
   * @param size       每页大小
   * @return 帖子分页列表
   */
  Page<Post> getPostList(Long categoryId, int page, int size);

  /**
   * 增加帖子浏览量
   * 
   * @param id 帖子ID
   * @return 是否成功
   */
  boolean incrementViewCount(Long id);

  /**
   * 获取热门帖子
   * 
   * @param limit 数量限制
   * @return 热门帖子列表
   */
  List<Post> getHotPosts(int limit);
}