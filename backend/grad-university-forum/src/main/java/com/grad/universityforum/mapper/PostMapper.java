package com.grad.universityforum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.grad.universityforum.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PostMapper extends BaseMapper<Post> {

  @Select("SELECT * FROM post WHERE category_id = #{categoryId} AND status = 0 ORDER BY create_time DESC")
  Page<Post> selectByCategory(Page<Post> page, @Param("categoryId") Long categoryId);

  @Select("SELECT * FROM post WHERE status = #{status} ORDER BY create_time DESC")
  Page<Post> selectByStatus(Page<Post> page, @Param("status") Integer status);

  @Update("UPDATE post SET view_count = view_count + 1 WHERE id = #{id}")
  int incrementViewCount(@Param("id") Long id);

  @Update("UPDATE post SET like_count = like_count + 1 WHERE id = #{id}")
  int incrementLikeCount(@Param("id") Long id);
}