package com.dsas.model.dao;

import com.dsas.model.pojo.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper {
  // 根据主键查询用户
  User selectByPrimaryKey(Integer id);
  // 根据主键删除用户
  int deleteByPrimaryKey(Integer id);
  // 插入用户记录
  int insert(User record);
  // 插入选中的数据
  int insertSelective(User record);
  // 更新数据（选择式更新）
  int updateByPrimaryKeySelective(User record);
  // 更新全部数据
  int updateByPrimaryKey(User record);
  // 根据名称查询用户记录
  User selectByName(String username);

  // 根据昵称查询用户记录
  User selectByNickName(String nickname);

  // 根据邮箱查询用户记录
  User selectByEmail(String email);

  /**
   * 普通用户登陆
   * @param username
   * @param password
   * @return
   */
  User selectLogin(@Param("username") String username, @Param("password") String password);

  /**
   * 管理员登陆
   * @param username
   * @param password
   * @return
   */
  User selectAdminLogin(@Param("username") String username, @Param("password") String password);

  List<User> selectAllUsers(@Param("role") Integer id);

  //查询用户总数量
  Integer selectCount();
  //统计某天的访问次数
  Integer selectCountByTime(@Param("loginTime") Date loginTime);

  List<User> selectByKeyWord(@Param("role")Integer role, @Param("keyword")String keyword);
}
