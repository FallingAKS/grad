package com.grad.universityforum.controller;

import com.grad.universityforum.model.Category;
import com.grad.universityforum.model.Result;
import com.grad.universityforum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping
  public Result<List<Category>> getAllCategories() {
    List<Category> categories = categoryService.getAllEnabledCategories();
    return Result.success(categories);
  }

  @GetMapping("/{id}")
  public Result<Category> getCategoryById(@PathVariable Long id) {
    Category category = categoryService.getCategoryById(id);
    if (category == null) {
      return Result.error("分类不存在");
    }
    return Result.success(category);
  }

  @PostMapping
  public Result<Long> createCategory(@RequestBody Category category) {
    if (category.getName() == null || category.getName().trim().isEmpty()) {
      return Result.error("分类名称不能为空");
    }
    Long categoryId = categoryService.createCategory(category);
    return Result.success(categoryId);
  }

  @PutMapping("/{id}")
  public Result<Boolean> updateCategory(@PathVariable Long id, @RequestBody Category category) {
    category.setId(id);
    boolean success = categoryService.updateCategory(category);
    if (!success) {
      return Result.error("分类不存在");
    }
    return Result.success(true);
  }

  @DeleteMapping("/{id}")
  public Result<Boolean> deleteCategory(@PathVariable Long id) {
    boolean success = categoryService.deleteCategory(id);
    if (!success) {
      return Result.error("分类不存在");
    }
    return Result.success(true);
  }
}