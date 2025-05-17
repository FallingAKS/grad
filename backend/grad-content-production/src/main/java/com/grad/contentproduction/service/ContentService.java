package com.grad.contentproduction.service;

import com.grad.contentproduction.model.Content;

import java.util.List;

public interface ContentService {
  Content getContentById(Long id);

  List<Content> getAllContents();

  Content createContent(Content content);

  Content updateContent(Content content);

  void deleteContent(Long id);

  Content updateContentStatus(Long id, String status);
}