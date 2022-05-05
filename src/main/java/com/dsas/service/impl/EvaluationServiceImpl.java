package com.dsas.service.impl;

import com.dsas.model.dao.EvaluationMapper;
import com.dsas.model.dao.FoodMapper;
import com.dsas.model.pojo.Evaluation;
import com.dsas.model.pojo.Food;
import com.dsas.model.request.CommEvaluation;
import com.dsas.service.EvaluationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    EvaluationMapper evaluationMapper;
    @Resource
    FoodMapper foodMapper;

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
    public PageInfo selectAllEvaluation(Integer pageNum, Integer pageSize,String keyword) {
        //执行分页操作
        PageHelper.startPage(pageNum,pageSize);
        //模糊查询分页结果
        if (keyword !=null){
            List<Evaluation> evaluations = evaluationMapper.selectAllEvaluationsByKeyword(keyword);
            PageInfo pageInfo = new PageInfo(evaluations);
            //将查询的结果存储到pageInfo中
            return pageInfo;
        }
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
     * 查询评论启用状态
     * @return
     */
    @Override
    public Map selectEvaStatus(){
        Map<String,Integer> res = new HashMap();
        List<Evaluation> evas = evaluationMapper.selectAllEvaluations();
        int[] count = {0,0};

        for(Evaluation evaluation:evas){
            if (Integer.valueOf(evaluation.getState())==1){
                count[0] +=1;
            }else if (Integer.valueOf(evaluation.getState())==0){
                count[1] +=1;
            }
        }
        res.put("启用",count[0]);
        res.put("未启用",count[1]);
        return res;
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

    /**
     * 向数据库插入评论信息
     *
     * @param commEvaluation
     */
    @Override
    public Integer insertEvaluation(CommEvaluation commEvaluation) {
        //当用户评论的菜品为null时，会将该菜品插入
//        Food DB_food = foodMapper.selectByFoodName(commEvaluation.getFoodName());
//        if (DB_food == null){
//            DB_food.setFoodName(commEvaluation.getFoodName());
//            DB_food.setIsRecommend(1);
//            DB_food.setFoodDescription(commEvaluation.getContent());
//            DB_food.setCreateTime(new Date());
//            DB_food.setStatus(0);
//            foodMapper.createFood(DB_food);
//        }
        Food food = foodMapper.selectByFoodName(commEvaluation.getFoodName());
        Evaluation evaluation = new Evaluation();
        evaluation.setFoodName(commEvaluation.getFoodName());
        evaluation.setEvaluationCategory(commEvaluation.getEvaluationCategory());
        evaluation.setState("0");
        evaluation.setContent(commEvaluation.getContent());
        evaluation.setCreateTime(new Date());
        evaluation.setUserId(commEvaluation.getUserId());
        evaluation.setFoodId(food.getId());
        evaluation.setLikes(commEvaluation.getLikes());
        Integer count = evaluationMapper.insertSelective(evaluation);
        return count;
    }

    /**
     * 评论为菜品推荐时，需要判断当前菜品是否存在
     *
     * @param commEvaluation
     * @return
     */
    @Override
    public Integer createRecommendFoodEva(CommEvaluation commEvaluation) {
        Integer count = 0;
        if (foodMapper.selectByFoodName(commEvaluation.getFoodName()) == null){
            Food food = new Food();
            food.setFoodName(commEvaluation.getFoodName());
            food.setStatus(0);
            food.setIsRecommend(1);
            food.setCreateTime(new Date());
            food.setIsTodayFood(0);
            count = foodMapper.createFood(food);
        }
        return count;
    }

    /**
     * 插入其他评论
     *
     * @param evaluation
     * @return
     */
    @Override
    public Integer insertOtherEvaluation(Evaluation evaluation) {
        int count = evaluationMapper.insertSelective(evaluation);
        return count;
    }


}
