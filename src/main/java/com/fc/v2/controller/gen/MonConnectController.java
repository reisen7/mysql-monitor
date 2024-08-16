package com.fc.v2.controller.gen;

import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.model.auto.MonCluster;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.auto.MonConnect;
import com.fc.v2.service.MonConnectService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据库连接Controller
 * @ClassName: MonConnectController
 * @author fuce
 * @date 2024-08-16 17:43:36
 */
@Api(value = "数据库连接")
@Controller
@RequestMapping("/MonConnectController")
public class MonConnectController extends BaseController {
	
	private String prefix = "gen/monConnect";
	
	@Autowired
	private MonConnectService monConnectService;
	
	
	/**
	 * 数据库连接页面展示
	 * @param model
	 * @return String
	 * @author fuce
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@SaCheckPermission("gen:monConnect:view")
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
	@SaCheckPermission("gen:monConnect:list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar,MonConnect monConnect){
		PageInfo<MonConnect> page=monConnectService.list(tablepar,monConnect) ; 
		return pageTable(page.getList(),page.getTotal());
	}
	
	/**
     * 新增跳转
     */
	@ApiOperation(value = "新增跳转", notes = "新增跳转")
    @GetMapping("/add")
    public String add(ModelMap modelMap, Map<String,Object> map)
    {
		List<MonCluster> monClusterList = monClusterService.selectByExample(null);
		map.put("monClusterList",monClusterList);
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
	@SaCheckPermission("gen:monConnect:add")
	@ResponseBody
	public AjaxResult add(MonConnect monConnect){
		int b=monConnectService.insertSelective(monConnect);
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
	@SaCheckPermission("gen:monConnect:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=monConnectService.deleteByPrimaryKey(ids);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	
	/**
	 * 修改跳转
	 * @param id
	 * @param
	 * @return
	 */
	@ApiOperation(value = "修改跳转", notes = "修改跳转")
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map)
    {
        map.put("MonConnect", monConnectService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "数据库连接修改", action = "111")
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @SaCheckPermission("gen:monConnect:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MonConnect monConnect)
    {
		monConnect.setUpdateTime(new Date());
        return toAjax(monConnectService.updateByPrimaryKeySelective(monConnect));
    }
    
    
    /**
	 * 修改状态
	 * @param
	 * @return
	 */
    @PutMapping("/updateVisible")
	@ResponseBody
    public AjaxResult updateVisible(@RequestBody MonConnect monConnect){
		int i=monConnectService.updateVisible(monConnect);
		return toAjax(i);
	}

    
    

	
}
