package com.fc.v2.service.monitor;

import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import com.fc.v2.common.base.BaseService;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.auto.MonConnectMapper;
import com.fc.v2.model.auto.MonConnect;
import com.fc.v2.model.auto.MonConnectExample;
import com.fc.v2.model.custom.Tablepar;
import com.fc.v2.util.SnowflakeIdWorker;
import com.fc.v2.util.StringUtils;

/**
 * 数据库连接 MonConnectService
 * @Title: MonConnectService.java
 * @Package com.fc.v2.service
 * @author reisen_自动生成
 * @date 2024-08-16 17:43:36
 **/
@Service
public class MonConnectService implements BaseService<MonConnect, MonConnectExample>{
	@Autowired
	private MonConnectMapper monConnectMapper;
	
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	 public PageInfo<MonConnect> list(Tablepar tablepar,MonConnect monConnect){
	        MonConnectExample testExample=new MonConnectExample();
			//搜索
			if(StrUtil.isNotEmpty(tablepar.getSearchText())) {//小窗体
	        	testExample.createCriteria().andLikeQuery2(tablepar.getSearchText());
	        }else {//大搜索
	        	testExample.createCriteria().andLikeQuery(monConnect);
	        }
			//表格排序
			//if(StrUtil.isNotEmpty(tablepar.getOrderByColumn())) {
	        //	testExample.setOrderByClause(StringUtils.toUnderScoreCase(tablepar.getOrderByColumn()) +" "+tablepar.getIsAsc());
	        //}else{
	        //	testExample.setOrderByClause("id ASC");
	        //}
	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<MonConnect> list= monConnectMapper.selectByExample(testExample);
	        PageInfo<MonConnect> pageInfo = new PageInfo<MonConnect>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
			
			Integer[] integers = ConvertUtil.toIntArray(",", ids);
			List<Integer> stringB = Arrays.asList(integers);
			MonConnectExample example=new MonConnectExample();
			example.createCriteria().andIdIn(stringB);
			return monConnectMapper.deleteByExample(example);
			
		
	}
	
	
	@Override
	public MonConnect selectByPrimaryKey(String id) {
		
			Integer id1 = Integer.valueOf(id);
			return monConnectMapper.selectByPrimaryKey(id1);
		
	}

	
	@Override
	public int updateByPrimaryKeySelective(MonConnect record) {
		return monConnectMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(MonConnect record) {
		
		record.setId(null);
		
		
		return monConnectMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(MonConnect record, MonConnectExample example) {
		
		return monConnectMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(MonConnect record, MonConnectExample example) {
		
		return monConnectMapper.updateByExample(record, example);
	}

	@Override
	public List<MonConnect> selectByExample(MonConnectExample example) {
		
		return monConnectMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(MonConnectExample example) {
		
		return monConnectMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(MonConnectExample example) {
		
		return monConnectMapper.deleteByExample(example);
	}
	
	/**
	 * 修改权限状态展示或者不展示
	 * @param monConnect
	 * @return
	 */
	public int updateVisible(MonConnect monConnect) {
		return monConnectMapper.updateByPrimaryKeySelective(monConnect);
	}


}
