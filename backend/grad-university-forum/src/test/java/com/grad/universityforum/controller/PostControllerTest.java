package com.grad.universityforum.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grad.universityforum.model.Post;
import com.grad.universityforum.model.Result;
import com.grad.universityforum.service.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testGetPostList() throws Exception {
    // 准备模拟数据
    Page<Post> postPage = new Page<>();
    List<Post> posts = new ArrayList<>();
    Post post = new Post();
    post.setId(1L);
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
    posts.add(post);

    postPage.setRecords(posts);
    postPage.setTotal(1);
    postPage.setCurrent(1);
    postPage.setSize(10);

    // 设置模拟行为
    when(postService.getPostList(eq(1L), eq(1), eq(10))).thenReturn(postPage);

    // 执行请求并验证
    mockMvc.perform(get("/api/forum/posts")
        .param("categoryId", "1")
        .param("pageNum", "1")
        .param("pageSize", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data.total").value(1))
        .andExpect(jsonPath("$.data.records[0].title").value("测试帖子"));
  }

  @Test
  public void testGetPostById() throws Exception {
    // 准备模拟数据
    Post post = new Post();
    post.setId(1L);
    post.setTitle("测试帖子");
    post.setContent("测试内容");

    // 设置模拟行为
    when(postService.getPostById(1L)).thenReturn(post);
    when(postService.getPostById(2L)).thenReturn(null);

    // 执行请求并验证存在的帖子
    mockMvc.perform(get("/api/forum/posts/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data.title").value("测试帖子"));

    // 执行请求并验证不存在的帖子
    mockMvc.perform(get("/api/forum/posts/2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(500))
        .andExpect(jsonPath("$.message").value("帖子不存在"));
  }

  @Test
  public void testCreatePost() throws Exception {
    // 准备测试数据
    Post post = new Post();
    post.setTitle("新帖子");
    post.setContent("新内容");
    post.setCategoryId(1L);

    // 设置模拟行为
    when(postService.createPost(any(Post.class))).thenReturn(1L);

    // 执行请求并验证
    mockMvc.perform(post("/api/forum/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data").value(1));

    // 测试标题为空的情况
    post.setTitle("");
    mockMvc.perform(post("/api/forum/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(500))
        .andExpect(jsonPath("$.message").value("标题不能为空"));
  }

  @Test
  public void testUpdatePost() throws Exception {
    // 准备测试数据
    Post post = new Post();
    post.setTitle("更新后的标题");
    post.setContent("更新后的内容");

    // 设置模拟行为
    when(postService.updatePost(any(Post.class))).thenReturn(true);

    // 执行请求并验证
    mockMvc.perform(put("/api/forum/posts/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data").value(true));

    // 测试更新不存在的帖子
    when(postService.updatePost(any(Post.class))).thenReturn(false);
    mockMvc.perform(put("/api/forum/posts/999")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(500))
        .andExpect(jsonPath("$.message").value("帖子不存在"));
  }

  @Test
  public void testDeletePost() throws Exception {
    // 设置模拟行为
    when(postService.deletePost(1L)).thenReturn(true);
    when(postService.deletePost(999L)).thenReturn(false);

    // 执行请求并验证
    mockMvc.perform(delete("/api/forum/posts/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data").value(true));

    // 测试删除不存在的帖子
    mockMvc.perform(delete("/api/forum/posts/999"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(500))
        .andExpect(jsonPath("$.message").value("帖子不存在"));
  }

  @Test
  public void testLikePost() throws Exception {
    // 准备模拟数据
    Post post = new Post();
    post.setId(1L);
    post.setLikeCount(1);

    // 设置模拟行为
    when(postService.likePost(1L)).thenReturn(post);
    when(postService.likePost(999L)).thenReturn(null);

    // 执行请求并验证
    mockMvc.perform(post("/api/forum/posts/1/like"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data.likeCount").value(1));

    // 测试点赞不存在的帖子
    mockMvc.perform(post("/api/forum/posts/999/like"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(500))
        .andExpect(jsonPath("$.message").value("帖子不存在"));
  }
}