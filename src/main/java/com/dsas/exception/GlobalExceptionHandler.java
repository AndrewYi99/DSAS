package com.dsas.exception;

import com.dsas.common.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

// 处理统一异常的handler，并转换为ApiRestResponse，实现统一的返回对象。
// 用于拦截异常
@ControllerAdvice
public class GlobalExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // 系统异常
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public Object handleException(Exception e) {
    // 记录日志
    log.error("Default", e);
    return CommonResult.error(DSASExceptionEnum.SYSTEM_ERROR);
  }

  // 业务异常
  @ExceptionHandler(DSASException.class)
  @ResponseBody
  public Object handleException(DSASExceptionEnum e) {
    // 记录日志
    log.error("DSASException", e);
    return CommonResult.error(e.getCode(), e.getMsg());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public CommonResult handleMethodArgumentException(MethodArgumentNotValidException e) {
    log.error("MethodArgumentNotValidException", e);
    return handleBindingResult(e.getBindingResult());
  }

  private CommonResult handleBindingResult(BindingResult result) {
    // 把异常处理为对外暴露的提示
    List<String> list = new ArrayList<>();

    if (result.hasErrors()) {
      List<ObjectError> allErrors = result.getAllErrors();
      for (ObjectError objectError : allErrors) {
        String message = objectError.getDefaultMessage();
        list.add(message);
      }
    }
    if (list.size() == 0) {
      return CommonResult.error(DSASExceptionEnum.REQUEST_PARAM_ERROR);
    }
    return CommonResult.error(DSASExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
  }
}
