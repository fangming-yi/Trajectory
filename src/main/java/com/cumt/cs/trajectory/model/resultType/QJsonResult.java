package com.cumt.cs.trajectory.model.resultType;

import qunar.api.pojo.CodeMessage;

/**
 * Created by fangming.yi on 2017/5/8.
 */
public class QJsonResult implements CodeMessage{
    private int status;
    private String message;
    private Object data;

    public static final QJsonResult EXCEPTION = new QJsonResult(-1, "操作失败");
    // 常用的错误状态
    public static final QJsonResult COMMON_ERROR = new QJsonResult(10, "通用错误");
    public static final QJsonResult NO_LIMITS = new QJsonResult(20, "权限不够");
    public static final QJsonResult NO_LOGIN = new QJsonResult(21, "用户未登录");
    public static final QJsonResult ARG_NOt_FOUND = new QJsonResult(22, "未查到该用户");

    public QJsonResult() {
    }

    public QJsonResult(Object data) {
        this.data = data;
    }

    public QJsonResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public QJsonResult(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public QJsonResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Object getData() {
        return null;
    }

    public static QJsonResult ExcetionHappened(String message) {
        return new QJsonResult(-1, message, null);
    }
}
