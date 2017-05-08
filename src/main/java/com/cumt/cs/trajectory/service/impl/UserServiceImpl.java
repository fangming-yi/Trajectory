package com.cumt.cs.trajectory.service.impl;

import com.cumt.cs.trajectory.dao.AdminDao;
import com.cumt.cs.trajectory.dao.UserDao;
import com.cumt.cs.trajectory.model.Admin;
import com.cumt.cs.trajectory.model.User;
import com.cumt.cs.trajectory.model.enums.UserRole;
import com.cumt.cs.trajectory.service.UserService;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by fangming.yi on 2017/5/8.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private AdminDao adminDao;

    private static final String USERID_ATTRIBUTE = "userId";
    @Override
    public UserRole  getUserRoleAccordingToCookies(HttpServletRequest httpRequest) {
        Preconditions.checkNotNull(httpRequest);
        return queryUserRole(getUserAccordingToCookiesWithCheck(httpRequest));
    }

    @Override
    public User getUserAccordingToCookiesWithCheck(HttpServletRequest request) {
        String userRtx = getUserIdAccordingToCookies(request);
        if (userRtx == null) {
            return null;
        }
        return getUserByUserIdFromDB(userRtx);
    }

    @Override
    public String getUserIdAccordingToCookies(HttpServletRequest request) {
        String userId = getUserIdCookie(request);
        if (userId == null) {
            return null;
        }

        User user = userDao.getUserByUserId(userId);
        if (user == null) {
            return null;
        }

        Date expireDate = DateUtils.addDays(user.getLastLoginTime(), 1);
        if (expireDate.before(new Date())) {
            // 登录过期
            return null;
        }

        return userId;
    }

    @Override
    public String getUserIdCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (USERID_ATTRIBUTE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }

    @Override
    public User getUserByUserIdFromDB(String userId) {
        User user=userDao.getUserByUserId(userId);
        if(user==null){
            return null;
        }
        Admin admin=adminDao.selectAdminByUserId(userId);
        if(admin==null){
            user.setAdmin(false);
        }else {
            user.setAdmin(true);
        }
        return user;
    }

    private UserRole queryUserRole(User user) {
        if (user == null) {
            return UserRole.NO_LOGIN;
        }

        if (user.isAdmin()) {
            return UserRole.ADMIN;
        }

        return UserRole.NORMAL;
    }
}
