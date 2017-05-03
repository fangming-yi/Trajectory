package com.cumt.cs.trajectory.model.common;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public class KDNode {
    // 所在轨迹的标号
    int id;
    // 经纬度 除以10W后为实际值
    int lng, lat;
    // 左右子树
    KDNode left, right;
}
