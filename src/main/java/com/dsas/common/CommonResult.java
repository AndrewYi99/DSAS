package com.dsas.common;

import com.dsas.exception.DSASExceptionEnum;

/** 通用返回对象 */
public class CommonResult<T> {
  // 状态码
  private Integer status;
  private String msg;
  // data类型不固定所以使用泛型
  private T data;

  private static final int OK_CODE = 10000;

  private static final String OK_MSG = "SUCCESS";

  /**
   * 三参构造函数
   *
   * @param status 状态码
   * @param msg 信息
   * @param data 返回的数据
   */
  public CommonResult(Integer status, String msg, T data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }
  // 重载构造函数
  public CommonResult(Integer status, String msg) {
    this.status = status;
    this.msg = msg;
  }

  // 空参构造函数
  public CommonResult() {
    this(OK_CODE, OK_MSG);
  }

  /**
   * 常用返回对象
   *
   * @param <T>
   * @return
   */
  public static <T> CommonResult<T> success() {
    return new CommonResult<>();
  }

  // 重载方法，携带data数据
  public static <T> CommonResult<T> success(T result) {
    CommonResult<T> commonResult = new CommonResult<>();
    commonResult.setData(result);
    return commonResult;
  }

  public static <T> CommonResult<T> error(Integer code, String msg) {
    return new CommonResult<>(code, msg);
  }
  // 重载失败方法，通过异常对象来获取状态信息
  public static <T> CommonResult<T> error(DSASExceptionEnum dsasExceptionEnum) {
    return new CommonResult<>(dsasExceptionEnum.getCode(), dsasExceptionEnum.getMsg());
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "CommonResult{" + "status=" + status + ", msg='" + msg + '\'' + ", data=" + data + '}';
  }
}
