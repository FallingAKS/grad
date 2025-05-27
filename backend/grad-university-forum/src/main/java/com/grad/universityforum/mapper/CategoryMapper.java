package com.grad.universityforum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grad.universityforum.model.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

  @Select("SELECT * FROM category WHERE status = 0 ORDER BY sort ASC")
  List<Category> selectAllEnabled();
}