package com.fc.v2.service.monitor.impl;

import com.fc.v2.dto.QueryResult;
import com.fc.v2.dto.RestResponse;
import com.fc.v2.mapper.mysql.MonitorServerMapper;
import com.fc.v2.model.monitor.MonitorServer;
import com.fc.v2.model.monitor.MonitorServerExample;
import com.fc.v2.model.mysql.Constant;
import com.fc.v2.service.monitor.JdbcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AbstractService
 * @Description
 * @author: reisen
 * @date: 2024-10-16
 * 
 */
public class AbstractService
{
    // 定义Logger
    protected static Logger logger = null;
    
    // 默认获取记录数
    protected static final Integer DEFAULT_LIMIT = 10;
    
    // 内置数据库
    protected static final String BUILT_IN_SCHEMA = "'information_schema','mysql','performance_schema','sys','test'";
    
    protected static final String MIN_SELECTIVITY="0.1";
    // 数据库操作服务
    @Autowired
    protected JdbcService jdbcService;

//    private String version = "8";
//
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }

    // MySQL服务器Mapper
    @Autowired
    protected MonitorServerMapper monitorServerMapper;
    
    public AbstractService()
    {
        logger = LoggerFactory.getLogger(getClass().getName());
    }
    
    /**
     * 获取MySQL Server列表 @Title: getMonitorServerList @return @throws
     */
    protected List<MonitorServer> getMonitorServers()
    {
        MonitorServerExample example = new MonitorServerExample();
        List<MonitorServer> monitorServers = monitorServerMapper.selectByExample(example);
        
        return monitorServers;
    }
    
    /**
     * 获取数据源URL @Title: getDataSourceUrl @param host @param port @param
     * schema @param username @param password @return @throws
     */
    protected String getDataSourceUrl(String host, String port, String username, String password)
    {
       // String url =
       //     "jdbc:mysql://" + host + ":" + port + "/?user=" + username + "&password=" + password + "&useSSL=false";
        String url ="jdbc:mysql://" + host + ":" + port + "/?useSSL=false";

        return url;
    }
    
    protected String getDataSourceUrl(String host, String port, String schema, String username, String password)
    {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + schema + "?useSSL=false";
        return url;
    }

    protected String getDataSourceUrl(String host, String port, String schema, String username, String password, String version)
    {
        return "jdbc:mysql://" + host + ":" + port + "/" +(schema!=null?schema:"") + "?useSSL=false&useUnicode=true&characterEncoding=utf-8" +((version.equals("8")) ?"&serverTimezone=GMT":"");
    }
    
    /**
     * 检查返回结果中，是否存在数据操作异常信息 @Title: isJdbcException @param list @return @throws
     */
    protected boolean isJdbcException(List<Map<Object, Object>> list)
    {
        boolean isException = false;
        
        // 如果有数据操作异常，则返回
        for (Map<Object, Object> map : list)
        {
            if (map.containsKey("exception"))
            {
                isException = true;
                
                break;
            }
        }
        
        return isException;
    }
    
    protected QueryResult<List<Map<Object, Object>>> getQueryResult(String host, String port, String sql,
                                                                    String username, String password, String version)  {
        String url = getDataSourceUrl(host, port, "", username, password, version);
        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        return queryResult;
    }
    
    protected Integer getQueryCount(String host, String port, String sql, String username, String password, String version)
    {
        String url = getDataSourceUrl(host, port,"", username, password, version);
        QueryResult<Integer> countQueryResult = jdbcService.queryForCount(url, sql,username,password, version);
        return countQueryResult.getData();
    }
    
    protected QueryResult<List<Map<Object, Object>>> getQueryResult(Long serverId, String sql)
    {
        MonitorServer monitorServer = getMonitorServerById(serverId);
        String host = monitorServer.getHost();
        String port = String.valueOf(monitorServer.getPort());
        String username = monitorServer.getUsername();
        String password = monitorServer.getPassword();
        String version = monitorServer.getVersion();
        String url = getDataSourceUrl(host, port, "", username, password, version);


        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        return queryResult;
    }
    
    protected QueryResult<List<Map<Object, Object>>> getQueryResult(Long serverId, String schema, String sql)
    {
        MonitorServer monitorServer = getMonitorServerById(serverId);
        String host = monitorServer.getHost();
        String port = String.valueOf(monitorServer.getPort());
        String username = monitorServer.getUsername();
        String password = monitorServer.getPassword();
        String version = monitorServer.getVersion();
        String url = getDataSourceUrl(host, port, schema, username, password, version);
        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        return queryResult;
    }
    /**
     * 获取指定MySQL节点上执行sql的结果集
     * @Title: getRestResponse   
     * @param serverId
     * @param sql
     * @return        
     * @throws
     */
    protected RestResponse<Object> getRestResponseBySql(Long serverId, String sql)
    {
        RestResponse<Object> restResponse = new RestResponse<>();
        QueryResult<List<Map<Object, Object>>> queryResult = getQueryResult(serverId, sql);
        //如果数据操作失败则返回
        if (!queryResult.isSuccess())
        {
            restResponse.setCode(Constant.JDBC_ERROR_CODE);
            restResponse.setMessage(queryResult.getException());
            return restResponse;
        }
        List<Map<Object, Object>> list = queryResult.getData();
        if (!list.isEmpty())
        {
            restResponse.setCode(Constant.SUCCESS_CODE);
            restResponse.setMessage(Constant.SUCCESS_MESSAGE);
            restResponse.setData(list);
        }
        else
        {
            restResponse.setCode(Constant.EMPTY_CODE);
            restResponse.setMessage(Constant.EMPTY_MESSAGE);
        }
        return restResponse;
    }
    
    public QueryResult<List<Map<Object, Object>>> getQueryResult(String host, String port, String schema, String sql,
        String username, String password, String version)
    {
        String url = getDataSourceUrl(host, port, schema, username, password,version);
        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        return queryResult;
    }
    
    private MonitorServer getMonitorServerById(Long id)
    {
        MonitorServer monitorServer = monitorServerMapper.selectByPrimaryKey(id);
        return monitorServer;
    }
    
    protected  RestResponse<Object> executeSqlForList(Long serverId, String sql) {
        RestResponse<Object> restResponse = new RestResponse<Object>();
        // 获取MySQL服务器信息
        MonitorServer monitorServer = monitorServerMapper.selectByPrimaryKey(serverId);
        String host = monitorServer.getHost();
        String port = String.valueOf(monitorServer.getPort());
        String username = monitorServer.getUsername();
        String password = monitorServer.getPassword();
        String version = monitorServer.getVersion();
        String url = "jdbc:mysql://" + host + ":" + port + "/" + "?useSSL=false";
        url = getDataSourceUrl(host, port,"", username, password, version);
        QueryResult<List<Map<Object, Object>>> queryResult = jdbcService.queryForList(url, sql, username, password, version);
        if (queryResult.isSuccess() == false) {
            restResponse.setCode(1);
            restResponse.setMessage(queryResult.getException());
            return restResponse;
        }
        restResponse.setCode(Constant.SUCCESS_CODE);
        restResponse.setMessage("SUCCESS");
        restResponse.setData(queryResult.getData());
        return restResponse;
    }
    
    public  RestResponse<Object> executeSqlForBoolean(Long serverId, String sql) {
        RestResponse<Object> restResponse = new RestResponse<Object>();
        // 获取MySQL服务器信息
        MonitorServer monitorServer = monitorServerMapper.selectByPrimaryKey(serverId);
        String host = monitorServer.getHost();
        String port = String.valueOf(monitorServer.getPort());
        String username = monitorServer.getUsername();
        String password = monitorServer.getPassword();
        String version = monitorServer.getVersion();
        //String url = "jdbc:mysql://" + host + ":" + port + "/" + "?user=" + username + "&password=" + password
        //        + "&useSSL=false";
        String url = "jdbc:mysql://" + host + ":" + port + "/?useSSL=false";
        url = getDataSourceUrl(host, port,"", username, password, version);
        QueryResult<Integer> queryResult = jdbcService.executeSqlForBoolean(url,sql,username,password, version);
        if (queryResult.isSuccess() == false) {
            restResponse.setCode(Constant.FAIL_CODE);
            restResponse.setMessage(queryResult.getException());
            return restResponse;
        }
        restResponse.setCode(Constant.SUCCESS_CODE);
        restResponse.setMessage("SUCCESS");
        restResponse.setData(queryResult.getData());
        return restResponse;
    }



}
