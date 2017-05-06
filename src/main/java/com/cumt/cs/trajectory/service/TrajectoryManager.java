package com.cumt.cs.trajectory.service;

import com.cumt.cs.trajectory.model.common.TrajPoint;

import java.util.Vector;

/**
 * Created by fangming.yi on 2017/5/6.
 */
public interface TrajectoryManager {
    Vector<TrajPoint> getTrajectory(int id);
    Vector<Vector<TrajPoint>> getTrajectories();
    boolean loadTrajectoriesFromFolder(String folderPath,boolean on);
    boolean loadTrajectoryFromFile(String filePath, boolean on);
    boolean loadTrajectoriesFromFile(String filePath);
    void storeTrajectories(String filePath);
    int addTrajectory(Vector<TrajPoint> traj );

    //void freeMemory();
}
