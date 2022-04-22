package com.dsas.aop;

import com.dsas.annotation.OperationLogAnnotation;
import com.dsas.common.CommonResult;
import com.dsas.common.Constant;
import com.dsas.exception.DSASException;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.dao.OperationLogMapper;
import com.dsas.model.pojo.OperationLog;
import com.dsas.model.pojo.User;
import com.dsas.util.IPUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class OperationLogAspect {
  @Resource OperationLogMapper operationLogMapper;

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /** 设置操作日志切入点 在注解的位置切入代码 */
  @Pointcut("@annotation(com.dsas.annotation.OperationLogAnnotation)")
  public void operationLogPointCut() {}

  @Around("@annotation(operationLogAnnotation)")
  public Object operationLogRecord(
      ProceedingJoinPoint joinPoint, OperationLogAnnotation operationLogAnnotation) {
    // 获取请求
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    // 响应
    // ResponseResult<Object> responseResult = null;
    CommonResult<Object> commonResult = null;
    // 判断原方法是否正常执行的标志
    boolean isNormalProcess = false;

    // 返回切点处继续执行原方法，并接收原方法的响应
    try {
      commonResult = (CommonResult<Object>) joinPoint.proceed();
    } catch (Throwable e) {
        e.printStackTrace();
    } finally {
      // 如果顺利执行，那么说明原方法执行正常，就可以进行日志记录。因为，如果原方法的增删改出问题了，那么日志就不需要记录了，不用记录失败的操作。
      if (commonResult.getStatus() == 10001) {
        isNormalProcess = false;
        return CommonResult.error(DSASExceptionEnum.NEED_PASSWORD);
      }else if (commonResult.getStatus() == 10006){
        isNormalProcess = false;
        return CommonResult.error(DSASExceptionEnum.WRONG_PASSWORD);
      }else if (commonResult.getStatus() == 10019){
        isNormalProcess = false;
        return CommonResult.error(DSASExceptionEnum.WRONG_VALIDATE);
      } else if (commonResult.getStatus() == 10021){
        isNormalProcess = false;
        return CommonResult.error(DSASExceptionEnum.NONE_ADMIN);
      }else if (commonResult.getStatus() != 10000){
        isNormalProcess = false;
        return CommonResult.error(DSASExceptionEnum.METHOD_RUN_FAILED);
      }
      else if (commonResult.getStatus() == 10000){
        isNormalProcess = true;
      }
    }

    try {
      if (isNormalProcess) {
        // 如果原方法正常执行完毕，那么需要记录操作日志
        saveOperationLog(joinPoint, operationLogAnnotation, request);
      }
    } catch (DSASException e) {
      commonResult.setMsg(e.getMessage());
      commonResult.setStatus(e.getCode());
      return commonResult;
      // throw new DSASException(DSASExceptionEnum.LOG_SAVE_FAILED);
    }

    return commonResult;
  }

  // @AfterReturning(returning = "result", value = "operationLogPointCut()")

  public void saveOperationLog(
      JoinPoint joinPoint,
      OperationLogAnnotation operationLogAnnotation,
      HttpServletRequest request) {
    // 获取requestAttributes
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    // 从获取RequestAttributes中获取HttpServletRequest的信息
    //    HttpServletRequest request =
    //        (HttpServletRequest)
    //            requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    // 将返回值转换成map集合
    // Map<String, String> map = (Map<String, String>) result;
    OperationLog operationLog = new OperationLog();
    // 从切面织入点处通过反射机制获取织入点处的方法
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    // 获取切入点所在的方法
    Method method = signature.getMethod();
    // 获取操作

    if (operationLogAnnotation != null) {
      operationLog.setModel(operationLogAnnotation.operationModel());
      operationLog.setType(operationLogAnnotation.operationType());
      operationLog.setDescription(operationLogAnnotation.operationDesc());
    }

    operationLog.setOperationTime(Timestamp.valueOf(sdf.format(new Date())));
    /*获取当前用户*/
    User currentUser = (User) request.getSession().getAttribute(Constant.DSAS_ADMIN);
    // 操作用户
    operationLog.setUserId(currentUser.getId());
    // 操作IP
    operationLog.setIp(IPUtil.getIpAddr(request));
    // 返回值信息
    operationLog.setResult("执行" + operationLogAnnotation.operationDesc() + "功能成功!");
    // 保存日志
    operationLogMapper.save(operationLog);
  }
}
