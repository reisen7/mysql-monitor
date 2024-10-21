package com.fc.v2.service.monitor.impl;

import com.fc.v2.dto.*;
import com.fc.v2.mapper.mysql.MysqlClusterMapper;
import com.fc.v2.mapper.mysql.MysqlServerMapper;
import com.fc.v2.model.mysql.*;
import com.fc.v2.service.monitor.MysqlClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**   
 * @ClassName:  MysqlClusterServiceImpl   
 * @Description:MySQL集群 Service
 * @author: reisen
 * @date:   2024-10-16
 *     
 */
@Service
public class MysqlClusterServiceImpl extends AbstractService implements MysqlClusterService
{
    @Autowired
    private MysqlClusterMapper mysqlClusterMapper;
    @Autowired
    private MysqlServerMapper mysqlServerMapper;
    /* (non-Javadoc)
     * @see io.mycat.eye.agent.service.MysqlClusterService#getAllClusters()
     */
    @Override
    public List<MysqlCluster> getAllClusters()
    {
        List<MysqlCluster> list = mysqlClusterMapper.selectByExample(new MysqlClusterExample());
        return list;
    }
    @Override
    public ClusterNetworkDto getClusterNetworkById(Long clusterId)
    {
        ClusterNetworkDto clusterNetworkDto=new ClusterNetworkDto();
        MysqlServerExample mysqlServerExample=new MysqlServerExample();
        mysqlServerExample.createCriteria().andClusterIdEqualTo(clusterId);
        List<MysqlServer> mysqlServers = mysqlServerMapper.selectByExample(mysqlServerExample);
        clusterNetworkDto.setMysqlServers(mysqlServers);
        List<Relation> relations=getRelations(mysqlServers);
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
    private List<Relation> getRelations(List<MysqlServer> mysqlServers){
        String showSlaveHostsSql="show slave hosts";
        String showVariablesSql="show variables like 'server_id'";
        String showSlaveStatusSql="show slave status";
        List<Relation> relations=new ArrayList<>();
        for (MysqlServer fromMysqlServer : mysqlServers)
        {
            QueryResult<List<Map<Object,Object>>> queryResultSlaveHosts = getQueryResult(fromMysqlServer.getId(), showSlaveHostsSql);
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
                for (MysqlServer toMysqlServer : mysqlServers)
                {
                    QueryResult<List<Map<Object, Object>>> queryResultVariables = getQueryResult(toMysqlServer.getId(), showVariablesSql);
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
                        QueryResult<List<Map<Object,Object>>> queryResultSlaveStatus = getQueryResult(toMysqlServer.getId(), showSlaveStatusSql);
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
                        Relation relation=new Relation(fromMysqlServer, toMysqlServer,slaveStatus);
                        relations.add(relation);
                        break;
                    }
                }
            }
        }
        return relations;
    }
    @Override
    public RestResponse<String> saveCluster(Long id, String name)
    {
        RestResponse<String> restResponse=new RestResponse<>();
        MysqlCluster mysqlCluster=new MysqlCluster();
        try
        {
            //如果id为0，则为插入
            if (id== Constant.ZERO)
            {
                mysqlCluster.setCreateTime(new Date());
                mysqlCluster.setName(name);
                mysqlClusterMapper.insert(mysqlCluster);
            }
            //如果id不为0，则为编辑
            else{
                mysqlCluster.setCreateTime(new Date());
                mysqlCluster.setName(name);
                mysqlCluster.setId(id);
                mysqlClusterMapper.updateByPrimaryKey(mysqlCluster);
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
    @Override
    public RestResponse<String> deleteCluster(Long id)
    {
        RestResponse<String> restResponse=new RestResponse<>();
        try
        {
            mysqlClusterMapper.deleteByPrimaryKey(id);
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
    
}
