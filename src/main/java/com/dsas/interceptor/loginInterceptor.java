package com.dsas.interceptor;

import com.dsas.common.Constant;
import com.dsas.model.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class loginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // 如果session中的uid为空，就说明还没有登录，则重定向到登录页面，阻止进入其他页面。
    // false:阻止；true:放行。
    HttpSession session = request.getSession();
    // 获取当前用户
    User currentUser = (User) session.getAttribute(Constant.DSAS_USER);
    User currentAdmin = (User) session.getAttribute(Constant.DSAS_ADMIN);
    if (currentUser == null && currentAdmin == null) {
      //未登录则重定向到登陆页面
      response.sendRedirect("/admin/toLogin");
      return false;
    }
    if (currentAdmin !=null){
      //判断是否为管理员
      if (currentAdmin.getRole() >1){
        //放行
        return true;
      }else {
        return false;
      }
    }
    return true;
  }
}
