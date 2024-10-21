package com.fc.v2.service.monitor;

import com.fc.v2.dto.QueryResult;

import java.util.List;
import java.util.Map;

/**   
 * @ClassName:  JdbcService   
 * @Description
 * @author: reisen
 * @date:   2024-10-16 18:20:36
 *     
 */
public interface JdbcService {
	/**
	 * @param user
	 * @param password
	 * 查询数据集
	 * @Title: queryForList   
	 * @param url
	 * @param sql
	 * @return        
	 * @throws
	 */
	public QueryResult<List<Map<Object, Object>>> queryForList(String url, String sql, String user, String password, String version);
	/**
	 * 根据SQL查询记录数
	 * @Title: queryForCount   
	 * @param url
	 * @param sql
	 * @return        
	 * @throws
	 */
	public QueryResult<Integer> queryForCount(String url, String sql, String user, String password, String version);


	/**
	 * 执行sql，返回是否执行成功
	 * @param url
	 * @param sql
	 * @return
	 */
	public QueryResult<Integer> executeSqlForBoolean(String url, String sql, String user, String password, String version);
	
	/**
	 * 执行SQL
	 * @Title: executeSql   
	 * @param url
	 * @param sql        
	 * @throws
	 */
	public void executeSql(String url, String sql, String user, String password, String version);

}
