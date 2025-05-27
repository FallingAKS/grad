package com.grad.universityforum.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grad.universityforum.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testCreateAndGetPost() throws Exception {
    // 创建帖子
    Post post = new Post();
    post.setTitle("集成测试帖子");
    post.setContent("这是一个集成测试的帖子内容");
    post.setCategoryId(1L);
    post.setAuthorId(1L);
    post.setAuthorName("测试用户");

    // 发送创建请求
    MvcResult createResult = mockMvc.perform(post("/api/forum/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data").isNumber())
        .andReturn();

    // 从响应中获取帖子ID
    String responseContent = createResult.getResponse().getContentAsString();
    Long postId = objectMapper.readTree(responseContent).path("data").asLong();
    assertNotNull(postId);

    // 获取帖子详情
    mockMvc.perform(get("/api/forum/posts/" + postId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data.id").value(postId))
        .andExpect(jsonPath("$.data.title").value("集成测试帖子"))
        .andExpect(jsonPath("$.data.content").value("这是一个集成测试的帖子内容"));
  }

  @Test
  public void testUpdatePost() throws Exception {
    // 创建帖子
    Post post = new Post();
    post.setTitle("原始标题");
    post.setContent("原始内容");
    post.setCategoryId(1L);
    post.setAuthorId(1L);
    post.setAuthorName("测试用户");

    // 发送创建请求
    MvcResult createResult = mockMvc.perform(post("/api/forum/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andReturn();

    // 从响应中获取帖子ID
    String responseContent = createResult.getResponse().getContentAsString();
    Long postId = objectMapper.readTree(responseContent).path("data").asLong();

    // 更新帖子
    post.setTitle("更新后的标题");
    post.setContent("更新后的内容");

    // 发送更新请求
    mockMvc.perform(put("/api/forum/posts/" + postId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data").value(true));

    // 验证更新结果
    mockMvc.perform(get("/api/forum/posts/" + postId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").value("更新后的标题"))
        .andExpect(jsonPath("$.data.content").value("更新后的内容"));
  }

  @Test
  public void testDeletePost() throws Exception {
    // 创建帖子
    Post post = new Post();
    post.setTitle("待删除的帖子");
    post.setContent("这个帖子将被删除");
    post.setCategoryId(1L);
    post.setAuthorId(1L);
    post.setAuthorName("测试用户");

    // 发送创建请求
    MvcResult createResult = mockMvc.perform(post("/api/forum/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andReturn();

    // 从响应中获取帖子ID
    String responseContent = createResult.getResponse().getContentAsString();
    Long postId = objectMapper.readTree(responseContent).path("data").asLong();

    // 删除帖子
    mockMvc.perform(delete("/api/forum/posts/" + postId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data").value(true));

    // 验证删除结果
    mockMvc.perform(get("/api/forum/posts/" + postId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(500))
        .andExpect(jsonPath("$.message").value("帖子不存在"));
  }

  @Test
  public void testGetPostList() throws Exception {
    // 创建多个测试帖子
    for (int i = 0; i < 3; i++) {
      Post post = new Post();
      post.setTitle("测试帖子" + i);
      post.setContent("测试内容" + i);
      post.setCategoryId(1L);
      post.setAuthorId(1L);
      post.setAuthorName("测试用户");

      mockMvc.perform(post("/api/forum/posts")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(post)))
          .andExpect(status().isOk());
    }

    // 获取帖子列表
    mockMvc.perform(get("/api/forum/posts")
        .param("pageNum", "1")
        .param("pageSize", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data.records", hasSize(greaterThanOrEqualTo(3))))
        .andExpect(jsonPath("$.data.total").value(greaterThanOrEqualTo(3)));
  }

  @Test
  public void testLikePost() throws Exception {
    // 创建帖子
    Post post = new Post();
    post.setTitle("点赞测试帖子");
    post.setContent("这个帖子用于测试点赞功能");
    post.setCategoryId(1L);
    post.setAuthorId(1L);
    post.setAuthorName("测试用户");

    // 发送创建请求
    MvcResult createResult = mockMvc.perform(post("/api/forum/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(post)))
        .andExpect(status().isOk())
        .andReturn();

    // 从响应中获取帖子ID
    String responseContent = createResult.getResponse().getContentAsString();
    Long postId = objectMapper.readTree(responseContent).path("data").asLong();

    // 点赞帖子
    mockMvc.perform(post("/api/forum/posts/" + postId + "/like"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data.likeCount").value(1));

    // 再次点赞
    mockMvc.perform(post("/api/forum/posts/" + postId + "/like"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.data.likeCount").value(2));
  }
}