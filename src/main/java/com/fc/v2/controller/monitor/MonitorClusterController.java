package com.fc.v2.controller.monitor;

import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.monitor.MonitorCluster;
import com.fc.v2.service.monitor.MonitorClusterService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 数据库集群Controller
 * @ClassName MonitorClusterController
 * @Description
 * @author reisen
 * @date 2024-10-21 10:58:36
 */
@Api(value = "数据库集群")
@Controller
@RequestMapping("/MonitorClusterController")
public class MonitorClusterController extends BaseController{
	
	private String prefix = "gen/monitorCluster";
	
	@Autowired
	private MonitorClusterService monitorClusterService;
	
	
	/**
	 * 数据库集群页面展示
	 * @param model
	 * @return String
	 * @author reisen
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@SaCheckPermission("gen:monitorCluster:view")
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
	//@Log(title = "数据库集群", action = "111")
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/list")
	@SaCheckPermission("gen:monitorCluster:list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar,MonitorCluster monitorCluster){
		PageInfo<MonitorCluster> page=monitorClusterService.list(tablepar,monitorCluster);
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
	//@Log(title = "数据库集群新增", action = "111")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/add")
	@SaCheckPermission("gen:monitorCluster:add")
	@ResponseBody
	public AjaxResult add(MonitorCluster monitorCluster){
		int b=monitorClusterService.insertSelective(monitorCluster);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 数据库集群删除
	 * @param ids
	 * @return
	 */
	//@Log(title = "数据库集群删除", action = "111")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	@SaCheckPermission("gen:monitorCluster:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=monitorClusterService.deleteByPrimaryKey(ids);
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
        map.put("MonitorCluster", monitorClusterService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "数据库集群修改", action = "111")
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @SaCheckPermission("gen:monitorCluster:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MonitorCluster monitorCluster)
    {
        return toAjax(monitorClusterService.updateByPrimaryKeySelective(monitorCluster));
    }
    
    
    /**
	 * 修改状态
	 * @param
	 * @return
	 */
    @PutMapping("/updateVisible")
	@ResponseBody
    public AjaxResult updateVisible(@RequestBody MonitorCluster monitorCluster){
		int i=monitorClusterService.updateVisible(monitorCluster);
		return toAjax(i);
	}

    
    

	
}
