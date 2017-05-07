package com.cumt.cs.trajectory.model.common;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public class KDNode {
    // 所在轨迹的标号
    private int id;
    // 经纬度 除以10W后为实际值
    private int lng, lat;
    // 左右子树
    private KDNode left, right;

    public KDNode(int id, int lng, int lat, KDNode left, KDNode right) {
        this.id = id;
        this.lng = lng;
        this.lat = lat;
        this.left = left;
        this.right = right;
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

    public KDNode getLeft() {
        return left;
    }

    public void setLeft(KDNode left) {
        this.left = left;
    }

    public KDNode getRight() {
        return right;
    }

    public void setRight(KDNode right) {
        this.right = right;
    }
}
