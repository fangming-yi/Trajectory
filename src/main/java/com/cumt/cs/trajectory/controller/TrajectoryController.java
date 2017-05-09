package com.cumt.cs.trajectory.controller;

import com.cumt.cs.trajectory.model.resultType.QJsonResult;
import com.cumt.cs.trajectory.service.TrajectoryManager;
import com.cumt.cs.trajectory.service.UserService;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import qunar.web.spring.annotation.JsonBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/5/3.
 */
@Controller
@RequestMapping("traj")
public class TrajectoryController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @JsonBody
    public Object testH(){
        return QJsonResult.UniversalError("测试");
    }


}
