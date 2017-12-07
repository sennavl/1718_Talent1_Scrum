package com.project.talent1.Models;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class People {
  @Id
  private long id;
  private String name;
  private long age;
  private String picture;
  private String category;
  private String email;
  private String mobileNumber;
  private long favorite;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }


  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }


  public long getFavorite() {
    return favorite;
  }

  public void setFavorite(long favorite) {
    this.favorite = favorite;
  }

}
