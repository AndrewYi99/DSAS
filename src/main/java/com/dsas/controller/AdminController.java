package com.dsas.controller;

import com.dsas.annotation.OperationLogAnnotation;
import com.dsas.common.CommonResult;
import com.dsas.common.Constant;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.dao.UserMapper;
import com.dsas.model.pojo.Evaluation;
import com.dsas.model.pojo.OperationLog;
import com.dsas.model.pojo.User;
import com.dsas.model.request.CommUserRequest;
import com.dsas.model.response.ResponseIndexInfo;
import com.dsas.service.EvaluationService;
import com.dsas.service.OperationLogService;
import com.dsas.service.UserService;
import com.dsas.util.VerifyCodeUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

  @Resource UserService userService;
  @Resource OperationLogService operationLogService;
  @Resource
  EvaluationService evaluationService;

  /**
   * 跳转管理员登陆页面
   *
   * @return
   */
  @GetMapping("/admin/toLogin")
  public String showAdminIndex() {
    return "/back/login";
  }

  /**
   * 管理员登陆校验
   *
   * @param username 用户名
   * @param password 密码
   * @param verifyCode 用户输入的验证码
   * @return 返回登陆的用户信息
   */
  @OperationLogAnnotation(operationModel = "管理员模块", operationType = "登陆", operationDesc = "进入后台系统")
  @PostMapping("/admin/login")
  @ApiOperation("用户登陆")
  @ResponseBody
  public CommonResult login(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      @RequestParam("verifyCode") String verifyCode,
      HttpServletRequest request) {
    // 获取session对象
    HttpSession session = request.getSession();
    session.setAttribute("vc", verifyCode);
    // 通过验证码工具类判断验证码是否正确
    if (VerifyCodeUtil.checkVerifyCode(request)) {
      // 查询用户名和密码是否正确
      User result = userService.adminLogin(username, password);
      if (result == null){
        return CommonResult.error(DSASExceptionEnum.NONE_ADMIN);
      }
      // 为保证安全，返回对象时，不存储密码
      result.setPassword(null);
      // 将查询到的结果存储在session中便于用户一次性登陆
      session.setAttribute(Constant.DSAS_ADMIN, result);
      // 返回查询到的用户对象
      return CommonResult.success(result);
    } else {
      // 返回异常信息
      return CommonResult.error(DSASExceptionEnum.WRONG_VALIDATE);
    }
  }

  /**
   * 显示管理后台首页
   *
   * @param session
   * @return
   */
  @GetMapping("/admin/showIndex")
  public ModelAndView showAdminView(HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("/back/index");
    ResponseIndexInfo responseIndexInfo = userService.selectIndexInfo();
    //查询最新10条评论
    List<Evaluation> evaluations = evaluationService.selectRecentEvaluation();
    modelAndView.addObject("recentEvaluations",evaluations);
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    modelAndView.addObject("currentAdmin", currentAdmin);
    modelAndView.addObject("responseIndexInfo",responseIndexInfo);
    return modelAndView;
  }

  /**
   * 传递管理员后台首页图表数据
   * @return
   */
  @PostMapping("/admin/showChartsInfo")
  @ResponseBody
  public CommonResult showChartsInfo(){
    Map dateCountMap = userService.selectIndexInfos();
    if (dateCountMap.isEmpty()){
      return CommonResult.error(DSASExceptionEnum.SYSTEM_ERROR);
    }
    return CommonResult.success(dateCountMap);
  }

  /**
   * 显示管理员个人信息视图
   *
   * @param session 传入session对象
   * @return modelAndView对象
   */
  @GetMapping("/admin/showAdminProfile")
  public ModelAndView showAdminProfile(HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("/back/profile");
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    // 获取当前用户的所有日志列表
    List<OperationLog> allOperationLog =
        operationLogService.findAllOperationLog(currentAdmin.getId());

    // 日志列表
    modelAndView.addObject("logList", allOperationLog);
    modelAndView.addObject("currentAdmin", currentAdmin);
    return modelAndView;
  }

  /**
   * 显示管理员信息编辑页面
   *
   * @param session
   * @return 视图对象
   */
  @GetMapping("/admin/showEditProfile")
  public ModelAndView showEditProfile(HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("/back/editProfile");
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    modelAndView.addObject("currentAdmin", currentAdmin);
    return modelAndView;
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
      HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("/back/users");
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    modelAndView.addObject("currentAdmin", currentAdmin);
    // 查询所有用户未分页
    // List<User> allUsers = userService.getAllUsers(currentAdmin.getRole());
    PageInfo pageInfo = userService.selectAllPageUsers(pageNum, pageSize, currentAdmin.getRole());
    modelAndView.addObject("pageInfo", pageInfo);
    return modelAndView;
  }

  @PostMapping("/admin/usersData")
  @ResponseBody
  public CommonResult showAllUsers(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "limit", defaultValue = "5") Integer pageSize,
                                   HttpSession session){
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    PageInfo pageInfo = userService.selectAllPageUsers(pageNum, pageSize, currentAdmin.getRole());
    return CommonResult.success(pageInfo);
  }

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

  @GetMapping("/admin/changeUserStatus/{id}")
  @ResponseBody
  public CommonResult ChangeUserStatusById(@PathVariable("id") String id){
    Integer count = userService.changeStatus(Integer.valueOf(id));
    if (count == 0){
      return CommonResult.error(DSASExceptionEnum.UPDATE_FAILED);
    }
    return CommonResult.success();
  }

  @PostMapping("/admin/UsersEcharts")
  @ResponseBody
  public CommonResult selectUserEchartsInfo(HttpSession session){
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    Map map = userService.selectUserEchartsInfo(currentAdmin.getRole());
    return CommonResult.success(map);
  }

//  @PostMapping("/admin/UsersLoginEcharts")
//  @ResponseBody
//  public CommonResult selectUserLoginEchartsInfo(HttpSession session){
//    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
//    Map map = userService.selectUserLoginEchartsInfo(currentAdmin.getRole());
//    return CommonResult.success(map);
//  }
  /**
   * 根据id查询用户
   * @param userId
   * @return
   */
  @PostMapping("/admin/selectUserById")
  @ResponseBody
  public CommonResult selectUserById(@RequestParam("id") Integer userId){
    User user = userService.getUser(userId);
    return CommonResult.success(user);
  }



  /**
   * 管理员退出登陆
   *
   * @param session
   * @return
   */
  @GetMapping("/admin/logout")
  public String AdminLogout(HttpSession session) {
    session.removeAttribute(Constant.DSAS_ADMIN);
    return "redirect:/admin/index";
  }
}
