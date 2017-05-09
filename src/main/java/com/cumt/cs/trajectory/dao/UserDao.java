package com.cumt.cs.trajectory.dao;

import com.cumt.cs.trajectory.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by fangming.yi on 2017/5/8.
 */
@Repository
public interface UserDao {

    int insertUser(User user);
    int deleteUserById(int id);
    int deleteUserByUserId(String userId);
    int updateUser(User user);
    User getUserByUserId(String userId);
    User getUserById(int id);

}
