package com.cumt.cs.trajectory.model.resultType;

import com.google.common.base.Objects;
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

    public static final QJsonResult UPLOAD_FILES_SUCCESS = new QJsonResult(0, "轨迹文件上传成功");

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

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object getData() {
        return data;
    }


    public static QJsonResult ExcetionHappened(String message) {
        return new QJsonResult(-1, message, null);
    }

    /**
     * 包含通用错误信息的QJsonResult
     *
     * @param message 通用错误信息
     * @return
     */
    public static QJsonResult UniversalError(String message) {
        return new QJsonResult(10, message);
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("status", status).add("message", message).toString();
    }
}
