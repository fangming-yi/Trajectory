package com.cumt.cs.trajectory.service;

import com.cumt.cs.trajectory.model.User;
import com.cumt.cs.trajectory.model.enums.UserRole;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fangming.yi on 2017/5/8.
 */
public interface UserService {

    /**
     * 根据cookie在数据库中查找用户角色,如果用户不存在，返回NO_LOGIN
     *
     * @param httpRequest
     * @return
     */
    UserRole getUserRoleAccordingToCookies(HttpServletRequest httpRequest);

    /**
     * 根据cookie获得用户信息
     *
     * @param request
     * @return
     */
    User getUserAccordingToCookiesWithCheck(HttpServletRequest request);

    @Transactional
    String getUserIdAccordingToCookies(HttpServletRequest request);

    String getUserIdCookie(HttpServletRequest request);

    User getUserByUserIdFromDB(String userId);
}
