package com.dsas.service.impl;

import com.dsas.common.CommonResult;
import com.dsas.exception.DSASException;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.dao.FoodMapper;
import com.dsas.model.pojo.Evaluation;
import com.dsas.model.pojo.Food;
import com.dsas.service.FoodService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FoodServiceImpl implements FoodService {
    @Resource
    FoodMapper foodMapper;
    /**
     * 分页查询菜品
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo selectALlFood(Integer pageNum, Integer pageSize,String keyword) {
        PageHelper.startPage(pageNum,pageSize);
        if (keyword != null){
            List<Food> foods = foodMapper.selectFoodsByKeyword(keyword);
            //生成分页对象
            //将查询的结果存储到pageInfo中
            PageInfo pageInfo = new PageInfo(foods);
            return pageInfo;
        }
        List<Food> foods = foodMapper.selectALlFoods();
        //生成分页对象
        //将查询的结果存储到pageInfo中
        PageInfo pageInfo = new PageInfo(foods);
        //将查询的结果存储到pageInfo中
        return pageInfo;
    }

    /**
     * 根据菜品id删除菜品
     *
     * @param id
     * @return
     */
    @Override
    public Integer DelFoodById(Integer id) {
        Integer count = foodMapper.delFoodById(id);
        return count;
    }

    /**
     * 改变菜品状态
     *
     * @param id
     * @return
     */
    @Override
    public Integer changeFoodState(Integer id) {
        Food foodOld = foodMapper.selectByPrimaryKey(id);
        Integer status = foodOld.getStatus();
        int count = 0;
        if (status == 0 ){
            foodOld.setStatus(1);
        }else if (status == 1){
            foodOld.setStatus(0);
        }
        count = foodMapper.updateByPrimaryKeySelective(foodOld);
        return count;
    }

    @Override
    public Map selectFoodStatus() {
        Map<String,Integer> map = new HashMap();
        List<Food> foods = foodMapper.selectALlFoods();
        int[] arr = {0,0,0};
        for (Food food : foods){
            if (food.getIsRecommend()==1){
                arr[0]+=1;
            }
            if (food.getIsTodayFood() == 1){
                arr[1]+=1;
            }
            if (food.getStatus()==1){
                arr[2]+=1;
            }
        }
        map.put("推荐菜品",arr[0]);
        map.put("是否为今日菜品",arr[1]);
        map.put("启用菜品",arr[2]);
        return map;
    }

    /**
     * 更新菜品信息
     *
     * @param food
     */
    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事物
    public Integer updateFood(Food food) {
        Food oldFood = foodMapper.selectByPrimaryKey(food.getId());
        if (oldFood ==null){
            throw  new DSASException(DSASExceptionEnum.NO_FOOD);
        }
        if (food.getFoodName()!=null){
            oldFood.setFoodName(food.getFoodName());
        }
        if (food.getFoodDescription()!=null){
            oldFood.setFoodDescription(food.getFoodDescription());
        }
        if (food.getIsTodayFood()!=null){
            oldFood.setIsTodayFood(food.getIsTodayFood());
        }
        if (food.getIsRecommend()!=null){
            oldFood.setIsRecommend(food.getIsRecommend());
        }
        oldFood.setUpdateTime(new Date());
        int count = foodMapper.updateByPrimaryKeySelective(oldFood);
        return count;
    }
}
