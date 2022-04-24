package com.dsas.service.impl;

import com.dsas.model.dao.OperationLogMapper;
import com.dsas.model.pojo.OperationLog;
import com.dsas.service.OperationLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
  public PageInfo findAllOperationLog(Integer pageNum, Integer pageSize, Integer userId) {
    //执行分页操作
    PageHelper.startPage(pageNum,pageSize);
    List<OperationLog> operationLogs = operationLogMapper.selectByUserId(userId);
    PageInfo pageInfo = new PageInfo(operationLogs);
    return pageInfo;
  }

  /**
   * 查询日志图表
   *
   * @return
   */
  @Override
  public Map selectLogsEchart(Integer userId) {
    Map<String, Integer> mapResult = new TreeMap<>();
    List<OperationLog> operationLogs = operationLogMapper.selectByUserId(userId);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (OperationLog operationLog : operationLogs) {
      Date operationTime = operationLog.getOperationTime();
      String formatTime = sdf.format(operationTime);
      Integer count = operationLogMapper.selectCountByTime(operationTime);
      mapResult.put(formatTime,count);
    }
    return mapResult;
  }
}
