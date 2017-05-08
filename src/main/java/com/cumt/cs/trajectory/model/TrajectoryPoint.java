package com.cumt.cs.trajectory.model;

import java.util.Date;

/**
 * Created by fangming.yi on 2017/5/8.
 */
public class TrajectoryPoint {
    private int id;
    private double longitude;
    private double latitude;
    private Date time;

    public TrajectoryPoint(){

    }

    public TrajectoryPoint(double longitude, double latitude, Date time) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }

    public TrajectoryPoint(int id, double longitude, double latitude, Date time) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
