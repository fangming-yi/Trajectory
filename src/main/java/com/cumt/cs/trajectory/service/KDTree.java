package com.cumt.cs.trajectory.service;

import com.cumt.cs.trajectory.model.common.KDNode;
import com.cumt.cs.trajectory.model.common.TrajPoint;
import javafx.util.Pair;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

/**
 * Created by fangming.yi on 2017/5/6.
 */
public interface KDTree {
    /*typedef Vector<TrajPoint> Trajectory;
    typedef Vector<Vector<TrajPoint>> Trajectories;*/
    // 从轨迹集合构建KD树
    void buildKDTreeFromTrajectories(Vector<Vector<TrajPoint>> trajs);
    // 存储KD树
    void storeKDTree(String fileName);
    // 加载KD树
    boolean loadKDTree(String fileName);
    // 查找K近邻
    Set<Integer> findKNN(int lng, int lat, int k);


  /*  // 带回溯的二分搜索KD树
    void bsearchKDTree(int lng, int lat, int k, KDNode root, int d, PriorityQueue<Pair<Double, Integer>> que, Stack<Pair<KDNode, Integer> > sta);
    // 递归构建KD树
    KDNode buildKDTree(int l, int r, int d);
    // 释放内存
    void freeMemory();*/
}
