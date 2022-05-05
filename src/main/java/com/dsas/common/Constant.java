package com.dsas.common;

import org.springframework.stereotype.Component;

// 常量类
@Component
public class Constant {
  // 用于session存储当前登陆的用户
  public static final String DSAS_USER = "dsas_user";
  public static final String DSAS_ADMIN = "dsas_admin";
  public static final String SALT = "fsif2`515d?";
  public static final Integer PAGE_NUM = 1;
  public static final Integer PAGE_SIZE = 5;
}
