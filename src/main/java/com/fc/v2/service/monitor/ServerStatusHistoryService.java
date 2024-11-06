package com.fc.v2.service.monitor;

import java.util.List;
import java.util.Arrays;

import com.fc.v2.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import com.fc.v2.common.base.BaseService;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.mysql.ServerStatusHistoryMapper;
import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import com.fc.v2.model.custom.Tablepar;

/**
 * 数据库状态历史表 ServerStatusHistoryService
 * @Title: ServerStatusHistoryService.java
 * @Package com.fc.v2.service
 * @author reisen_自动生成
 * @Description
 * @date 2024-10-24 11:19:41
 **/
@Service
public class ServerStatusHistoryService  implements BaseService<ServerStatusHistory, ServerStatusHistoryExample> {

	@Autowired
	private ServerStatusHistoryMapper serverStatusHistoryMapper;
	
	
	/**
	 * 分页查询
	 * @param tablepar
	 * @param
	 * @return
	 */
	 public PageInfo<ServerStatusHistory> list(Tablepar tablepar,ServerStatusHistory serverStatusHistory){
	        ServerStatusHistoryExample testExample=new ServerStatusHistoryExample();
			//搜索
			if(StrUtil.isNotEmpty(tablepar.getSearchText())) {//小窗体
	        	testExample.createCriteria().andLikeQuery2(tablepar.getSearchText());
	        }else {//大搜索
	        	testExample.createCriteria().andLikeQuery(serverStatusHistory);
	        }
			//表格排序
			//if(StrUtil.isNotEmpty(tablepar.getOrderByColumn())) {
	        //	testExample.setOrderByClause(StringUtils.toUnderScoreCase(tablepar.getOrderByColumn()) +" "+tablepar.getIsAsc());
	        //}else{
	        //	testExample.setOrderByClause("id ASC");
	        //}
	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<ServerStatusHistory> list= serverStatusHistoryMapper.selectByExample(testExample);
	        PageInfo<ServerStatusHistory> pageInfo = new PageInfo<ServerStatusHistory>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
		
			String[] strings = ConvertUtil.toStrArray(",", ids);
			List<String> stringB = Arrays.asList(strings);
			ServerStatusHistoryExample example=new ServerStatusHistoryExample();
			example.createCriteria().andIdIn(stringB);
			return serverStatusHistoryMapper.deleteByExample(example);

	}
	
	
	@Override
	public ServerStatusHistory selectByPrimaryKey(String id) {

			return serverStatusHistoryMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(ServerStatusHistory record) {
		return serverStatusHistoryMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(ServerStatusHistory record) {
		record.setId(SnowflakeIdWorker.getUUID());
		return serverStatusHistoryMapper.insertSelective(record);
	}


	/**
	 * 添加
	 */
	public int insert(ServerStatusHistory record) {
		record.setId(SnowflakeIdWorker.getUUID());
		return serverStatusHistoryMapper.insert(record);
	}
	
	
	@Override
	public int updateByExampleSelective(ServerStatusHistory record, ServerStatusHistoryExample example) {
		
		return serverStatusHistoryMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(ServerStatusHistory record, ServerStatusHistoryExample example) {
		
		return serverStatusHistoryMapper.updateByExample(record, example);
	}

	@Override
	public List<ServerStatusHistory> selectByExample(ServerStatusHistoryExample example) {
		
		return serverStatusHistoryMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(ServerStatusHistoryExample example) {
		
		return serverStatusHistoryMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(ServerStatusHistoryExample example) {
		
		return serverStatusHistoryMapper.deleteByExample(example);
	}
	
	/**
	 * 修改权限状态展示或者不展示
	 * @param serverStatusHistory
	 * @return
	 */
	public int updateVisible(ServerStatusHistory serverStatusHistory) {
		return serverStatusHistoryMapper.updateByPrimaryKeySelective(serverStatusHistory);
	}


}
