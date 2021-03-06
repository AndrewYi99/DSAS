package com.dsas.controller;

import com.dsas.annotation.OperationLogAnnotation;
import com.dsas.common.CommonResult;
import com.dsas.common.Constant;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.pojo.User;
import com.dsas.model.request.CommUserRequest;
import com.dsas.service.OperationLogService;
import com.dsas.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

//管理用户列表
@Controller
public class AdminEditController {

    @Resource
    UserService userService;
    @Resource
    OperationLogService operationLogService;

    /**
     * 编辑点击的当前用户信息
     * @param id
     * @param session
     * @return
     */
    @GetMapping("/admin/showAdminEditUser")
    public ModelAndView showAdminEditUser(@RequestParam("userId") Integer id, HttpSession session){
        //获取当前点击编辑的用户
        User user = userService.getUser(id);
        User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
        ModelAndView modelAndView = new ModelAndView("/back/editUserProfile");
        modelAndView.addObject("currentEditUser",user);
        modelAndView.addObject("currentAdmin",currentAdmin);
        return modelAndView;
    }

    /**
     * 更新管理员信息
     *
     * @param
     * @return 返回更新后的用户
     */
    @PostMapping("/admin/updateAdmin")
    @ResponseBody
    @ApiOperation("更新用户数据")
    @OperationLogAnnotation(operationModel = "管理员模块", operationType = "更新", operationDesc = "更新管理员信息")
    public CommonResult updateAdminInfo(
            @RequestBody @Valid CommUserRequest commUserRequest,HttpSession session) {
        // 更新用户
        userService.updateInfo(commUserRequest);
        session.removeAttribute(Constant.DSAS_ADMIN);
        return CommonResult.success();
    }

    /**
     * 更新用户信息
     *
     * @param
     * @return 返回更新后的用户
     */
    @PostMapping("/admin/update")
    @ResponseBody
    @ApiOperation("更新用户数据")
    @OperationLogAnnotation(operationModel = "用户模块", operationType = "更新", operationDesc = "更新用户信息")
    public CommonResult updateInfo(
            @RequestBody @Valid CommUserRequest commUserRequest) {
        // 更新用户
        userService.updateInfo(commUserRequest);
        return CommonResult.success();
    }


    /**
     * 新增用户
     * @param session
     * @return
     */
    @GetMapping("/admin/showAddUser")
    public ModelAndView showAddUser(HttpSession session){
        User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
        ModelAndView modelAndView = new ModelAndView("/back/addUsers");
        modelAndView.addObject("currentAdmin",currentAdmin);
        return modelAndView;
    }

    /**
     * 用户列表查询根据id查询当前用户
     * @param id
     * @return
     */
    @PostMapping("/user/getCurrentUserInfo")
    @ResponseBody
    public CommonResult getCurrentUserInfo(@RequestParam("id") Integer id){
        User currentUser = userService.getUser(id);
        if (currentUser !=null){
            return CommonResult.success(currentUser);
        }
        return CommonResult.error(DSASExceptionEnum.NONE_USER);
    }

}
