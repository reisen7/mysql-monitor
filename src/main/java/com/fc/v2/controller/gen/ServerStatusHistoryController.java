package com.fc.v2.controller.gen;

import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.service.monitor.ServerStatusHistoryService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 数据库状态历史表Controller
 * @ClassName ServerStatusHistoryController
 * @Description
 * @author reisen
 * @date 2024-10-24 11:19:41
 */
@Api(value = "数据库状态历史表")
@Controller
@RequestMapping("/ServerStatusHistoryController")
public class ServerStatusHistoryController extends BaseController{
	
	private String prefix = "gen/serverStatusHistory";
	
	@Autowired
	private ServerStatusHistoryService serverStatusHistoryService;
	
	
	/**
	 * 数据库状态历史表页面展示
	 * @param model
	 * @return String
	 * @author reisen
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@SaCheckPermission("gen:serverStatusHistory:view")
    public String view(ModelMap model)
    {
        return prefix + "/list";
    }
	
	/**
	 * list集合
	 * @param tablepar
	 * @param
	 * @return
	 */
	//@Log(title = "数据库状态历史表", action = "111")
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/list")
	@SaCheckPermission("gen:serverStatusHistory:list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar,ServerStatusHistory serverStatusHistory){
		PageInfo<ServerStatusHistory> page=serverStatusHistoryService.list(tablepar,serverStatusHistory) ; 
		return pageTable(page.getList(),page.getTotal());
	}
	
	/**
     * 新增跳转
     */
	@ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap)
    {
        return prefix + "/add";
    }
	
    /**
     * 新增保存
     * @param 
     * @return
     */
	//@Log(title = "数据库状态历史表新增", action = "111")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/add")
	@SaCheckPermission("gen:serverStatusHistory:add")
	@ResponseBody
	public AjaxResult add(ServerStatusHistory serverStatusHistory){
		int b=serverStatusHistoryService.insertSelective(serverStatusHistory);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 数据库状态历史表删除
	 * @param ids
	 * @return
	 */
	//@Log(title = "数据库状态历史表删除", action = "111")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	@SaCheckPermission("gen:serverStatusHistory:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=serverStatusHistoryService.deleteByPrimaryKey(ids);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	
	/**
	 * 修改跳转
	 * @param id
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "修改跳转", notes = "修改跳转")
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map)
    {
        map.put("ServerStatusHistory", serverStatusHistoryService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "数据库状态历史表修改", action = "111")
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @SaCheckPermission("gen:serverStatusHistory:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ServerStatusHistory serverStatusHistory)
    {
        return toAjax(serverStatusHistoryService.updateByPrimaryKeySelective(serverStatusHistory));
    }
    
    
    /**
	 * 修改状态
	 * @param
	 * @return
	 */
    @PutMapping("/updateVisible")
	@ResponseBody
    public AjaxResult updateVisible(@RequestBody ServerStatusHistory serverStatusHistory){
		int i=serverStatusHistoryService.updateVisible(serverStatusHistory);
		return toAjax(i);
	}

    
    

	
}
