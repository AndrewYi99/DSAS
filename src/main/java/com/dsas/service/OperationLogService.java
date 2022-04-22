package com.dsas.service;

import com.dsas.model.pojo.OperationLog;
import com.dsas.service.impl.OperationLogServiceImp;

import java.util.List;

public interface OperationLogService {

  /**
   * 查询当前管理员的所有日志记录
   *
   * @param userId
   * @return 操作记录list
   */
  public List<OperationLog> findAllOperationLog(Integer userId);
}
