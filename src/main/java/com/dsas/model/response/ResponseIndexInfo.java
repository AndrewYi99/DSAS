package com.dsas.model.response;


import java.io.Serializable;
import java.util.List;

//首页信息响应对象
public class ResponseIndexInfo implements Serializable {
    private Integer totalUsers;
    private Integer totalEvaluations;
    private Integer totalFoods;
    private Integer currentUsers;
    private List<Integer> dailyRequest;
    private List<Integer> dailyEvaluation;


    public ResponseIndexInfo() {
    }

    public ResponseIndexInfo(Integer totalUsers, Integer totalEvaluations, Integer totalFoods, Integer currentUsers) {
        this.totalUsers = totalUsers;
        this.totalEvaluations = totalEvaluations;
        this.totalFoods = totalFoods;
        this.currentUsers = currentUsers;
    }

    public ResponseIndexInfo(Integer totalUsers, Integer totalEvaluations, Integer totalFoods, Integer currentUsers, List<Integer> dailyRequest, List<Integer> dailyEvaluation) {
        this.totalUsers = totalUsers;
        this.totalEvaluations = totalEvaluations;
        this.totalFoods = totalFoods;
        this.currentUsers = currentUsers;
        this.dailyRequest = dailyRequest;
        this.dailyEvaluation = dailyEvaluation;
    }

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Integer getTotalEvaluations() {
        return totalEvaluations;
    }

    public void setTotalEvaluations(Integer totalEvaluations) {
        this.totalEvaluations = totalEvaluations;
    }

    public Integer getTotalFoods() {
        return totalFoods;
    }

    public void setTotalFoods(Integer totalFoods) {
        this.totalFoods = totalFoods;
    }

    public Integer getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(Integer currentUsers) {
        this.currentUsers = currentUsers;
    }

    public List<Integer> getDailyRequest() {
        return dailyRequest;
    }

    public void setDailyRequest(List<Integer> dailyRequest) {
        this.dailyRequest = dailyRequest;
    }

    public List<Integer> getDailyEvaluation() {
        return dailyEvaluation;
    }

    public void setDailyEvaluation(List<Integer> dailyEvaluation) {
        this.dailyEvaluation = dailyEvaluation;
    }

    @Override
    public String toString() {
        return "ResponseIndexInfo{" +
                "totalUsers=" + totalUsers +
                ", totalEvaluations=" + totalEvaluations +
                ", totalFoods=" + totalFoods +
                ", currentUsers=" + currentUsers +
                ", dailyRequest=" + dailyRequest +
                ", dailyEvaluation=" + dailyEvaluation +
                '}';
    }
}
