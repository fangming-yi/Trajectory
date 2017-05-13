package com.cumt.cs.trajectory.controller.admin;

import com.cumt.cs.trajectory.model.resultType.QJsonResult;
import com.cumt.cs.trajectory.service.TrajectoryManager;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import qunar.web.spring.annotation.JsonBody;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by fangming.yi on 2017/5/8.
 */
@Controller
@RequestMapping("admin")
public class AdminTrajectoryManagementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTrajectoryManagementController.class);

    @Resource
    private TrajectoryManager trajectoryManager;

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @JsonBody
    public Object uploadTrajectoryFiles(@RequestParam MultipartFile[] multipartFiles) {
        Preconditions.checkArgument(multipartFiles != null && multipartFiles.length > 0, "上传文件个数要大于0");
        try {
            boolean status = trajectoryManager.saveTrajectoryFiles(multipartFiles);
        } catch (IOException e) {
            LOGGER.error("保存轨迹文件失败");
            return QJsonResult.ExcetionHappened("保存文件异常");
        }
        return QJsonResult.UPLOAD_FILES_SUCCESS;
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @JsonBody
    public Object testJson(){
        return QJsonResult.UniversalError("测试测试");
    }
}
