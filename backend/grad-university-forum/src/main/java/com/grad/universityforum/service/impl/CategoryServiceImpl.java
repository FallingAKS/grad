package com.grad.universityforum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.grad.universityforum.mapper.CategoryMapper;
import com.grad.universityforum.model.Category;
import com.grad.universityforum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryMapper categoryMapper;

  @Override
  @Transactional
  public Long createCategory(Category category) {
    category.setStatus(0); // 默认启用
    categoryMapper.insert(category);
    return category.getId();
  }

  @Override
  @Transactional
  public boolean updateCategory(Category category) {
    return categoryMapper.updateById(category) > 0;
  }

  @Override
  @Transactional
  public boolean deleteCategory(Long id) {
    return categoryMapper.deleteById(id) > 0;
  }

  @Override
  public Category getCategoryById(Long id) {
    return categoryMapper.selectById(id);
  }

  @Override
  public List<Category> getAllEnabledCategories() {
    return categoryMapper.selectAllEnabled();
  }
}