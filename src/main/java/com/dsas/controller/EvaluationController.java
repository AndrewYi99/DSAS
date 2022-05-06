package com.dsas.controller;

import com.dsas.annotation.OperationLogAnnotation;
import com.dsas.common.CommonResult;
import com.dsas.common.Constant;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.dao.FoodMapper;
import com.dsas.model.pojo.Evaluation;
import com.dsas.model.pojo.User;
import com.dsas.model.request.CommEvaluation;
import com.dsas.service.OperationLogService;
import com.dsas.service.UserService;
import com.dsas.service.impl.EvaluationServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class EvaluationController {
    @Resource
    UserService userService;
    @Resource
    OperationLogService operationLogService;
    @Resource
    EvaluationServiceImpl evaluationService;

    @Resource
    FoodMapper foodMapper;

    @GetMapping("/admin/showEvaluations")
    public ModelAndView showEvaluations(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("/back/evaluations");
        User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
        modelAndView.addObject("currentAdmin", currentAdmin);
        return modelAndView;
    }

    @PostMapping("/admin/evaluationData")
    @ResponseBody
    public CommonResult getEvaluation(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "limit", defaultValue = "5") Integer pageSize,
                                      @RequestParam(name = "keyword",required = false,defaultValue = "") String keyword
                                      ){
        PageInfo pageInfo = evaluationService.selectAllEvaluation(pageNum,pageSize,keyword);
        return CommonResult.success(pageInfo);
    }

    @GetMapping("/admin/deleteEvaluation/{id}")
    @ResponseBody
    @OperationLogAnnotation(operationModel = "评论模块", operationType = "删除", operationDesc = "根据id删除指定评论")
    public CommonResult delEvaluation(@PathVariable("id") String id){
        Integer count = evaluationService.DelEvaluationById(Integer.valueOf(id));
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.DELETE_FAILED);
        }
        return CommonResult.success();
    }

    @GetMapping("/admin/changeEvaluation/{id}")
    @ResponseBody
    @OperationLogAnnotation(operationModel = "评论模块", operationType = "更新", operationDesc = "根据id修改评论状态")
    public CommonResult changeEvaluationState(@PathVariable("id") String id){
        Integer count = evaluationService.changeEvaluationState(Integer.valueOf(id));
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.UPDATE_FAILED);
        }
        return CommonResult.success();
    }

    @PostMapping("/admin/changEvaluationsByIds")
    @ResponseBody
    @OperationLogAnnotation(operationModel = "评论模块", operationType = "批量修改", operationDesc = "根据id数组修改评论状态")
    public CommonResult ChangeEvaluationsByIds(String ids,String type){
        int result= 0;
        if (type.equals("del")){
            int delSum = 0;
            String[] split = ids.split(",");
            for (int i = 0;i<split.length;i++){
                int count = 0;
                int id = Integer.parseInt(split[i]);
                count = evaluationService.DelEvaluationById(id);
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
                count = evaluationService.changeEvaluationState(id);
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
                count = evaluationService.changeEvaluationState(id);
                if (count !=0){
                    startSum++;
                }
            }
            result = startSum;
        }
        return CommonResult.success(result);
    }
    /**
     * 查询评论分类图的数据
     * @return
     */
    @PostMapping("/admin/EvalautionEcharts2")
    @ResponseBody
    public CommonResult showEvaluationInfo(){
        Map map = evaluationService.selectMapEvaInfo();
        return CommonResult.success(map);
    }

    /**
     * 查询第三个统计图的数据
     * @return
     */
    @PostMapping("/admin/EvalautionEcharts3")
    @ResponseBody
    public CommonResult showFoodInfo(){
        List<Evaluation> evaluations = evaluationService.selectAllEvaluationAvg();
        return CommonResult.success(evaluations);
    }

    /**
     * 查询评论启用与未启用的状态
     * @return
     */
    @PostMapping("/admin/EvaluationEcharts4")
    @ResponseBody
    public CommonResult selectEvaState(){
        Map map = evaluationService.selectEvaStatus();
        return CommonResult.success(map);
    }

    @PostMapping("/admin/EvalautionEcharts5")
    @ResponseBody
    public CommonResult getEvaCount(){
        Map map = evaluationService.selectMapFoodEva();
        return CommonResult.success(map);
    }

    @PostMapping("/evaluation/insertEva")
    @ResponseBody
    public CommonResult insertEvaluation(@RequestBody CommEvaluation commEvaluation){
        if (!StringUtils.hasText(commEvaluation.getFoodName())){
            return CommonResult.error(DSASExceptionEnum.NEED_FOOD_NAME);
        }
        if (commEvaluation.getEvaluationCategory() == 1 && foodMapper.selectByFoodName(commEvaluation.getFoodName())==null){
            return CommonResult.error(DSASExceptionEnum.NO_FOOD);
        }
        if (commEvaluation.getEvaluationCategory() == 2 && foodMapper.selectByFoodName(commEvaluation.getFoodName())==null){
            Integer recommendFoodEva = evaluationService.createRecommendFoodEva(commEvaluation);
            if (recommendFoodEva !=0){
                return CommonResult.success();
            }else {
                return CommonResult.error(DSASExceptionEnum.CREATE_FAILED);
            }
        }
        if (!StringUtils.hasText(commEvaluation.getContent())){
            return CommonResult.error(DSASExceptionEnum.NEED_CONTENT);
        }
        if (commEvaluation.getEvaluationCategory() == 1){
            if (commEvaluation.getLikes() >10 || commEvaluation.getLikes() <1){
                return CommonResult.error(DSASExceptionEnum.LIKES_ILLEGAL);
            }
        }

        Integer count = evaluationService.insertEvaluation(commEvaluation);
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.INSERT_FAILED);
        }
        return CommonResult.success();
    }

    /**
     * 插入其他建议
     * @param title 建议标题
     * @param content
     * @param userId
     * @return
     */
    @PostMapping("/evaluation/insertOtherEva")
    @ResponseBody
    public CommonResult insertOtherEva(@RequestParam("title") String title,
                                       @RequestParam("content") String content,
                                       @RequestParam("userId") String userId){
        if (!StringUtils.hasText(title)){
            return CommonResult.error(DSASExceptionEnum.NEED_TITLE);
        }
        if (!StringUtils.hasText(content)){
            return CommonResult.error(DSASExceptionEnum.NEED_CONTENT);
        }
        Evaluation evaluation = new Evaluation();
        evaluation.setFoodId(101);
        evaluation.setUserId(Integer.valueOf(userId));
        evaluation.setContent(title+": "+content);
        evaluation.setEvaluationCategory(3);
        evaluation.setState("0");
        evaluation.setCreateTime(new Date());
        Integer count = evaluationService.insertOtherEvaluation(evaluation);
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.INSERT_FAILED);
        }
        return CommonResult.success();
    }


}
