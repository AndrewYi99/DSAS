package com.dsas.service.impl;

import com.dsas.model.dao.OperationLogMapper;
import com.dsas.model.pojo.OperationLog;
import com.dsas.service.OperationLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OperationLogServiceImp implements OperationLogService {
  @Resource OperationLogMapper operationLogMapper;

  /**
   * 查询当前管理员的所有日志记录
   *
   * @param userId
   * @return 操作记录list
   */
  @Override
  public List<OperationLog> findAllOperationLog(Integer userId) {
    List<OperationLog> operationLogs = operationLogMapper.selectByUserId(userId);
    return operationLogs;
  }
}
