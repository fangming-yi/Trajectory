package com.cumt.cs.trajectory.dao;

import com.cumt.cs.trajectory.model.TrajectoryPoint;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by fangming.yi on 2017/5/8.
 */
public interface TrajectotyPointDao {

    int insertTrajectPoint(TrajectoryPoint trajectoryPoint);
    int insertTrajectPoints(List<TrajectoryPoint> trajectoryPointList);
    int deleteTrajectoryPointById(int id);
    TrajectoryPoint selectTrajectoryPointById(int id);
    List<TrajectoryPoint> seletTrajectoryPoints(RowBounds rowBounds);

}
