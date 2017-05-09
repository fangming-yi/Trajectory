package com.cumt.cs.trajectory.model;

import java.util.Date;

/**
 * Created by fangming.yi on 2017/5/8.
 */
public class TrajectoryPoint {
    private int id;
    private double longitude;
    private double latitude;
    private Date generateTime;

    public TrajectoryPoint(){

    }

    public TrajectoryPoint(double longitude, double latitude, Date generateTime) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.generateTime = generateTime;
    }

    public TrajectoryPoint(int id, double longitude, double latitude, Date generateTime) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.generateTime = generateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Date generateTime) {
        this.generateTime = generateTime;
    }
}
