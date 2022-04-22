package com.dsas.service.impl;

import com.dsas.model.dao.EvaluationMapper;
import com.dsas.model.pojo.Evaluation;
import com.dsas.service.EvaluationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    EvaluationMapper evaluationMapper;

    /**
     * 根据评论id删除指定评论
     * @param id
     * @return
     */
    @Override
    public Integer DelEvaluationById(Integer id){
        Integer count = evaluationMapper.delEvaluationById(id);
        return count;
    }

    /**
     * 分页查询所有评论
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo selectAllEvaluation(Integer pageNum, Integer pageSize) {
        //执行分页操作
        PageHelper.startPage(pageNum,pageSize);
        List<Evaluation> evaluations = evaluationMapper.selectAllEvaluations();
        //生成分页对象
        //将查询的结果存储到pageInfo中
        PageInfo pageInfo = new PageInfo(evaluations);
        //将查询的结果存储到pageInfo中
        return pageInfo;
    }

    /**
     * 更新评论状态
     * @param id
     */
    @Override
    public Integer changeEvaluationState(Integer id) {
        Evaluation evaluationOld = evaluationMapper.selectByPrimaryKey(id);
        String state = evaluationOld.getState();
        int count = 0;
        if (state.equals("1")){
            evaluationOld.setState("0");
        }else if (state.equals("0")){
            evaluationOld.setState("1");
        }
        count = evaluationMapper.updateByPrimaryKeySelective(evaluationOld);
        return count;
    }

    /**
     * 查询最近的10条评论
     * @return
     */
    @Override
    public List<Evaluation> selectRecentEvaluation() {
        evaluationMapper.selectRecentEvaluation();
        return null;
    }

    /**
     * 统计三种分类的评论条数
     * @return
     */
    @Override
    public Map selectMapEvaInfo(){
        Map<String, Integer> Map = new HashMap<>();
        List<Evaluation> evaluations = evaluationMapper.selectAllEvaluations();

        int[] count = {0,0,0};

        for(Evaluation evaluation:evaluations){
                if (evaluation.getEvaluationCategory() == 1){
                    count[0] +=1;
                }else if (evaluation.getEvaluationCategory() == 2){
                    count[1] +=1;
                }else if (evaluation.getEvaluationCategory() == 3){
                    count[2] +=1;
                }

        }
        Map.put("菜品建议",count[0]);
        Map.put("菜品推荐",count[1]);
        Map.put("其他建议",count[2]);
        return Map;
    }

    /**
     * 统计热门10条菜品的评论
     * @return
     */
    @Override
    public Map selectMapFoodEva() {
        Map<String,Integer> evaMap = new HashMap();
        List<Evaluation> evaluations = evaluationMapper.selectAllFoodInfo();
        for (Evaluation evaluation : evaluations) {
            evaMap.put(evaluation.getFoodName(),evaluation.getTotalCommentUser());
        }
        return evaMap;
    }

    /**
     * 查询图表三需要的数据
     * @return
     */
    @Override
    public List<Evaluation> selectAllEvaluationAvg(){
        List<Evaluation> evaluations = evaluationMapper.selectAllFoodInfo();
        return evaluations;
    }
}
