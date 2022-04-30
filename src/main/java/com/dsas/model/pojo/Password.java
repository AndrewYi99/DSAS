package com.dsas.model.pojo;

import java.io.Serializable;

//密码对照表
public class Password implements Serializable {
    private Integer id;
    private String md5Str;
    private String originPwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMd5Str() {
        return md5Str;
    }

    public void setMd5Str(String md5Str) {
        this.md5Str = md5Str;
    }

    public String getOriginPwd() {
        return originPwd;
    }

    public void setOriginPwd(String originPwd) {
        this.originPwd = originPwd;
    }

    @Override
    public String toString() {
        return "Password{" +
                "id=" + id +
                ", md5Str='" + md5Str + '\'' +
                ", originPwd='" + originPwd + '\'' +
                '}';
    }
}
