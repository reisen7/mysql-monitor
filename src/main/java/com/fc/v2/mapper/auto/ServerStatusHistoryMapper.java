package com.fc.v2.mapper.auto;

import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库状态历史表 ServerStatusHistoryMapper
 * @author reisen_自动生成
 * @Description
 * @date 2024-10-24 11:19:41
 */
public interface ServerStatusHistoryMapper {

    long countByExample(ServerStatusHistoryExample example);

    int deleteByExample(ServerStatusHistoryExample example);
	
    int deleteByPrimaryKey(Long id);
	
    int insert(ServerStatusHistory record);

    int insertSelective(ServerStatusHistory record);

    List<ServerStatusHistory> selectByExample(ServerStatusHistoryExample example);
	
    ServerStatusHistory selectByPrimaryKey(Long id);
	
    int updateByExampleSelective(@Param("record") ServerStatusHistory record, @Param("example") ServerStatusHistoryExample example);

    int updateByExample(@Param("record") ServerStatusHistory record, @Param("example") ServerStatusHistoryExample example); 
	
    int updateByPrimaryKeySelective(ServerStatusHistory record);

    int updateByPrimaryKey(ServerStatusHistory record);
  	
}