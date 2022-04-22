package com.dsas.service.impl;

import com.dsas.exception.DSASException;
import com.dsas.exception.DSASExceptionEnum;
import com.dsas.model.dao.EvaluationMapper;
import com.dsas.model.dao.FoodMapper;
import com.dsas.model.dao.PasswordMapper;
import com.dsas.model.dao.UserMapper;
import com.dsas.model.pojo.Password;
import com.dsas.model.pojo.User;
import com.dsas.model.request.CommUserRequest;
import com.dsas.model.response.ResponseIndexInfo;
import com.dsas.service.UserService;
import com.dsas.util.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

  @Resource UserMapper userMapper;
  @Resource PasswordMapper passwordMapper;
  @Resource
  EvaluationMapper evaluationMapper;
  @Resource
  FoodMapper foodMapper;
  /**
   * 根据主键获取user
   *
   * @return
   */
  @Override
  public User getUser(Integer id) {
    return userMapper.selectByPrimaryKey(id);
  }

  /**
   * 用户注册
   * @param commUserRequest 注册请求体
   */
  @Override
  public void register(CommUserRequest commUserRequest) {
    User user = new User();
    Password pwd = new Password();
    int count = 0;
    if (!checkUserConflict(commUserRequest)){
      user.setUsername(commUserRequest.getUsername());
      try {
        // 使用md5工具类进行加密
        String md5Str = MD5Utils.getMD5Str(commUserRequest.getPassword());
        user.setPassword(md5Str);
        // 将密码加入密码对照表中
        pwd.setOriginPwd(commUserRequest.getPassword());
        pwd.setMd5Str(md5Str);
        passwordMapper.savePwd(pwd);
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
      count = userMapper.insertSelective(user);
    }else {
      throw new DSASException(DSASExceptionEnum.CREATE_FAILED);
    }

    if (count == 0) {
      throw new DSASException(DSASExceptionEnum.INSERT_FAILED);
    }
  }

  /**
   * 普通用户登录
   * @param username
   * @param password
   * @return
   * @throws DSASException
   */
  @Override
  public User login(String username, String password) throws DSASException {
    String md5Pwd = null;
    try {
      md5Pwd = MD5Utils.getMD5Str(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    // 密码应该传入经过md5转换后的字符串
    User user = userMapper.selectLogin(username, md5Pwd);
    //更新登录时间
    if (user !=null){
      user.setLoginTime(new Date());
      userMapper.updateByPrimaryKeySelective(user);
    }
    return user;
  }

  /**
   * 用户登陆校验
   *
   * @param username 用户名
   * @param password 密码
   * @return 返回登陆的用户对象
   * @throws DSASException
   */
  @Override
  public User adminLogin(String username, String password) throws DSASException {
    String md5Password = null;
    try {
      md5Password = MD5Utils.getMD5Str(password);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    // 密码应该传入经过md5转换后的字符串
    User user = userMapper.selectAdminLogin(username, md5Password);
    //更新登录时间
    if (user !=null){
      user.setLoginTime(new Date());
      userMapper.updateByPrimaryKeySelective(user);
    }
    return user;
  }
  /**
   * 冲突校验
   *
   * @param commUserRequest
   * @return
   */
  @Override
  public boolean checkUserConflict(CommUserRequest commUserRequest) {
    String userName = commUserRequest.getUsername();
    String email = commUserRequest.getEmail();
    String nickName = commUserRequest.getNickname();
    if (userMapper.selectByName(userName) != null) {
      throw new DSASException(DSASExceptionEnum.NAME_EXISTED);
    }
    if (userMapper.selectByNickName(nickName) != null) {
      throw new DSASException(DSASExceptionEnum.NICKNAME_EXISTED);
    }
    if (userMapper.selectByEmail(email) != null) {
      throw new DSASException(DSASExceptionEnum.EMAIL_EXISTED);
    }
    // 若都通过校验，则返回false
    return false;
  }
  /**
   * 更新用户信息
   *
   * @param commUserRequest 更新用户信息请求对象
   *
   */
  @Override
  @Transactional(rollbackFor = Exception.class) // 开启事物
  public void updateInfo(CommUserRequest commUserRequest) {
    // 查询到需要更新后的用户
    User userOld = userMapper.selectByPrimaryKey(commUserRequest.getId());
    //boolean isConflict = checkUserConflict(commUserRequest);
    // 将更新的数据存入到userNew对象中
      // 更新用户名
      if (commUserRequest.getUsername() != null) {
        userOld.setUsername(commUserRequest.getUsername());
      }
      if (StringUtils.hasText(commUserRequest.getPassword())) {
        try {
          // 更新密码
          userOld.setPassword(MD5Utils.getMD5Str(commUserRequest.getPassword()));
        } catch (NoSuchAlgorithmException e) {
          throw new DSASException(DSASExceptionEnum.WRONG_PASSWORD);
        }
      }
      if (commUserRequest.getAvatar() != null) {
        // 更新头像
        userOld.setAvatar(commUserRequest.getAvatar());
      }
      if (commUserRequest.getEmail() != null) {
        // 更新邮箱
        userOld.setEmail(commUserRequest.getEmail());
      }
      if (commUserRequest.getNickname() != null) {
        // 更新昵称
        userOld.setNickname(commUserRequest.getNickname());
      }
      userOld.setUpdateTime(new Date());

    // 更新数据库
    int count = userMapper.updateByPrimaryKeySelective(userOld);
    if (count == 0) {
      throw new DSASException(DSASExceptionEnum.UPDATE_FAILED);
    }
  }

  /**
   * 根据当前权限查询所有用户
   *
   * @param role 当前管理员的权限
   * @return
   */
  @Override
  public List<User> getAllUsers(Integer role) {
    List<User> users = userMapper.selectAllUsers(role);
    return users;
  }

  /**
   * 获取首页概览信息
   * @return
   */
  @Override
  public ResponseIndexInfo selectIndexInfo() {
    ResponseIndexInfo responseIndexInfo = new ResponseIndexInfo();
    //设置总用户数量
    responseIndexInfo.setTotalUsers(userMapper.selectCount());
    //设置总评论数量
    responseIndexInfo.setTotalEvaluations(evaluationMapper.selectCount());
    //设置总菜品种类
    responseIndexInfo.setTotalFoods(foodMapper.selectCount());

    return responseIndexInfo;
  }

  /**
   * 判断是否为管理员用户
   *
   * @param user
   * @return
   */
  @Override
  public boolean checkAdminRole(User user) {
    // 2为管理员
    return user.getRole().equals(2);
  }

  /**
   * 根据密码对照表获取原密码
   *
   * @param
   * @return
   */
  //@Override
//  public String comparePwd(String md5Str) {
//    return passwordMapper.getOriginPwd(md5Str);
//  }

  /**
   * 查询分页用户
   *
   * @param pageNum  第几页
   * @param pageSize 每页记录数
   * @return 分页对象
   */
  @Override
  public PageInfo selectAllPageUsers(Integer pageNum, Integer pageSize,Integer role) {
    //执行分页操作
    PageHelper.startPage(pageNum,pageSize);
    //根据role查询user数据
    List<User> users = userMapper.selectAllUsers(role);
    //生成分页对象
    //将查询的结果存储到pageInfo中
    //当数据库返回的数据和实体类一致时，采用new PageInfo(users)
    PageInfo pageInfo = new PageInfo(users);
    //将查询的结果存储到pageInfo中
    //当查询的结果和实体类不一致时，采用下面setList方式。
    //pageInfo.setList(users);
    return pageInfo;
  }



  /**
   * 查询管理员后台图表所需信息
   *
   * @return
   */
  @Override
  public Map selectIndexInfos() {
    //使用treeMap保证插入数据的有序性
    Map<String, Integer> mapResult = new TreeMap<>();
    //查询所有普通用户
    List<User> users = userMapper.selectAllUsers(2);
    //将查询出来的日期进行格式化，只保留年月日
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //遍历所有用户，并将其存储到map中
    for(User user:users){
      Date loginTime = user.getLoginTime();
      String formatTime = sdf.format(loginTime);
      Integer count = userMapper.selectCountByTime(loginTime);
      mapResult.put(formatTime,count);
    }
    return mapResult;
  }

  /**
   * 更新用户状态
   *
   * @param userId
   *
   */
  @Override
  public Integer changeStatus(Integer userId) {
    User user = userMapper.selectByPrimaryKey(userId);
    if (user.getStatus() == 1){
      user.setStatus(0);
    }else if (user.getStatus() == 0){
      user.setStatus(1);
    }
    int count = userMapper.updateByPrimaryKeySelective(user);
    if (count == 0){
      throw new DSASException(DSASExceptionEnum.UPDATE_FAILED);
    }
    return count;
  }

  /**
   * 根据用户id删除对应用户
   *
   * @param id
   * @return
   */
  @Override
  public Integer delUserById(Integer id) {
    int count = userMapper.deleteByPrimaryKey(id);
    return count;
  }

  @Override
  public Map selectUserEchartsInfo(Integer role) {
    Map<String, Integer> Map = new HashMap<>();
    List<User> users = userMapper.selectAllUsers(role);
    int[] count = {0,0,0,0,0};

    for(User user:users){
      if (user.getRole() == 1){
        count[0] +=1;
      }else if (user.getRole() == 2){
        count[1] +=1;
      }else if (user.getRole() == 3){
        count[2] +=1;
      }
      if (user.getStatus() == 0){
        count[3]+=1;
      }else if (user.getStatus() == 1){
        count[4] +=1;
      }

    }
    Map.put("普通用户",count[0]);
    Map.put("普通管理员",count[1]);
    Map.put("超级管理员",count[2]);
    Map.put("未启用用户",count[3]);
    Map.put("启用用户",count[4]);
    return Map;
  }

}
