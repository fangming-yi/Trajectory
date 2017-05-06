package com.cumt.cs.trajectory.service;


import com.cumt.cs.trajectory.model.common.QueryResult;
import com.cumt.cs.trajectory.model.common.TrajPoint;
import com.cumt.cs.trajectory.model.enums.AlgorithmID;
import javafx.util.Pair;

import java.util.Queue;
import java.util.Vector;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public interface CalcCore {

   /* typedef QVector<TrajPoint> Trajectory;
    typedef QVector<Trajectory> Trajectories;*/
    //初始化
    void init();

    // 公有的计算相似度的接口
    double calc(Vector<TrajPoint> a, Vector<TrajPoint> b);
    // 计算最相似子轨迹段
    Vector<Vector<TrajPoint>> mostSimSubTraj(Vector<TrajPoint> a, Vector<TrajPoint> b);
    // 计算最近点对
    Pair<Integer, Integer> closestPoints(Vector<TrajPoint> a, Vector<TrajPoint> b);
    // 查询相似轨迹
    Queue<QueryResult> findSimilar(Vector<TrajPoint> target,TrajectoryManager trajManager, KDTree kdTree,int qn);
    // 获得经纬度极值
    int getMinLng(Vector<TrajPoint> a,Vector<TrajPoint> b);
    int getMaxLng(Vector<TrajPoint> a,Vector<TrajPoint> b);
    int getMinLat(Vector<TrajPoint> a,Vector<TrajPoint> b);
    int getMaxLat(Vector<TrajPoint> a,Vector<TrajPoint> b);
    // 获得时间极值
    int getMinTime(Vector<TrajPoint> a,Vector<TrajPoint> b);
    int getMaxTime(Vector<TrajPoint> a,Vector<TrajPoint> b);


    /*// 辅助计算函数
    // 计算两点距离
    double ppDistance(TrajPoint p1, TrajPoint p2);
    // 计算方向角
    double directAngle(TrajPoint a, TrajPoint b, TrajPoint c);
    // 计算向量点乘
    double dot(TrajPoint p1,TrajPoint p2);
    //计算关键点
    void keyPoint(Vector<TrajPoint> a, Vector<Integer> vec);
    //轨迹拷贝
    void copy(Vector<TrajPoint> a,int start,int end, Vector<TrajPoint> traj);
    // 网格划分
    void partition(Vector<TrajPoint> a, Vector<TrajPoint> b);
    // 相似度计算函数
    double lcss(Vector<TrajPoint> a, Vector<TrajPoint> b);
    double lcss2(Vector<TrajPoint> a, Vector<TrajPoint> b);
    double dis_based_lcss(Vector<TrajPoint> a, Vector<TrajPoint> b);
    double frechet(Vector<TrajPoint> a,Vector<TrajPoint> b);
    double dtw(Vector<TrajPoint> a,Vector<TrajPoint> b);
    double mix(Vector<TrajPoint> a,Vector<TrajPoint> b);
    double mix2(Vector<TrajPoint> a,Vector<TrajPoint> b);
    // 追溯转移路径
    Pair<Double, Double> tracebackLcss(double spaceDis, Vector<TrajPoint> a,Vector<TrajPoint> b);
    Pair<Double, Double> tracebackFrechet(double spaceDis,Vector<TrajPoint>a, Vector<TrajPoint>b);
    Pair<Double, Double> tracebackDTW(double spaceDis,Vector<TrajPoint> a, Vector<TrajPoint> b);*/

}
