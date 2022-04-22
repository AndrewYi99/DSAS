package com.dsas.service;

import com.dsas.model.pojo.Evaluation;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface EvaluationService {
    Integer DelEvaluationById(Integer id);

    PageInfo selectAllEvaluation(Integer pageNum, Integer pageSize);

    Integer changeEvaluationState(Integer valueOf);

    List<Evaluation> selectRecentEvaluation();

    Map selectMapEvaInfo();

    Map selectMapFoodEva();
    List<Evaluation> selectAllEvaluationAvg();
}
