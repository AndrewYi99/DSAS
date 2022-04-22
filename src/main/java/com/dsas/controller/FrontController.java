package com.dsas.controller;

import com.dsas.common.CommonResult;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.pojo.Evaluation;
import com.dsas.service.EvaluationService;
import com.dsas.service.FoodService;
import com.dsas.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/front")
public class FrontController {

    @Resource
    UserService userService;
    @Resource
    FoodService foodService;

    @Resource
    EvaluationService evaluationService;

    /**
     * 查询前台第一个饼状图数据
     * @return
     */
    @PostMapping("/showFrontEcharts1")
    @CrossOrigin
    @ResponseBody
    public CommonResult showFrontEcharts1(){
        Map map = evaluationService.selectMapFoodEva();
        return CommonResult.success(map);
    }

    /**
     *查询第二个柱状图数据
     * @return
     */
    @PostMapping("/showFrontEcharts2")
    @CrossOrigin
    @ResponseBody
    public CommonResult showFrontEcharts2(){
        List<Evaluation> evaluations = evaluationService.selectAllEvaluationAvg();
        return CommonResult.success(evaluations);
    }

    /**
     * 查询第三个折线图
     * @return
     */
    @PostMapping("/showFrontEcharts3")
    @CrossOrigin
    @ResponseBody
    public CommonResult showFrontEcharts3(){
        Map dateCountMap = userService.selectIndexInfos();
        if (dateCountMap.isEmpty()){
            return CommonResult.error(DSASExceptionEnum.SYSTEM_ERROR);
        }
        return CommonResult.success(dateCountMap);
    }
}
