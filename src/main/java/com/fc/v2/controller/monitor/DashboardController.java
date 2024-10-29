package com.fc.v2.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.model.monitor.MonitorServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName DashboardController
 * @Author reisen
 * @Description
 * @date 2024年10月17日
 **/
@Api(value = "监控面板")
@Controller
@RequestMapping("/DashboardController")
public class DashboardController extends BaseController {

    private String prefix = "mysql/";


    /**
     * 页面展示
     * @param model
     * @return String
     * @author reisen
     * @date 2024年10月24日
     */
    @ApiOperation(value = "页面跳转", notes = "面板查看")
    @GetMapping("/view")
    @SaCheckPermission("gen:dashboard:view")
    public String view(ModelMap model)
    {
        List<MonitorServer> monitorServerList = monitorServerService.selectByExample(null);
        model.put("monitorServerList",monitorServerList);
        return prefix + "/dashboard";
    }

    /**
     * 获取控制台概要信息
     * @Title: getDashboardOverview
     * @param serverId
     * @date 2024年10月24日
     */
    @ApiOperation(value = "控制台概要信息", notes = "面板查看")
    @GetMapping("/dashboard-overview/{serverId}")
    @ResponseBody
    public AjaxResult getDashboardOverview(@PathVariable Long serverId) {
        return dashboardService.getDashboardOverview(serverId);
    }

    /**
     * 获取控制台显示数据
     * @Title: getDashboardProcesslist
     * @param serverId
     * @return
     * @throws
     */
    @ApiOperation(value = "控制台显示进程数据", notes = "面板查看")
    @GetMapping("/processlist/{serverId}")
    @ResponseBody
    public AjaxResult getDashboardProcesslist(@PathVariable Long serverId) {
        return dashboardService.getDashboardProcesslist(serverId);
    }


}
