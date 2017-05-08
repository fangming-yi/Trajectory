package com.cumt.cs.trajectory.service;

import com.cumt.cs.trajectory.model.common.TrajPoint;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    //以下方法是我添加的
    /**
     * 保存多个轨迹数据文件
     * @param multipartFiles
     * @return
     */
    boolean saveTrajectoryFiles(MultipartFile[] multipartFiles) throws IOException;
}
