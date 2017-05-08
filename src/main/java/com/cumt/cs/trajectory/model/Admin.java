package com.cumt.cs.trajectory.model;


/**
 * Created by fangming.yi on 2017/5/8.
 */
public class Admin {
    private int id;
    private String userId;

    public Admin(){

    }

    public Admin(int id, String userId) {
        this.id = id;
        this.userId = userId;
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
}
