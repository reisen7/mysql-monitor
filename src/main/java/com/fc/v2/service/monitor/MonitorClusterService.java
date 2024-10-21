package com.fc.v2.service.monitor;

import java.math.BigInteger;
import java.util.*;

import com.fc.v2.dto.*;
import com.fc.v2.mapper.mysql.MonitorServerMapper;
import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.model.monitor.MonitorServerExample;
import com.fc.v2.model.mysql.Constant;
import com.fc.v2.service.monitor.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import com.fc.v2.common.base.BaseService;
import com.fc.v2.common.support.ConvertUtil;
import com.fc.v2.mapper.mysql.MonitorClusterMapper;
import com.fc.v2.model.monitor.MonitorCluster;
import com.fc.v2.model.monitor.MonitorClusterExample;
import com.fc.v2.model.custom.Tablepar;

/**
 * 数据库集群 MonitorClusterService
 * @Title: MonitorClusterService.java
 * @Package com.fc.v2.service
 * @author reisen_自动生成
 * @Description
 * @date 2024-10-21 10:58:36
 **/
@Service
public class MonitorClusterService extends AbstractService implements BaseService<MonitorCluster, MonitorClusterExample>{
	@Autowired
	private MonitorClusterMapper monitorClusterMapper;

	@Autowired
	private MonitorServerMapper monitorServerMapper;
	/* (non-Javadoc)
	 * @see io.mycat.eye.agent.service.MonitorClusterService#getAllClusters()
	 */

	public List<MonitorCluster> getAllClusters()
	{
		List<MonitorCluster> list = monitorClusterMapper.selectByExample(new MonitorClusterExample());
		return list;
	}
	public ClusterNetworkDto getClusterNetworkById(Long clusterId)
	{
		ClusterNetworkDto clusterNetworkDto=new ClusterNetworkDto();
		MonitorServerExample monitorServerExample=new MonitorServerExample();
		monitorServerExample.createCriteria().andClusterIdEqualTo(clusterId);
		List<MonitorServer> monitorServers = monitorServerMapper.selectByExample(monitorServerExample);
		clusterNetworkDto.setMonitorServers(monitorServers);
		List<Relation> relations=getRelations(monitorServers);
		clusterNetworkDto.setRelations(relations);
		return clusterNetworkDto;
	}

	/**
	 * 获取节点的复制关系
	 * @Title: getRelation
	 * @param
	 * @return
	 * @throws
	 */
	private List<Relation> getRelations(List<MonitorServer> monitorServers){
		String showSlaveHostsSql="show slave hosts";
		String showVariablesSql="show variables like 'server_id'";
		String showSlaveStatusSql="show slave status";
		List<Relation> relations=new ArrayList<>();
		for (MonitorServer fromMonitorServer : monitorServers)
		{
			QueryResult<List<Map<Object,Object>>> queryResultSlaveHosts = getQueryResult(fromMonitorServer.getId(), showSlaveHostsSql);
			//当数据库查询异常，继续执行下一个节点
			if (!queryResultSlaveHosts.isSuccess())
			{
				logger.error(queryResultSlaveHosts.getException());
				continue;
			}
			//当数据库查询异常，继续执行下一个节点
			if (queryResultSlaveHosts.getData().isEmpty())
			{
				continue;
			}
			//获取从库节点列表
			List<Map<Object,Object>> listSlaveHosts = queryResultSlaveHosts.getData();
			for (Map<Object, Object> map : listSlaveHosts)
			{
				//检查从库节点是否在已添加的节点当中
				String toServerId = String.valueOf((Long)map.get("Server_id"));
				for (MonitorServer toMonitorServer : monitorServers)
				{
					QueryResult<List<Map<Object, Object>>> queryResultVariables = getQueryResult(toMonitorServer.getId(), showVariablesSql);
					//如果数据库操作异常或结果集为空，则继续检查下一个节点
					if ((!queryResultVariables.isSuccess())||queryResultVariables.getData().isEmpty())
					{
						continue;
					}
					List<Map<Object, Object>> listVariables = queryResultVariables.getData();
					Map<Object, Object> mapVariables = listVariables.get(0);
					String _toServerId=(String)mapVariables.get("Value");
					//当被检查到的节点serverid和当前从库的serverid一致时，则保存复制关系
					if (toServerId.equals(_toServerId))
					{
						//查询从库的状态
						QueryResult<List<Map<Object,Object>>> queryResultSlaveStatus = getQueryResult(toMonitorServer.getId(), showSlaveStatusSql);
						List<Map<Object,Object>> listSlaveStatus = queryResultSlaveStatus.getData();
						Map<Object, Object> mapSlaveStatus = listSlaveStatus.get(0);
						SlaveStatus slaveStatus=new SlaveStatus();
						slaveStatus.setLastIOError((String) mapSlaveStatus.get("Last_IO_Error"));
						slaveStatus.setLastSQLError((String) mapSlaveStatus.get("Last_SQL_Error"));
						slaveStatus.setMasterHost((String) mapSlaveStatus.get("Master_Host"));
						slaveStatus.setMasterLogFile((String) mapSlaveStatus.get("Master_Log_File"));
						slaveStatus.setReadMasterLogPos(
								String.valueOf((BigInteger) mapSlaveStatus.get("Read_Master_Log_Pos")));
						slaveStatus.setSecondsBehindMaster(
								String.valueOf((BigInteger) mapSlaveStatus.get("Seconds_Behind_Master")));
						slaveStatus
								.setSlaveIORunning((String) mapSlaveStatus.get("Slave_IO_Running"));
						slaveStatus
								.setSlaveSQLRunning((String) mapSlaveStatus.get("Slave_SQL_Running"));
						Relation relation=new Relation(fromMonitorServer, toMonitorServer,slaveStatus);
						relations.add(relation);
						break;
					}
				}
			}
		}
		return relations;
	}

	public RestResponse<String> saveCluster(Long id, String name)
	{
		RestResponse<String> restResponse=new RestResponse<>();
		MonitorCluster monitorCluster=new MonitorCluster();
		try
		{
			//如果id为0，则为插入
			if (id== Constant.ZERO)
			{
				monitorCluster.setCreateTime(new Date());
				monitorCluster.setName(name);
				monitorClusterMapper.insert(monitorCluster);
			}
			//如果id不为0，则为编辑
			else{
				monitorCluster.setCreateTime(new Date());
				monitorCluster.setName(name);
				monitorCluster.setId(id);
				monitorClusterMapper.updateByPrimaryKey(monitorCluster);
			}
			restResponse.setCode(Constant.SUCCESS_CODE);
			restResponse.setMessage(Constant.SUCCESS_MESSAGE);
		}
		catch (Exception e)
		{
			restResponse.setCode(Constant.FAIL_CODE);
			restResponse.setMessage(e.getMessage());
		}
		return restResponse;
	}

	public RestResponse<String> deleteCluster(Long id)
	{
		RestResponse<String> restResponse=new RestResponse<>();
		try
		{
			monitorClusterMapper.deleteByPrimaryKey(id);
			restResponse.setCode(Constant.SUCCESS_CODE);
			restResponse.setMessage(Constant.SUCCESS_MESSAGE);
		}
		catch (Exception e)
		{
			restResponse.setCode(Constant.FAIL_CODE);
			restResponse.setMessage(e.getMessage());
		}

		return restResponse;
	}
	/**
	 * 分页查询
	 * @param tablepar
	 * @param
	 * @return
	 */
	 public PageInfo<MonitorCluster> list(Tablepar tablepar,MonitorCluster monitorCluster){
	        MonitorClusterExample testExample=new MonitorClusterExample();
			//搜索
			if(StrUtil.isNotEmpty(tablepar.getSearchText())) {//小窗体
	        	testExample.createCriteria().andLikeQuery2(tablepar.getSearchText());
	        }else {//大搜索
	        	testExample.createCriteria().andLikeQuery(monitorCluster);
	        }
			//表格排序
			//if(StrUtil.isNotEmpty(tablepar.getOrderByColumn())) {
	        //	testExample.setOrderByClause(StringUtils.toUnderScoreCase(tablepar.getOrderByColumn()) +" "+tablepar.getIsAsc());
	        //}else{
	        //	testExample.setOrderByClause("id ASC");
	        //}
	        PageHelper.startPage(tablepar.getPage(), tablepar.getLimit());
	        List<MonitorCluster> list= monitorClusterMapper.selectByExample(testExample);
	        PageInfo<MonitorCluster> pageInfo = new PageInfo<MonitorCluster>(list);
	        return  pageInfo;
	 }

	@Override
	public int deleteByPrimaryKey(String ids) {
		
			Long[] integers = ConvertUtil.toLongArray(",", ids);
			List<Long> stringB = Arrays.asList(integers);
			MonitorClusterExample example=new MonitorClusterExample();
			example.createCriteria().andIdIn(stringB);
			return monitorClusterMapper.deleteByExample(example);
		
		
	}
	
	
	@Override
	public MonitorCluster selectByPrimaryKey(String id) {
		
			Long id1 = Long.valueOf(id);
			return monitorClusterMapper.selectByPrimaryKey(id1);
			
		
	}

	
	@Override
	public int updateByPrimaryKeySelective(MonitorCluster record) {
		return monitorClusterMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 添加
	 */
	@Override
	public int insertSelective(MonitorCluster record) {
		
		record.setId(null);
		
		
		return monitorClusterMapper.insertSelective(record);
	}
	
	
	@Override
	public int updateByExampleSelective(MonitorCluster record, MonitorClusterExample example) {
		
		return monitorClusterMapper.updateByExampleSelective(record, example);
	}

	
	@Override
	public int updateByExample(MonitorCluster record, MonitorClusterExample example) {
		
		return monitorClusterMapper.updateByExample(record, example);
	}

	@Override
	public List<MonitorCluster> selectByExample(MonitorClusterExample example) {
		
		return monitorClusterMapper.selectByExample(example);
	}

	
	@Override
	public long countByExample(MonitorClusterExample example) {
		
		return monitorClusterMapper.countByExample(example);
	}

	
	@Override
	public int deleteByExample(MonitorClusterExample example) {
		
		return monitorClusterMapper.deleteByExample(example);
	}
	
	/**
	 * 修改权限状态展示或者不展示
	 * @param monitorCluster
	 * @return
	 */
	public int updateVisible(MonitorCluster monitorCluster) {
		return monitorClusterMapper.updateByPrimaryKeySelective(monitorCluster);
	}


}
