package com.dsas.service;

import com.dsas.model.pojo.OperationLog;
import com.dsas.service.impl.OperationLogServiceImp;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface OperationLogService {

  /**
   * 查询当前管理员的所有日志记录
   *
   * @param userId
   * @return 操作记录list
   */
  public PageInfo findAllOperationLog(Integer pageNum, Integer pageSize, Integer userId);

  /**
   * 查询日志图表
   * @return
   */
  public Map selectLogsEchart(Integer userId);
}
