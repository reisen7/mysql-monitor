package com.fc.v2.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.model.custom.Tablepar;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ShowVarController
 * @Author reisen
 * @Description
 * @date 2024年11月01日
 * description 显示数据库的参数
 **/
@Api(value = "数据库参数")
@Controller
@RequestMapping("/ShowVarController")
public class ShowVarController extends BaseController {

    private String prefix = "gen/showVarController";

    /**
     * 数据库连接页面展示
     * @param model
     * @return String
     * @author reisen
     */
    @ApiOperation(value = "分页跳转", notes = "分页跳转")
    @GetMapping("/view")
    @SaCheckPermission("gen:showVar:view")
    public String view(ModelMap model)
    {
        return prefix + "/list";
    }

    /**
     * 获取全局变量
     * @Title: getGlobalVariables
     * @param serverId
     * @param tablepar
     * @return
     * @throws
     */
    @ApiOperation(value = "getGlobalVariables", notes = "获取全局变量")
    @GetMapping(value = "/{serverId}/globalVariables")
    @ResponseBody
    public AjaxResult getGlobalVariables(@PathVariable Long serverId, Tablepar tablepar)
    {
        if (tablepar.getSearchText() == null){
            tablepar.setSearchText("");
        }
        return monitorServerService.getGlobalVariables(serverId, tablepar.getSearchText());
    }

}