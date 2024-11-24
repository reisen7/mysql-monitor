package com.fc.v2.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.service.monitor.InnodbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName InnodbController
 * @Author reisen
 * @Description
 * @date 2024年11月24日
 **/
@Api(value = "数据库集群")
@Controller
@RequestMapping("/InnodbController")
public class InnodbController extends BaseController {

    private String prefix = "gen/innodb";

    @ApiOperation(value = "获取状态", notes = "获取状态")
    @GetMapping("/status/{serverId}")
    @SaCheckPermission("gen:innodb:view")
    @ResponseBody
    public AjaxResult getStatus(@PathVariable Long serverId) {
        return innodbService.getStatus(serverId);
    }

    @ApiOperation(value = "页面跳转", notes = "页面跳转")
    @GetMapping("/view")
    @SaCheckPermission("gen:innodb:view")
    public String toView(ModelMap model) {
        return prefix + "/innodbStatus";
    }
}
