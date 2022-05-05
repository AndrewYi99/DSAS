package com.dsas.controller;

import com.dsas.annotation.OperationLogAnnotation;
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
import org.springframework.transaction.annotation.Transactional;
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
                                    @RequestParam(value = "limit", defaultValue = "5") Integer pageSize,
                                    @RequestParam(name = "keyword",required = false,defaultValue = "") String keyword
    ){
        PageInfo pageInfo  = foodService.selectALlFood(pageNum, pageSize,keyword);
        return CommonResult.success(pageInfo);
    }


    @GetMapping("/admin/deleteFood/{id}")
    @ResponseBody
    @OperationLogAnnotation(operationModel = "菜品模块", operationType = "删除", operationDesc = "根据id删除指定菜品")
    public CommonResult delFood(@PathVariable("id") String id){
        Integer count = foodService.DelFoodById(Integer.valueOf(id));
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.DELETE_FAILED);
        }
        return CommonResult.success();
    }

    @GetMapping("/admin/changeFood/{id}")
    @ResponseBody
    @OperationLogAnnotation(operationModel = "菜品模块", operationType = "更新", operationDesc = "根据id修改菜品状态")
    public CommonResult changeFoodState(@PathVariable("id") String id){
        Integer count = foodService.changeFoodState(Integer.valueOf(id));
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.UPDATE_FAILED);
        }
        return CommonResult.success();
    }
    /**
     * 批量对用户列表进行操作
     * @param ids
     * @return
     */
    @PostMapping("/admin/changFoodsByIds")
    @ResponseBody
    @OperationLogAnnotation(operationModel = "菜品模块", operationType = "批量修改", operationDesc = "根据id数组修改菜品状态")
    public CommonResult ChangeFoodsByIds(String ids,String type){
        int result= 0;
        if (type.equals("del")){
            int delSum = 0;
            String[] split = ids.split(",");
            for (int i = 0;i<split.length;i++){
                int count = 0;
                int id = Integer.parseInt(split[i]);
                count = foodService.DelFoodById(id);
                if (count !=0){
                    delSum++;
                }
            }
            result = delSum;
        }else if (type.equals("stop")){
            int stopSum = 0;
            String[] split = ids.split(",");
            for (int i = 0;i<split.length;i++){
                int count = 0;
                int id = Integer.parseInt(split[i]);
                count = foodService.changeFoodState(id);
                if (count !=0){
                    stopSum++;
                }
            }
            result = stopSum;
        }else if (type.equals("start")){
            int startSum = 0;
            String[] split = ids.split(",");
            for (int i = 0;i<split.length;i++){
                int count = 0;
                int id = Integer.parseInt(split[i]);
                count = foodService.changeFoodState(id);
                if (count !=0){
                    startSum++;
                }
            }
            result = startSum;
        }
        return CommonResult.success(result);
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
    @Transactional(rollbackFor = Exception.class)
    @OperationLogAnnotation(operationModel = "菜品模块", operationType = "更新", operationDesc = "修改菜品信息")
    public CommonResult updateFoodInfo(@RequestBody Food food){
        Integer count = foodService.updateFood(food);
        if (count==0){
            return CommonResult.error(DSASExceptionEnum.UPDATE_FAILED);
        }
        return CommonResult.success();
    }
}
