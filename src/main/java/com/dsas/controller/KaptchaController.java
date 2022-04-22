package com.dsas.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {

  @Resource private Producer kaptchaProducer;

  @GetMapping("/verify_code")
  public void createVerifyCode(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // 响应立即过期
    response.setDateHeader("Expires", 0);
    // 缓存控制，每次刷新都会创建新的验证码，所以必须清除浏览器缓存
    response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
    response.setHeader("Cache-Control", "post-check=0,pre-check=0");
    response.setHeader("Pragma", "no-cache");
    // 设置返回内容的类型
    response.setContentType("image/png");

    // 生成验证码字符文本
    String verifyCode = kaptchaProducer.createText();
    // 将验证码字符保存到session中
    request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, verifyCode);
    //System.out.println(request.getSession().getAttribute("verifyCode"));
    // 创建验证码图片
    BufferedImage image = kaptchaProducer.createImage(verifyCode);
    // 图片是二进制，所以使用outputStream进行输出
    ServletOutputStream outputStream = response.getOutputStream();
    // 输出图片流 imageIO图片输入输出流
    ImageIO.write(image, "png", outputStream);
    outputStream.flush();
    outputStream.close();
  }
}
