package com.dsas.service;

import com.dsas.exception.DSASException;
import com.dsas.model.pojo.User;
import com.dsas.model.request.CommUserRequest;
import com.dsas.model.response.ResponseIndexInfo;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 根据用户id获取当前用户
     * @param id
     * @return
     */
    User getUser(Integer id);

    /**
     * 根据用户名称获取当前用户
     * @param username
     * @return
     */
    User selectUserByUserName(String username);
    /**
     * 用户注册
     * @param commUserRequest
     */
    public void register(CommUserRequest commUserRequest);

    User login(String username, String password) throws DSASException;

    List<User> getAllUsers(Integer role);

    /**
     * 查询首页显示信息
     *
     * @return
     */
    ResponseIndexInfo selectIndexInfo();

    /**
     * 判断是否为管理员
     *
     * @param user
     * @return
     */
    boolean checkAdminRole(User user);

    User adminLogin(String username, String password) throws DSASException;

    boolean checkUserConflict(CommUserRequest commUserRequest);

    /**
     * 更新用户信息
     *
     *
     */
    void updateInfo(CommUserRequest commUserRequest);

    //String comparePwd(String md5Str);

    /**
     * 查询分页用户
     *
     * @param pageNum  第几页
     * @param pageSize 每页记录数
     * @param role     查询权限
     * @return 分页对象
     */
    PageInfo selectAllPageUsers(Integer pageNum, Integer pageSize, Integer role,String keyword);



    /**
     * 查询管理员后台图表所需信息
     * @return
     */
    public Map selectIndexInfos();

    /**
     * 更新用户状态
     * @param userId
     *
     */
    Integer changeStatus(Integer userId);

    /**
     * 根据用户id删除对应用户
     * @param id
     * @return
     */
    Integer delUserById(Integer id);

    Map selectUserEchartsInfo(Integer role);

//    Map selectUserLoginEchartsInfo(Integer role);
}
