package com.cumt.cs.trajectory.controller.admin;

import com.cumt.cs.trajectory.service.AdminService;
import com.cumt.cs.trajectory.service.TrajectoryManager;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Created by fangming.yi on 2017/5/8.
 */
@Controller
@RequestMapping("admin")
public class AdminTrajectoryManagementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminTrajectoryManagementController.class);

    @Resource
    private AdminService adminService;
    @Resource
    private TrajectoryManager trajectoryManager;

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public void uploadTrajectoryFiles(@RequestParam MultipartFile[] multipartFiles) {
        Preconditions.checkArgument(multipartFiles != null && multipartFiles.length > 0, "上传文件个数要大于0");
        trajectoryManager.saveTrajectoryFiles(multipartFiles);
    }
}
