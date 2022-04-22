package com.dsas.service;

import com.dsas.model.pojo.Food;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

public interface FoodService {
    /**
     * 分页查询菜品
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo selectALlFood(Integer pageNum, Integer pageSize);

    /**
     * 根据菜品id删除菜品
     * @param id
     * @return
     */
    Integer DelFoodById(Integer id);

    /**
     * 改变菜品状态
     * @param id
     * @return
     */
    Integer changeFoodState(Integer id);

    Map selectFoodStatus();

    /**
     * 更新菜品信息
     * @param food
     */
    Integer updateFood(Food food);
}
