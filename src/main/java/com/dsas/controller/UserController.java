package com.dsas.controller;

import com.dsas.common.CommonResult;
import com.dsas.common.Constant;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.pojo.User;
import com.dsas.model.request.CommUserRequest;
import com.dsas.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

  @Resource UserService userService;

//  @GetMapping("/test")
//  @ResponseBody
//  public User personalPage() {
//    return userService.getUser();
//  }

  @GetMapping("/showLogin")
  public String showLogin() {
    return "/front/login";
  }

  @PostMapping("/user/register")
  @ResponseBody
  @ApiOperation("用户注册")
  public CommonResult register(@RequestBody CommUserRequest commUserRequest) {

    // 判断用户名是否为空
    //字符序列不为 null 值,并且字符序列的长度大于 0,并且不含有空白字符序列,则返回 true
    if (!StringUtils.hasText(commUserRequest.getUsername())) {
      return CommonResult.error(DSASExceptionEnum.NEED_USER_NAME);
    }
    // 判断密码是否为空
    if (!StringUtils.hasText(commUserRequest.getPassword())) {
      return CommonResult.error(DSASExceptionEnum.NEED_PASSWORD);
    }
    if (commUserRequest.getPassword().length() < 6) {
      return CommonResult.error(DSASExceptionEnum.PASSWORD_TOO_SHORT);
    }
    // 插入当前用户
    userService.register(commUserRequest);
    return CommonResult.success();
  }

  /**
   * 用户登陆
   *
   * @param username 用户名
   * @param password 密码
   * @param session 存储用户登陆状态
   * @return 返回登陆的用户信息
   */
  @PostMapping("/user/login")
  @ResponseBody
  @ApiOperation("用户登陆")
  public CommonResult login(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      HttpSession session) {
    // 判断用户名是否为空
    if (!StringUtils.hasText(username)) {
      return CommonResult.error(DSASExceptionEnum.NEED_USER_NAME);
    }
    // 判断密码是否为空
    if (!StringUtils.hasText(password)) {
      return CommonResult.error(DSASExceptionEnum.NEED_PASSWORD);
    }
    // 查询用户名和密码是否正确
    User result = userService.login(username, password);
    // 为保证安全，返回对象时，不存储密码
    result.setPassword(null);
    // 将查询到的结果存储在session中便于用户一次性登陆
    session.setAttribute(Constant.DSAS_USER, result);
    // 返回查询到的用户对象
    return CommonResult.success(result);
  }

  /**
   * 更新用户信息
   *
   * @param session 通过session获取当前登陆用户
   * @return 返回更新后的用户
   */
  @PostMapping("/user/update")
  @ResponseBody
  @ApiOperation("更新用户数据")
  public CommonResult updateInfo(
      @RequestBody @Valid CommUserRequest commUserRequest, HttpSession session) {
    User currentUser = (User) session.getAttribute(Constant.DSAS_USER);
    // 更新用户
    userService.updateInfo(commUserRequest);
    return CommonResult.success();
  }
}
