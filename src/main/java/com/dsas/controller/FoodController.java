package com.dsas.controller;

import com.dsas.common.CommonResult;
import com.dsas.common.Constant;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.pojo.Food;
import com.dsas.model.pojo.User;
import com.dsas.service.FoodService;
import com.dsas.service.OperationLogService;
import com.dsas.service.UserService;
import com.dsas.service.impl.EvaluationServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class FoodController {
    @Resource
    UserService userService;
    @Resource
    OperationLogService operationLogService;
    @Resource
    EvaluationServiceImpl evaluationService;
    @Resource
    FoodService foodService;

    @GetMapping("/admin/showFood")
    public ModelAndView showEvaluations(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("/back/food");
        User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
        modelAndView.addObject("currentAdmin", currentAdmin);
        return modelAndView;
    }

    @PostMapping("/admin/foodData")
    @ResponseBody
    public CommonResult getAllFoods(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "limit", defaultValue = "5") Integer pageSize
    ){
        PageInfo pageInfo  = foodService.selectALlFood(pageNum, pageSize);
        return CommonResult.success(pageInfo);
    }

    @GetMapping("/admin/deleteFood/{id}")
    @ResponseBody
    public CommonResult delFood(@PathVariable("id") String id){
        Integer count = foodService.DelFoodById(Integer.valueOf(id));
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.DELETE_FAILED);
        }
        return CommonResult.success();
    }

    @GetMapping("/admin/changeFood/{id}")
    @ResponseBody
    public CommonResult changeFoodState(@PathVariable("id") String id){
        Integer count = foodService.changeFoodState(Integer.valueOf(id));
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.UPDATE_FAILED);
        }
        return CommonResult.success();
    }

    @PostMapping("/admin/foodStatus")
    @ResponseBody
    public CommonResult showFoodStatus(){
        Map map = foodService.selectFoodStatus();
        return CommonResult.success(map);
    }

    /**
     * 更新菜品信息
     * @param food
     * @return
     */
    @PostMapping("/admin/foodUpdate")
    @ResponseBody
    public CommonResult updateFoodInfo(@RequestBody Food food){
        Integer count = foodService.updateFood(food);
        if (count==0){
            return CommonResult.error(DSASExceptionEnum.UPDATE_FAILED);
        }
        return CommonResult.success();
    }
}