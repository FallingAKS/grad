package com.grad.contentproduction.service.impl;

import com.grad.contentproduction.model.Content;
import com.grad.contentproduction.service.ContentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContentServiceImpl implements ContentService {

  // 模拟数据库，实际应使用数据库
  private final ConcurrentHashMap<Long, Content> contentMap = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(1);

  @Override
  public Content getContentById(Long id) {
    return contentMap.get(id);
  }

  @Override
  public List<Content> getAllContents() {
    return new ArrayList<>(contentMap.values());
  }

  @Override
  public Content createContent(Content content) {
    content.setId(idGenerator.getAndIncrement());
    content.setCreateTime(new Date());
    content.setUpdateTime(new Date());
    contentMap.put(content.getId(), content);
    return content;
  }

  @Override
  public Content updateContent(Content content) {
    Content existingContent = contentMap.get(content.getId());
    if (existingContent != null) {
      content.setCreateTime(existingContent.getCreateTime());
      content.setUpdateTime(new Date());
      contentMap.put(content.getId(), content);
      return content;
    }
    return null;
  }

  @Override
  public void deleteContent(Long id) {
    contentMap.remove(id);
  }

  @Override
  public Content updateContentStatus(Long id, String status) {
    Content content = contentMap.get(id);
    if (content != null) {
      content.setStatus(status);
      content.setUpdateTime(new Date());
      return content;
    }
    return null;
  }
}