package com.dsas.exception;
// 错误枚举类
public enum DSASExceptionEnum {
  NEED_USER_NAME(10001, "用户名不能为空"),
  NEED_PASSWORD(10002, "密码不能为空"),
  PASSWORD_TOO_SHORT(10003, "密码长度不能低于8位"),
  NAME_EXISTED(10004, "用户名重复"),
  INSERT_FAILED(10005, "插入失败，请重试"),
  WRONG_PASSWORD(10006, "密码错误"),
  NEED_LOGIN(10007, "需要登陆"),
  UPDATE_FAILED(10008, "更新失败"),
  NEED_ADMIN(10009, "需要管理员登陆"),
  PARA_NOT_NULL(10010, "参数不能为空"),
  CREATE_FAILED(10011, "创建失败"),
  REQUEST_PARAM_ERROR(10012, "请求参数错误"),
  DELETE_FAILED(100013, "删除失败"),
  MKDIR_FAILED(10014, "文件夹创建失败"),
  UPLOAD_FAILED(10015, "文件上传失败"),
  EMAIL_EXISTED(10016, "邮箱账号已经存在!"),
  NICKNAME_EXISTED(10017, "昵称已经被使用"),
  LOGIN_FAILED(10018, "登陆失败,注意检查用户名或密码是否正确"),
  WRONG_VALIDATE(10019,"验证码错误"),
  NONE_USER(10020,"当前用户未注册" ),
  NONE_ADMIN(10021,"未查询到管理员"),
  NO_FOOD(10022,"当前菜品不存在" ),
  LIKES_ILLEGAL(10023,"评分参数不合法"),
  NEED_TITLE(10034,"需要标题" ),
  NEED_CONTENT(10035,"需要内容"),
  NEED_FOOD_NAME(10036,"需要菜品名称"),
  // 20000 系统错误
  SYSTEM_ERROR(20000, "系统异常"),
  NO_ENUM(20001, "未找到对应的枚举类"),
  LOG_SAVE_FAILED(20002,"日志保存失败"),
  METHOD_RUN_FAILED(20003,"方法执行失败" );
    // 异常码
  Integer code;
  // 异常信息
  String msg;

  DSASExceptionEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
