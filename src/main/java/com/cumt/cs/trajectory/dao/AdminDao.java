package com.cumt.cs.trajectory.dao;

import com.cumt.cs.trajectory.model.Admin;

/**
 * Created by fangming.yi on 2017/5/8.
 */
public interface AdminDao {

    int insertAdmin(Admin admin);
    int deleteAdmin(int id);
    int updateAdmin(Admin admin);
    Admin selectAdminById(int id);
    Admin selectAdminByUserId(String userId);
    Admin selectAdminByUsername(String username);

}
