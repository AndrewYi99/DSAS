package com.dsas.controller;

import com.dsas.common.CommonResult;
import com.dsas.common.Constant;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.pojo.Evaluation;
import com.dsas.model.pojo.User;
import com.dsas.service.OperationLogService;
import com.dsas.service.UserService;
import com.dsas.service.impl.EvaluationServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
                                      @RequestParam(value = "limit", defaultValue = "5") Integer pageSize
                                      ){
        PageInfo pageInfo = evaluationService.selectAllEvaluation(pageNum,pageSize);
        return CommonResult.success(pageInfo);
    }

    @GetMapping("/admin/deleteEvaluation/{id}")
    @ResponseBody
    public CommonResult delEvaluation(@PathVariable("id") String id){
        Integer count = evaluationService.DelEvaluationById(Integer.valueOf(id));
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.DELETE_FAILED);
        }
        return CommonResult.success();
    }

    @GetMapping("/admin/changeEvaluation/{id}")
    @ResponseBody
    public CommonResult changeEvaluationState(@PathVariable("id") String id){
        Integer count = evaluationService.changeEvaluationState(Integer.valueOf(id));
        if (count == 0){
            return CommonResult.error(DSASExceptionEnum.UPDATE_FAILED);
        }
        return CommonResult.success();
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


}
