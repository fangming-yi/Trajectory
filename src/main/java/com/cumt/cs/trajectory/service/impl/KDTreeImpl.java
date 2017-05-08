package com.cumt.cs.trajectory.service.impl;

import com.cumt.cs.trajectory.model.common.KDNode;
import com.cumt.cs.trajectory.model.common.TrajPoint;
import com.cumt.cs.trajectory.model.enums.TrajPointDimension;
import com.cumt.cs.trajectory.service.KDTree;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by fangming.yi on 2017/5/7.
 */
@Service
public class KDTreeImpl implements KDTree {
    private KDNode[] nodes = null;
    private KDNode root = null;
    private int totalSize = 0;
    private Set<Integer> knnSet;

    @Override
    public void buildKDTreeFromTrajectories(Vector<Vector<TrajPoint>> trajs) {
        freeMemory();
        for (Vector<TrajPoint> trajPointVector : trajs) {
            totalSize += trajPointVector.size();
        }
        nodes = new KDNode[totalSize];
        KDNode p = nodes[0];
        int i = 0;
        for (Vector<TrajPoint> trajPointVector : trajs) {
            for (TrajPoint trajPoint : trajPointVector) {
                p.setId(trajPoint.getId());
                p.setLng(trajPoint.getLng());
                p.setLat(trajPoint.getLat());
                p.setLeft(null);
                p.setRight(null);
                // TODO: 2017/5/7 这里可能数组越界
                p = nodes[++i];
            }
        }
        root = buildKDTree(0, totalSize - 1, TrajPointDimension.longitude);

    }

    @Override
    public void storeKDTree(String fileName) {

    }

    @Override
    public boolean loadKDTree(String fileName) {
        return false;
    }

    @Override
    public Set<Integer> findKNN(int lng, int lat, int k) {
        PriorityQueue<Pair<Double, Integer>> que = new PriorityQueue<Pair<Double, Integer>>();
        Stack<Pair<KDNode, Integer>> sta = new Stack<Pair<KDNode, Integer>>();
        // 从根节点二分搜索
        bsearchKDTree(lng, lat, k, root, 0, que, sta);
        // 遍历KD树
        while (!sta.empty()) {
            // 取出要回溯的节点
            Pair<KDNode, Integer> e = sta.peek();
            sta.pop();
            // 判断比较的维度
            if (e.getValue() == 0) {
                // 以目标点为圆心第k近邻距离为半径作圆
                // 如果圆与分割线有交点 则搜索回溯点
                if (lng <= e.getKey().getLng()) {
                    if (que.size() < k || lng + que.peek().getKey() > e.getKey().getLng())
                        bsearchKDTree(lng, lat, k, e.getKey().getRight(), e.getValue() ^ 1, que, sta);
                } else {
                    if (que.size() < k || lng - que.peek().getKey() < e.getKey().getLng())
                        bsearchKDTree(lng, lat, k, e.getKey().getLeft(), e.getValue() ^ 1, que, sta);
                }
            } else {
                if (lat <= e.getKey().getLat()) {
                    if (que.size() < k || lat + que.peek().getKey() > e.getKey().getLat())
                        bsearchKDTree(lng, lat, k, e.getKey().getRight(), e.getValue() ^ 1, que, sta);
                } else {
                    if (que.size() < k || lat - que.peek().getKey() < e.getKey().getLat())
                        bsearchKDTree(lng, lat, k, e.getKey().getLeft(), e.getValue() ^ 1, que, sta);
                }
            }
        }
        // 输出
        knnSet.clear();
        while (!que.isEmpty()) {
            //qDebug() << que.top().second;
            knnSet.add(que.peek().getValue());
            que.poll();
        }
        return knnSet;
    }

    private void bsearchKDTree(int lng, int lat, int k, KDNode root, int d, PriorityQueue<Pair<Double, Integer>> que, Stack<Pair<KDNode, Integer>> sta) {
        while (root != null) {
            // 压入子树的根以及比较的维度
            sta.push(new Pair<KDNode, Integer>(root, d));
            // 调整最大堆
            if (que.size() < k)
                que.add(new Pair<Double, Integer>(getDis(lng, lat, root), root.getId()));
            else if (que.peek().getKey() > getDis(lng, lat, root)) {
                que.poll();
                que.add(new Pair<Double, Integer>(getDis(lng, lat, root), root.getId()));
            }
            // 比较经度
            if (d == 0) {
                // 二分搜索
                if (lng <= root.getLng())
                    root = root.getLeft();
                else
                    root = root.getRight();
            } else // 比较纬度
            {
                // 二分搜索
                if (lat <= root.getLat())
                    root = root.getLeft();
                else
                    root = root.getRight();
            }
            d ^= 1;
        }
    }

    // 递归构建KD树
    private KDNode buildKDTree(int left, int right, final TrajPointDimension dimension) {
        if (right < left)
            return null;
        // 按照d维度排序
        Arrays.sort(nodes, left, right + 1, new Comparator<KDNode>() {
            @Override
            public int compare(KDNode o1, KDNode o2) {
                switch (dimension) {
                    case longitude:
                        return o1.getLng() < o2.getLng() ? 1 : -1;
                    case latitude:
                        return o1.getLat() < o2.getLat() ? 1 : -1;
                    default:
                        break;
                }
                return 0;
            }
        });

        int mid = (left + right) >> 1;
        // 中间点选作子树的根
        KDNode cur = nodes[mid];
        // 递归建立左右子树
        cur.setLeft(buildKDTree(left, mid - 1, TrajPointDimension.getById(dimension.getId() ^ 1)));
        cur.setRight(buildKDTree(mid + 1, right, TrajPointDimension.getById(dimension.getId() ^ 1)));
        return cur;
    }

    // 计算欧氏距离
    private double getDis(int lng, int lat, KDNode p) {
        return Math.sqrt(1.0 * (lng - p.getLng()) * (lng - p.getLng()) + 1.0 * (lat - p.getLat()) * (lat - p.getLat()));
    }

    // 释放内存
    private void freeMemory() {

    }
}
