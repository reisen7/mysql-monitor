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
import com.fc.v2.mapper.auto.MonClusterMapper;
import com.fc.v2.model.monitor.MonCluster;
import com.fc.v2.model.monitor.MonClusterExample;
import com.fc.v2.model.custom.Tablepar;

/**
 * 数据库集群 MonClusterService
 * @Title: MonClusterService.java
 * @Package com.fc.v2.service
 * @author reisen_自动生成
 * @date 2024-08-16 18:20:36
 **/
@Service
public class MonClusterService implements BaseService<MonCluster, MonClusterExample>{
	@Autowired
	private MonClusterMapper monClusterMapper;
	
	
	/**
	 * 分页查询
	 * @param
	 * @param
	 * @return
	 */
	 public PageInfo<MonCluster> list(Tablepar tablepar,MonCluster monCluster){
	        MonClusterExample testExample=new MonClusterExample();
			//搜索
			if(StrUtil.isNotEmpty(tablepar.getSearchText())) {//小窗体
	        	testExample.createCriteria().andLikeQuery2(tablepar.getSearchText());
	        }else {//大搜索
	        	testExample.createCriteria().andLikeQuery(monCluster);
	        }
			//表格排序
			//if(StrUtil.isNotEmpty(tablepar.getOrderByColumn())) {
	        //	testExample.setOrderByClause(StringUtils.toUnderScoreCase(tablepar.getOrderByColumn()) +" "+tablepar.getIsAsc());
	        //}else{
	        //	testExample.setOrderByClause("id ASC");
	        //}
	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<MonCluster> list= monClusterMapper.selectByExample(testExample);
	        PageInfo<MonCluster> pageInfo = new PageInfo<MonCluster>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
			
			Integer[] integers = ConvertUtil.toIntArray(",", ids);
			List<Integer> stringB = Arrays.asList(integers);
			MonClusterExample example=new MonClusterExample();
			example.createCriteria().andIdIn(stringB);
			return monClusterMapper.deleteByExample(example);
			
		
	}
	
	
	@Override
	public MonCluster selectByPrimaryKey(String id) {
		
			Integer id1 = Integer.valueOf(id);
			return monClusterMapper.selectByPrimaryKey(id1);
		
	}

	
	@Override
	public int updateByPrimaryKeySelective(MonCluster record) {
		return monClusterMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(MonCluster record) {
		
		record.setId(null);
		
		
		return monClusterMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(MonCluster record, MonClusterExample example) {
		
		return monClusterMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(MonCluster record, MonClusterExample example) {
		
		return monClusterMapper.updateByExample(record, example);
	}

	@Override
	public List<MonCluster> selectByExample(MonClusterExample example) {
		
		return monClusterMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(MonClusterExample example) {
		
		return monClusterMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(MonClusterExample example) {
		
		return monClusterMapper.deleteByExample(example);
	}
	
	/**
	 * 修改权限状态展示或者不展示
	 * @param monCluster
	 * @return
	 */
	public int updateVisible(MonCluster monCluster) {
		return monClusterMapper.updateByPrimaryKeySelective(monCluster);
	}


}
