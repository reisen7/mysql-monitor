package com.fc.v2.controller.gen;

import com.fc.v2.common.base.BaseController;
import com.fc.v2.common.domain.AjaxResult;
import com.fc.v2.common.domain.ResultTable;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.model.auto.MonCluster;
import com.fc.v2.service.MonClusterService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 数据库集群Controller
 * @ClassName: MonClusterController
 * @author fuce
 * @date 2024-08-16 18:20:36
 */
@Api(value = "数据库集群")
@Controller
@RequestMapping("/MonClusterController")
public class MonClusterController extends BaseController {
	
	private String prefix = "gen/monCluster";
	
	@Autowired
	private MonClusterService monClusterService;
	
	
	/**
	 * 数据库集群页面展示
	 * @param model
	 * @return String
	 * @author fuce
	 */
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/view")
	@SaCheckPermission("gen:monCluster:view")
    public String view(ModelMap model)
    {
        return prefix + "/list";
    }
	
	/**
	 * list集合
	 * @param tablepar
	 * @param searchText
	 * @return
	 */
	//@Log(title = "数据库集群", action = "111")
	@ApiOperation(value = "分页跳转", notes = "分页跳转")
	@GetMapping("/list")
	@SaCheckPermission("gen:monCluster:list")
	@ResponseBody
	public ResultTable list(Tablepar tablepar,MonCluster monCluster){
		PageInfo<MonCluster> page=monClusterService.list(tablepar,monCluster);
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
	@SaCheckPermission("gen:monCluster:add")
	@ResponseBody
	public AjaxResult add(MonCluster monCluster){
		int b=monClusterService.insertSelective(monCluster);
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
	@SaCheckPermission("gen:monCluster:remove")
	@ResponseBody
	public AjaxResult remove(String ids){
		int b=monClusterService.deleteByPrimaryKey(ids);
		if(b>0){
			return success();
		}else{
			return error();
		}
	}
	
	
	/**
	 * 修改跳转
	 * @param id
	 * @param mmap
	 * @return
	 */
	@ApiOperation(value = "修改跳转", notes = "修改跳转")
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map)
    {
        map.put("MonCluster", monClusterService.selectByPrimaryKey(id));

        return prefix + "/edit";
    }
	
	/**
     * 修改保存
     */
    //@Log(title = "数据库集群修改", action = "111")
	@ApiOperation(value = "修改保存", notes = "修改保存")
    @SaCheckPermission("gen:monCluster:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MonCluster monCluster)
    {
		monCluster.setUpdateTime(new Date());
        return toAjax(monClusterService.updateByPrimaryKeySelective(monCluster));
    }
    
    
    /**
	 * 修改状态
	 * @param record
	 * @return
	 */
    @PutMapping("/updateVisible")
	@ResponseBody
    public AjaxResult updateVisible(@RequestBody MonCluster monCluster){
		int i=monClusterService.updateVisible(monCluster);
		return toAjax(i);
	}

    
    

	
}
