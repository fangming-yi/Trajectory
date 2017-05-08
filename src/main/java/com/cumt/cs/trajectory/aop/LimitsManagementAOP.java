package com.cumt.cs.trajectory.aop;

import com.cumt.cs.trajectory.model.enums.UserRole;
import com.cumt.cs.trajectory.model.resultType.QJsonResult;
import com.cumt.cs.trajectory.service.UserService;
import com.google.common.collect.Sets;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import qunar.api.pojo.CodeMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by fangming.yi on 2017/5/8.
 */
public class LimitsManagementAOP {
    private static Logger logger = LoggerFactory.getLogger(LimitsManagementAOP.class);
    @Resource
    private UserService userService;
    // 包含管理员json数据接口的集合
    private static Set<String> superAdminUrls;

    static {
        superAdminUrls= Sets.newConcurrentHashSet();
        superAdminUrls.add("admin/buildKDTree");
    }

    public Object around(ProceedingJoinPoint proceedingJoinPoint) {

        logger.info("### pointcut function: {}", proceedingJoinPoint.toString());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        if (request == null) {
            return getProceed(proceedingJoinPoint);
        }
        String requestURI = request.getRequestURI();
        logger.info("### request url: {}", requestURI);

        // 获取用户信息
        UserRole userRole = userService.getUserRoleAccordingToCookies(request);

        int flag = 0; // 0通过，1未登陆，2无权限
        if (requestURI.startsWith("/user")) {
            // 以/user开头的，必须登陆才能看
            if (userRole == UserRole.NO_LOGIN) {
                flag = 1;
            }
        } else if (superAdminUrls.contains(requestURI)) {
            // 超级管理员权限控制
            if (userRole == UserRole.NO_LOGIN) {
                flag = 1;
            } else if (userRole != UserRole.SUPER) {
                flag = 2;
            }
        } else if (requestURI.startsWith("/admin")) {
            // 以/admin开头的，必须是管理员
            if (userRole == UserRole.NO_LOGIN) {
                flag = 1;
            } else if (userRole == UserRole.NORMAL) {
                flag = 2;
            }
        }
        if (flag == 1) {
            return QJsonResult.NO_LOGIN;
        } else if (flag == 2) {
            return QJsonResult.NO_LIMITS;
        }

        return getProceed(proceedingJoinPoint);
    }

    private Object getProceed(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            logger.error("exception found: ", e);
            return QJsonResult.ExcetionHappened("操作失败！");
        }
        // 如果异常被JsonBody处理，需要将在此返回的message中的一场信息转化为用户可见文字
        if (result instanceof CodeMessage) {
            CodeMessage codeMessage = (CodeMessage) result;
            if (codeMessage.getStatus() == QJsonResult.EXCEPTION.getStatus()) {
                logger.info("json result error: {}", codeMessage.getMessage());
                return QJsonResult.EXCEPTION;
            }
        } else if (result instanceof QJsonResult) {
            QJsonResult qJsonResult = (QJsonResult) result;
            if (qJsonResult.getStatus() == QJsonResult.EXCEPTION.getStatus()) {
                logger.info("json result error: {}", qJsonResult.getMessage());
                return QJsonResult.EXCEPTION;
            }
        }
        return result;
    }
}
