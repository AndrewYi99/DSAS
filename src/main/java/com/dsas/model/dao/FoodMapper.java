package com.dsas.model.dao;

import com.dsas.model.pojo.Food;
import com.dsas.model.pojo.User;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodMapper {
  Food selectByPrimaryKey(Integer id);
  //int deleteByPrimaryKey(Integer id);

  // 更新数据（选择式更新）
  int updateByPrimaryKeySelective(Food record);

  //查询用户总数量
  Integer selectCount();

  List<Food> selectALlFoods();

  Integer delFoodById(Integer id);

  Food selectByFoodName(String foodName);

  Integer createFood(Food food);
}
