package com.grad.contentproduction.controller;

import com.grad.contentproduction.model.Content;
import com.grad.contentproduction.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ContentController {

  private final ContentService contentService;

  @Autowired
  public ContentController(ContentService contentService) {
    this.contentService = contentService;
  }

  @GetMapping
  public List<Content> getAllContents() {
    return contentService.getAllContents();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Content> getContentById(@PathVariable Long id) {
    Content content = contentService.getContentById(id);
    if (content != null) {
      return ResponseEntity.ok(content);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public Content createContent(@RequestBody Content content) {
    return contentService.createContent(content);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content content) {
    content.setId(id);
    Content updatedContent = contentService.updateContent(content);
    if (updatedContent != null) {
      return ResponseEntity.ok(updatedContent);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
    contentService.deleteContent(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<Content> updateContentStatus(@PathVariable Long id, @RequestParam String status) {
    Content updatedContent = contentService.updateContentStatus(id, status);
    if (updatedContent != null) {
      return ResponseEntity.ok(updatedContent);
    }
    return ResponseEntity.notFound().build();
  }
}