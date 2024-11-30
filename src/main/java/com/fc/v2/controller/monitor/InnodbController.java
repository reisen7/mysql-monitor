package com.fc.v2.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.dto.PagedDto;
import com.fc.v2.dto.Processlist;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.mysql.InnodbLockWaits;
import com.fc.v2.model.mysql.InnodbTrx;
import com.fc.v2.service.monitor.InnodbService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName InnodbController
 * @Author reisen
 * 参考： https://gitee.com/redtie/mycateye
 * @Description
 * @date 2024年11月24日
 **/
@Api(value = "Innodb")
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

    @ApiOperation(value = "事务页面", notes = "页面跳转")
    @GetMapping("/transaction/view")
    @SaCheckPermission("gen:transaction:view")
    public String toTransactionView(ModelMap model) {
        return prefix + "/transaction";
    }

    @ApiOperation(value = "事务详情", notes = "事务详情")
    @GetMapping("/trx/{serverId}")
    @ResponseBody
    @SaCheckPermission("gen:transaction:view")
    public ResultTable getTransaction(@PathVariable Long serverId, Tablepar tablepar) {
        PageInfo<InnodbTrx> page = innodbService.getInnodbTrxs(serverId, tablepar);
        return pageTable(page.getList(),page.getTotal());
    }

    @RequestMapping("/lockwaits/view")
    @SaCheckPermission("gen:lock:view")
    public String toLockView(ModelMap model) {
        return prefix + "/lockwaits";
    }


    @RequestMapping("/lockwaits/{serverId}")
    @SaCheckPermission("gen:lock:view")
    @ResponseBody
    public ResultTable getLockWaits(@PathVariable Long serverId, Tablepar tablepar) {
        PageInfo<InnodbLockWaits> page = innodbService.getInnodbLockWaits(serverId, tablepar);
        return pageTable(page.getList(),page.getTotal());
    }




}
