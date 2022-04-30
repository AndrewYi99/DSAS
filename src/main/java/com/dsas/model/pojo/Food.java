package com.dsas.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Food implements Serializable {
  private Integer id;
  private String foodName;
  private String foodDescription;
  private String foodImage;
  private Integer price;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:SS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateTime;
  private Integer isRecommend;
  private Integer status;
  private Integer isTodayFood;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFoodName() {
    return foodName;
  }

  public void setFoodName(String foodName) {
    this.foodName = foodName;
  }

  public String getFoodDescription() {
    return foodDescription;
  }

  public void setFoodDescription(String foodDescription) {
    this.foodDescription = foodDescription;
  }

  public String getFoodImage() {
    return foodImage;
  }

  public void setFoodImage(String foodImage) {
    this.foodImage = foodImage;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
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

  public Integer getIsRecommend() {
    return isRecommend;
  }

  public void setIsRecommend(Integer isRecommend) {
    this.isRecommend = isRecommend;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getIsTodayFood() {
    return isTodayFood;
  }

  public void setIsTodayFood(Integer isTodayFood) {
    this.isTodayFood = isTodayFood;
  }

  @Override
  public String toString() {
    return "Food{" +
            "id=" + id +
            ", foodName='" + foodName + '\'' +
            ", foodDescription='" + foodDescription + '\'' +
            ", foodImage='" + foodImage + '\'' +
            ", price=" + price +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", isRecommend=" + isRecommend +
            ", status=" + status +
            ", isTodayFood=" + isTodayFood +
            '}';
  }
}
