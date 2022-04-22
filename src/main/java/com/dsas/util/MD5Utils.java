package com.dsas.util;

import com.dsas.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// MD5工具
// 通过hash算法将一个字符串转换为另一种，无法逆转。
public class MD5Utils {
  public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    // 使用tomcat下的base64.encodeBase64
    // 加salt,使得加密的密码难以破解。
    return Base64.encodeBase64String(md5.digest((strValue + Constant.SALT).getBytes()));
  }


  public static void main(String[] args) {
    String md5 = null;
    try {
      md5 = getMD5Str("12345");

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    System.out.println(md5);
  }
}
