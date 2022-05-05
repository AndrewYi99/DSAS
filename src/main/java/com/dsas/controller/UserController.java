package com.dsas.controller;

import com.dsas.annotation.OperationLogAnnotation;
import com.dsas.common.CommonResult;
import com.dsas.common.Constant;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.pojo.User;
import com.dsas.model.request.CommUserRequest;
import com.dsas.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    if (userService.selectUserByUserName(commUserRequest.getUsername())!=null){
      return CommonResult.error(DSASExceptionEnum.NAME_EXISTED);
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
    if (result == null){
      return CommonResult.error(DSASExceptionEnum.LOGIN_FAILED);
    }
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

  /**
   * 显示用户信息列表页面
   *
   * @param session
   * @return
   */
  @GetMapping("/admin/showUsers")
  public ModelAndView showUsers(
          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
          @RequestParam(name = "keyword",required = false,defaultValue = "") String keyword,
          HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("/back/users");
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    modelAndView.addObject("currentAdmin", currentAdmin);
    // 查询所有用户未分页
    // List<User> allUsers = userService.getAllUsers(currentAdmin.getRole());
    PageInfo pageInfo = userService.selectAllPageUsers(pageNum, pageSize, currentAdmin.getRole(),keyword);
    modelAndView.addObject("pageInfo", pageInfo);
    return modelAndView;
  }

  @PostMapping("/admin/usersData")
  @ResponseBody
  public CommonResult showAllUsers(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "limit", defaultValue = "5") Integer pageSize,
                                   @RequestParam(name = "keyword",required = false,defaultValue = "") String keyword,
                                   HttpSession session){
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    PageInfo pageInfo = userService.selectAllPageUsers(pageNum, pageSize, currentAdmin.getRole(),keyword);
    return CommonResult.success(pageInfo);
  }

  /**
   * 根据指定id删除用户
   * @param id 用户id
   * @return
   */
  @GetMapping("/admin/deleteUser/{id}")
  @OperationLogAnnotation(operationModel = "用户模块",operationType = "删除",operationDesc = "删除用户")
  @ResponseBody
  public CommonResult delUserById(@PathVariable("id") String id){
    Integer count = userService.delUserById(Integer.valueOf(id));
    if (count == 0){
      return CommonResult.error(DSASExceptionEnum.DELETE_FAILED);
    }
    return CommonResult.success();
  }

  /**
   * 批量对用户列表进行操作
   * @param ids
   * @return
   */
  @PostMapping("/admin/changeUsersByIds")
  @ResponseBody
  @OperationLogAnnotation(operationModel = "用户模块",operationType = "更新",operationDesc = "根据id数组修改用户状态")
  public CommonResult ChangeUsersByIds(String ids,String type){
    int result= 0;
    if (type.equals("del")){
      int delSum = 0;
      String[] split = ids.split(",");
      for (int i = 0;i<split.length;i++){
        int count = 0;
        int id = Integer.parseInt(split[i]);
        count = userService.delUserById(id);
        if (count !=0){
          delSum++;
        }
      }
      result = delSum;
    }else if (type.equals("stop")){
      int stopSum = 0;
      String[] split = ids.split(",");
      for (int i = 0;i<split.length;i++){
        int count = 0;
        int id = Integer.parseInt(split[i]);
        count = userService.changeStatus(id);
        if (count !=0){
          stopSum++;
        }
      }
      result = stopSum;
    }else if (type.equals("start")){
      int startSum = 0;
      String[] split = ids.split(",");
      for (int i = 0;i<split.length;i++){
        int count = 0;
        int id = Integer.parseInt(split[i]);
        count = userService.changeStatus(id);
        if (count !=0){
          startSum++;
        }
      }
      result = startSum;
    }
    return CommonResult.success(result);
  }


  /**
   * 根据用户id更改指定用户的状态
   * @param id
   * @return
   */
  @GetMapping("/admin/changeUserStatus/{id}")
  @ResponseBody
  @OperationLogAnnotation(operationModel = "用户模块",operationType = "更新",operationDesc = "修改用户状态")
  public CommonResult ChangeUserStatusById(@PathVariable("id") String id){
    Integer count = userService.changeStatus(Integer.valueOf(id));
    if (count == 0){
      return CommonResult.error(DSASExceptionEnum.UPDATE_FAILED);
    }
    return CommonResult.success();
  }
}
