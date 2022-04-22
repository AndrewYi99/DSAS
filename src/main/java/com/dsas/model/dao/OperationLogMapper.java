package com.dsas.model.dao;

import com.dsas.model.pojo.OperationLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationLogMapper {
    List<OperationLog> selectByUserId(@Param("userId") Integer userId);
    void save(OperationLog operationLog);
}
