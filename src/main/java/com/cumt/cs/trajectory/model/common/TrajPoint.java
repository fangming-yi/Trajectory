package com.cumt.cs.trajectory.model.common;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public class TrajPoint {
    // 所在轨迹的标号
    int id;
    // 经纬度 除以10W后为实际值
    int lng = 0;
    int lat = 0;
    // 时间
    int time;

    TrajPoint(int _lng, int _lat) {
        lng = _lng;
        lat = _lat;
    }
}
