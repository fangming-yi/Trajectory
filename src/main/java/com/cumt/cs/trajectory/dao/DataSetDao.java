package com.cumt.cs.trajectory.dao;

import com.cumt.cs.trajectory.model.DataSet;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangming.yi on 2017/5/13.
 */
@Repository
public interface DataSetDao {

    int insertDataSet(DataSet dataSet);
    int deleteDataSet(int id);
    int updateDataSet(DataSet dataSet);
    DataSet selectDataSetById(int id);
    List<DataSet> selectDataSetByStatus(int status);
    List<DataSet> selectDataSetByUserId(String userId);

}
