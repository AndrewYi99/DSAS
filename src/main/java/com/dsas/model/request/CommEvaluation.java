package com.dsas.model.request;

import java.io.Serializable;

public class CommEvaluation implements Serializable {
    private Integer evaluationId;
    private Integer userId;
    private String userName;
    private Integer foodId;
    private String foodName;
    private Integer evaluationCategory;
    private String content;
    private Integer likes;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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

    @Override
    public String toString() {
        return "CommEvaluation{" +
                "evaluationId=" + evaluationId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", evaluationCategory=" + evaluationCategory +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                '}';
    }
}
