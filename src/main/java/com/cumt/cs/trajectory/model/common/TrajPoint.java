package com.cumt.cs.trajectory.model.common;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public class TrajPoint {
    // 所在轨迹的标号
    private int id;
    // 经纬度 除以10W后为实际值
    private int lng = 0;
    private int lat = 0;
    // 时间
    int time;

    public TrajPoint(int id, int lng, int lat, int time) {
        this.id = id;
        this.lng = lng;
        this.lat = lat;
        this.time = time;
    }

    public TrajPoint(int _lng, int _lat) {
        lng = _lng;
        lat = _lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
