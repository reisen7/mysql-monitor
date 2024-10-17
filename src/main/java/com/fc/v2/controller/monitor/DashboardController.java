package com.fc.v2.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName DashboardController
 * @Author reisen
 * @Description
 * @date 2024年10月17日
 **/
@Api(value = "监控面板")
@Controller
@RequestMapping("/DashboardController")
public class DashboardController {

    private String prefix = "mysql/";

    /**
     * 页面展示
     * @param model
     * @return String
     * @author reisen
     */
    @ApiOperation(value = "页面跳转", notes = "面板查看")
    @GetMapping("/view")
    @SaCheckPermission("gen:dashboard:view")
    public String view(ModelMap model)
    {
        return prefix + "/dashboard";
    }


}
