package com.dsas.model.dao;

import com.dsas.model.pojo.Evaluation;
import com.dsas.model.pojo.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationMapper {

    // 根据主键查询用户
    Evaluation selectByPrimaryKey(@Param("evaluationId") Integer id);

    // 更新数据（选择式更新）
    int updateByPrimaryKeySelective(Evaluation record);

    //查询用户总数量
    Integer selectCount();

    /**
     * 查询评论单表数据
     * @return
     */
    List<Evaluation> selectAllEvaluation();

    /**
     * 查询评论表连接数据
     * @return
     */
    List<Evaluation> selectAllEvaluations();

    Integer delEvaluationById(@Param("evaluationId") Integer id);

    List<Evaluation> selectRecentEvaluation();

    List<Evaluation>selectAllFoodInfo();


}
