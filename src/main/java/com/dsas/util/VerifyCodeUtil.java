package com.dsas.util;

import javax.servlet.http.HttpServletRequest;

public class VerifyCodeUtil {
    //获取用户输入的验证码
    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = (String) request.getSession().getAttribute("vc");
            if(result != null) {
                //删除头尾空白字符
                result = result.trim();
            }
            if(result == "") {
                result = null;
            }
            return result;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 验证码校验
     * @param request
     * @return boolean
     */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        //获取生成的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码
        String validateCode = VerifyCodeUtil.getString(request, "vc");
        if(validateCode == null || !validateCode.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
