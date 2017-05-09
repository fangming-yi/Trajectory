package com.cumt.cs.trajectory.dao;

import com.cumt.cs.trajectory.model.TrajectoryPoint;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangming.yi on 2017/5/8.
 */
@Repository
public interface TrajectoryPointDao {

    int insertTrajectoryPoint(TrajectoryPoint trajectoryPoint);
    int insertTrajectoryPoints(List<TrajectoryPoint> trajectoryPointList);
    int deleteTrajectoryPointById(int id);
    TrajectoryPoint selectTrajectoryPointById(int id);
    List<TrajectoryPoint> selectTrajectoryPoints(RowBounds rowBounds);
    int getTotal();

}
