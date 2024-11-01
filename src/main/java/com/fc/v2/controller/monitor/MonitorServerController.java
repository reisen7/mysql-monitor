package com.fc.v2.controller.monitor;

import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.model.monitor.MonitorCluster;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.service.monitor.MonitorServerService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据库连接Controller
 * @ClassName MonitorServerController
 * @Description
 * @author reisen
 * @date 2024-10-21 10:57:44
 */
@Api(value = "数据库连接")
@Controller
@RequestMapping("/MonitorServerController")
public class MonitorServerController extends BaseController{
	
	private String prefix = "gen/monitorServer";
	
	@Autowired
	private MonitorServerService monitorServerService;
	
	
	/**
	 * 数据库连接页面展示
	 * @param model
	 * @return String
	 * @author reisen
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@SaCheckPermission("gen:monitorServer:view")
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
	//@Log(title = "数据库连接", action = "111")
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/list")
	@SaCheckPermission("gen:monitorServer:list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar,MonitorServer monitorServer){
		PageInfo<MonitorServer> page=monitorServerService.list(tablepar,monitorServer) ; 
		return pageTable(page.getList(),page.getTotal());
	}


	@ApiOperation(value = "获取全部", notes = "获取全部")
	@GetMapping("/listAll")
	@ResponseBody
	public AjaxResult listAll(){
		List<MonitorServer> monitorServerList = monitorServerService.selectByExample(null);
		return AjaxResult.successData(200,monitorServerList);
	}

	
	/**
     * 新增跳转
     */
	@ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap, Map<String,Object> map)
    {
		List<MonitorCluster> monitorClusterList = monitorClusterService.selectByExample(null);
		map.put("monitorClusterList",monitorClusterList);
        return prefix + "/add";
    }
	
    /**
     * 新增保存
     * @param 
     * @return
     */
	//@Log(title = "数据库连接新增", action = "111")
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/add")
	@SaCheckPermission("gen:monitorServer:add")
	@ResponseBody
	public AjaxResult add(MonitorServer monitorServer){
		int b=monitorServerService.insertSelective(monitorServer);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	/**
	 * 数据库连接删除
	 * @param ids
	 * @return
	 */
	//@Log(title = "数据库连接删除", action = "111")
	@ApiOperation(value = "删除", notes = "删除")
	@DeleteMapping("/remove")
	@SaCheckPermission("gen:monitorServer:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=monitorServerService.deleteByPrimaryKey(ids);
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
        map.put("MonitorServer", monitorServerService.selectByPrimaryKey(id));
		List<MonitorCluster> monitorClusterList = monitorClusterService.selectByExample(null);
		map.put("monitorClusterList",monitorClusterList);
        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "数据库连接修改", action = "111")
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @SaCheckPermission("gen:monitorServer:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MonitorServer monitorServer)
    {
        return toAjax(monitorServerService.updateByPrimaryKeySelective(monitorServer));
    }
    
    
    /**
	 * 修改状态
	 * @param
	 * @return
	 */
    @PutMapping("/updateVisible")
	@ResponseBody
    public AjaxResult updateVisible(@RequestBody MonitorServer monitorServer){
		int i=monitorServerService.updateVisible(monitorServer);
		return toAjax(i);
	}

    
    

	
}
