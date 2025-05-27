package com.grad.universityforum.service;

import com.grad.universityforum.model.Category;

import java.util.List;

public interface CategoryService {

  /**
   * 创建分类
   *
   * @param category 分类信息
   * @return 创建的分类ID
   */
  Long createCategory(Category category);

  /**
   * 更新分类
   *
   * @param category 分类信息
   * @return 是否更新成功
   */
  boolean updateCategory(Category category);

  /**
   * 删除分类
   *
   * @param id 分类ID
   * @return 是否删除成功
   */
  boolean deleteCategory(Long id);

  /**
   * 获取分类详情
   *
   * @param id 分类ID
   * @return 分类信息
   */
  Category getCategoryById(Long id);

  /**
   * 获取所有启用的分类
   *
   * @return 分类列表
   */
  List<Category> getAllEnabledCategories();
}