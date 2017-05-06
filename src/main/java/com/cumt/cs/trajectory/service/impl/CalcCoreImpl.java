package com.cumt.cs.trajectory.service.impl;

import com.cumt.cs.trajectory.model.common.CalcParam;
import com.cumt.cs.trajectory.model.common.QueryResult;
import com.cumt.cs.trajectory.model.common.TrajPoint;
import com.cumt.cs.trajectory.model.enums.AlgorithmID;
import com.cumt.cs.trajectory.service.CalcCore;
import com.cumt.cs.trajectory.service.KDTree;
import com.cumt.cs.trajectory.service.TrajectoryManager;
import com.google.common.collect.Sets;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by fangming.yi on 2017/5/3.
 */
public class CalcCoreImpl implements CalcCore {

    /*typedef Vector<TrajPoint> Trajectory;
    typedef Vector<Vector<TrajPoint>> Trajectories;*/
    // 最相似轨迹段
    private static  Vector<Vector<TrajPoint>> mostSSTraj;
    // 查询结果
    private static  Queue<QueryResult> queryResult;
    // 经纬度极值
    private static double minLng, maxLng, minLat, maxLat;
    // 行列单位
    private static double colUnit, rowUnit;
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
        double mostDis = 0.0;
        int ak = 1, bk = 1;
        int sa, sb, ea, eb;
        Vector<Integer> akpNum=new Vector<Integer>(), bkpNum=new Vector<Integer>();
        keyPoint(a, akpNum);
        keyPoint(b, bkpNum);
        Vector<TrajPoint> atemp=new Vector<TrajPoint>(), btemp=new Vector<TrajPoint>();
        int alen = akpNum.size(), blen = bkpNum.size();
        for (int i = 1; i < alen; i++)
        {
            copy(a,akpNum.get(i-1),akpNum.get(i), atemp);
            for (int j = 1; j < blen; j++)
            {
                copy(b,bkpNum.get(j-1),bkpNum.get(j), btemp);
                if (atemp.size() > 2 || btemp.size() > 2 || calcParam.getAlgorithm() != AlgorithmID.LCSS2)
                {
                    double tempDis = calc(atemp, btemp);
                    if (mostDis < tempDis)
                    {
                        mostDis = tempDis;
                        ak = i;
                        bk = j;
                    }
                }
            }
        }
        if (Math.abs(mostDis) < 1e-9)
        {
            mostSSTraj.get(0).clear();
            mostSSTraj.get(1).clear();
            if (calcParam.getAlgorithm() == AlgorithmID.LCSS2)
                calcParam.setAlgorithm(AlgorithmID.LCSS);
            else if (calcParam.getAlgorithm() == AlgorithmID.MIXED2)
                calcParam.setAlgorithm(AlgorithmID.MIXED);
            return mostSSTraj;
        }
        sa = ea = ak;
        sb = eb = bk;
        // 向前扩展
        for (int i = ak, j = bk; i > 0 && j > 0; i--, j--)
        {
            copy(a,akpNum.get(i-1),akpNum.get(i),atemp);
            copy(b,bkpNum.get(j-1),bkpNum.get(j),btemp);
            double tempDis = calc(atemp, btemp);
            if (mostDis * 0.8 <= tempDis) {
                sa = i;
                sb = j;
            }
            else
                break;
        }
        // 向后扩展
        for (int i = ak, j = bk; i < alen&&j < blen; i++, j++)
        {
            copy(a,akpNum.get(i-1),akpNum.get(i),atemp);
            copy(b,bkpNum.get(j-1),bkpNum.get(j),btemp);
            double tempDis = calc(atemp, btemp);
            if (mostDis * 0.8 <= tempDis) {
                ea = i;
                eb = j;
            }
            else
                break;
        }
        // 保存结果
        copy(a,akpNum.get(sa-1),akpNum.get(ea), mostSSTraj.get(0));
        copy(b,bkpNum.get(sb-1),bkpNum.get(eb), mostSSTraj.get(1));
        if (calcParam.getAlgorithm() == AlgorithmID.LCSS2)
            calcParam.setAlgorithm(AlgorithmID.LCSS);
        else if (calcParam.getAlgorithm() == AlgorithmID.MIXED2)
            calcParam.setAlgorithm(AlgorithmID.MIXED);
        return mostSSTraj;
    }

    @Override
    public Pair<Integer, Integer> closestPoints(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int alen = a.size(),blen = b.size();
        double maxDis = ppDistance(a.get(0),b.get(0));
        double temp;
        int ansi = 0, ansj = 0;
        for(int i = 0; i < alen; i++)
            for(int j = 0; j < blen; j++)
            {
                temp = ppDistance(a.get(i),b.get(j));
                if( temp < maxDis)
                {
                    maxDis = temp;
                    ansi = i;
                    ansj = j;
                }
            }
        return new Pair<Integer, Integer>(ansi, ansj);
    }

    @Override
    public Queue<QueryResult> findSimilar(Vector<TrajPoint> target, TrajectoryManager trajManager, KDTree kdTree, int qn) {
        // 找到近邻轨迹ID
        Set<Integer> ids= Sets.newConcurrentHashSet();
        for (TrajPoint pt : target) {
            ids.addAll(kdTree.findKNN(pt.getLng(), pt.getLat(), qn));
        }
        // TODO: 2017/5/6 这是什么意思，删除第一个元素？
        ids.remove(target.get(0).getId());
        //ids.erase(ids.find(target.get(0).getId());
        // 查询最相似的轨迹
        //PriorityQueue<Pair<Double, Integer> , Vector<Pair<Double, Integer>>, Greater<Pair<Double, Integer>>> que;
        // TODO: 2017/5/6 优先队列的操作不是很清楚，push和pop
        PriorityQueue<Pair<Double,Integer>> que=new PriorityQueue<Pair<Double, Integer>>();
        Pair<Double, Integer> temp;
        for (int id : ids)
        {
            //temp.getValue() = id;
            if (calcParam.getAlgorithm() == AlgorithmID.LCSS2) {
                calcParam.setAlgorithm(AlgorithmID.LCSS);
            }
            else if (calcParam.getAlgorithm() == AlgorithmID.MIXED2) {
                calcParam.setAlgorithm(AlgorithmID.MIXED);
            }
            double first = calc(target, trajManager.getTrajectory(id));
            temp=new Pair<Double, Integer>(first,id);
            if (que.size() < qn) {
                que.add(temp);
                //que.push(temp);
            }
            else if (temp.getKey() > que.peek().getKey())
            {
                que.poll();
                que.add(temp);
                /*que.pop();
                que.push(temp);*/
            }
        }
        // 输出
        while (!queryResult.isEmpty()) {
            queryResult.poll();
            //queryResult.pop();
        }
        while(!que.isEmpty()){
            temp = que.peek();
            queryResult.add(new QueryResult(target.get(0).getId(), temp.getValue(), temp.getKey()));
            que.poll();
        }
        return queryResult;
    }

    @Override
    public int getMinLng(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int ans = (int) 1e9;
        for (TrajPoint pt : a)
            ans = Math.min(ans, pt.getLng());
        for (TrajPoint pt : b)
            ans = Math.min(ans, pt.getLng());
        return ans;
    }

    @Override
    public int getMaxLng(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int ans =(int) -1e9;
        for (TrajPoint pt : a)
        ans = Math.max(ans, pt.getLng());
        for (TrajPoint pt : b)
        ans = Math.max(ans, pt.getLng());
        return ans;
    }

    @Override
    public int getMinLat(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int ans =(int) 1e9;
        for (TrajPoint pt : a)
            ans = Math.min(ans, pt.getLat());
        for (TrajPoint pt : b)
            ans = Math.min(ans, pt.getLat());
        return ans;
    }

    @Override
    public int getMaxLat(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int ans =(int) -1e9;
        for (TrajPoint pt : a)
            ans = Math.max(ans, pt.getLat());
        for (TrajPoint pt : b)
            ans = Math.max(ans, pt.getLat());
        return ans;
    }

    @Override
    public int getMinTime(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int ans = (int) 2e9;
        for (TrajPoint pt : a)
            ans = Math.min(ans, pt.getTime() % calcParam.getMod());
        for (TrajPoint pt : b)
            ans = Math.min(ans, pt.getTime() % calcParam.getMod());
        return ans;
    }

    @Override
    public int getMaxTime(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int ans = 0;
        for (TrajPoint pt : a)
            ans = Math.max(ans, pt.getTime() % calcParam.getMod());
        for (TrajPoint pt : b)
            ans = Math.max(ans, pt.getTime() % calcParam.getMod());
        return ans;
    }



    /*
    以下是计算相似度所需的私有方法
     */
    // 计算两点距离
    private double ppDistance(TrajPoint p1, TrajPoint p2) {
        return Math.sqrt(Math.pow(p1.getLat() - p2.getLat(), 2.0) + Math.pow(p1.getLng() - p2.getLng(), 2.0));
    }

    // 计算方向角
    private double directAngle(TrajPoint a, TrajPoint b, TrajPoint c) {
        TrajPoint da=new TrajPoint(b.getLat() - a.getLat(), b.getLng() - a.getLng());
        TrajPoint db=new TrajPoint(c.getLat() - b.getLat(), c.getLng() - b.getLng());
        double temp = dot(da, db) / (ppDistance(a, b) * ppDistance(b, c));
        return Math.acos(temp) * 180 / Math.PI;
    }

    // 计算向量点乘
    private double dot(TrajPoint p1, TrajPoint p2) {
        return 1.0 * p1.getLat() * p2.getLat() + 1.0 * p1.getLng() * p2.getLng();
    }


    //计算关键点
    private void keyPoint(Vector<TrajPoint> a, Vector<Integer> vec) {
        vec.clear();
        int len =a.size(),num = 1 ;
        boolean[] flag=new boolean[len];
        Arrays.fill(flag,false);
        //memset(flag,false,sizeof(flag));
        flag[0] = flag[len - 1] = true;
        for (int i = 1; i < len - 1; i++)
        {
            if(directAngle(a.get(i - 1), a.get(i), a.get(i + 1)) > 30 || num > 8) {
                flag[i] = true;
                num = 1;
            }
            else
                num++;
        }
        for (int i = 0; i < len; i++) {
            if (flag[i])
                vec.add(i);
        }
    }

    //轨迹拷贝
    private void copy(Vector<TrajPoint> a, int start, int end, Vector<TrajPoint> traj) {
        traj.clear();
        for(int i = start; i <= end; i++)
            traj.add(a.get(i));
    }

    // 网格划分
    private void partition(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int alen = a.size(),blen = b.size();
        for (int i = 0; i < alen; i++)
        {
            aa[i] = (int)Math.floor((a.get(i).getLat() - minLat) / rowUnit) * calcParam.getColCnt()
                    + (int)Math.floor((a.get(i).getLng() - minLng) / colUnit);
        }
        for (int i = 0; i < blen; i++)
        {
            bb[i] = (int)Math.floor((b.get(i).getLat() - minLat) / rowUnit) * calcParam.getColCnt()
                    + (int)Math.floor((b.get(i).getLng() - minLng) / colUnit);
        }
    }

    // 相似度计算函数
    private double lcss(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        minLng = getMinLng(a, b);
        maxLng = getMaxLng(a, b);
        minLat = getMinLat(a, b);
        maxLat = getMaxLat(a, b);
        colUnit = (maxLng + 1.0 - minLng) / calcParam.getColCnt();
        rowUnit = (maxLat + 1.0 - minLat) / calcParam.getRowCnt();
        calcParam.setAlgorithm(AlgorithmID.LCSS2);
        return lcss2(a, b);
    }


    private double lcss2(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int alen = a.size(), blen = b.size();
        partition(a, b);
        dis[0][0] = (aa[0] == bb[0] ? 1 : 0);
        dir[0][0] = (aa[0] == bb[0] ? 3 : 1);
        for (int i = 1; i < alen; i++)
        {
            if (aa[i] == bb[0]) {
                dis[i][0] = 1.0;
                dir[i][0] = 3;
            }
            else {
                dis[i][0] = dis[i - 1][0];
                dir[i][0] = 1;
            }
        }
        for (int i = 1; i < blen; i++)
        {
            if (aa[0] == bb[i]) {
                dis[0][i] = 1.0;
                dir[0][i] = 3;
            }
            else {
                dis[0][i] = dis[0][i - 1];
                dir[0][i] = 2;
            }
        }
        for (int i = 1; i < alen; i++)
            for (int j = 1; j < blen; j++)
            {
                if (aa[i] == bb[j]) {
                    dis[i][j] = dis[i - 1][j - 1] + 1.0;
                    dir[i][j] = 3;
                }
                else if (dis[i-1][j] > dis[i][j-1]) {
                    dis[i][j] = dis[i - 1][j];
                    dir[i][j] = 1;
                }
                else {
                    dis[i][j] = dis[i][j - 1];
                    dir[i][j] = 2;
                }
            }
        Pair<Double, Double> ans = tracebackLcss(dis[alen-1][blen-1] / Math.max(alen, blen), a, b);
        if (!calcParam.isConsiderTime())
            return ans.getKey();
        return (ans.getKey() * 10 + ans.getValue() * calcParam.getWeight()) / (10.0 + calcParam.getWeight());
    }


    private double dis_based_lcss(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int alen = a.size(), blen = b.size();
        dis[0][0] = (ppDistance(a.get(0), b.get(0)) <= calcParam.getCloseDis() ? 1 : 0);
        dir[0][0] = (ppDistance(a.get(0), b.get(0)) <= calcParam.getCloseDis() ? 3 : 1);
        for (int i = 1; i < alen; i++)
        {
            if (ppDistance(a.get(i), b.get(0)) <= calcParam.getCloseDis()) {
                dis[i][0] = 1.0;
                dir[i][0] = 3;
            }
            else {
                dis[i][0] = dis[i - 1][0];
                dir[i][0] = 1;
            }
        }
        for (int i = 1; i < blen; i++)
        {
            if (ppDistance(a.get(0), b.get(i)) <= calcParam.getCloseDis()) {
                dis[0][i] = 1.0;
                dir[0][i] = 3;
            }
            else {
                dis[0][i] = dis[0][i - 1];
                dir[0][i] = 2;
            }
        }
        for (int i = 1; i < alen; i++)
            for (int j = 1; j < blen; j++)
            {
                if (ppDistance(a.get(i), b.get(j)) <= calcParam.getCloseDis()) {
                    dis[i][j] = dis[i - 1][j - 1] + 1.0;
                    dir[i][j] = 3;
                }
                else if (dis[i-1][j] > dis[i][j-1]) {
                    dis[i][j] = dis[i - 1][j];
                    dir[i][j] = 1;
                }
                else {
                    dis[i][j] = dis[i][j - 1];
                    dir[i][j] = 2;
                }
            }
        Pair<Double, Double> ans = tracebackLcss(dis[alen-1][blen-1] / Math.max(alen, blen), a, b);
        if (!calcParam.isConsiderTime())
            return ans.getKey();
        return (ans.getKey() * 10 + ans.getValue() * calcParam.getWeight()) / (10.0 + calcParam.getWeight());
    }


    private double frechet(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int minLng = getMinLng(a, b), maxLng = getMaxLng(a, b);
        int minLat = getMinLat(a, b), maxLat = getMaxLat(a, b);
        int alen = a.size(), blen = b.size();
        dis[0][0] = ppDistance(a.get(0), b.get(0));
        dir[0][0] = 3;
        for (int i = 1; i < alen; i++)
        {
            if (dis[i-1][0] > ppDistance(a.get(i), b.get(0)))
            {
                dis[i][0] = dis[i-1][0];
                dir[i][0] = 1;
            }
            else
            {
                dis[i][0] = ppDistance(a.get(i), b.get(0));
                dir[i][0] = 3;
            }
        }
        for (int j = 1; j < blen; j++)
        {
            if (dis[0][j-1] > ppDistance(a.get(0), b.get(j)))
            {
                dis[0][j] = dis[0][j-1];
                dir[0][j] = 2;
            }
            else
            {
                dis[0][j] = ppDistance(a.get(0), b.get(j));
                dir[0][j] = 3;
            }
        }
        for (int i = 1; i < alen; i++)
            for (int j = 1; j < blen; j++)
            {
                dis[i][j] = Math.min(dis[i-1][j], Math.min(dis[i][j-1], dis[i-1][j-1]));
                if (dis[i][j] == dis[i-1][j])
                    dir[i][j] = 1;
                else if (dis[i][j] == dis[i][j-1])
                    dir[i][j] = 2;
                else
                    dir[i][j] = 3;
                dis[i][j] = Math.max(dis[i][j], ppDistance(a.get(i), b.get(j)));
            }
        double maxDis = ppDistance(new TrajPoint(maxLng, maxLat), new TrajPoint(minLng, minLat));
        Pair<Double, Double> ans = tracebackFrechet((maxDis - dis[alen-1][blen-1]) / maxDis, a, b);
        if (!calcParam.isConsiderTime())
            return ans.getKey();
        return (ans.getKey() * 10 + ans.getValue() * calcParam.getWeight()) / (10.0 + calcParam.getWeight());
    }


    private double dtw(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int minLng = getMinLng(a, b), maxLng = getMaxLng(a, b);
        int minLat = getMinLat(a, b), maxLat = getMaxLat(a, b);
        int alen = a.size(), blen = b.size();
        dis[0][0] = ppDistance(a.get(0), b.get(0));
        dir[0][0] = 3;
        for (int i = 1; i < alen; i++)
        {
            dis[i][0] = dis[i-1][0] + ppDistance(a.get(i), b.get(0));
            dir[i][0] = 1;
        }
        for (int j = 1; j < blen; j++)
        {
            dis[0][j] = dis[0][j-1] + ppDistance(a.get(0), b.get(j));
            dir[0][j] = 2;
        }
        double minDis;
        for (int i = 1; i < alen; i++)
            for (int j = 1; j < blen; j++)
            {
                minDis = Math.min(dis[i-1][j], Math.min(dis[i][j-1], dis[i-1][j-1]));
                dis[i][j] = ppDistance(a.get(i), b.get(j)) + minDis;
                if (minDis == dis[i-1][j])
                    dir[i][j] = 1;
                else if (minDis == dis[i][j-1])
                    dir[i][j] = 2;
                else
                    dir[i][j] = 3;
            }
        Pair<Double, Double> ans = tracebackDTW(dis[alen-1][blen-1], a, b);
        double maxDis = ppDistance(new TrajPoint(maxLng, maxLat), new TrajPoint(minLng, minLat));
        if (!calcParam.isConsiderTime())
            return (maxDis - ans.getKey()) / maxDis;
        return ((maxDis - ans.getKey()) * 10.0 / maxDis + ans.getValue() * calcParam.getWeight()) / (10.0 + calcParam.getWeight());
    }


    private double mix(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        double ans = frechet(a, b) * calcParam.getwFrechet() + dtw(a, b) * calcParam.getwDTW() + lcss(a, b) * calcParam.getwLCSS() + dis_based_lcss(a, b) * calcParam.getwDisBasedLCSS();
        ans /= (calcParam.getwFrechet() + calcParam.getwDTW() + calcParam.getwLCSS() + calcParam.getwDisBasedLCSS());
        calcParam.setAlgorithm(AlgorithmID.MIXED2);
        return ans;
    }


    private double mix2(Vector<TrajPoint> a, Vector<TrajPoint> b) {
        double ans = frechet(a, b) * calcParam.getwFrechet() + dtw(a, b) * calcParam.getwDTW() + lcss2(a, b) * calcParam.getwLCSS() + dis_based_lcss(a, b) * calcParam.getwDisBasedLCSS();
        ans /= (calcParam.getwFrechet() + calcParam.getwDTW() + calcParam.getwLCSS() + calcParam.getwDisBasedLCSS());
        return ans;
    }

    // 追溯转移路径
    private Pair<Double, Double> tracebackLcss(double spaceDis, Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int minTime = getMinTime(a, b);
        int maxTime = getMaxTime(a, b);
        int i = a.size() - 1, j = b.size() - 1;
        int cnt = 0;
        long sum = 0;
        while (i >= 0 && j >= 0)
        {
            if (dir[i][j] == 3)
            {
                sum += Math.abs(a.get(i).getTime() % calcParam.getMod() - b.get(j).getTime() % calcParam.getMod());
                ++cnt;
                --i;
                --j;
            }
            else if (dir[i][j] == 2)
                --j;
            else
                --i;
        }
        double delta = maxTime - minTime;
        double timeDis = (cnt != 0 ? (delta - 1.0 * sum / cnt) / delta : 0.0);
        return new Pair<Double, Double>(spaceDis, timeDis);
    }


    private Pair<Double, Double> tracebackFrechet(double spaceDis, Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int minTime = getMinTime(a, b);
        int maxTime = getMaxTime(a, b);
        int i = a.size() - 1, j = b.size() - 1;
        int cnt = 0;
        long sum = 0;
        while (i >= 0 && j >= 0)
        {
            sum += Math.abs(a.get(i).getTime() % calcParam.getMod() - b.get(j).getTime() % calcParam.getMod());
            ++cnt;
            if (dir[i][j] == 3) {
                --i;
                --j;
            }
            else if (dir[i][j] == 2)
                --j;
            else
                --i;
        }
        double delta = maxTime - minTime;
        double timeDis = (delta - 1.0 * sum / cnt) / delta;
        return new Pair<Double, Double>(spaceDis, timeDis);
    }


    private Pair<Double, Double> tracebackDTW(double spaceDis, Vector<TrajPoint> a, Vector<TrajPoint> b) {
        int minTime = getMinTime(a, b);
        int maxTime = getMaxTime(a, b);
        int i = a.size() - 1, j = b.size() - 1;
        int cnt = 0;
        long sum = 0;
        while (i >= 0 && j >= 0)
        {
            sum += Math.abs(a.get(i).getTime() % calcParam.getMod() - b.get(j).getTime() % calcParam.getMod());
            ++cnt;
            if (dir[i][j] == 3) {
                --i;
                --j;
            }
            else if (dir[i][j] == 2)
                --j;
            else
                --i;
        }
        double delta = maxTime - minTime;
        double timeDis = (delta - 1.0 * sum / cnt) / delta;
        return new Pair<Double, Double>(spaceDis / cnt, timeDis);
    }
}
