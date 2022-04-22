package com.dsas.model.dao;

import com.dsas.model.pojo.Password;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordMapper {
    void savePwd(@Param("password") Password password);

//    String getOriginPwd(String md5Str);
}
