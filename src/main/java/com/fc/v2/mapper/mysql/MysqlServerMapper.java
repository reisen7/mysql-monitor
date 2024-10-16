package com.fc.v2.mapper.mysql;

import com.fc.v2.model.mysql.MysqlServer;
import com.fc.v2.model.mysql.MysqlServerExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MysqlServerMapper {
    int countByExample(MysqlServerExample example);

    int deleteByExample(MysqlServerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MysqlServer record);

    int insertSelective(MysqlServer record);

    List<MysqlServer> selectByExample(MysqlServerExample example);

    MysqlServer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MysqlServer record, @Param("example") MysqlServerExample example);

    int updateByExample(@Param("record") MysqlServer record, @Param("example") MysqlServerExample example);

    int updateByPrimaryKeySelective(MysqlServer record);

    int updateByPrimaryKey(MysqlServer record);
}