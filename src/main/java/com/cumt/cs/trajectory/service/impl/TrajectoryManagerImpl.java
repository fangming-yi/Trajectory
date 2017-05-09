package com.cumt.cs.trajectory.service.impl;

import com.cumt.cs.trajectory.dao.TrajectoryPointDao;
import com.cumt.cs.trajectory.model.TrajectoryPoint;
import com.cumt.cs.trajectory.model.common.TrajPoint;
import com.cumt.cs.trajectory.service.TrajectoryManager;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by fangming.yi on 2017/5/6.
 */
@Service
public class TrajectoryManagerImpl implements TrajectoryManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrajectoryManagerImpl.class);
    private static final Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");

    private Vector<Vector<TrajPoint>> trajs = new Vector<Vector<TrajPoint>>();

    @Resource
    private TrajectoryPointDao trajectoryPointDao;

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
        for (TrajPoint trajPoint : traj) {
            trajPoint.setId(id);
            trajs.get(id).add(trajPoint);
        }
        return id;
    }

    @Override
    public boolean saveTrajectoryFiles(MultipartFile[] multipartFiles) throws IOException{

        for (MultipartFile multipartFile:multipartFiles){
            try {
                List<TrajectoryPoint> trajectoryPointList = parseFile(multipartFile);
                saveTrajectoriyPointListToDB(trajectoryPointList);
            } catch (IOException e) {
                LOGGER.error("解析文件异常");
                throw e;
            }
        }
        return true;
    }

    private List<TrajectoryPoint> parseFile(MultipartFile file) throws IOException {
        final List<TrajectoryPoint> resultList = Lists.newArrayList();
        BufferedReader bufferedReader = null;
        try {

            bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
            String line;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                List<String> list = splitter.splitToList(line);
                if (3 == list.size()) {
                    double longitude=Double.parseDouble(list.get(0));
                    double latitude=Double.parseDouble(list.get(1));
                    Date time=formatter.parseDateTime(list.get(2)).toDate();

                    resultList.add(new TrajectoryPoint(longitude,latitude,time));
                } else {
                    //存在非法行打日志后跳过，忽略
                    LOGGER.error("file contain illegal line,file name:{},illegal line:{}",file.getName(),line);
                    //throw new IOException("illegal line:" + line);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw e;
                }
            }
        }
        return resultList;
    }

    @Transactional
    private int saveTrajectoriyPointListToDB(List<TrajectoryPoint> trajectoryPointList){
        Preconditions.checkArgument(trajectoryPointList!=null&&trajectoryPointList.size()>0,"轨迹点列表不能为空");
        return trajectoryPointDao.insertTrajectoryPoints(trajectoryPointList);
    }

    private void freeMemory() {
        for (Vector<TrajPoint> trajPointVector:trajs) {
            trajPointVector.clear();
        }
        trajs.clear();
    }
}
