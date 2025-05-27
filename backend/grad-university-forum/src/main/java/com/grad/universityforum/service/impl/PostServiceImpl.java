package com.grad.universityforum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.universityforum.mapper.CategoryMapper;
import com.grad.universityforum.mapper.PostMapper;
import com.grad.universityforum.model.Category;
import com.grad.universityforum.model.Post;
import com.grad.universityforum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostMapper postMapper;

  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  @Transactional
  public Long createPost(Post post) {
    // 设置初始值
    post.setCreateTime(LocalDateTime.now());
    post.setUpdateTime(LocalDateTime.now());
    post.setViewCount(0);
    post.setReplyCount(0);
    post.setLikeCount(0);
    post.setStatus(0);

    // 验证分类是否存在
    if (post.getCategoryId() != null) {
      Category category = categoryMapper.selectById(post.getCategoryId());
      if (category != null) {
        post.setCategoryName(category.getName());
      }
    }

    // 保存帖子
    postMapper.insert(post);
    return post.getId();
  }

  @Override
  @Transactional
  public boolean updatePost(Post post) {
    // 验证帖子是否存在
    Post existingPost = postMapper.selectById(post.getId());
    if (existingPost == null) {
      return false;
    }

    // 更新帖子
    post.setUpdateTime(LocalDateTime.now());
    if (post.getCategoryId() != null) {
      Category category = categoryMapper.selectById(post.getCategoryId());
      if (category != null) {
        post.setCategoryName(category.getName());
      }
    }

    return postMapper.updateById(post) > 0;
  }

  @Override
  @Transactional
  public boolean deletePost(Long id) {
    return postMapper.deleteById(id) > 0;
  }

  @Override
  public Post getPostById(Long id) {
    return postMapper.selectById(id);
  }

  @Override
  public Page<Post> getPostList(Long categoryId, Integer pageNum, Integer pageSize) {
    Page<Post> page = new Page<>(pageNum, pageSize);
    LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();

    // 按分类筛选
    if (categoryId != null) {
      queryWrapper.eq(Post::getCategoryId, categoryId);
    }

    // 按状态筛选（只显示正常状态的帖子）
    queryWrapper.eq(Post::getStatus, 0);

    // 按创建时间倒序排序
    queryWrapper.orderByDesc(Post::getCreateTime);

    return postMapper.selectPage(page, queryWrapper);
  }

  @Override
  @Transactional
  public boolean incrementViewCount(Long id) {
    Post post = postMapper.selectById(id);
    if (post == null) {
      return false;
    }

    post.setViewCount(post.getViewCount() + 1);
    return postMapper.updateById(post) > 0;
  }

  @Override
  @Transactional
  public Post likePost(Long id) {
    Post post = postMapper.selectById(id);
    if (post == null) {
      return null;
    }

    post.setLikeCount(post.getLikeCount() + 1);
    postMapper.updateById(post);
    return post;
  }
}