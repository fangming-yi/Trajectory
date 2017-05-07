package com.cumt.cs.trajectory.service.impl;

import com.cumt.cs.trajectory.model.common.TrajPoint;
import com.cumt.cs.trajectory.service.TrajectoryManager;

import java.util.Vector;

/**
 * Created by fangming.yi on 2017/5/6.
 */
public class TrajectoryManagerImpl implements TrajectoryManager {
    private Vector<Vector<TrajPoint>> trajs=new Vector<Vector<TrajPoint>>();
    @Override
    public Vector<TrajPoint> getTrajectory(int id) {
        return trajs.get(id);
    }

    @Override
    public Vector<Vector<TrajPoint>> getTrajectories() {
        return trajs;
    }

    @Override
    public boolean loadTrajectoriesFromFolder(String folderPath, boolean on) {
        return false;
    }

    @Override
    public boolean loadTrajectoryFromFile(String filePath, boolean on) {
        return false;
    }

    @Override
    public boolean loadTrajectoriesFromFile(String filePath) {
        return false;
    }

    @Override
    public void storeTrajectories(String filePath) {

    }

    @Override
    public int addTrajectory(Vector<TrajPoint> traj) {
        int id = trajs.size();
        trajs.add(new Vector<TrajPoint>());
        //trajs.push_back(Trajectory());
        TrajPoint pt;
        for (TrajPoint trajPoint:traj){
            trajPoint.setId(id);
            trajs.get(id).add(trajPoint);
        }
        return id;
    }

    private void freeMemory() {

    }
}
