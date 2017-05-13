package com.cumt.cs.trajectory.model;

/**
 * Created by fangming.yi on 2017/5/13.
 */
public class DataSet {
    private int id;
    private String setName;
    private int setSize;
    private int status;
    private String creatorId;
    private String createTime;

    public DataSet(){

    }

    public DataSet(int id, String setName, int setSize, int status, String creatorId, String createTime) {
        this.id = id;
        this.setName = setName;
        this.setSize = setSize;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public int getSetSize() {
        return setSize;
    }

    public void setSetSize(int setSize) {
        this.setSize = setSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
