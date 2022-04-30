package com.dsas.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价实体类
 */
public class Evaluation implements Serializable {
  private Integer evaluationId;
  private Integer userId;
  private String userName;
  private Integer foodId;
  private String foodName;
  private Integer evaluationCategory;
  private String content;
  private Integer likes;
  private String state;
  private String disableReason;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateTime;
  private String photo;
  private String answer;
  private Double avgScore;
  private Integer totalCommentUser;


  public Integer getEvaluationId() {
    return evaluationId;
  }

  public void setEvaluationId(Integer evaluationId) {
    this.evaluationId = evaluationId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getFoodId() {
    return foodId;
  }

  public void setFoodId(Integer foodId) {
    this.foodId = foodId;
  }

  public Integer getEvaluationCategory() {
    return evaluationCategory;
  }

  public void setEvaluationCategory(Integer evaluationCategory) {
    this.evaluationCategory = evaluationCategory;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getLikes() {
    return likes;
  }

  public void setLikes(Integer likes) {
    this.likes = likes;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getDisableReason() {
    return disableReason;
  }

  public void setDisableReason(String disableReason) {
    this.disableReason = disableReason;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFoodName() {
    return foodName;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  public Double getAvgScore() {
    return avgScore;
  }

  public void setAvgScore(Double avgScore) {
    this.avgScore = avgScore;
  }

  public Integer getTotalCommentUser() {
    return totalCommentUser;
  }

  public void setTotalCommentUser(Integer totalCommentUser) {
    this.totalCommentUser = totalCommentUser;
  }


  @Override
  public String toString() {
    return "Evaluation{" +
            "evaluationId=" + evaluationId +
            ", userId=" + userId +
            ", userName='" + userName + '\'' +
            ", foodId=" + foodId +
            ", foodName='" + foodName + '\'' +
            ", evaluationCategory=" + evaluationCategory +
            ", content='" + content + '\'' +
            ", likes=" + likes +
            ", state='" + state + '\'' +
            ", disableReason='" + disableReason + '\'' +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", photo='" + photo + '\'' +
            ", answer='" + answer + '\'' +
            ", avgScore=" + avgScore +
            ", totalCommentUser=" + totalCommentUser +
            '}';
  }
}

