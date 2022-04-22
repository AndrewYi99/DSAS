package com.dsas;

import com.dsas.model.dao.EvaluationMapper;
import com.dsas.model.dao.OperationLogMapper;
import com.dsas.model.dao.PasswordMapper;
import com.dsas.model.dao.UserMapper;
import com.dsas.model.pojo.Evaluation;
import com.dsas.model.pojo.OperationLog;
import com.dsas.model.pojo.Password;
import com.dsas.model.pojo.User;
import com.dsas.service.EvaluationService;
import com.dsas.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class DsasApplicationTests {

  @Resource UserMapper userMapper;
  @Resource OperationLogMapper operationLogMapper;

  @Test
  void contextLoads() {}

  @Test
  void testRegister() {
    User user = new User();
    user.setUsername("test2");
    user.setPassword("123");

    userMapper.insert(user);
  }

  @Test
  public void testSave() {
    OperationLog operationLog = new OperationLog();
    operationLog.setId(1);
    operationLog.setDescription("test");
    operationLog.setOperationTime(new Date());
    operationLog.setIp("127.0.0.1:8090");
    operationLog.setModel("test1");
    operationLog.setResult("插入成功");
    operationLog.setType("插入方法");
    operationLog.setUserId(1);

    operationLogMapper.save(operationLog);
  }

  @Test
  public void testSelectByPrimaryKey() {
    List<OperationLog> operationLog = operationLogMapper.selectByUserId(12);
    for (OperationLog op : operationLog) {
      System.out.println(op);
    }
  }

  @Test
  public void testSelectByRole() {
    List<User> users = userMapper.selectAllUsers(2);
    for (User user : users) {
      System.out.println(user);
    }
  }

  @Resource
  PasswordMapper passwordMapper;
  @Test
  public void testPasswrod() {
    Password password = new Password();
    password.setMd5Str("ffsf");
    password.setOriginPwd("1234");
    passwordMapper.savePwd(password);
  }

  @Resource UserService userService;
  @Resource
  EvaluationService evaluationService;
  @Test
  public void testPageInfo() {
    PageInfo pageInfo = userService.selectAllPageUsers(2, 5, 2);
    //PageInfo pageInfo = evaluationService.selectAllEvaluation(1,5);
    System.out.println(pageInfo.toString());
  }

  @Test
  public void testSelectByTime() throws ParseException {
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    String time = "2022-04-14";
    Date date = ft.parse(time);
    Integer integer = userMapper.selectCountByTime(date);
    System.out.println(integer);
  }

  @Test
  public void testSelectByT() {
    List<User> users = userMapper.selectAllUsers(1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for(User user:users){
      System.out.println(user.toString());
//      Date loginTime = user.getLoginTime();
//      String formatTime = sdf.format(loginTime);
//      Integer count = userMapper.selectCountByTime(loginTime);
//      System.out.println(count);
//      System.out.println(formatTime);
    }
  }

  @Test
  public void testSelectByTimeM() {
    HashMap<String, Integer> mapResult = new HashMap<>();
    //查询所有普通用户
    List<User> users = userMapper.selectAllUsers(1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (User user : users) {
      System.out.println(user.toString());
      Date loginTime = user.getLoginTime();
      String formatTime = sdf.format(loginTime);
      Integer count = userMapper.selectCountByTime(loginTime);
      mapResult.put(formatTime, count);
    }

  }

  @Resource
  EvaluationMapper evaluationMapper;
  @Test
  public void testEvaSelectAll(){
    List<Evaluation> evaluations = evaluationMapper.selectAllEvaluations();
    for (Evaluation eva:evaluations){
      System.out.println(eva);
    }
  }

  @Test
  public void testRecentEva() {
    List<Evaluation> evaluations = evaluationMapper.selectRecentEvaluation();
    for (Evaluation eva : evaluations) {
      System.out.println(eva);
    }
  }

  @Test
  public void testAllEvalationCategoryCount() {
    Map map = evaluationService.selectMapEvaInfo();
    for (Object entry : map.entrySet()) {
      System.out.println(entry);
    }
  }

  @Test
  public void testAllFoodInfo(){
    List<Evaluation> evaluations = evaluationMapper.selectAllFoodInfo();
    for (Evaluation eva:evaluations){
      System.out.println(eva);
    }
  }

  @Test
  public void testUserLogin() {
    User test124 = userService.login("test124", "12345678");
    System.out.println(test124);
  }
}
