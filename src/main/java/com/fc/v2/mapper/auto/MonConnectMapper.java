package com.fc.v2.mapper.auto;

import com.fc.v2.model.monitor.MonConnect;
import com.fc.v2.model.monitor.MonConnectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库连接 MonConnectMapper
 * @author reisen_自动生成
 * @email ${email}
 * @date 2024-08-16 17:43:36
 */
public interface MonConnectMapper {

    long countByExample(MonConnectExample example);

    int deleteByExample(MonConnectExample example);
	
    int deleteByPrimaryKey(Integer id);
	
    int insert(MonConnect record);

    int insertSelective(MonConnect record);

    List<MonConnect> selectByExample(MonConnectExample example);
	
    MonConnect selectByPrimaryKey(Integer id);
	
    int updateByExampleSelective(@Param("record") MonConnect record, @Param("example") MonConnectExample example);

    int updateByExample(@Param("record") MonConnect record, @Param("example") MonConnectExample example); 
	
    int updateByPrimaryKeySelective(MonConnect record);

    int updateByPrimaryKey(MonConnect record);
  	
}