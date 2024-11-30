package com.fc.v2.mapper.mysql;

import com.fc.v2.model.monitor.MonitorCluster;
import com.fc.v2.model.monitor.MonitorClusterExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库集群 MonitorClusterMapper
 * @author fuce_自动生成
 * @Description
 * @date 2024-10-21 10:58:36
 */
@Mapper
public interface MonitorClusterMapper {

    long countByExample(MonitorClusterExample example);

    int deleteByExample(MonitorClusterExample example);
	
    int deleteByPrimaryKey(Long id);
	
    int insert(MonitorCluster record);

    int insertSelective(MonitorCluster record);

    List<MonitorCluster> selectByExample(MonitorClusterExample example);
	
    MonitorCluster selectByPrimaryKey(Long id);
	
    int updateByExampleSelective(@Param("record") MonitorCluster record, @Param("example") MonitorClusterExample example);

    int updateByExample(@Param("record") MonitorCluster record, @Param("example") MonitorClusterExample example); 
	
    int updateByPrimaryKeySelective(MonitorCluster record);

    int updateByPrimaryKey(MonitorCluster record);
  	
}