package com.grad.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.forum.mapper.CategoryMapper;
import com.grad.forum.mapper.PostMapper;
import com.grad.forum.model.Category;
import com.grad.forum.model.Post;
import com.grad.forum.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子服务实现
 */
@Service
public class PostServiceImpl implements PostService {

  private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

  private final PostMapper postMapper;
  private final CategoryMapper categoryMapper;

  public PostServiceImpl(PostMapper postMapper, CategoryMapper categoryMapper) {
    this.postMapper = postMapper;
    this.categoryMapper = categoryMapper;
  }

  @Override
  @Transactional
  public Long createPost(Post post) {
    // 设置初始值
    post.setCreateTime(LocalDateTime.now());
    post.setUpdateTime(LocalDateTime.now());
    post.setViewCount(0);
    post.setReplyCount(0);
    post.setLikeCount(0);
    post.setStatus(0); // 正常状态

    // 获取板块名称
    if (post.getCategoryId() != null) {
      Category category = categoryMapper.selectById(post.getCategoryId());
      if (category != null) {
        post.setCategoryName(category.getName());
      }
    }

    // 插入数据库
    postMapper.insert(post);
    logger.info("创建帖子成功: {}", post.getTitle());
    return post.getId();
  }

  @Override
  @Transactional
  public boolean updatePost(Post post) {
    // 设置更新时间
    post.setUpdateTime(LocalDateTime.now());

    // 获取板块名称
    if (post.getCategoryId() != null) {
      Category category = categoryMapper.selectById(post.getCategoryId());
      if (category != null) {
        post.setCategoryName(category.getName());
      }
    }

    // 更新数据库
    int result = postMapper.updateById(post);
    return result > 0;
  }

  @Override
  @Transactional
  public boolean deletePost(Long id) {
    int result = postMapper.deleteById(id);
    return result > 0;
  }

  @Override
  public Post getPostById(Long id) {
    return postMapper.selectById(id);
  }

  @Override
  public Page<Post> getPostList(Long categoryId, int page, int size) {
    Page<Post> pageParam = new Page<>(page, size);
    LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();

    // 如果指定了板块ID，则按板块筛选
    if (categoryId != null && categoryId > 0) {
      queryWrapper.eq(Post::getCategoryId, categoryId);
    }

    // 只查询正常状态的帖子
    queryWrapper.ne(Post::getStatus, 3); // 不等于禁用状态

    // 按置顶和创建时间排序
    queryWrapper.orderByDesc(Post::getStatus) // 置顶的排在前面
        .orderByDesc(Post::getCreateTime);

    return postMapper.selectPage(pageParam, queryWrapper);
  }

  @Override
  @Transactional
  public boolean incrementViewCount(Long id) {
    Post post = postMapper.selectById(id);
    if (post != null) {
      post.setViewCount(post.getViewCount() + 1);
      return postMapper.updateById(post) > 0;
    }
    return false;
  }

  @Override
  public List<Post> getHotPosts(int limit) {
    LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.ne(Post::getStatus, 3) // 不等于禁用状态
        .orderByDesc(Post::getViewCount)
        .orderByDesc(Post::getReplyCount)
        .last("LIMIT " + limit);
    return postMapper.selectList(queryWrapper);
  }
}