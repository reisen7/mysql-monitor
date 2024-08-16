package com.fc.v2.mapper.auto;

import com.fc.v2.model.auto.MonCluster;
import com.fc.v2.model.auto.MonClusterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 数据库集群 MonClusterMapper
 * @author fuce_自动生成
 * @email ${email}
 * @date 2024-08-16 18:20:36
 */
public interface MonClusterMapper {

    long countByExample(MonClusterExample example);

    int deleteByExample(MonClusterExample example);
	
    int deleteByPrimaryKey(Integer id);
	
    int insert(MonCluster record);

    int insertSelective(MonCluster record);

    List<MonCluster> selectByExample(MonClusterExample example);
	
    MonCluster selectByPrimaryKey(Integer id);
	
    int updateByExampleSelective(@Param("record") MonCluster record, @Param("example") MonClusterExample example);

    int updateByExample(@Param("record") MonCluster record, @Param("example") MonClusterExample example); 
	
    int updateByPrimaryKeySelective(MonCluster record);

    int updateByPrimaryKey(MonCluster record);
  	
}