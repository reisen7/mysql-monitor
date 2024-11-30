package com.fc.v2.mapper.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.v2.model.monitor.ServerStatusHistory;
import com.fc.v2.model.monitor.ServerStatusHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库状态历史表 ServerStatusHistoryMapper
 * @author fuce_自动生成
 * @Description
 * @date 2024-11-04 15:20:27
 */
public interface ServerStatusHistoryMapper extends BaseMapper<ServerStatusHistory> {

    long countByExample(ServerStatusHistoryExample example);

    int deleteByExample(ServerStatusHistoryExample example);
	
    int deleteByPrimaryKey(String id);
	
    int insert(ServerStatusHistory record);

    int insertSelective(ServerStatusHistory record);

    List<ServerStatusHistory> selectByExample(ServerStatusHistoryExample example);
	
    ServerStatusHistory selectByPrimaryKey(String id);
	
    int updateByExampleSelective(@Param("record") ServerStatusHistory record, @Param("example") ServerStatusHistoryExample example);

    int updateByExample(@Param("record") ServerStatusHistory record, @Param("example") ServerStatusHistoryExample example); 
	
    int updateByPrimaryKeySelective(ServerStatusHistory record);

    int updateByPrimaryKey(ServerStatusHistory record);
  	
}