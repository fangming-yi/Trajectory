package com.cumt.cs.trajectory.service.impl;

import com.cumt.cs.trajectory.model.common.CalcParam;
import com.cumt.cs.trajectory.model.common.QueryResult;
import com.cumt.cs.trajectory.model.common.TrajPoint;
import com.cumt.cs.trajectory.service.CalcCore;
import javafx.util.Pair;

import java.util.Queue;
import java.util.Vector;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public class CalcCoreImpl implements CalcCore {

    /*typedef Vector<TrajPoint> Trajectory;
    typedef Vector<Vector<TrajPoint>> Trajectories;*/
    // 最相似轨迹段
    private  Vector<Vector<TrajPoint>> mostSSTraj;
    // 查询结果
    private  Queue<QueryResult> queryResult;
    // 网格临时标号
    private static int[] aa, bb;
    // 相似度计算的临时数组
    private static double[][] dis;
    // 状态转移方向的临时数组
    private static int[][] dir;

    private static CalcParam calcParam;

    @Override
    public void init() {
        /*mostSSTraj.push_back(Trajectory());
        mostSSTraj.push_back(Trajectory());*/
        mostSSTraj.add(new Vector<TrajPoint>());
        mostSSTraj.add(new Vector<TrajPoint>());
        aa=new int[110];
        bb=new int[110];
        dis=new double[110][110];
        dir=new int[110][110];
        calcParam=new CalcParam();

    }

    @Override
    public double calc(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        switch (calcParam.getAlgorithm())
        {
            case FRECHET:
                return frechet(a, b);
            case DTW:
                return dtw(a, b);
            case LCSS:
                return lcss(a, b);
            case LCSS2:
                return lcss2(a, b);
            case DIS_BASED_LCSS:
                return dis_based_lcss(a, b);
            case MIXED:
                return mix(a, b);
            case MIXED2:
                return mix2(a, b);
            default:
                return 0.0;
        }
    }

    @Override
    public Vector<Vector<TrajPoint>> mostSimSubTraj(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return null;
    }

    @Override
    public Pair<Integer, Integer> closestPoints(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return null;
    }

    @Override
    public Queue<QueryResult> findSimilar(Vector<TrajPoint> target, int qn) {
        return null;
    }

    @Override
    public int getMinLng(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public int getMaxLng(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public int getMinLat(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public int getMaxLat(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public int getMinTime(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public int getMaxTime(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public double ppDistance(TrajPoint p1, TrajPoint p2) {
        return 0;
    }

    @Override
    public double directAngle(TrajPoint a, TrajPoint b, TrajPoint c) {
        return 0;
    }

    @Override
    public double dot(TrajPoint p1, TrajPoint p2) {
        return 0;
    }

    @Override
    public void keyPoint(Vector<TrajPoint> a, Vector<Integer> vec) {

    }

    @Override
    public void copy(Vector<TrajPoint> a, int start, int end, Vector<TrajPoint> traj) {

    }

    @Override
    public void partition(Vector<TrajPoint> a, Vector<TrajPoint> b) {

    }

    @Override
    public double lcss(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public double lcss2(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public double dis_based_lcss(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public double frechet(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public double dtw(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public double mix(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public double mix2(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return 0;
    }

    @Override
    public Pair<Double, Double> tracebackLcss(double spaceDis, Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return null;
    }

    @Override
    public Pair<Double, Double> tracebackFrechet(double spaceDis, Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return null;
    }

    @Override
    public Pair<Double, Double> tracebackDTW(double spaceDis, Vector<TrajPoint> a, Vector<TrajPoint> b) {
        return null;
    }
}
