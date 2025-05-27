package com.grad.universityforum.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.universityforum.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PostMapperTest {

  @Autowired
  private PostMapper postMapper;

  @Test
  void testInsert() {
    // 创建测试数据
    Post post = new Post();
    post.setTitle("测试帖子");
    post.setContent("测试内容");
    post.setAuthorId(1L);
    post.setAuthorName("测试用户");
    post.setCategoryId(1L);
    post.setCategoryName("测试分类");
    post.setCreateTime(LocalDateTime.now());
    post.setUpdateTime(LocalDateTime.now());
    post.setViewCount(0);
    post.setReplyCount(0);
    post.setLikeCount(0);
    post.setStatus(0);

    // 执行插入操作
    int rows = postMapper.insert(post);

    // 验证结果
    assertEquals(1, rows);
    assertNotNull(post.getId());
  }

  @Test
  void testSelectById() {
    // 创建测试数据
    Post post = new Post();
    post.setTitle("测试帖子");
    post.setContent("测试内容");
    post.setAuthorId(1L);
    post.setAuthorName("测试用户");
    post.setCategoryId(1L);
    post.setCategoryName("测试分类");
    post.setCreateTime(LocalDateTime.now());
    post.setUpdateTime(LocalDateTime.now());
    post.setViewCount(0);
    post.setReplyCount(0);
    post.setLikeCount(0);
    post.setStatus(0);

    // 先插入测试数据
    postMapper.insert(post);
    Long postId = post.getId();

    // 执行查询
    Post result = postMapper.selectById(postId);

    // 验证结果
    assertNotNull(result);
    assertEquals("测试帖子", result.getTitle());
    assertEquals("测试内容", result.getContent());
    assertEquals(1L, result.getAuthorId());
  }

  @Test
  void testUpdateById() {
    // 创建测试数据
    Post post = new Post();
    post.setTitle("原始标题");
    post.setContent("原始内容");
    post.setAuthorId(1L);
    post.setAuthorName("测试用户");
    post.setCategoryId(1L);
    post.setCategoryName("测试分类");
    post.setCreateTime(LocalDateTime.now());
    post.setUpdateTime(LocalDateTime.now());
    post.setViewCount(0);
    post.setReplyCount(0);
    post.setLikeCount(0);
    post.setStatus(0);

    // 先插入测试数据
    postMapper.insert(post);
    Long postId = post.getId();

    // 修改数据
    post.setTitle("更新后的标题");
    post.setContent("更新后的内容");
    post.setUpdateTime(LocalDateTime.now());

    // 执行更新
    int rows = postMapper.updateById(post);

    // 验证更新结果
    assertEquals(1, rows);

    // 查询更新后的数据
    Post updated = postMapper.selectById(postId);
    assertNotNull(updated);
    assertEquals("更新后的标题", updated.getTitle());
    assertEquals("更新后的内容", updated.getContent());
  }

  @Test
  void testSelectByCategory() {
    // 创建测试数据 - 分类1
    Post post1 = new Post();
    post1.setTitle("分类1的帖子");
    post1.setContent("测试内容1");
    post1.setAuthorId(1L);
    post1.setCategoryId(1L);
    post1.setCategoryName("分类1");
    post1.setCreateTime(LocalDateTime.now());
    post1.setStatus(0);
    postMapper.insert(post1);

    // 创建测试数据 - 分类2
    Post post2 = new Post();
    post2.setTitle("分类2的帖子");
    post2.setContent("测试内容2");
    post2.setAuthorId(1L);
    post2.setCategoryId(2L);
    post2.setCategoryName("分类2");
    post2.setCreateTime(LocalDateTime.now());
    post2.setStatus(0);
    postMapper.insert(post2);

    // 按分类查询
    Page<Post> page = new Page<>(1, 10);
    LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Post::getCategoryId, 1L);
    queryWrapper.eq(Post::getStatus, 0);

    Page<Post> result = postMapper.selectPage(page, queryWrapper);

    // 验证结果
    assertNotNull(result);
    assertEquals(1, result.getRecords().size());
    assertEquals("分类1的帖子", result.getRecords().get(0).getTitle());
  }

  @Test
  void testLogicalDelete() {
    // 创建测试数据
    Post post = new Post();
    post.setTitle("测试帖子");
    post.setContent("测试内容");
    post.setAuthorId(1L);
    post.setAuthorName("测试用户");
    post.setCategoryId(1L);
    post.setCategoryName("测试分类");
    post.setCreateTime(LocalDateTime.now());
    post.setUpdateTime(LocalDateTime.now());
    post.setStatus(0);

    // 插入数据
    postMapper.insert(post);
    Long postId = post.getId();

    // 查询确认存在
    Post inserted = postMapper.selectById(postId);
    assertNotNull(inserted);
    assertEquals(0, inserted.getStatus());

    // 执行逻辑删除
    inserted.setStatus(1); // 标记为删除
    int rows = postMapper.updateById(inserted);
    assertEquals(1, rows);

    // 查询已删除的帖子
    Post deleted = postMapper.selectById(postId);
    assertNotNull(deleted);
    assertEquals(1, deleted.getStatus());

    // 按正常状态查询，验证不存在
    LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Post::getId, postId);
    queryWrapper.eq(Post::getStatus, 0);

    Post notFound = postMapper.selectOne(queryWrapper);
    assertNull(notFound);
  }

  @Test
  void testSelectAll() {
    // 插入多条测试数据
    for (int i = 0; i < 3; i++) {
      Post post = new Post();
      post.setTitle("测试帖子" + i);
      post.setContent("测试内容" + i);
      post.setAuthorId(1L);
      post.setCategoryId(1L);
      post.setCreateTime(LocalDateTime.now());
      post.setStatus(0);
      postMapper.insert(post);
    }

    // 查询所有未删除的帖子
    LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(Post::getStatus, 0);

    List<Post> posts = postMapper.selectList(queryWrapper);

    // 验证结果
    assertNotNull(posts);
    assertTrue(posts.size() >= 3);
  }
}