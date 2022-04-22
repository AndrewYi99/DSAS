package com.dsas.exception;

// 统一异常
public class DSASException extends RuntimeException {
  private final Integer code;
  private final String message;

  public DSASException(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public DSASException(DSASExceptionEnum exceptionEnum) {
    this(exceptionEnum.getCode(), exceptionEnum.getMsg());
  }

  public Integer getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
