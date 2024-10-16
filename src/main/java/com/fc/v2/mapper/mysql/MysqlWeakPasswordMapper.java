package com.fc.v2.mapper.mysql;


import com.fc.v2.model.mysql.MysqlWeakPassword;
import com.fc.v2.model.mysql.MysqlWeakPasswordExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MysqlWeakPasswordMapper {
    int countByExample(MysqlWeakPasswordExample example);

    int deleteByExample(MysqlWeakPasswordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MysqlWeakPassword record);

    int insertSelective(MysqlWeakPassword record);

    List<MysqlWeakPassword> selectByExample(MysqlWeakPasswordExample example);

    MysqlWeakPassword selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MysqlWeakPassword record, @Param("example") MysqlWeakPasswordExample example);

    int updateByExample(@Param("record") MysqlWeakPassword record, @Param("example") MysqlWeakPasswordExample example);

    int updateByPrimaryKeySelective(MysqlWeakPassword record);

    int updateByPrimaryKey(MysqlWeakPassword record);
}