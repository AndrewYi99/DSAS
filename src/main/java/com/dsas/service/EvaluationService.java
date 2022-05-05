package com.dsas.service;

import com.dsas.model.pojo.Evaluation;
import com.dsas.model.request.CommEvaluation;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import javax.crypto.Cipher;
import java.util.List;
import java.util.Map;

public interface EvaluationService {
    Integer DelEvaluationById(Integer id);

    /**
     * 分页查询评论数据
     * @param pageNum 起始页
     * @param pageSize 页大小
     * @param keyword 搜索关键字
     * @return
     */
    PageInfo selectAllEvaluation(Integer pageNum, Integer pageSize,String keyword);

    Integer changeEvaluationState(Integer valueOf);

    Map selectEvaStatus();

    List<Evaluation> selectRecentEvaluation();

    Map selectMapEvaInfo();

    Map selectMapFoodEva();
    List<Evaluation> selectAllEvaluationAvg();

    /**
     * 向数据库插入评论信息
     * @param commEvaluation
     */
    Integer insertEvaluation(CommEvaluation commEvaluation);

    /**
     * 评论为菜品推荐时，需要判断当前菜品是否存在
     * @param commEvaluation
     * @return
     */
    Integer createRecommendFoodEva(CommEvaluation commEvaluation);

    /**
     * 插入其他评论
     * @param evaluation
     * @return
     */
    Integer insertOtherEvaluation(Evaluation evaluation);
}
