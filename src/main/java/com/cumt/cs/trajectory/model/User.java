package com.cumt.cs.trajectory.model;

import java.util.Date;

/**
 * Created by fangming.yi on 2017/5/8.
 */
public class User {
    private int id;
    private String userId;
    private String username;
    private int gender;
    private String phone;
    private String email;
    private int loginNum;
    private Date lastLoginTime;
    private boolean isAdmin;

    public User(){

    }

    public User(int id, String userId, String username, int gender, String phone, String email, int loginNum, Date lastLoginTime, boolean isAdmin) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.loginNum = loginNum;
        this.lastLoginTime = lastLoginTime;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
