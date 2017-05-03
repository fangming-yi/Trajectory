package com.cumt.cs.trajectory.model.common;

import com.cumt.cs.trajectory.model.enums.AlgorithmID;
import com.cumt.cs.trajectory.model.enums.CycleType;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public class CalcParam {
    // 使用的算法
    AlgorithmID algorithm;
    // 考虑时间标记
    boolean considerTime;
    // 取余周期
    int mod;
    // 临近点阈值
    double closeDis;
    // 行列数
    int rowCnt;
    int colCnt;
    // 时间权重
    int weight;
    // 三个算法的权重
    int wFrechet, wDTW, wLCSS, wDisBasedLCSS;

    public AlgorithmID getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmID algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isConsiderTime() {
        return considerTime;
    }

    public void setConsiderTime(boolean considerTime) {
        this.considerTime = considerTime;
    }

    public int getMod() {
        return mod;
    }

    public void setMod(CycleType type) {
        switch (type)
        {
            case ABSOLUTE:
                this.mod =Integer.MAX_VALUE;
                break;
            case WEEK:
                this.mod = 7 * 24 * 3600;
                break;
            case DAY:
                this.mod = 24 * 3600;
                break;
            default:
                this.mod = Integer.MAX_VALUE;
                break;
        }
    }

    public double getCloseDis() {
        return closeDis;
    }

    public void setCloseDis(double closeDis) {
        this.closeDis = closeDis;
    }

    public int getRowCnt() {
        return rowCnt;
    }

    public void setRowCnt(int rowCnt) {
        this.rowCnt = rowCnt;
    }

    public int getColCnt() {
        return colCnt;
    }

    public void setColCnt(int colCnt) {
        this.colCnt = colCnt;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getwFrechet() {
        return wFrechet;
    }

    public void setwFrechet(int wFrechet) {
        this.wFrechet = wFrechet;
    }

    public int getwDTW() {
        return wDTW;
    }

    public void setwDTW(int wDTW) {
        this.wDTW = wDTW;
    }

    public int getwLCSS() {
        return wLCSS;
    }

    public void setwLCSS(int wLCSS) {
        this.wLCSS = wLCSS;
    }

    public int getwDisBasedLCSS() {
        return wDisBasedLCSS;
    }

    public void setwDisBasedLCSS(int wDisBasedLCSS) {
        this.wDisBasedLCSS = wDisBasedLCSS;
    }
}
