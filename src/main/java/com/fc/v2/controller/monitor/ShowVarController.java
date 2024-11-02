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
     * 分页
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
     * 修改
     * @param varName 变量名称
     * @param var 变量值
     * @return String
     * @author reisen
     */
    @ApiOperation(value = "修改页面", notes = "修改页面")
    @GetMapping("/edit/{varName}/{var}")
    @SaCheckPermission("gen:showVar:edit")
    public String edit(@PathVariable("varName") String varName, @PathVariable("var") String var, ModelMap map)
    {
        map.put("var",var);
        map.put("varName",varName);
        return prefix + "/edit";
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


    /**
     * 设置全局变量值
     * @Title: setGlobalVariables
     * @param serverId
     * @param name
     * @param value
     * @return
     * @throws
     */
    @ApiOperation(value = "setGlobalVariables", notes = "设置全局变量值")
    @GetMapping(value = "/{serverId}/globalVariables/{name}/{value}/")
    @ResponseBody
    public AjaxResult setGlobalVariables(@PathVariable Long serverId, @PathVariable String name, @PathVariable String value)
    {
        return monitorServerService.setGlobalVariables(serverId, name, value);
    }

}
