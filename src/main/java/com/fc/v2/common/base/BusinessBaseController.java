package com.fc.v2.common.base;

import com.fc.v2.service.monitor.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 数据库连接Controller
 * @ClassName: MonConnectController
 * @author reisen
 * @date 2024-08-16 17:43:36
 */
@Api(value = "业务基础")
@Controller
public class BusinessBaseController {


    @Autowired
    protected MonitorClusterService monitorClusterService;

    @Autowired
    protected MonitorServerService monitorServerService;

    @Autowired
    protected DashboardService dashboardService;

    @Autowired
    protected ServerStatusHistoryService serverStatusHistoryService;

    @Autowired
    protected InnodbService innodbService;

}
