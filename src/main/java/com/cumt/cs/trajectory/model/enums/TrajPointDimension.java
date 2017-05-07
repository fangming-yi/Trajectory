package com.cumt.cs.trajectory.model.enums;

/**
 * Created by fangming.yi on 2017/5/7.
 */
public enum TrajPointDimension {
    longitude(0,"经度"),latitude(1,"纬度");
    int id;
    String description;

    TrajPointDimension(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static TrajPointDimension getById(int id){
        switch (id){
            case 0:
                return longitude;
            case 1:
                return latitude;
            default:
                return longitude;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
