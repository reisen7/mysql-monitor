package com.fc.v2.mapper.mysql;

import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.model.monitor.MonitorServerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库连接 MonitorServerMapper
 * @author reisen_自动生成
 * @Description
 * @date 2024-10-21 10:57:44
 */
public interface MonitorServerMapper {

    long countByExample(MonitorServerExample example);

    int deleteByExample(MonitorServerExample example);
	
    int deleteByPrimaryKey(Long id);
	
    int insert(MonitorServer record);

    int insertSelective(MonitorServer record);

    List<MonitorServer> selectByExample(MonitorServerExample example);
	
    MonitorServer selectByPrimaryKey(Long id);
	
    int updateByExampleSelective(@Param("record") MonitorServer record, @Param("example") MonitorServerExample example);

    int updateByExample(@Param("record") MonitorServer record, @Param("example") MonitorServerExample example); 
	
    int updateByPrimaryKeySelective(MonitorServer record);

    int updateByPrimaryKey(MonitorServer record);
  	
}